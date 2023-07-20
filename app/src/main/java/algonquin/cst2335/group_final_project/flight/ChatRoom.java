package algonquin.cst2335.group_final_project.flight;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.group_final_project.databinding.ReceiveMessageBinding;
import algonquin.cst2335.group_final_project.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {



    public ActivityChatRoomBinding binding;
    public ArrayList<ChatMessage> messages = new ArrayList<>();
    public ChatRoomViewModel chatModel ;
    public RecyclerView.Adapter myAdapter;
    SharedPreferences prefs;
    public int postionTemp;
    public ChatMessage selected;



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




        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(click -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("searchHistory", binding.textInput.getText().toString());
            editor.apply();


            String message = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(message, currentDateandTime, true);
            /*------------------------------------------------------------------
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(chatMessage);
            });
            */
            //------------------------------------------------------------------

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

    @Override
    public void onStart(){
        super.onStart();
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String searchHistory = prefs.getString("searchHistory", "");
        binding.textInput.setText(searchHistory);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
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