package algonquin.cst2335.group_final_project.Si_Wang_Trivia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityTriviaBinding;

/**
 * This class represents the Trivia Activity, where users can start quizzes, view high scores,
 * and perform various actions related to the quiz app.
 */
public class TriviaActivity extends AppCompatActivity {

    private ActivityTriviaBinding binding;
    private String userName;
    String selectedTopic;
    private LeaderboardAdapter leaderboardAdapter;

    private List<Score> leaderboardData = new ArrayList<>();

    private Executor thread;

    /**
     * {@inheritDoc}
     * Inflates the options menu and handles menu item selection.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //inflate the menu:
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     * Handles menu item selection, showing a help dialog when the "Help" option is selected.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_1) {
            showHelpDialog();
        }
        return true;
    }
    /**
     * Shows a help dialog with information about the quiz app.
     */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Objective:\n" +
                "Have fun and test your knowledge with a quick trivia quiz!\n" +
                "\n" +
                "Instructions:\n" +
                "\n" +
                "Gather: Get everyone together in a comfortable space.\n" +
                "\n" +
                "Ask Questions: I'll ask a series of questions one by one.\n" +
                "\n" +
                "Answer: Write down your answers on a piece of paper.\n" +
                "\n" +
                "Reveal Answers: After each question, I'll share the correct answer.\n" +
                "\n" +
                "Score: Keep track of how many you get right.\n" +
                "\n" +
                "Winning: The person with the most correct answers wins.\n" +
                "\n" +
                "Enjoy: Have a great time sharing what you know!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Dialog dismissed
        });
        builder.show();
    }
    /**
     * {@inheritDoc}
     * Initializes the Trivia Activity, sets up UI components, and handles user interactions.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriviaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedUserName = prefs.getString("LoginName", "");
        binding.nameEditText.setText(savedUserName);

        ArrayAdapter<CharSequence> topicAdapter = ArrayAdapter.createFromResource(this, R.array.topic_options, R.layout.topic_item_layout);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.topicSpinner.setAdapter(topicAdapter);

        leaderboardAdapter = new LeaderboardAdapter(leaderboardData);
        binding.recycleView.setAdapter(leaderboardAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.startQuizButton.setOnClickListener(v -> {
            userName = binding.nameEditText.getText().toString().trim();
            selectedTopic = binding.topicSpinner.getSelectedItem().toString();
            if (userName.isEmpty()) {
                binding.nameEditText.setError("Please enter your name");
            }
//            else if(selectedTopic.isEmpty()){}
            else {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("LoginName", userName);
                editor.putInt("TopicId", getTopicId(selectedTopic));
                editor.apply();
                // Show a Toast notification
                Toast.makeText(TriviaActivity.this, "Welcome, " + userName + "!", Toast.LENGTH_SHORT).show();

                //Start the quiz activity with user name and selected topic
                Intent nextPage = new Intent(TriviaActivity.this, QuizRoom.class);
                startActivity(nextPage);
            }
        });

        binding.viewHighScoresButton.setOnClickListener(v -> {
            // Implement the logic to view high scores here
            // start a new activity to show high scores
            loadLeaderboardData();
        });
    }
    /**
     * This class represents the RecyclerView adapter for the leaderboard.
     */
    class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

        private List<Score> leaderboardData;

        public LeaderboardAdapter(List<Score> leaderboardData) {
            this.leaderboardData = leaderboardData;
        }

        public void setData(List<Score> data) {
            this.leaderboardData = data;
            notifyDataSetChanged();
        }

        /**
         * {@inheritDoc}
         * Inflates the leaderboard item view and binds data to the view holder.
         */
        @NonNull
        @Override
        public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
            return new LeaderboardViewHolder(view);
        }

        /**
         * {@inheritDoc}
         * Binds data to the leaderboard item view and sets a click listener for deleting scores.
         */
        @Override
        public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
            Score score = leaderboardData.get(position);
            holder.bind(score);
            holder.itemView.setOnClickListener(view -> {
                showDeleteConfirmationDialog(score);
            });
        }

        @Override
        public int getItemCount() {
            return leaderboardData.size();
        }

        public class LeaderboardViewHolder extends RecyclerView.ViewHolder {

            private TextView nameTextView;
            private TextView scoreTextView;

            public LeaderboardViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                scoreTextView = itemView.findViewById(R.id.scoreTextView);
            }

            public void bind(Score score) {
                nameTextView.setText(score.getName());
                scoreTextView.setText(String.valueOf(score.getScore()));

            }
        }
    }
    /**
     * Shows a confirmation dialog for deleting a score.
     *
     * @param score The score to be deleted.
     */
    private void showDeleteConfirmationDialog(Score score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Score")
                .setMessage("Are you sure you want to delete the score for " + score.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Delete the score from the database
                    deleteScore(score);
                    // Show a Snackbar to indicate deletion
                    showDeletionSnackbar(score.getName());
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Cancel deletion
                })
                .show();
    }
    private void showDeletionSnackbar(String scoreName) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                "Score for " + scoreName + " deleted", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    /**
     * Deletes a score from the database and updates the leaderboard.
     *
     * @param score The score to be deleted.
     */
    private void deleteScore(Score score) {
        // Assuming you have a database instance named 'quizDatabase'
        QuizDatabase quizDatabase = QuizDatabase.getInstance(this);
        QuizDAO quizDao = quizDatabase.quizDao();

        thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            // Delete the score from the database
            quizDao.deleteScore(score);
            // Reload leaderboard data
            loadLeaderboardData();
        });
    }

    /**
     * Loads leaderboard data from the database and updates the adapter.
     */
    private void loadLeaderboardData() {
        // Assuming you have a database instance named 'quizDatabase'
        QuizDatabase quizDatabase = QuizDatabase.getInstance(this);
        QuizDAO quizDao = quizDatabase.quizDao();
        thread = Executors.newSingleThreadExecutor();
        thread.execute(()-> {
            leaderboardData = quizDao.getTopHighScores();
            runOnUiThread(() -> {
                leaderboardAdapter.setData(leaderboardData);
                leaderboardAdapter.notifyDataSetChanged();
            });
        });

    }
    /**
     * {@inheritDoc}
     * Shows an AlertDialog notification to welcome the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // If you want to show the AlertDialog notification on every app launch, move it to the onCreate method.
        showAlertDialogNotification();
    }

    /**
     * Shows an AlertDialog notification to welcome the user.
     */
    private void showAlertDialogNotification() {
        // Show an AlertDialog notification
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome!");
        builder.setMessage("Thank you for using our quiz app!");
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        builder.show();
    }

    /**
     * Returns the topic ID based on the selected topic.
     *
     * @param selectedTopic The selected topic.
     * @return The topic ID.
     */
    public int getTopicId(String selectedTopic){

        int topicId;

        switch (selectedTopic) {
            case "General Knowledge":
                topicId = 9;
                break;
            case "Entertainment: Video Games":
                topicId = 15;
                break;
            case "Science: Computers":
                topicId = 18;
                break;
            case "Sports":
                topicId = 21;
                break;
            case "Geography":
                topicId = 22;
                break;
            case "History":
                topicId = 23;
                break;
            case "Politics":
                topicId = 24;
                break;
            case "Vehicles":
                topicId = 28;
                break;
            case "Animals":
                topicId = 27;
                break;
            default:
                topicId = 0;
                break;
        }
        return topicId;
    }
}



