package app.registerVisitor.service;

import app.common.assembler.GenericAssembler;
import app.email.EmailSender;
import app.person.model.Enums.PersonType;
import app.person.model.entity.Person;
import app.person.model.vo.PersonVO;
import app.person.repository.PersonRepository;
import app.registerVisitor.model.DirectionType;
import app.registerVisitor.model.RegisterVisitor;
import app.registerVisitor.model.RegisterVisitorByVisitCardWrapper;
import app.registerVisitor.model.RegisterVisitorVO;
import app.registerVisitor.repository.RegisterVisitorRepository;
import app.registerVisitor.repository.RegisterVisitorSearchRepository;
import app.registerVisitor.validator.RegisterVisitorValidator;
import app.visitCard.model.VisitCard;
import app.visitCard.model.vo.VisitCardVO;
import app.visitCard.repository.VisitCardRepository;
import app.visitCard.service.VisitCardCreator;
import app.visitRegister.model.TypeShowData;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by matczy on 14.03.16.
 */
@Service
public class RegisterVisitorServiceImpl implements RegisterVisitorService {

    private static final String AUTO_COMMENT = "Auto register, wrong visitor localization state";
    private RegisterVisitorRepository registerVisitorRepository;
    private RegisterVisitorValidator registerVisitorValidator;
    private VisitCardCreator visitCardCreator;
    private GenericAssembler<Person,PersonVO> personAssembler;
    private PersonRepository personRepository;
    private RegisterVisitorCreator registerVisitorCreator;
    private VisitCardRepository visitCardRepository;
    private RegisterVisitorSearchRepository registerVisitorSearchRepository;
    private EmailSender emailSender;


    @Autowired
    public RegisterVisitorServiceImpl(RegisterVisitorRepository registerVisitorRepository, RegisterVisitorValidator registerVisitorValidator, VisitCardCreator visitCardCreator, GenericAssembler<Person,PersonVO> personAssembler, PersonRepository personRepository, RegisterVisitorCreator registerVisitorCreator, VisitCardRepository visitCardRepository, RegisterVisitorSearchRepository registerVisitorSearchRepository, EmailSender emailSender) {
        this.registerVisitorRepository = registerVisitorRepository;
        this.registerVisitorValidator = registerVisitorValidator;
        this.visitCardCreator = visitCardCreator;
        this.personAssembler = personAssembler;
        this.personRepository = personRepository;
        this.registerVisitorCreator = registerVisitorCreator;
        this.visitCardRepository = visitCardRepository;
        this.registerVisitorSearchRepository = registerVisitorSearchRepository;
        this.emailSender = emailSender;
    }

    @Override
    public boolean isPersonInside(Long id) {
        return registerVisitorRepository.findByEntryDateNotNullAndExitDateNullAndPersonId(id).isPresent();
    }

    @Override
    @Transactional
    public RegisterVisitor registerVisitorAction(RegisterVisitorVO registerVisitorVO) throws Exception {

        Person person = findOrCreatePerson(registerVisitorVO.getPerson());
        if (!registerVisitorValidator.checkPersonIsOnCorrectSide(person.getId(), registerVisitorVO.getDirection())) {
            autoSaveRegisterVisitor(registerVisitorVO.getDirection(), person.getId());
        }
        RegisterVisitor registerVisitor = saveRegisterVisitor(registerVisitorVO,person);
        sendMailConfirmation(registerVisitor);

        return registerVisitor;

    }

    @Override
    @Transactional
    public RegisterVisitor registerVisitorAction(RegisterVisitorByVisitCardWrapper registerVisitorByVisitCardWrapper) throws Exception {
        if(DirectionType.ENTRY ==registerVisitorByVisitCardWrapper.getDirection() && !registerVisitorByVisitCardWrapper.getVisitCard().getActive()){
            throw new Exception("Przepustka o numerze "+registerVisitorByVisitCardWrapper.getVisitCard().getId()+" nie aktywna");
        }else{
            PersonVO personVO = registerVisitorByVisitCardWrapper.getVisitCard().getPerson();
            DirectionType directionType = registerVisitorByVisitCardWrapper.getDirection();
            if (!registerVisitorValidator.checkPersonIsOnCorrectSide(personVO.getId(), directionType)) {
                autoSaveRegisterVisitorByVisitCard(directionType, registerVisitorByVisitCardWrapper.getVisitCard());
            }
            return saveRegisterVisitorByVisitCard(registerVisitorByVisitCardWrapper.getVisitCard(),directionType);
        }


    }



    private Person findOrCreatePerson(PersonVO personVO){
        Person person;
        if(personVO.getId()!=null){
            person = personRepository.findOne(personVO.getId());
        }else{
            person = personRepository.findBySurnameAndNameAndDocumentIdentifierAndActiveTrue(personVO.getSurname(), personVO.getName(), personVO.getDocumentIdentifier());
            if(person==null){
                person = personAssembler.toEntity(personVO);
            }

            personRepository.save(person);
            personRepository.flush();
        }
        return person;
    }


    //Auto save when person localization is in bad state
    private RegisterVisitor autoSaveRegisterVisitor(DirectionType directionType, Long personId) throws Exception {
        RegisterVisitor registerVisitor;
        if(DirectionType.ENTRY == directionType){//chcesz wejsc ale system mowi ze juz jestes czyli musi Cie automatycznie wypisac
            registerVisitor = registerVisitorRepository.findByPersonIdAndEntryDateNotNullAndExitDateNull(personId);
            registerVisitor.setDirection(DirectionType.EXIT);
            registerVisitor.setExitDate(DateTime.now());
            registerVisitor.setActive(false);
            registerVisitor.setComment(AUTO_COMMENT);
            registerVisitorRepository.save(registerVisitor);
            registerVisitorRepository.flush();
            return registerVisitor;

        }else{
            throw new Exception("nie mozna wyjsc nie badac zapisanym");
        }


    }
    private RegisterVisitor autoSaveRegisterVisitorByVisitCard(DirectionType directionType, VisitCardVO visitCardVO) {
        RegisterVisitor registerVisitor;
        if(DirectionType.ENTRY == directionType){//chcesz wejsc ale system mowi ze juz jestes czyli musi Cie automatycznie wypisac
            registerVisitor = registerVisitorRepository.findByPersonIdAndEntryDateNotNullAndExitDateNull(visitCardVO.getPerson().getId());
            registerVisitor.setDirection(DirectionType.EXIT);
            registerVisitor.setExitDate(DateTime.now());
            registerVisitor.setActive(false);
            registerVisitor.setComment(AUTO_COMMENT);
        }else{
            registerVisitor = registerVisitorCreator.create(visitCardVO,DirectionType.ENTRY);
            if(visitCardVO.getActive()){
                registerVisitor.setComment(AUTO_COMMENT);
            }else{
                registerVisitor.setComment("Przepustka nie aktywna");
            }
        }
        registerVisitorRepository.save(registerVisitor);
        registerVisitorRepository.flush();

        return registerVisitor;
    }

    private RegisterVisitor saveRegisterVisitor(RegisterVisitorVO registerVisitorVO, Person person) {
        RegisterVisitor registerVisitor;
        if(DirectionType.ENTRY == registerVisitorVO.getDirection()){
            registerVisitor = createEntryRegisterVisitor(registerVisitorVO, person);
        }else{
            registerVisitor = registerVisitorRepository.findByPersonIdAndEntryDateNotNullAndExitDateNull(person.getId());
            registerVisitor.setExitDate(DateTime.now());
            registerVisitor.setActive(false);
        }

        registerVisitorRepository.save(registerVisitor);

        return registerVisitor;
    }

    private void sendMailConfirmation(RegisterVisitor registerVisitor){
        if(registerVisitor.getDirection() ==DirectionType.ENTRY && registerVisitor.getContactPerson()!=null && StringUtils.isNoneBlank(registerVisitor.getContactPerson().getEmail() )&& registerVisitor.getContactPerson().getConfirmEmailAvailable())
        {
            emailSender.sendMessage(registerVisitor.getContactPerson().getEmail(),"Powiadomienie",
                    "Osoba "+registerVisitor.getPerson().getName() +" "+registerVisitor.getPerson().getSurname()+ "tel: "+registerVisitor.getPerson().getPhone()+" oczekuje w recepcji.");
        }

    }

    private RegisterVisitor saveRegisterVisitorByVisitCard(VisitCardVO visitCard, DirectionType directionType) {
        RegisterVisitor registerVisitor;
        if(DirectionType.ENTRY == directionType){
            registerVisitor = registerVisitorCreator.create(visitCard, directionType);

        }else{
            registerVisitor = registerVisitorRepository.findByPersonIdAndEntryDateNotNullAndExitDateNull(visitCard.getPerson().getId());
            registerVisitor.setExitDate(DateTime.now());
            registerVisitor.setActive(false);
        }

        registerVisitorRepository.save(registerVisitor);

        return registerVisitor;
    }

    private RegisterVisitor createEntryRegisterVisitor(RegisterVisitorVO registerVisitorVO, Person person) {
        RegisterVisitor registerVisitor = registerVisitorCreator.create(registerVisitorVO, person);
        VisitCard visitCard =visitCardCreator.create(person, registerVisitor.getContactPerson(), registerVisitorVO.getVisitCard().getDateFrom(),registerVisitorVO.getVisitCard().getDateTo());
        visitCardRepository.saveAndFlush(visitCard);
        if (PersonType.VISITOR == visitCard.getPerson().getType()) {
            visitCard.setNumber("VV" + visitCard.getId());
        } else {
            visitCard.setNumber("VW" + visitCard.getId());
        }
        visitCardRepository.saveAndFlush(visitCard);

        registerVisitor.setVisitCard(visitCard);

        return registerVisitor;
    }



    @Override
    @Transactional
    public RegisterVisitor registerVisitorExitAction(Long id) {
        RegisterVisitor registerVisitor = registerVisitorRepository.findOne(id);
        if (registerVisitorValidator.checkPersonIsOnCorrectSide(registerVisitor.getPerson().getId(), DirectionType.EXIT)) {
            registerVisitor.setExitDate(DateTime.now());
            registerVisitor.setActive(false);
            registerVisitorRepository.save(registerVisitor);
        }
         return registerVisitor;
    }




    @Override
    @javax.transaction.Transactional
    public Page filterData(Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception {
        Pageable pageable = new PageRequest(page,resultOnPage);
        if(searchText.isPresent() ){
            return registerVisitorSearchRepository.findBySearchText(searchText.get(), typeShowData, pageable);
        }else{
           return  registerVisitorSearchRepository.findBySearchText(org.apache.commons.lang3.StringUtils.EMPTY, typeShowData, pageable);
        }
    }

    @Override
    @javax.transaction.Transactional
    public List<RegisterVisitor> registerMultiVisitorAction(List<RegisterVisitorVO> registerVisitorsVO){
        List<RegisterVisitor> results =  Lists.newArrayList();
        registerVisitorsVO.forEach(visitor -> {
            Person person = findOrCreatePerson(visitor.getPerson());
            if (!registerVisitorValidator.checkPersonIsOnCorrectSide(person.getId(), visitor.getDirection())) {
                try {
                    autoSaveRegisterVisitor(visitor.getDirection(), person.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            results.add(saveRegisterVisitor(visitor,person));
        });
        sendMailConfirmation(results);

        return results;


    }

    //TODO do przemyslenia
    private void sendMailConfirmation(List<RegisterVisitor> registerVisitors){
            Map<String,List<RegisterVisitor>> emailGroup  = registerVisitors.stream()
                    .filter(registerVisitor -> registerVisitor.getDirection()==DirectionType.ENTRY)
                    .filter(registerVisitor-> registerVisitor.getContactPerson()!=null)
                    .filter(registerVisitor-> registerVisitor.getContactPerson().getConfirmEmailAvailable())
                    .collect(Collectors.groupingBy(registerVisitor -> registerVisitor.getContactPerson().getEmail()));
            Set<String> emails = emailGroup.keySet();

            if(emails.size()>0)
            {
                emails.stream().filter(StringUtils::isNotBlank).forEach(email -> {
                    String text = emailGroup.get(email).stream().map(registerVisitor -> registerVisitor.getPerson().getName() + " " + registerVisitor.getPerson().getSurname() + "tel: " + registerVisitor.getPerson().getPhone()).collect(Collectors.joining(", "));
                    text = text.replaceAll("null", "-");
                    emailSender.sendMessage(email, "Powiadomienie", "Osoby "+text+" oczekują w recepcji");
                });
            }
    }

    @Override
    public Long countAllVisitors() {
        return registerVisitorRepository.count();
    }


    @Override
    public Long countAllVisitorsOnObject() {
        return registerVisitorRepository.countByActiveTrue();
    }

    @Override
    public Long countTodayVisits() {
        return registerVisitorRepository.countByCreatedDateBetween(DateTime.now().withTimeAtStartOfDay() , DateTime.now());
    }

    @Override
    public List countLast30DaysVisits() {
        return registerVisitorRepository.countLast30DaysVisits( DateTime.now().minusDays(29).withTimeAtStartOfDay(),DateTime.now().plusDays(1).withTimeAtStartOfDay());
    }

    @Override
    public List countLastDayByHourVisits() {
        return registerVisitorRepository.countLastDayByHourVisits(DateTime.now().minusDays(1), DateTime.now());
    }

    @Override
    public List<String> getLast30DaysLabel() {
        List<String> dates = Lists.newLinkedList();
        DateTime startDate = DateTime.now().minusDays(29).withTimeAtStartOfDay();
        int days = Days.daysBetween(startDate,DateTime.now().withTimeAtStartOfDay()).getDays();

        for(int i = 0; i <= days; i++) {
            dates.add(startDate.plusDays(i).toString().substring(0,10));
        }

        return dates;
    }

    @Override
    public List<String> getLastDayByHourLabel() {
        List<String> dates = Lists.newLinkedList();
        DateTime startDate = DateTime.now().minusDays(1);
        for(int i = 0; i <24; i++) {
            dates.add(startDate.plusHours(1).toString().substring(11,13).replace('T',' ')+":00");
            startDate = startDate.plusHours(1);
        }

        return dates;
    }

}
