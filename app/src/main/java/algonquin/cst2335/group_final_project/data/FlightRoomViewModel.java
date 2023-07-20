package algonquin.cst2335.group_final_project.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.group_final_project.JiayiLi_Flight_Part.FlightMessage;

public class FlightRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<FlightMessage>> messages = new MutableLiveData< >();
    public MutableLiveData<FlightMessage> selectedMessage = new MutableLiveData<>();
}
