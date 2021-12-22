package kz.ukteplo.uktsrepairs.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Route {
    @PrimaryKey
    private Integer id;
    private String name;
    private Integer district;

    public Route(@NonNull Integer id, @NonNull String name, @NonNull Integer district) {
        this.id = id;
        this.name = name;
        this.district = district;
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

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }
}
