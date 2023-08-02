package algonquin.cst2335.group_final_project.Jiayi_Li_flight;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * Data Access Object (DAO) interface for performing database operations on chat messages.
 */
@Dao
public interface ChatMessageDAO {
    /**
     * Inserts a new chat message into the database.
     *
     * @param m The chat message to be inserted.
     */
    @Insert
    public void insertMessage(ChatMessage m);
    /**
     * Retrieves all chat messages from the database.
     *
     * @return A list of all chat messages stored in the database.
     */
    @Query("Select * from ChatMessage")
    public List<ChatMessage> getAllMessages();
    /**
     * Deletes a chat message from the database.
     *
     * @param m The chat message to be deleted.
     */
    @Delete
    public void deleteMessage (ChatMessage m);



}
