package app.registerVisitor.model;

import app.visitCard.model.vo.VisitCardVO;

/**
 * Created by matczy on 14.03.16.
 */
public class RegisterVisitorByVisitCardWrapper {
    private DirectionType direction;
    private VisitCardVO visitCard;

    public DirectionType getDirection() {
        return direction;
    }

    public void setDirection(DirectionType direction) {
        this.direction = direction;
    }

    public VisitCardVO getVisitCard() {
        return visitCard;
    }

    public void setVisitCard(VisitCardVO visitCard) {
        this.visitCard = visitCard;
    }
}
