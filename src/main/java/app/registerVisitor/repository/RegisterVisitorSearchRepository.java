package app.registerVisitor.repository;


import app.visitRegister.model.TypeShowData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * User: Mateusz Czyrny
 * Date: 12/10/14
 * Time: 8:32 AM
 */
public interface RegisterVisitorSearchRepository {

    Page findBySearchText(String searchText, TypeShowData typeShowData, Pageable page) throws Exception;

}
