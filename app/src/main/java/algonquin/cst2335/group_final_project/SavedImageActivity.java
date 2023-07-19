//package algonquin.cst2335.group_final_project;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Room;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import java.util.List;
//
//public class SavedImageActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_saved_image);
//
//        recyclerView = findViewById(R.id.BearRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "image").build();
//        new Thread(() -> {
//            List<BearImage> images = db.bearImageDAO().getAllImages();
//            runOnUiThread(() -> {
//                if (images.isEmpty()) {
//                    Toast.makeText(SavedImageActivity.this, "No saved images", Toast.LENGTH_SHORT).show();
//                } else {
//                    recyclerView.setAdapter(new BearImageAdapter(images));
//                }
//            });
//        }).start();
//    }
//}