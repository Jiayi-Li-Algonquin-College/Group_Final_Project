package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "quiz_questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question_text")
    private String questionText;

    @ColumnInfo(name = "option1")
    private String option1;

    @ColumnInfo(name = "option2")
    private String option2;

    @ColumnInfo(name = "option3")
    private String option3;

    @ColumnInfo(name = "correct_option")
    private String correctOption;

    @Ignore
    private ArrayList<String> RandomSequenceQuestions;

    /**
     * Constructs a new Question object with the provided question details.
     *
     * @param questionText   The text of the question.
     * @param option1        The first answer option.
     * @param option2        The second answer option.
     * @param option3        The third answer option.
     * @param correctOption  The correct answer option.
     */
    public Question(String questionText, String option1, String option2, String option3, String correctOption) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.correctOption = correctOption;
    }

    /**
     * Returns the unique ID of the question.
     *
     * @return The ID of the question.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the unique ID of the question.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the text of the question.
     *
     * @return The text of the question.
     */
    public String getQuestionText() {
        return questionText;
    }
    /**
     * Sets the text of the question.
     *
     * @param questionText The text to set.
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }
    /**
     * Returns the text of the question.
     *
     * @return The text of the question.
     */
    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }
    /**
     * Returns the list of randomly sequenced answer options.
     *
     * @return The list of randomly sequenced answer options.
     */
    public ArrayList<String> getRandomSequenceQuestions() {
        return RandomSequenceQuestions;
    }
    /**
     * Sets the list of randomly sequenced answer options.
     *
     * @param randomSequenceQuestions The list to set.
     */
    public void setRandomSequenceQuestions(ArrayList<String> randomSequenceQuestions) {
        RandomSequenceQuestions = randomSequenceQuestions;
    }
}

