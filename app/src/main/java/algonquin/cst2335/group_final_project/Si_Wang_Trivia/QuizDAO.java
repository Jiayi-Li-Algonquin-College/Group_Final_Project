package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * Data Access Object (DAO) interface for interacting with the quiz database.
 */
@Dao
public interface QuizDAO {
    /**
     * Inserts a new score into the database.
     *
     * @param score The score to be inserted.
     */
    @Insert
    void insertScore(Score score);

    /**
     * Retrieves the top high scores from the database.
     *
     * @return A list of top high scores.
     */
    @Query("SELECT DISTINCT * FROM user_scores ORDER BY quiz_score DESC LIMIT 10")
    List<Score> getTopHighScores();

    /**
     * Deletes a score from the database.
     *
     * @param score The score to be deleted.
     */
    @Delete
    void deleteScore(Score score);

}
