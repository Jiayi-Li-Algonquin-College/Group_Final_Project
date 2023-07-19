package algonquin.cst2335.group_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.TriviaActivity;
import algonquin.cst2335.group_final_project.databinding.ActivityMainBinding;

/**
 * MainActivity class represents an Android activity that allows the user to enter and check a password.
 * It uses various methods to validate the password complexity and display feedback messages.
 *
 * @author Jiayi Li
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

//        variableBinding.Plane.setOnClickListener( clk-> {
//            Intent nextPage = new Intent( MainActivity.this, ChatRoom.class);
//            startActivity( nextPage);
//        });

        variableBinding.Trivia.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, TriviaActivity.class);
            startActivity( nextPage);
        });

//        variableBinding.bear.setOnClickListener( clk-> {
//            Intent nextPage = new Intent( MainActivity.this, ChatRoom.class);
//            startActivity( nextPage);
//        });
//
//        variableBinding.Currency.setOnClickListener( clk-> {
//            Intent nextPage = new Intent( MainActivity.this, ChatRoom.class);
//            startActivity( nextPage);
//        });

    }
}