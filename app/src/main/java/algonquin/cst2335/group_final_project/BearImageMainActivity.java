package algonquin.cst2335.group_final_project.Yueying_Li_bear;

// ... your import statements ...

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import algonquin.cst2335.group_final_project.R;

/**
 * This class is the main activity class to do the main logic of the project
 */
public class BearImageMainActivity extends AppCompatActivity {

    private EditText widthEditText;
    private EditText heightEditText;
    private Button generateButton;

    private RecyclerView recyclerView;
    private BearImageAdapter bearImageAdapter;
    private BearImageDAO bearImageDAO;
    private FrameLayout fragmentContainer;

    private GestureDetector gestureDetector;
    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // When user double taps on an item, show the details in a new fragment
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                int position = recyclerView.getChildAdapterPosition(child);
                BearImage image = bearImageAdapter.getBearImageAtPosition(position);
                BearImageDetailFragment fragment = BearImageDetailFragment.newInstance(Integer.toString(image.getId()), image.getUrl());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
                fragmentContainer.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            return true;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear_image_main);

        widthEditText = findViewById(R.id.bearWidthEditText);
        heightEditText = findViewById(R.id.bearHeightEditText);
        generateButton = findViewById(R.id.bearGenerateButton);

        recyclerView = findViewById(R.id.bearImagesRecyclerView);
        fragmentContainer = findViewById(R.id.fragment_container);

        // Initially hide the FrameLayout
        fragmentContainer.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        bearImageAdapter = new BearImageAdapter(this);
//        recyclerView.setAdapter(bearImageAdapter);

        // Initialize the database
        BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "image").build();
        bearImageDAO = db.bearImageDAO();

        // Clear the database of old images
        new Thread(() -> bearImageDAO.deleteAllImages()).start();

        // Initialize an empty list of BearImage objects
        List<BearImage> initialImageList = new ArrayList<>();

        // Create a new BearImageAdapter with the empty list
        bearImageAdapter = new BearImageAdapter(initialImageList, bearImageDAO);
        recyclerView.setAdapter(bearImageAdapter);

        // Initialize gesture detector
        gestureDetector = new GestureDetector(this, gestureListener);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return gestureDetector.onTouchEvent(e);
            }
        });

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
            /**
             * manipulates the clicking feature to generate an image from the http
             * and to display the image on the screen
             * @param v The view that was clicked.
             */
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

                // Use Volley to fetch the image
                RequestQueue requestQueue = Volley.newRequestQueue(BearImageMainActivity.this);
                ImageRequest request = new ImageRequest(url,
                        new Response.Listener<Bitmap>() {
                            /**
                             * responses from the retrieving images from http
                             * @param bitmap the bitmap of the http
                             */
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                // This method will be invoked when the image is successfully fetched
                                // Save the image to the database and update the RecyclerView
                                new Thread(() -> {
                                    BearImage newImage = new BearImage(url);
                                    bearImageDAO.insert(newImage);
                                    List<BearImage> allImages = bearImageDAO.getAllImages();
                                    runOnUiThread(() -> {
                                        bearImageAdapter.updateImages(allImages);
                                        Toast.makeText(BearImageMainActivity.this, "Image saved and displayed", Toast.LENGTH_SHORT).show();
                                    });
                                }).start();
                                // After successfully fetching and saving the image,
                                // hide the FrameLayout and show the RecyclerView
                                fragmentContainer.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER, null,
                        new Response.ErrorListener() {
                            /**
                             * responses an error message about failing to retrieve images from http
                             * @param error the error of Volley of the http
                             */
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // This method will be invoked if there was an error fetching the image
                                Toast.makeText(BearImageMainActivity.this, "Failed to fetch image", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Add the image request to the Volley request queue
                requestQueue.add(request);

                // Save the image to the database and update the RecyclerView
//                new Thread(() -> {
//                    BearImage newImage = new BearImage(url);
//                    bearImageDAO.insert(newImage);
//                    List<BearImage> allImages = bearImageDAO.getAllImages();
//                    runOnUiThread(() -> {
//                        bearImageAdapter.updateImages(allImages);
//                        Toast.makeText(BearImageMainActivity.this, "Image saved and displayed", Toast.LENGTH_SHORT).show();
//                    });
//                }).start();

            }
        });
    }

    // Inflate the menu; this adds items to the action bar if it is present.

    /**
     * inflates the menu items and adds the items to the action bar
     * @param menu The options menu in which you place your items.
     *
     * @return a boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bear_menu, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.

    /**
     * handles the clicking of the action bar item
     * @param item The menu item that was selected.
     *
     * @return a boolean true or a selected image
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bear_help) {
            showHelpDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.bear_help_title)
                .setMessage(R.string.bear_help_message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    /**
     * manually manipulates the going back to the previous page
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}




//package algonquin.cst2335.group_final_project.Yueying_Li_bear;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
////import androidx.recyclerview.widget.RecyclerView.*;
//import androidx.room.Room;
//
//import android.content.ClipData;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.Nullable;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.google.android.material.snackbar.Snackbar;
//import com.squareup.picasso.Picasso;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.ImageRequest;
//import com.android.volley.toolbox.Volley;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import algonquin.cst2335.group_final_project.R;
//import algonquin.cst2335.group_final_project.databinding.ActivityBearImageMainBinding;
////import algonquin.cst2335.group_final_project.databinding.*;
////import algonquin.cst2335.group_final_project.databinding.ActivityChatRoomBinding;
//
//public class BearImageMainActivity extends AppCompatActivity {
//
//    private EditText widthEditText;
//    private EditText heightEditText;
//    private ImageView imageView;
//    private Button generateButton;
//    private Button savedImagesButton;
//
////    private RecyclerView.Adapter myAdapter;
////    private ActivityBearImageMainBinding binding;
//
//    ArrayList<BearImage> images;
//
////    //new **
//    private RecyclerView recyclerView;
//    private BearImageAdapter bearImageAdapter;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bear_image_main);
//
//        widthEditText = findViewById(R.id.bearWidthEditText);
//        heightEditText = findViewById(R.id.bearHeightEditText);
//        imageView = findViewById(R.id.bearImageView);
//        generateButton = findViewById(R.id.bearGenerateButton);
//        savedImagesButton = findViewById(R.id.bearViewImagesButton);
//
////        //new **
////        recyclerView = findViewById(R.id.recyclerView);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        bearImageAdapter = new BearImageAdapter(this);
////        recyclerView.setAdapter(bearImageAdapter);
//
//
//
////        binding = ActivityBearImageMainBinding.inflate(getLayoutInflater());
////        View view = binding.getRoot();
////        setContentView(binding.getRoot());
//
////        //new **
////        //set up RecyclerView
////        bearImageAdapter = new BearImageAdapter(new BearImageAdapter.ImageDiff());
////        recyclerView.setAdapter(bearImageAdapter);
//
////        //new **
////        // Set up ViewModel
////        bearImageViewModel = new ViewModelProvider(this).get(BearImageViewModel.class);
////        allImages = bearImageViewModel.getAllImages();
//
////        allImages.observe(this, new Observer<List<BearImage>>() {
////            @Override
////            public void onChanged(@Nullable final List<BearImage> images) {
////                // Update the cached copy of the images in the adapter.
////                bearImageAdapter.setImages(images);
////            }
////        });
////            // Update the cached copy of the images in the adapter.
////            bearImageAdapter.submitList(images);
////        });
//
//        // Restore the last entered dimensions from SharedPreferences
//        SharedPreferences preferences = getSharedPreferences("BearImageGenerator", MODE_PRIVATE);
//        widthEditText.setText(preferences.getString("lastWidth", ""));
//        heightEditText.setText(preferences.getString("lastHeight", ""));
//
//        // Hide the ActionBar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
//
//        generateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String width = widthEditText.getText().toString();
//                String height = heightEditText.getText().toString();
//
//                if (width.isEmpty() || height.isEmpty()) {
//                    Toast.makeText(BearImageMainActivity.this, "Please enter both dimensions", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Save the dimensions to SharedPreferences
//                preferences.edit()
//                        .putString("lastWidth", width)
//                        .putString("lastHeight", height)
//                        .apply();
//
//                // Generate and display the image
//                String url = "https://placebear.com/" + width + "/" + height;
//                Picasso.get().load(url).into(imageView);
//
//                // Use Volley to fetch the image
//                RequestQueue queue = Volley.newRequestQueue(BearImageMainActivity.this);
//                ImageRequest imageRequest = new ImageRequest(url,
//                        response -> imageView.setImageBitmap(response), 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
//                        error -> Toast.makeText(BearImageMainActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show());
//
//                queue.add(imageRequest);
//
//                // Save the image to the database
//                BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "image").build();
//                new Thread(() -> {
//                    BearImageDAO.insert(new BearImage(url));
//                    runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
//                }).start();
//
//                 //When the image is clicked, show a dialog asking if the user wants to save or delete it
//                 imageView.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View v) {
//                         new AlertDialog.Builder(BearImageMainActivity.this)
//                                 .setTitle("Image Options")
//                                 .setMessage("Do you want to delete this image?")
//                                 .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                     public void onClick(DialogInterface dialog, int which) {
//                                         // Handler for undo operation
//                                         final Handler handler = new Handler();
//                                         final Runnable runnable = new Runnable() {
//                                             @Override
//                                             public void run() {
//                                                 // Delete the image from the database
//                                                 new Thread(() -> {
//                                                     BearImageDAO.deleteBearImage(new BearImage(url));
//                                                     runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image deleted", Toast.LENGTH_SHORT).show());
//                                                 }).start();
//                                             }
//                                         };
//
//                                         // Post the task to run after 3 seconds (3000 ms)
//                                         handler.postDelayed(runnable, 3000);
//
//                                         Snackbar snackbar = Snackbar.make(v, "Image will be deleted", Snackbar.LENGTH_LONG);
//                                         snackbar.setAction("UNDO", new View.OnClickListener() {
//                                             @Override
//                                             public void onClick(View v) {
//                                                 handler.removeCallbacks(runnable);
//                                                 Toast.makeText(BearImageMainActivity.this, "Image delete operation cancelled", Toast.LENGTH_SHORT).show();
//                                             }
//                                         });
//                                         snackbar.setDuration(10000); // Here 10000 milliseconds is 10 seconds.
//                                         snackbar.show();
////                                         // Save the image to the database
////                                         new Thread(() -> {
////                                             BearImageDAO.insert(new BearImage(url));
////                                             runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image deleted", Toast.LENGTH_SHORT).show());
////                                         }).start();
//                                     }
//                                 })
//                                 .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                     public void onClick(DialogInterface dialog, int which) {
//                                         // Save the image to the database
//                                         new Thread(() -> {
//                                             BearImageDAO.insert(new BearImage(url));
//                                             runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
//                                         }).start();
//                                         // Delete the image from the database
////                                         new Thread(() -> {
////                                             BearImageDAO.deleteBearImage(new BearImage(url));
////                                             runOnUiThread(() -> Toast.makeText(BearImageMainActivity.this, "Image saved", Toast.LENGTH_SHORT).show());
////                                         }).start();
//                                     }
//                                 })
//                                 .setNeutralButton("Cancel", null)
//                                 .show();
//                     }
//                 });
//            }
//        });
//
//        savedImagesButton.setOnClickListener(v -> {
//            Intent intent = new Intent(BearImageMainActivity.this, SavedImageActivity.class);
//            startActivity(intent);
//        });
//    }
//
//    // Inflate the menu; this adds items to the action bar if it is present.
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.bear_menu, menu);
//        return true;
//    }
//
//    // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.bear_help) {
//            showHelpDialog();
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
////        switch (item.getItemId()) {
////            case R.id.bear_help:
////                showHelpDialog();
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void showHelpDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle(R.string.bear_help_title)
//                .setMessage(R.string.bear_help_message)
//                .setPositiveButton(android.R.string.ok, null)
//                .show();
//    }
//}
