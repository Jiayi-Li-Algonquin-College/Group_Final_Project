package algonquin.cst2335.group_final_project.Jiayi_Li_flight;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_final_project.databinding.DetailsLayoutBinding;
/**
 * A fragment that displays details of a selected chat message, including its content, timestamp,
 * and database ID. Provides functionality for saving changes and deleting the message.
 */
public class MessageDetailsFragment extends Fragment {
    /**
     * The selected chat message to display details for.
     */
    ChatMessage selected;
    /**
     * The list of chat messages.
     */
    ArrayList<ChatMessage> messages;
    /**
     * The position of the selected message in the list.
     */
    public int postionTemp;
    /**
     * The RecyclerView adapter for the chat messages.
     */
    public RecyclerView.Adapter myAdapter;
    /**
     * The DAO for performing database operations on chat messages.
     */
    ChatMessageDAO mDAO;

    /**
     * Constructs a new instance of the MessageDetailsFragment class.
     *
     * @param m The selected chat message to display details for.
     * @param messages The list of chat messages.
     * @param postionTemp The position of the selected message in the list.
     * @param myAdapter The RecyclerView adapter for the chat messages.
     * @param mDAO The DAO for performing database operations on chat messages.
     */
    public MessageDetailsFragment (ChatMessage m, ArrayList<ChatMessage> messages, int postionTemp, RecyclerView.Adapter myAdapter, ChatMessageDAO mDAO) {

        selected = m;
        this.messages = messages;
        this.postionTemp = postionTemp;
        this.myAdapter = myAdapter;
        this.mDAO = mDAO;
    }
    /**
     * Called when the fragment view is being created. Inflates the view, displays details of
     * the selected chat message, and provides functionality for saving changes and deleting
     * the message.
     *
     * @param inflater The LayoutInflater used to inflate the view.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     * @return The inflated view containing the chat message details.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText( selected.message );
        binding.timeText.setText(selected.timeSent);
        binding.databaseText.setText("Id = " + selected.id);

//        binding.saveButton.setOnClickListener(click -> {
////            Toast.makeText(getActivity(), "This is my Toast message!",
////                    Toast.LENGTH_LONG).show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//
//            builder.setMessage("Do you want to delete the message: " + binding.messageText.getText())
//                    .setTitle("Question: ")
//                    .setNegativeButton("No", (dialog, cl) -> { })
//                    .setPositiveButton( "Yes", (dialog, cl) -> {
//
//                        ChatMessage removedMessage = messages.get(postionTemp);
//                        messages.remove(postionTemp);
//                        myAdapter.notifyItemRemoved (postionTemp);
//
//                        Executor thread = Executors.newSingleThreadExecutor();
//                        thread.execute(() ->
//                        {
//                            mDAO.deleteMessage(selected);
//                        });
//
//
//
//                        Snackbar.make(binding.messageText, "You deleted message #"+ postionTemp, Snackbar.LENGTH_LONG)
//                                .setAction( "Undo", clicked -> {
//                                    messages.add(postionTemp, removedMessage);
//                                    myAdapter.notifyItemInserted (postionTemp);
//
//                                    Executor anotherThread = Executors.newSingleThreadExecutor();
//                                    anotherThread.execute(() ->
//                                    {
//                                        mDAO.insertMessage(removedMessage);
//                                    });
//
//                                })
//                                .show();
//                    })
//                    .create().show();
//
//        });

        binding.testForRealSaveButton.setOnClickListener(realClick -> {
            Toast.makeText(getActivity(), "You just clicked the save button!",
                    Toast.LENGTH_LONG).show();
            selected.setSentButton(false);


            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(selected);
            });



        });






        return binding.getRoot();
    }


}