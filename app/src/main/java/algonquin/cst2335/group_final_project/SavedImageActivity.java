package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import algonquin.cst2335.group_final_project.R;

/**
 * This class maintains the saved images inside the RecyclerView
 */
public class SavedImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BearImageDAO bearImageDAO;

    /**
     * creates the database of the image list
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_image);

        recyclerView = findViewById(R.id.BearRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database
        BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "image").build();
        bearImageDAO = db.bearImageDAO();
        new Thread(() -> {
            List<BearImage> images = db.bearImageDAO().getAllImages();
            runOnUiThread(() -> {
                if (images.isEmpty()) {
                    Toast.makeText(SavedImageActivity.this, "No saved images", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerView.setAdapter(new BearImageAdapter(images, bearImageDAO));
                }
            });
        }).start();
    }
}
