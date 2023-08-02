package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
/**
 The CurrencyViewModel class is a subclass of Android's ViewModel class that holds and manages the data related
 * to currency conversions. It provides an observable MutableLiveData to hold a list of CurrencyConvert objects.
 * This class acts as a ViewModel, which means it survives configuration changes and retains its data during
 * configuration changes (e.g., screen rotations) to avoid data loss and unnecessary data re-fetching from sources.
 * @author Jiebo Peng
 * @version 1.0
 */
public class CurrencyViewModel extends ViewModel {
    /**
     * The MutableLiveData that holds an ArrayList of CurrencyConvert objects.
     */

    public MutableLiveData<ArrayList<CurrencyConvert>> currencyConverts = new MutableLiveData< >();
}
