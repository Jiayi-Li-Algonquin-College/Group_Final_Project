package algonquin.cst2335.group_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.RecyclerView.*;
import androidx.room.Room;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear_image_main);

        widthEditText = findViewById(R.id.bearWidthEditText);
        heightEditText = findViewById(R.id.bearHeightEditText);
        imageView = findViewById(R.id.bearImageView);
        generateButton = findViewById(R.id.bearGenerateButton);
        savedImagesButton = findViewById(R.id.bearViewImagesButton);

//        binding = ActivityBearImageMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(binding.getRoot());

        // Restore the last entered dimensions from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("BearImageGenerator", MODE_PRIVATE);
        widthEditText.setText(preferences.getString("lastWidth", ""));
        heightEditText.setText(preferences.getString("lastHeight", ""));

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

//                // Use Volley to fetch the image
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                ImageRequest imageRequest = new ImageRequest(url,
//                        response -> imageView.setImageBitmap(response), 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
//                        error -> Toast.makeText(MainActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show());
//
//                queue.add(imageRequest);

                 //When the image is clicked, show a dialog asking if the user wants to save or delete it
                 imageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     new AlertDialog.Builder(BearImageMainActivity.this)
                             .setTitle("Image Options")
                             .setMessage("Do you want to delete this image?")
                             .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     // Save the image to the database
                                     new Thread(() -> {
                                         BearImageDAO.insert(new BearImage(url));
                                         runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
                                     }).start();
                                 }
                             })
                             .setNegativeButton("NO", new DialogInterface.OnClickListener() {
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

//        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
//            @NonNull
//            @Override
//            //inflates the row layout
//            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                //viewType is 0 or 1 based on getItemViewType(int position)
//                //int viewType is what layout to load
//                if(viewType == 0) {
//                    // how big is parent?
//                    ActivitySavedImageBinding binding = ActivitySavedImageBinding.inflate(getLayoutInflater(), parent, false);
//                    return new MyRowHolder(binding.getRoot());
//                }
//                // how big is parent?
////                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
////                return new MyRowHolder(binding.getRoot());
//            }
//
//            //initialize the row to data
//            @Override
//            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//                BearImage obj = images.get(position);
//
//                //override the text in the rows:
//                holder.messageText.setText(obj.url);
//            }
//
//            //how many rows there are
//            @Override
//            public int getItemCount() {
//                return images.size();
//            }
//
////            @Override
////            public int getItemViewType(int position) {
////                BearImage image = images.get(position);
////                if (image.getIsSentButton() == true){
////                    return 0;
////                }
////                else{
////                    return 2;
////                }
////            }
//            binding.recycleView.setAdapter(myAdapter);
//
//            binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
//        });

//    class MyRowHolder extends RecyclerView.ViewHolder {
//            //two variables for what is on a single row
//        ImageView imageView;
//
//            //This is a row
//            public MyRowHolder(@NonNull View itemView) {
//                super(itemView);
//                BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "MyBearImageDatabase").build();
//                BearImageDAO mDAO = db.bearImageDAO();
//
//                imageView.setOnClickListener(clk -> {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(BearImageMainActivity.this);
//                    builder.setMessage("Do you want to delete the image?")
//                            .setTitle("Question: ")
//
//                            .setNegativeButton("NO", (cl, which) -> {
//                                Log.d("DELETE", "The user clicked NO");
//                            })
//                            .setPositiveButton("YES", (dialog, cl) -> {
//                                int position = getAbsoluteAdapterPosition();
//                                BearImage image = images.get(position);
//                                Executor thread = Executors.newSingleThreadExecutor();
//                                thread.execute(() -> {
//                                    BearImageDAO.deleteBearImage(image); //background
//                                    images.remove(position);
//
//                                    runOnUiThread(() -> {
//                                        //on UI thread
//                                        myAdapter.notifyDataSetChanged(); //update rows
//                                    });
//                                });
//                                Snackbar.make(imageView, "You deleted image #" + position, Snackbar.LENGTH_LONG)
//                                        .setAction("UNDO", click -> {
//                                            images.add(position, image);
//                                            runOnUiThread(() -> {
//                                                //on UI thread
//                                                myAdapter.notifyDataSetChanged(); //update rows
//                                            });
//                                        })
//                                        .show();
//                            })
//                            .create().show();
//                });
//                imageView = itemView.findViewById(R.id.bearImageView);
//            }
//        }


        savedImagesButton.setOnClickListener(v -> {
            Intent intent = new Intent(BearImageMainActivity.this, SavedImageActivity.class);
            startActivity(intent);
        });
    }
}