package algonquin.cst2335.group_final_project.JiayiLi_Flight_Part;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlightMessageDAO {
    @Insert
    public void insertMessage(FlightMessage m);
    @Query("Select * from FlightMessage")
    public List<FlightMessage> getAllMessages();
    @Delete
    public void deleteMessage (FlightMessage m);



}
