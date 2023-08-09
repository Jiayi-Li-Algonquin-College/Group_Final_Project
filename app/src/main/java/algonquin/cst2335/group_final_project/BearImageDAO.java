package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This interface maintains the inserting, deleting one image, deleting all images and selecting all
 * images from the database
 */
@Dao
public interface BearImageDAO {
    /**
     * inserts a new image into database
     * @param bearImage the object of BearImage
     */
    @Insert
    void insert(BearImage bearImage);

    /**
     * selects all images from database
     * @return a list of images
     */
    @Query("SELECT * FROM BearImage")
    List<BearImage> getAllImages();

    /**
     * deletes one image from database
     * @param bearImage an object of bear image
     */
    @Delete
    void deleteBearImage(BearImage bearImage);

    /**
     * deletes all images from database
     */
    @Query("DELETE FROM BearImage")
    void deleteAllImages();
}
