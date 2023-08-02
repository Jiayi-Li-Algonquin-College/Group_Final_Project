package algonquin.cst2335.group_final_project.Jiayi_Li_flight;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Represents a chat message entity to be stored in the database.
 */
@Entity
public class ChatMessage {
    /**
     * The primary key ID of the chat message.
     */
    @PrimaryKey (autoGenerate=true)
    @ColumnInfo(name="id")
    public long id;
    /**
     * The content of the chat message.
     */
    @ColumnInfo(name="Message")
    public String message;
    /**
     * The timestamp when the chat message was sent.
     */
    @ColumnInfo(name="TimeSent")
    public String timeSent;


    /**
     * Indicates whether the chat message was sent by the user (true) or received (false).
     */
    @ColumnInfo(name="IsSentButton")
    public boolean isSentButton;
    /**
     * Constructs a new instance of the ChatMessage class.
     *
     * @param message The content of the chat message.
     * @param timeSent The timestamp when the chat message was sent.
     * @param isSentButton Indicates whether the chat message was sent by the user (true) or received (false).
     */
    public ChatMessage(String message, String timeSent, boolean isSentButton) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }
    /**
     * Sets the ID of the chat message.
     *
     * @param id The ID to set for the chat message.
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * Gets the ID of the chat message.
     *
     * @return The ID of the chat message.
     */
    public long getId() {
        return id;
    }
    /**
     * Sets whether the chat message was sent by the user or received.
     *
     * @param sentButton True if the chat message was sent by the user, false if received.
     */
    public void setSentButton(boolean sentButton) {
        isSentButton = sentButton;
    }
    /**
     * Gets the content of the chat message.
     *
     * @return The content of the chat message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the timestamp when the chat message was sent.
     *
     * @return The timestamp of the chat message.
     */
    public String getTimeSent() {
        return timeSent;
    }

    /**
     * Checks whether the chat message was sent by the user or received.
     *
     * @return True if the chat message was sent by the user, false if received.
     */
    public boolean getIsSentButton() {
        return isSentButton;
    }
}

