package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.group_final_project.databinding.OneCurrencyConvertDetailsBinding;

/**
 * The CurrencyConvertDetailsFragment is a Fragment that displays detailed information about a selected
 * CurrencyConvert object. It is designed to be used as part of an Android UI to show the details of a specific
 * currency conversion record.
 * @author Jiebo Peng
 * @version 1.0
 */
public class CurrencyConvertDetailsFragment extends Fragment {
    CurrencyConvert selected;

    /**
     * argument constructor
     * @param cc a CurrencyConvert object
     */
    public CurrencyConvertDetailsFragment(CurrencyConvert cc){
        selected = cc;
    }

    /**
     * this function bing the widgets in the CurrencyConvertDetailsFragment and set value that should be show in it.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return a view object
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        OneCurrencyConvertDetailsBinding binding = OneCurrencyConvertDetailsBinding.inflate(inflater);
        binding.fromCurrencyText.setText("From Currency: " + selected.fromCurrency);
        binding.toCurrencyText.setText("To Currency: " + selected.toCurrency);
        binding.fromAmountText.setText("From Amount: " + selected.fromAmount);
        binding.toAmountText.setText("To Amount: " + selected.toAmount);
        binding.databaseID.setText("ID: "+selected.id);

        return binding.getRoot();
    }
}
