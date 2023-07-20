package algonquin.cst2335.group_final_project.JiayiLi_Flight_Part;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FlightMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {

    public abstract FlightMessageDAO cmDAO();




}