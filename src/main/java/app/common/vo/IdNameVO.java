package app.common.vo;

/**
 * Created by Dachu on 2015-12-15.
 */
public class IdNameVO {

    public IdNameVO() {
    }

    private IdNameVO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;

    public static IdNameVO create(Long id, String name) {
        return new IdNameVO(id, name);
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
