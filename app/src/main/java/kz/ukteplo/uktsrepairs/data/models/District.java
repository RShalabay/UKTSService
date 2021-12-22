package kz.ukteplo.uktsrepairs.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class District {
    @PrimaryKey
    private Integer id;
    private String name;

    public District(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
