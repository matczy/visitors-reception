package app.registerVisitor.service;


import app.registerVisitor.model.RegisterVisitor;
import app.registerVisitor.model.RegisterVisitorByVisitCardWrapper;
import app.registerVisitor.model.RegisterVisitorVO;
import app.visitRegister.model.TypeShowData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by matczy on 14.03.16.
 */
public interface RegisterVisitorService {

    boolean isPersonInside(Long id);

    RegisterVisitor registerVisitorAction(RegisterVisitorVO registerVisitorVO) throws Exception;


    RegisterVisitor registerVisitorExitAction(Long id);

    RegisterVisitor registerVisitorAction(RegisterVisitorByVisitCardWrapper registerVisitorByVisitCardWrapper) throws Exception;

    Page filterData(Optional<String> searchText, TypeShowData typeShowData, Integer page, Integer resultOnPage) throws Exception;

    List<RegisterVisitor> registerMultiVisitorAction(List<RegisterVisitorVO> registerVisitorsVO);

    Long countAllVisitors();

    Long countAllVisitorsOnObject();

    Long countTodayVisits();

    List countLast30DaysVisits();

    List countLastDayByHourVisits();

    List<String> getLast30DaysLabel();

    List<String> getLastDayByHourLabel();
}
