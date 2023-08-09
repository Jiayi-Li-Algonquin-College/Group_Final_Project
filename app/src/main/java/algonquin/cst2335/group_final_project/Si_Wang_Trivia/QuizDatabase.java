package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/**
 * The QuizDatabase class is a Room database that stores quiz-related data,
 * including questions and user scores.
 */
@Database(entities = {Question.class, Score.class}, version = 2)
public abstract class QuizDatabase extends RoomDatabase {

    private static QuizDatabase instance;

    /**
     * Returns a singleton instance of the QuizDatabase.
     *
     * @param context The application context.
     * @return The QuizDatabase instance.
     */
    public static synchronized QuizDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            QuizDatabase.class, "quiz_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    /**
     * Returns the Data Access Object (DAO) interface for interacting with the database.
     *
     * @return The QuizDAO instance.
     */
    public abstract QuizDAO quizDao();
}
