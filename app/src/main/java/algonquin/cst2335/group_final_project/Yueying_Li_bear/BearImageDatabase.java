package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BearImage.class}, version = 1)
public abstract class BearImageDatabase extends RoomDatabase {

    public abstract BearImageDAO bearImageDAO();
}
