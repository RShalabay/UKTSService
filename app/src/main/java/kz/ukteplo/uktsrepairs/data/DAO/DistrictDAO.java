package kz.ukteplo.uktsrepairs.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.District;

@Dao
public interface DistrictDAO {
    @Query("SELECT * FROM district")
    LiveData<List<District>> getAll();

    @Query("SELECT * FROM district WHERE id = :id")
    LiveData<District> getById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(District district);
}
