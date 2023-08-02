package algonquin.cst2335.group_final_project.Jiayi_Li_flight;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
/**
 * Represents a ViewModel for managing data related to the chat room activity.
 * This ViewModel holds mutable live data objects for managing chat messages and selected messages.
 */
public class ChatRoomViewModel extends ViewModel {
    /**
     * Mutable live data object for managing a list of chat messages.
     */
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData< >();
    /**
     * Mutable live data object for managing the currently selected chat message.
     */
    public MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData<>();
}
