package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.RecyclerView.*;
import androidx.room.Room;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Objects;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityBearImageMainBinding;
//import algonquin.cst2335.group_final_project.databinding.*;
//import algonquin.cst2335.group_final_project.databinding.ActivityChatRoomBinding;

public class BearImageMainActivity extends AppCompatActivity {

    private EditText widthEditText;
    private EditText heightEditText;
    private ImageView imageView;
    private Button generateButton;
    private Button savedImagesButton;

//    private RecyclerView.Adapter myAdapter;
//    private ActivityBearImageMainBinding binding;

    ArrayList<BearImage> images;

//    //new **
//    private RecyclerView recyclerView;
//    private BearImageViewModel bearImageViewModel;
//    private BearImageAdapter bearImageAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear_image_main);

        widthEditText = findViewById(R.id.bearWidthEditText);
        heightEditText = findViewById(R.id.bearHeightEditText);
        imageView = findViewById(R.id.bearImageView);
        generateButton = findViewById(R.id.bearGenerateButton);
        savedImagesButton = findViewById(R.id.bearViewImagesButton);

//        //new **
//        recyclerView = findViewById(R.id.recyclerView);

//        binding = ActivityBearImageMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(binding.getRoot());

//        //new **
//        //set up RecyclerView
//        bearImageAdapter = new BearImageAdapter(new BearImageAdapter.ImageDiff());
//        recyclerView.setAdapter(bearImageAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        //new **
//        // Set up ViewModel
//        bearImageViewModel = new ViewModelProvider(this).get(BearImageViewModel.class);
//        bearImageViewModel.getAllImages().observe(this, images -> {
//            // Update the cached copy of the images in the adapter.
//            bearImageAdapter.submitList(images);
//        });

        // Restore the last entered dimensions from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("BearImageGenerator", MODE_PRIVATE);
        widthEditText.setText(preferences.getString("lastWidth", ""));
        heightEditText.setText(preferences.getString("lastHeight", ""));

        // Hide the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String width = widthEditText.getText().toString();
                String height = heightEditText.getText().toString();

                if (width.isEmpty() || height.isEmpty()) {
                    Toast.makeText(BearImageMainActivity.this, "Please enter both dimensions", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the dimensions to SharedPreferences
                preferences.edit()
                        .putString("lastWidth", width)
                        .putString("lastHeight", height)
                        .apply();

                // Generate and display the image
                String url = "https://placebear.com/" + width + "/" + height;
                Picasso.get().load(url).into(imageView);

                // Save the image to the database
                BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "image").build();
                new Thread(() -> {
                    BearImageDAO.insert(new BearImage(url));
                    runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
                }).start();

                // Use Volley to fetch the image
                RequestQueue queue = Volley.newRequestQueue(BearImageMainActivity.this);
                ImageRequest imageRequest = new ImageRequest(url,
                        response -> imageView.setImageBitmap(response), 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                        error -> Toast.makeText(BearImageMainActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show());

                queue.add(imageRequest);

                 //When the image is clicked, show a dialog asking if the user wants to save or delete it
                 imageView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         new AlertDialog.Builder(BearImageMainActivity.this)
                                 .setTitle("Image Options")
                                 .setMessage("Do you want to delete this image?")
                                 .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int which) {
                                         // Handler for undo operation
                                         final Handler handler = new Handler();
                                         final Runnable runnable = new Runnable() {
                                             @Override
                                             public void run() {
                                                 // Delete the image from the database
                                                 new Thread(() -> {
                                                     BearImageDAO.deleteBearImage(new BearImage(url));
                                                     runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image deleted", Toast.LENGTH_SHORT).show());
                                                 }).start();
                                             }
                                         };

                                         // Post the task to run after 3 seconds (3000 ms)
                                         handler.postDelayed(runnable, 3000);

                                         Snackbar snackbar = Snackbar.make(v, "Image will be deleted", Snackbar.LENGTH_LONG);
                                         snackbar.setAction("UNDO", new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 handler.removeCallbacks(runnable);
                                                 Toast.makeText(BearImageMainActivity.this, "Image delete operation cancelled", Toast.LENGTH_SHORT).show();
                                             }
                                         });
                                         snackbar.setDuration(10000); // Here 10000 milliseconds is 10 seconds.
                                         snackbar.show();
//                                         // Save the image to the database
//                                         new Thread(() -> {
//                                             BearImageDAO.insert(new BearImage(url));
//                                             runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image deleted", Toast.LENGTH_SHORT).show());
//                                         }).start();
                                     }
                                 })
                                 .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int which) {
                                         // Save the image to the database
                                         new Thread(() -> {
                                             BearImageDAO.insert(new BearImage(url));
                                             runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
                                         }).start();
                                         // Delete the image from the database
//                                         new Thread(() -> {
//                                             BearImageDAO.deleteBearImage(new BearImage(url));
//                                             runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
//                                         }).start();
                                     }
                                 })
                                 .setNeutralButton("Cancel", null)
                                 .show();
                     }
                 });
            }
        });

        savedImagesButton.setOnClickListener(v -> {
            Intent intent = new Intent(BearImageMainActivity.this, SavedImageActivity.class);
            startActivity(intent);
        });
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bear_menu, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bear_help) {
            showHelpDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.bear_help:
//                showHelpDialog();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
        }
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.bear_help_title)
                .setMessage(R.string.bear_help_message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
