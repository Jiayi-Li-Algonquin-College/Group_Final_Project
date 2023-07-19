package algonquin.cst2335.group_final_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.group_final_project.databinding.ActivityMainBinding;

public class TriviaActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String userName;
    Snackbar snackbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //inflate the menu:
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {;
        if (item.getItemId() == R.id.item_1) {
            showHelpDialog();
        }
        return true;
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("This is Si Wang's Final Project! Have fun and enjoy the game!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Dialog dismissed
        });
        builder.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedUserName = prefs.getString("LoginName", "");
        binding.nameEditText.setText(savedUserName);

        // TODO: Set up the spinner with quiz topics
//        List<String> topics = new ArrayList<>();
//        topics.add("Select a topic");
//        topics.add("General Knowledge");
//        topics.add("Science");
//        topics.add("History");
//        topics.add("Geography");
//        topics.add("Sports");

//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, topics);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.topicSpinner.setAdapter(spinnerAdapter);

//        binding.topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0) {
//                    selectedTopic = topics.get(position);
//                } else {
//                    selectedTopic = null;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selectedTopic = null;
//            }
//        });




        binding.startQuizButton.setOnClickListener(v -> {
            userName = binding.nameEditText.getText().toString().trim();

            if (userName.isEmpty()) {
                binding.nameEditText.setError("Please enter your name");
            } else {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("LoginName", userName);
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

            snackbar = Snackbar.make(binding.getRoot(), "High scores feature coming soon!", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", v1 -> {

                // Inform the user that the action has been undone
                Snackbar.make(binding.getRoot(), "Action cancelled!", Snackbar.LENGTH_SHORT).show();
            });
            snackbar.show();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // If you want to show the AlertDialog notification on every app launch, move it to the onCreate method.
        showAlertDialogNotification();
    }

    private void showAlertDialogNotification() {
        // Show an AlertDialog notification
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome!");
        builder.setMessage("Thank you for using our quiz app!");
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        builder.show();
    }
}



