package algonquin.cst2335.group_final_project.JiayiLi_Flight_Part;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlightMessage {
    @PrimaryKey (autoGenerate=true)
    @ColumnInfo(name="id")
    public long id;
    @ColumnInfo(name="Message")
    public String message;
    @ColumnInfo(name="TimeSent")
    public String timeSent;



    @ColumnInfo(name="IsSentButton")
    public boolean isSentButton;

    public FlightMessage(String message, String timeSent, boolean isSentButton) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setSentButton(boolean sentButton) {
        isSentButton = sentButton;
    }
    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean getIsSentButton() {
        return isSentButton;
    }
}

