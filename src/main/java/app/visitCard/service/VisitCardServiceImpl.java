package app.visitCard.service;

import app.common.assembler.GenericAssembler;
import app.login.SecurityUtils;
import app.person.model.Enums.PersonType;
import app.person.model.entity.Person;
import app.person.model.vo.PersonVO;
import app.visitCard.model.VisitCard;
import app.visitCard.model.vo.ManuallyPrintVisitCardVO;
import app.visitCard.model.vo.VisitCardVO;
import app.visitCard.repository.Specyfications.VisitCardSpecification;
import app.visitCard.repository.VisitCardRepository;
import app.visitCard.repository.VisitCardSearchRepository;
import app.visitRegister.model.TypeShowData;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static app.common.TimeUtils.checkIsActive;


@Service
public class VisitCardServiceImpl implements VisitCardService {

    private VisitCardRepository visitCardRepository;

    private VisitCardCreator visitCardCreator;

    private GenericAssembler<Person,PersonVO> personAssembler;

    private VisitCardSearchRepository visitCardSearchRepository;

    @Autowired
    public VisitCardServiceImpl(VisitCardRepository visitCardRepository, VisitCardCreator visitCardCreator, GenericAssembler<Person,PersonVO>  personAssembler, VisitCardSearchRepository visitCardSearchRepository) {
        this.visitCardRepository = visitCardRepository;
        this.visitCardCreator = visitCardCreator;
        this.personAssembler = personAssembler;
        this.visitCardSearchRepository = visitCardSearchRepository;
    }

    @Transactional
    @Override
    public List<VisitCard> findAllActiveForPerson(Long id) {
        return visitCardRepository.findAll(VisitCardSpecification.findAllActiveForPerson(id));
    }


    @Override
    @Transactional
    public void disableAllVisitCardForPerson(Long personId) {
        List<VisitCard> visitCardListOptional = findAllActiveForPerson(personId);
        visitCardListOptional.stream().forEach(visitCard -> visitCard.setActive(false));
    }

    @Override
    @Transactional
    public VisitCard createVisitCard(ManuallyPrintVisitCardVO manuallyPrintVisitCardVO) {
        VisitCard visitCard = visitCardCreator.create(personAssembler.toEntity(manuallyPrintVisitCardVO.getPerson()), personAssembler.toEntity(manuallyPrintVisitCardVO.getContactPerson()), manuallyPrintVisitCardVO.getNumberOfDays());
        visitCardRepository.saveAndFlush(visitCard);
        if (PersonType.VISITOR == visitCard.getPerson().getType()) {
            visitCard.setNumber("VV" + visitCard.getId());
        } else {
            visitCard.setNumber("VW" + visitCard.getId());
        }
        visitCardRepository.saveAndFlush(visitCard);
        return visitCard;
    }

    @Override
    public VisitCard createPeriodVisitCard(VisitCardVO visitCardVO) {
        VisitCard visitCard = visitCardCreator.create(visitCardVO);
        visitCardRepository.saveAndFlush(visitCard);
        if (PersonType.VISITOR == visitCard.getPerson().getType()) {
            visitCard.setNumber("VV" + visitCard.getId());
        } else {
            visitCard.setNumber("VW" + visitCard.getId());
        }
        visitCardRepository.saveAndFlush(visitCard);
        return visitCard;
    }



    @Override
    @Transactional
    public void updateVisitCardStatus() {
        List<VisitCard> visitCards = visitCardRepository.findByDateFromAfterAndManuallyDeactivatedFalse(DateTime.now().minusDays(1).withTimeAtStartOfDay());
        visitCards.addAll(visitCardRepository.findByDateToBeforeAndManuallyDeactivatedFalse(DateTime.now().plusDays(1).withTimeAtStartOfDay()));

        visitCards.stream().forEach(e -> {
                    if (checkIsActive(e.getDateFrom(), e.getDateTo())) {
                        if (!e.getActive()) {
                            e.setActive(true);
                        }
                    } else {
                        if (e.getActive()) {
                            e.setActive(false);
                        }
                    }
                    visitCardRepository.saveAndFlush(e);
                    if (PersonType.VISITOR == e.getPerson().getType()) {
                        e.setNumber("VV" + e.getId());
                    } else {
                        e.setNumber("VW" + e.getId());
                    }
                    visitCardRepository.saveAndFlush(e);

                }
        );
    }


    @Override
    @Transactional
    public VisitCard deactivate(Long id) {
        VisitCard visitCard = visitCardRepository.getOne(id);
        visitCard.setActive(false);
        visitCard.setManuallyDeactivated(true);
        visitCard.setLastModifiedBy(SecurityUtils.getCurrentLogin());
        visitCard.setLastModifiedDate(DateTime.now());
        visitCardRepository.save(visitCard);

        return visitCard;
    }

    @Override
    @Transactional
    public Page filterData(Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception {
        Pageable pageable = new PageRequest(page, resultOnPage);

        if (searchText.isPresent()) {
            return visitCardSearchRepository.findBySearchText(searchText.get(), typeShowData, pageable);
        } else {
            return visitCardSearchRepository.findBySearchText(org.apache.commons.lang3.StringUtils.EMPTY, typeShowData, pageable);
        }
    }

    @Override
    public Long countActiveVisitCard() {
        return visitCardRepository.countByActiveTrue();
    }

    @Override
    public List<VisitCard> filterData(Optional<String> searchText) {
        if (searchText.isPresent()) {
            return visitCardSearchRepository.findBySearchText(searchText.get());
        } else {
            return visitCardSearchRepository.findBySearchText(org.apache.commons.lang3.StringUtils.EMPTY);
        }
    }

    @Override
    public VisitCard findByNumber(Optional<String> searchText) {
        if (searchText.isPresent()) {
            try {
                return visitCardRepository.findByNumber(searchText.get());
            } catch (NumberFormatException e) {
                return null;
            }

        } else {
            return null;
        }

    }


}
