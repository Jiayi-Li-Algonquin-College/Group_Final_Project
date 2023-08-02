package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityQuizRoomBinding;

/**
 * This class represents the QuizRoom activity where users can answer trivia questions.
 * Users can select answers, view correct answers, and calculate their total score.
 */
public class QuizRoom extends AppCompatActivity {

    private RecyclerView.Adapter<QuizQuestionAdapter.QuestionViewHolder> myAdapter;
    private ActivityQuizRoomBinding binding;
    ArrayList<Question> questions = new ArrayList<>();
    QuizRoomViewModel quizModel;

    Context context;
    RequestQueue queue = null;

    int totalScore = 0;
    int topicId = 0;

    private Executor thread;
    /**
     * Initializes the QuizRoom activity and sets up the UI components.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQuizRoomBinding.inflate(getLayoutInflater());
        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this); //like a constructor
        setContentView(binding.getRoot());
        context = this;
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedUserName = prefs.getString("LoginName", "");
        topicId = prefs.getInt("TopicId", 0);
        binding.nameTextView.setText(savedUserName);

        // Initialize the ViewModel

        quizModel = new ViewModelProvider(this).get(QuizRoomViewModel.class);

        //initialize the ViewModel arrayList:
        questions = QuizRoomViewModel.questions.getValue();
        if (questions == null) {
            quizModel.questions.postValue(questions = new ArrayList<>());
        }

        // Set up the RecyclerView and adapter
        myAdapter = new QuizQuestionAdapter(this, questions, quizModel);
        binding.recycleView.setAdapter(myAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));



        // Load the questions from the API
        loadQuestions();

        binding.calculateScoreButton.setOnClickListener(clk -> {
            totalScore = 0;
            for (int score : quizModel.scoresMap.values()) {
                totalScore += score;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Congratulations! You achieved:  " + totalScore + "/10")
                    .setMessage("you will have the opportunity to achieve the top ten user scores \n\n").
                    setPositiveButton("Yes",(dialog, cl)->{
                        QuizDatabase quizDatabase = QuizDatabase.getInstance(this);
                        QuizDAO quizDao = quizDatabase.quizDao();
                        Score score = new Score(savedUserName,totalScore);
                        thread = Executors.newSingleThreadExecutor();
                        thread.execute(()-> {
                            quizDao.insertScore(score);
                        });

                        finish();
                    }).
                    setNegativeButton("No",(dialog, cl)->{


                    }).
                    create().show();
        });

    }
    /**
     * Loads trivia questions from an API and populates the questions list.
     * The loaded questions are displayed in the RecyclerView.
     */
    private void loadQuestions() {
        String url = "https://opentdb.com/api.php?amount=10&category="+topicId+"&type=multiple";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Get the array of questions from the response
                        JSONArray questionsArray = response.getJSONArray("results");

                        // Clear the existing questions list
                        questions.clear();

                        // Iterate over the array and create a new Question object for each item
                        for (int i = 0; i < questionsArray.length(); i++) {
                            JSONObject questionObject = questionsArray.getJSONObject(i);

                            // Get the question details
                            String questionText = questionObject.getString("question");
                            String correctAnswer = questionObject.getString("correct_answer");
                            JSONArray incorrectAnswers = questionObject.getJSONArray("incorrect_answers");

                            // Create a new Question object and add it to the list
                            Question question = new Question(questionText, incorrectAnswers.getString(0),
                                    incorrectAnswers.getString(1), incorrectAnswers.getString(2), correctAnswer);

                            //make answers randomly and save them in a arraylist
                            ArrayList<String> answers = new ArrayList<String>();
                            answers.add(incorrectAnswers.getString(0));
                            answers.add(incorrectAnswers.getString(1));
                            answers.add(incorrectAnswers.getString(2));
                            answers.add(correctAnswer);
                            Collections.shuffle(answers);
                            question.setRandomSequenceQuestions(answers);

                            questions.add(question);
                        }

                        // Notify the adapter that the data has changed
                        myAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace());

        // Add the request to the queue
        queue.add(jsonObjectRequest);
    }
}
/**
 * Adapter class for displaying trivia questions and handling user interactions.
 */
class QuizQuestionAdapter extends RecyclerView.Adapter<QuizQuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;
    private QuizRoomViewModel quizModel;
    String selectedAnswer;
    private Context context;

    /**
     * Adapter class for displaying trivia questions and handling user interactions.
     */
    public QuizQuestionAdapter(Context context, List<Question> questionList, QuizRoomViewModel quizModel) {
        this.context = context;
        this.questionList = questionList;
        this.quizModel = quizModel;
    }
    /**
     * Creates a ViewHolder instance for displaying a trivia question.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the item.
     * @return A QuestionViewHolder instance.
     */
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_question, parent, false);
        return new QuestionViewHolder(view);
    }
    /**
     * Binds a trivia question to a ViewHolder and sets up user interaction listeners.
     *
     * @param holder   The ViewHolder instance.
     * @param position The position of the item in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.questionTextView.setText(question.getQuestionText());

        //get randomly answers
        List<String> answers = question.getRandomSequenceQuestions();

        holder.option1RadioButton.setText(answers.get(0));
        holder.option2RadioButton.setText(answers.get(1));
        holder.option3RadioButton.setText(answers.get(2));
        holder.option4RadioButton.setText(answers.get(3));


        holder.optionsRadioGroup.setTag(position);
        holder.optionsRadioGroup.clearCheck();
        if(quizModel.questionsMap.get(position) != null) {
            switch (quizModel.questionsMap.get(position)){
                case 1:
                    holder.option1RadioButton.setChecked(true);
                    break;
                case 2:
                    holder.option2RadioButton.setChecked(true);
                    break;
                case 3:
                    holder.option3RadioButton.setChecked(true);
                    break;
                case 4:
                    holder.option4RadioButton.setChecked(true);
                    break;
                default:
                    quizModel.questionsMap.put(position, 0);
            }
        }


        holder.option1RadioButton.setOnClickListener(click -> {
            quizModel.questionsMap.put(position, 1);
            selectedAnswer = holder.option1RadioButton.getText().toString();
            if (selectedAnswer.equalsIgnoreCase(question.getCorrectOption())) {
                quizModel.scoresMap.put(position, 1);
            } else {
                quizModel.scoresMap.put(position, 0);
            }
        });
        holder.option2RadioButton.setOnClickListener(click -> {
            quizModel.questionsMap.put(position, 2);
            selectedAnswer = holder.option2RadioButton.getText().toString();
            if (selectedAnswer.equalsIgnoreCase(question.getCorrectOption())) {
                quizModel.scoresMap.put(position, 1);
            } else {
                quizModel.scoresMap.put(position, 0);
            }
        });
        holder.option3RadioButton.setOnClickListener(click -> {
            quizModel.questionsMap.put(position, 3);
            selectedAnswer = holder.option3RadioButton.getText().toString();
            if (selectedAnswer.equalsIgnoreCase(question.getCorrectOption())) {
                quizModel.scoresMap.put(position, 1);
            } else {
                quizModel.scoresMap.put(position, 0);
            }
        });
        holder.option4RadioButton.setOnClickListener(click -> {
            quizModel.questionsMap.put(position, 4);
            selectedAnswer = holder.option4RadioButton.getText().toString();
            if (selectedAnswer.equalsIgnoreCase(question.getCorrectOption())) {
                quizModel.scoresMap.put(position, 1);
            } else {
                quizModel.scoresMap.put(position, 0);
            }
        });

        holder.questionTextView.setOnClickListener(clk -> {
            if (question != null) {
                AnswerDetailsFragment answerFragment = new AnswerDetailsFragment(question);
                FragmentManager fMgr = ((QuizRoom)context).getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                tx.add(R.id.fragmentLocation, answerFragment);
                tx.replace(R.id.fragmentLocation, answerFragment);
                tx.addToBackStack(null);
                tx.commit();
            }
        });
    }
    /**
     * Returns the total number of trivia questions in the list.
     *
     * @return The total number of questions.
     */
    @Override
    public int getItemCount() {
        return questionList.size();
    }
    /**
     * ViewHolder class for displaying a trivia question and handling user interactions.
     */
    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView questionTextView;
        private RadioGroup optionsRadioGroup;
        private RadioButton option1RadioButton, option2RadioButton, option3RadioButton, option4RadioButton;
        /**
         * Constructs a QuestionViewHolder instance.
         *
         * @param itemView The view for the ViewHolder.
         */
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsRadioGroup = itemView.findViewById(R.id.optionsRadioGroup);
            option1RadioButton = itemView.findViewById(R.id.option1RadioButton);
            option2RadioButton = itemView.findViewById(R.id.option2RadioButton);
            option3RadioButton = itemView.findViewById(R.id.option3RadioButton);
            option4RadioButton = itemView.findViewById(R.id.option4RadioButton);
        }

    }


}