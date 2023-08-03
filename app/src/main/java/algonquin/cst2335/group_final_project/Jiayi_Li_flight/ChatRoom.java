/**
 * Represents a chat room activity where users can send and receive messages.
 */
package algonquin.cst2335.group_final_project.Jiayi_Li_flight;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_final_project.MainActivity;
import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.group_final_project.databinding.ReceiveMessageBinding;
import algonquin.cst2335.group_final_project.databinding.SentMessageBinding;
/**
 * Represents a chat room activity where users can send and receive messages.
 */
public class ChatRoom extends AppCompatActivity {


    /**
     * The binding for the activity's layout.
     */
    public ActivityChatRoomBinding binding;
    /**
     * The list of chat messages.
     */
    public ArrayList<ChatMessage> messages = new ArrayList<>();
    /**
     * The ViewModel for managing chat room data.
     */
    public ChatRoomViewModel chatModel ;
    /**
     * The RecyclerView adapter for displaying chat messages.
     */
    public RecyclerView.Adapter myAdapter;
    /**
     * Shared preferences for storing user data.
     */
    SharedPreferences prefs;
    /**
     * Temporary variable to store the position of a selected message.
     */
    public int postionTemp;
    /**
     * The currently selected chat message.
     */
    public ChatMessage selected;
    /**
     * The request queue for making API requests.
     */
    protected RequestQueue queue;
    /**
     * The base URL for the flight API.
     */
    public String stringURL;
    /**
     * The airport code for flight status lookup.
     */
    String airportCode;

    /**
     * Called when the activity is first created. Initializes the chat room activity,
     * including setting up the UI, handling button clicks, and observing changes in ViewModel.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity, if any.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //这两个必须放在onCreate里😅😅😅😅😅😅😅😅😅😅😅😅😅😅😅😅😅😅
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        ChatMessageDAO mDAO = db.cmDAO();
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String searchHistory = prefs.getString("searchHistory", "");


        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
        }
        binding.showListButton.setOnClickListener(clickButton->{

            messages.clear();
            myAdapter.notifyDataSetChanged();


            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });

        });

        binding.helpButton.setOnClickListener(onononclick -> {
                    String helpMessage =
                            "Welcome to our app! Here are some instructions on how to use the interface:\n\n" +
                            "1. Click on the buttons to explore features.\n" +
                            "2. Select an item from the list history to view its details.\n" +
                            "3. Use the back button to go back to the previous screen.\n" +
                            "4. Click on the show button to see the saved items.\n";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(helpMessage)
                    .setTitle("Help")
                    .setPositiveButton( "Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(click -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("searchHistory", binding.textInput.getText().toString());
            editor.apply();


            String message = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());


//            airportCode = binding.textInput.getText().toString();
//
//
//            stringURL = "http://api.aviationstack.com/v1/flights?access_key=49303af5d63fb4780a6467075b9d721b&dep_iata=" + airportCode;
//
//            queue = Volley.newRequestQueue(this);
//            JsonObjectRequest request = new JsonObjectRequest(
//                    Request.Method.GET,
//                    stringURL,
//                    null,
//                    response -> {
//                        try {
//                            JSONArray dataArray = response.getJSONArray("data");
//                            JSONObject position0 = dataArray.getJSONObject(0);
//
//                            String flight_status = position0.getString("flight_status");
//
//                            runOnUiThread(() -> {
//                                Toast.makeText(ChatRoom.this, flight_status, Toast.LENGTH_SHORT).show();
//                                ChatMessage chatMessage = new ChatMessage(flight_status, currentDateandTime, true);
//                                messages.add(chatMessage);
//                                myAdapter.notifyDataSetChanged();
//                                binding.textInput.setText("");
//                                    });
//
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    },
//                    error -> {
//                        Toast.makeText(ChatRoom.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
//                    });
//            queue.add(request);



            ChatMessage chatMessage = new ChatMessage(message, currentDateandTime, true);
//            Executor thread = Executors.newSingleThreadExecutor();
//            thread.execute(() ->
//            {
//                mDAO.insertMessage(chatMessage);
//            });

            messages.add(chatMessage);
            myAdapter.notifyDataSetChanged();
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            String message = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(message, currentDateandTime, false);



            messages.add(chatMessage);
            myAdapter.notifyDataSetChanged();
            binding.textInput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder( binding.getRoot() );
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder( binding.getRoot() );
                }


            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messages.get(position);
                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage chatMessage = messages.get(position);
                if (chatMessage.getIsSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        chatModel.selectedMessage.observe(this, newMessageValue -> {
            // Handle selected message change
            // Create a new fragment to show the details for the selected message

            if(selected.getIsSentButton()){
                MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue,messages,postionTemp, myAdapter, mDAO);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, chatFragment)
                        .addToBackStack("")
                        .commit();
            }else{
                SavedMessageDetailsFragment chatFragment = new SavedMessageDetailsFragment(newMessageValue,messages,postionTemp, myAdapter, mDAO);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, chatFragment)
                        .addToBackStack("")
                        .commit();
            }

        });

    }
    /**
     * Called when the activity is becoming visible to the user. Loads and displays the user's
     * search history from shared preferences, ensuring the input field is populated with the
     * last entered search query.
     */
    @Override
    public void onStart(){
        super.onStart();
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String searchHistory = prefs.getString("searchHistory", "");
        binding.textInput.setText(searchHistory);
    }
    /**
     * Called when the activity is resumed from a paused state. Performs any necessary actions
     * to resume the activity's functionality, such as refreshing the UI or updating data.
     */
    @Override
    public void onResume() {
        super.onResume();
    }
    /**
     * ViewHolder class for displaying individual chat message rows in the RecyclerView.
     * Responsible for binding the UI elements and handling item click events.
     */
    public class MyRowHolder extends RecyclerView.ViewHolder {
        /**
         * TextView for displaying the message content.
         */
        TextView messageText;
        /**
         * TextView for displaying the timestamp of the message.
         */
        TextView timeText;
        /**
         * Constructs a new instance of the MyRowHolder class.
         *
         * @param itemView The root view of the individual chat message row.
         */
        public MyRowHolder( View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk ->{

                int position = getAbsoluteAdapterPosition();

                /*
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this);

                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                        .setTitle("Question: ")
                        .setNegativeButton("No", (dialog, cl) -> { })
                        .setPositiveButton( "Yes", (dialog, cl) -> {

                            ChatMessage removedMessage = messages.get(position);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved (position);

                            Snackbar.make(messageText, "You deleted message #"+ position, Snackbar.LENGTH_LONG)
                                    .setAction( "Undo", click -> {
                                        messages.add(position, removedMessage);
                                        myAdapter.notifyItemInserted (position);
                                    })
                                    .show();
                        })
                        .create().show();*/
                postionTemp = position;
                selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);




            });



            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);



        }


    }


}