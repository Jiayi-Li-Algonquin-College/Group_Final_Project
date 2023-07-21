package algonquin.cst2335.group_final_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BearImage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int id;
    @ColumnInfo(name = "URL")
    public String url;

    public BearImage(String url) {
        this.url = url;
    }
}
