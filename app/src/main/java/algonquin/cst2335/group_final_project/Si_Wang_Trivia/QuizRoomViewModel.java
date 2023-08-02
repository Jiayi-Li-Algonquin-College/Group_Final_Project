package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * ViewModel class for managing data related to the quiz room.
 * This ViewModel stores and manages data such as questions, selected question, scores, and question answers.
 */
public class QuizRoomViewModel extends ViewModel {
    /**
     * A MutableLiveData holding a list of quiz questions.
     */
    public static MutableLiveData<ArrayList<Question>> questions = new MutableLiveData< >();
    /**
     * A MutableLiveData holding the currently selected question.
     */
    public MutableLiveData<Question> selectedQuestion = new MutableLiveData<>();

    /**
     * A HashMap holding scores for each question.
     * The key represents the question position, and the value represents the score.
     */
    public HashMap<Integer, Integer> scoresMap = new HashMap<>();

    /**
     * A HashMap holding selected answers for each question.
     * The key represents the question position, and the value represents the selected answer.
     */
    public HashMap<Integer, Integer> questionsMap = new HashMap<>();

}
