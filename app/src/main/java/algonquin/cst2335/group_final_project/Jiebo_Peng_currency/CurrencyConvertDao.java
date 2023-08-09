package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * The CurrencyConvertDao interface defines the data access methods for performing database operations related to
 * currency conversions. It is used to interact with the underlying database and perform operations on the CurrencyConvert table.
 *  @author Jiebo Peng
 *  @version 1.0
 */
@Dao
public interface CurrencyConvertDao {
    /**
     * insertions, database returns id as long
     * @param cc an CurrencyConvert object
     */
    @Insert
    long insertConvert(CurrencyConvert cc);
    /**
     * get all converters from database:
     */
    @Query("Select * from CurrencyConvert") //Table name is @Entity
    List<CurrencyConvert> getAllConverts();

    /**
     * delete the CurrencyConvert object
     * @param cc an CurrencyConvert object
     */
    @Delete //number of rows deleted, should be 1 if id matches
    int deleteConvert(CurrencyConvert cc);

    /**
     * update the CurrencyConvert object
     * @param cc an CurrencyConvert object
     */
    @Update
    void updateConvert(CurrencyConvert cc);
}
