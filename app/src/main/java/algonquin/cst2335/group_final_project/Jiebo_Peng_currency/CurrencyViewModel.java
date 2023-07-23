package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CurrencyViewModel extends ViewModel {
    public MutableLiveData<ArrayList<OneCurrencyConvert>> currencyConverts = new MutableLiveData< >();
}
