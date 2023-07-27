package algonquin.cst2335.group_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import algonquin.cst2335.group_final_project.Yueying_Li_bear.BearImageMainActivity;
import algonquin.cst2335.group_final_project.Jiebo_Peng_currency.CurrencyConvertActivity;
import algonquin.cst2335.group_final_project.Jiayi_Li_flight.ChatRoom;
import algonquin.cst2335.group_final_project.Si_Wang_Trivia.TriviaActivity;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_bar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.main_Item_1) {
            Intent nextPage = new Intent( MainActivity.this, ChatRoom.class);
            startActivity( nextPage);
        } else if (itemId == R.id.main_Item_2) {
//            Toast.makeText(getApplicationContext(), "This is a about message 😁" , Toast.LENGTH_SHORT).show();
            Intent nextPage = new Intent( MainActivity.this, BearImageMainActivity.class);
            startActivity( nextPage);
        }else if (itemId == R.id.main_Item_3) {
//            Toast.makeText(getApplicationContext(), "This is a about message 😁" , Toast.LENGTH_SHORT).show();
            Intent nextPage = new Intent( MainActivity.this, TriviaActivity.class);
            startActivity( nextPage);
        }else if (itemId == R.id.main_item_4) {
//            Toast.makeText(getApplicationContext(), "This is a about message 😁" , Toast.LENGTH_SHORT).show();
            Intent nextPage = new Intent( MainActivity.this, CurrencyConvertActivity.class);
            startActivity( nextPage);
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        setSupportActionBar(variableBinding.myToolbar);

        variableBinding.Plane.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, ChatRoom.class);
            startActivity( nextPage);
        });

        variableBinding.Trivia.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, TriviaActivity.class);
            startActivity( nextPage);
        });

        variableBinding.bear.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, BearImageMainActivity.class);
            startActivity( nextPage);
        });

        variableBinding.Currency.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, CurrencyConvertActivity.class);
            startActivity( nextPage);
        });

    }
}