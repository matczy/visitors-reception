package app.company.model.vo;



import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 * User: Dachu
 * Date: 31.03.15
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */

public class CompanyVO implements Serializable {

    private CompanyVO() {
    }

    public static CompanyVO create() {
        return new CompanyVO();
    }

    private CompanyVO(Long id, String name) {
        this.id = id;
        this.name = name;

    }

    private Long id;

    private String name;


    public static CompanyVO create(Long id, String name) {
        return new CompanyVO(id, name);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
