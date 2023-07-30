package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CurrencyConvertDao {
    //insertions, database returns id as long
    @Insert
    long insertConvert(CurrencyConvert cc);

    //query, get all from database:
    @Query("Select * from CurrencyConvert") //Table name is @Entity
    List<CurrencyConvert> getAllConverts();

    //delete
    @Delete //number of rows deleted, should be 1 if id matches
    int deleteConvert(CurrencyConvert cc);
}
