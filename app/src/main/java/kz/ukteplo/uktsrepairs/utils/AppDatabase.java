package kz.ukteplo.uktsrepairs.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kz.ukteplo.uktsrepairs.data.DAO.DistrictDAO;
import kz.ukteplo.uktsrepairs.data.DAO.RouteDAO;
import kz.ukteplo.uktsrepairs.data.models.District;
import kz.ukteplo.uktsrepairs.data.models.Route;

@Database(entities = {District.class, Route.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DistrictDAO districtDAO();
    public abstract RouteDAO routeDAO();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    //Инициализация базы данных приложения
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
