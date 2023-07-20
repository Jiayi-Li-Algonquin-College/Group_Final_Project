package algonquin.cst2335.group_final_project.trivia;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuizQuestionDAO {

    @Insert
    long insertQuestion(QuizQuestion question);

    @Query("SELECT * FROM quiz_questions")
    List<QuizQuestion> getAllQuestions();
}
