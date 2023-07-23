package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityCurrencyConvertBinding;
import algonquin.cst2335.group_final_project.databinding.OneCurrencyConvertBinding;

public class CurrencyConvertActivity extends AppCompatActivity {

    ActivityCurrencyConvertBinding binding;
    CurrencyViewModel currencyViewModel;
    ArrayList<OneCurrencyConvert> currencyConverts;

    private RecyclerView.Adapter myAdapter;

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCurrencyConvertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String savedFromCurrency = prefs.getString("fromCurrency", "CAD");
        Spinner fromCurrency = binding.fromCurrency;
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) fromCurrency.getAdapter();
        int positionToSet = adapter.getPosition(savedFromCurrency);
        if (positionToSet != -1) {
            fromCurrency.setSelection(positionToSet);
        }

        String savedToCurrency = prefs.getString("toCurrency", "CAD");
        Spinner toCurrency = binding.toCurrency;
        adapter = (ArrayAdapter<String>) toCurrency.getAdapter();
        positionToSet = adapter.getPosition(savedToCurrency);
        if (positionToSet != -1) {
            toCurrency.setSelection(positionToSet);
        }

        String savedAmountText = prefs.getString("amountText", "");
        binding.amountText.setText(savedAmountText);


        currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);

        currencyConverts = currencyViewModel.currencyConverts.getValue();
        if(currencyConverts == null)
        {
            currencyViewModel.currencyConverts.postValue( currencyConverts = new ArrayList<OneCurrencyConvert>());
        }

        binding.saveButton.setOnClickListener(click -> {
            String amountInput = binding.amountText.getText().toString();
            float floatValue = 0;

            if (!amountInput.isEmpty()) {
                floatValue = Float.parseFloat(amountInput);

                OneCurrencyConvert oneCurrencyConvert =
                        new OneCurrencyConvert(
                                binding.fromCurrency.getSelectedItem().toString(),
                                binding.toCurrency.getSelectedItem().toString(),
                                floatValue
                        );

                currencyConverts.add(oneCurrencyConvert);
                myAdapter.notifyItemInserted(currencyConverts.size()-1);
                //clear the previous text
                binding.amountText.setText("");

                Snackbar.make(binding.getRoot(), "Currency convert saved!", Snackbar.LENGTH_LONG).show();
            } else {
                // Handle the case where no input is provided
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning!");
                builder.setMessage("Amount can not be empty!");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    // Dialog dismissed
                });
                builder.show();
            }

        });

        binding.convertButton.setOnClickListener(click -> {
            String amountInput = binding.amountText.getText().toString();
            float floatValue = 0;

            if (!amountInput.isEmpty()) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("fromCurrency", binding.fromCurrency.getSelectedItem().toString());
                editor.putString("toCurrency", binding.toCurrency.getSelectedItem().toString());
                editor.putString("amountText", binding.amountText.getText().toString());
                editor.apply();

                floatValue = Float.parseFloat(amountInput);

                Toast.makeText(CurrencyConvertActivity.this,
                        floatValue + " " + binding.fromCurrency.getSelectedItem().toString() + " equal to ? " + binding.toCurrency.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Handle the case where no input is provided
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning!");
                builder.setMessage("Amount can not be empty!");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    // Dialog dismissed
                });
                builder.show();
            }
        });

        binding.updateButton.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Hello!");
            builder.setMessage("Update function will be implemented!");
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Dialog dismissed
            });
            builder.show();
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    OneCurrencyConvertBinding binding = OneCurrencyConvertBinding.inflate(getLayoutInflater(),parent,false);
                    return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                OneCurrencyConvert obj = currencyConverts.get(position);
                holder.messageText.setText(obj.getAmount() + " " + obj.getFromCurrency() + " equal to ? " + obj.getToCurrency());
            }

            @Override
            public int getItemCount() {
                return currencyConverts.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });
    }
}