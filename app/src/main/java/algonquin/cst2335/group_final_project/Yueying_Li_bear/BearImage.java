package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class maintains ID and URL of Bear Image.
 */
@Entity
public class BearImage {
    /**
     * ID of the bear image
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int id;

    /**
     * URL of the bear image
     */
    @ColumnInfo(name = "URL")
    public String url;

    /**
     * constructor with one parameter
     * @param url the URL of bear image
     */
    public BearImage(String url) {
        this.url = url;
    }

    /**
     * gets the ID of bear image
     * @return id the ID of bear image
     */
    public int getId() {
        return id;
    }

    /**
     * sets ID of bear image
     * @param id the id of bear image
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the URL of bear image
     * @return url the URL of bear image
     */
    public String getUrl() {
        return url;
    }

    /**
     * sets URL of bear image
     * @param url the url of bear image
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
