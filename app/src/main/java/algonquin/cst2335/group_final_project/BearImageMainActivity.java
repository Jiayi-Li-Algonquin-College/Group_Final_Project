package algonquin.cst2335.group_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.RecyclerView.*;
import androidx.room.Room;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_final_project.databinding.ActivityBearImageMainBinding;
import algonquin.cst2335.group_final_project.databinding.ActivitySavedImageBinding;
//import algonquin.cst2335.group_final_project.databinding.*;
//import algonquin.cst2335.group_final_project.databinding.ActivityChatRoomBinding;

public class BearImageMainActivity extends AppCompatActivity {

    private EditText widthEditText;
    private EditText heightEditText;
    private ImageView imageView;
    private Button generateButton;
    private Button savedImagesButton;
    private RecyclerView.Adapter myAdapter;
    private ActivityBearImageMainBinding binding;

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

        //good ##
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
                             .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     // Save the image to the database
                                     new Thread(() -> {
                                         BearImageDAO.insert(new BearImage(url));
                                         runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
                                     }).start();
                                 }
                             })
                             .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     // Delete the image from the database
                                     new Thread(() -> {
                                         BearImageDAO.deleteBearImage(new BearImage(url));
                                         runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image deleted", Toast.LENGTH_SHORT).show());
                                     }).start();
                                 }
                             })
                             .setNeutralButton("Cancel", null)
                             .show();
                 }
                 });}
        });
        //good ##

    //good ##
        savedImagesButton.setOnClickListener(v -> {
            Intent intent = new Intent(BearImageMainActivity.this, SavedImageActivity.class);
            startActivity(intent);
        });
    }
}