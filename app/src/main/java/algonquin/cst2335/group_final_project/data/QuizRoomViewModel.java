package algonquin.cst2335.group_final_project.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.group_final_project.Trivia.QuizQuestion;

public class QuizRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<QuizQuestion>> quizQuestions = new MutableLiveData<>();
}
