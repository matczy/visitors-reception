package app.person.model.Enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Mateusz Czyrny
 * Date: 12/14/14
 * Time: 7:38 PM
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PersonType {
    WORKER(1, "Pracownik"), VISITOR(2, "Gość");

    private long id;
    private String name;

    PersonType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    private static final Map<String, PersonType> lookupForName = new HashMap<>();

    static {
        for (PersonType type : PersonType.values()) {
            lookupForName.put(type.getName(), type);
        }
    }

    public static PersonType getByName(String name) {
        return lookupForName.get(name);
    }


    private static final Map<Long, PersonType> lookupForId = new HashMap<>();

    static {
        for (PersonType type : PersonType.values()) {
            lookupForId.put(type.getId(), type);
        }
    }

    public static PersonType getById(Long id) {
        return lookupForId.get(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
