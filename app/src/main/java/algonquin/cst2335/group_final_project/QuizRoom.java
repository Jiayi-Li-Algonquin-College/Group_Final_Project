package algonquin.cst2335.group_final_project;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.group_final_project.data.QuizRoomViewModel;
import algonquin.cst2335.group_final_project.databinding.ActivityQuizRoomBinding;

public class QuizRoom extends AppCompatActivity {

    private RecyclerView.Adapter myAdapter;
    private List<QuizQuestion> quizQuestions = new ArrayList<>();
    private QuizQuestionDAO myDAO;
    private ActivityQuizRoomBinding binding;
    private QuizRoomViewModel quizModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        quizModel = new ViewModelProvider(this).get(QuizRoomViewModel.class);
        binding = ActivityQuizRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedUserName = prefs.getString("LoginName", "");
        binding.nameTextView.setText(savedUserName);

//        binding.recycleView.setAdapter(new RecyclerView.Adapter<MyRowHolder>() )；
//
//        class MyRowHolder extends RecyclerView.ViewHolder {
//            public MyRowHolder(@NonNull View itemView) {
//                super(itemView);
//            }
//        }
//        //initialize to the ViewModel arrayList:
//        quizQuestions = quizModel.quizQuestions.getValue();
//        if (quizQuestions == null)
//            quizModel.quizQuestions.postValue((ArrayList<QuizQuestion>) (quizQuestions = new ArrayList<>()));
//
//        // Access the database
//        QuestionDatabase db = Room.databaseBuilder(getApplicationContext(), QuestionDatabase.class, "MyQuestionDatabase").build();
//        myDAO = db.cmDAO();
//
//        //get all message:
//        Executor thread1 = Executors.newSingleThreadExecutor();
//        thread1.execute(() -> {
//            //run on a second processor:
//            List<QuizQuestion> fromDatabse = myDAO.getAllQuestions();
//            quizQuestions.addAll(fromDatabse);//add previous messages
//
//            runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));
//            //update the recycle view
//            if (myAdapter != null) {
//                myAdapter.notifyDataSetChanged();
//            }
//        });
//
//
//            // Display the total score
//        String score = null;
//        String resultMessage = "Your total score is " + score + " out of " + quizQuestions.size();
//            Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
        }
    }