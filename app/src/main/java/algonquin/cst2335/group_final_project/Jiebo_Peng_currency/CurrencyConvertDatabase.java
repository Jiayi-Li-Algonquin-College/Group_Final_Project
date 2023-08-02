package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;


import androidx.room.Database;
import androidx.room.RoomDatabase;
/**
 * The CurrencyConvertDatabase is an abstract class that serves as the Room database for the currency conversion data.
 * It is annotated with `@Database` to specify the entities it contains and the database version.
 * This class is responsible for defining the database configuration and providing access to the DAO (Data Access Object)
 * for performing database operations related to currency conversions.
 * @author Jiebo Peng
 * @version 1.0
 */
@Database(entities = {CurrencyConvert.class}, version = 1)
public abstract class CurrencyConvertDatabase extends RoomDatabase {
    /**
     * Get the CurrencyConvertDao object, which is used to perform database operations related to currency conversions.
     * @return The CurrencyConvertDao object.
     */
    public abstract CurrencyConvertDao ccDao();
}
