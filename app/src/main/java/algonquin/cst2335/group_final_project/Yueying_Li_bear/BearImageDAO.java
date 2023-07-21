package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BearImageDAO {
    @Insert
    public static void insert(BearImage bearImage){

    }

    @Query("SELECT * FROM BearImage")
    List<BearImage> getAllImages();

    @Delete
    public static void deleteBearImage(BearImage bearImage){

    }
}
