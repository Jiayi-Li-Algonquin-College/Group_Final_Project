package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CurrencyConvert.class}, version = 1)
public abstract class CurrencyConvertDatabase extends RoomDatabase {
    //Which DAO do we use for this database
    public abstract CurrencyConvertDao ccDao();
}
