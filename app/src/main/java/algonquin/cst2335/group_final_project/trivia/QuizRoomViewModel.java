package algonquin.cst2335.group_final_project.trivia;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class QuizRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<QuizQuestion>> quizQuestions = new MutableLiveData<>();
}
