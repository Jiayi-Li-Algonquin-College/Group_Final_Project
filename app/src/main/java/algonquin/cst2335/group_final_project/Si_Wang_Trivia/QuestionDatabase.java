package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {QuizQuestion.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {
    //which DAO do we use for this database:
    public abstract QuizQuestionDAO cmDAO();
}
