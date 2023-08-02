package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Represents a user's score in the trivia quiz.
 */
@Entity(tableName = "user_scores")
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_name")
    public String name;

    @ColumnInfo(name = "quiz_score")
    public int score;

    /**
     * Constructs a Score object with the specified user name and score.
     *
     * @param name  The user's name.
     * @param score The user's quiz score.
     */
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the unique ID of the score entry.
     *
     * @return The ID of the score entry.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the unique ID of the score entry.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the user's name associated with the score.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the user's name associated with the score.
     *
     * @param name The user's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the user's quiz score.
     *
     * @return The user's quiz score.
     */
    public int getScore() {
        return score;
    }
    /**
     * Sets the user's quiz score.
     *
     * @param score The quiz score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
