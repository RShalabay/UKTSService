package kz.ukteplo.uktsrepairs.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.Route;

@Dao
public interface RouteDAO {
    @Query("SELECT * FROM route WHERE district = :districtId")
    LiveData<List<Route>> getAll(Integer districtId);

    @Query("SELECT * FROM route WHERE id = :id")
    LiveData<Route> getById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);
}
