package app.registerVisitor.validator;

import app.registerVisitor.model.DirectionType;
import app.registerVisitor.repository.RegisterVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by matczy on 14.03.16.
 */
@Service
public class RegisterVisitorValidatorImpl implements RegisterVisitorValidator {

    private RegisterVisitorRepository registerVisitorRepository;

    @Autowired
    public RegisterVisitorValidatorImpl(RegisterVisitorRepository registerVisitorRepository) {
        this.registerVisitorRepository = registerVisitorRepository;
    }

    @Override
    public boolean checkPersonIsOnCorrectSide(Long personId, DirectionType direction) {

        if (DirectionType.ENTRY== direction) {
            return checkPersonIsOutside(personId);
        } else {
            return checkPersonIsInside(personId);

        }
    }

    private boolean checkPersonIsInside(Long id) {
        return registerVisitorRepository.findByEntryDateNotNullAndExitDateNullAndPersonId(id).isPresent();
    }

    private boolean checkPersonIsOutside(Long id) {
        return !registerVisitorRepository.findByEntryDateNotNullAndExitDateNullAndPersonId(id).isPresent();
    }
}
