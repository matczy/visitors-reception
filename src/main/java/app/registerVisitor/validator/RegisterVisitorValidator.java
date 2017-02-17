package app.registerVisitor.validator;

import app.registerVisitor.model.DirectionType;

/**
 * Created by matczy on 14.03.16.
 */
public interface RegisterVisitorValidator {
    boolean checkPersonIsOnCorrectSide(Long personId, DirectionType direction);
}
