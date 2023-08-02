package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;


import algonquin.cst2335.group_final_project.databinding.DetailsLayoutTriviaBinding;

/**
 * A Fragment class for displaying details of a selected question's answers.
 * This fragment displays the question text, correct answer, and incorrect answer options.
 */
public class AnswerDetailsFragment extends Fragment {
    Question selected;
    /**
     * Constructs a new instance of the AnswerDetailsFragment.
     *
     * @param q The Question object for which to display the answer details.
     */
    public AnswerDetailsFragment(Question q) {selected = q;}
    /**
     * Inflates and creates the layout for the fragment, and displays question details.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root View of the fragment's layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutTriviaBinding binding = DetailsLayoutTriviaBinding.inflate(inflater);

        binding.question.setText("Question: " + selected.getQuestionText());
        binding.correctAnswer.setText("Correct Answer:" + selected.getCorrectOption());
        binding.incorrectAnswers.setText("Incorrect Answers: " + selected.getOption1() +", "+ selected.getOption2()+", "+ selected.getOption3());

        return binding.getRoot();
    }
}