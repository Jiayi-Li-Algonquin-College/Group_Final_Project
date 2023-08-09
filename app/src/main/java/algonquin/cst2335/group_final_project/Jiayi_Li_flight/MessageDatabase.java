package algonquin.cst2335.group_final_project.Jiayi_Li_flight;

import androidx.room.Database;
import androidx.room.RoomDatabase;
/**
 * Represents a Room database that stores chat messages using the RoomDatabase class.
 * This class defines the database version and provides an abstract method for accessing
 * the DAO (Data Access Object) for chat messages.
 */
@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    /**
     * Abstract method for accessing the ChatMessageDAO (Data Access Object).
     *
     * @return An instance of the ChatMessageDAO for performing database operations on chat messages.
     */
    public abstract ChatMessageDAO cmDAO();




}