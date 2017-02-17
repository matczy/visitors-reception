package app.visitCard.repository;


import app.visitCard.model.VisitCard;
import app.visitRegister.model.TypeShowData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 8:32 AM
 */
public interface VisitCardSearchRepository {

   Page findBySearchText(String searchText, TypeShowData typeShowData, Pageable pageable) throws Exception;


   List<VisitCard> findBySearchText(String empty);
}
