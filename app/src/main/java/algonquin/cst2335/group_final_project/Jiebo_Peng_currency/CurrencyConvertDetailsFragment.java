package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.group_final_project.databinding.OneCurrencyConvertDetailsBinding;

public class CurrencyConvertDetailsFragment extends Fragment {
    CurrencyConvert selected;

    public CurrencyConvertDetailsFragment(CurrencyConvert cc){
        selected = cc;
    }

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
