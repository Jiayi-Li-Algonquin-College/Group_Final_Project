package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This abstract class maintains the database and the DAO
 */
@Database(entities = {BearImage.class}, version = 1)
public abstract class BearImageDatabase extends RoomDatabase {

    /**
     * This abstract method is for DAO of bear image.
     * @return a DAO of bear image
     */
    public abstract BearImageDAO bearImageDAO();
}
