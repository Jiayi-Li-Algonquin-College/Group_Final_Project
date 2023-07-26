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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityCurrencyConvertBinding;
import algonquin.cst2335.group_final_project.databinding.OneCurrencyConvertBinding;

public class CurrencyConvertActivity extends AppCompatActivity {

    ActivityCurrencyConvertBinding binding;
    CurrencyViewModel currencyViewModel;
    ArrayList<OneCurrencyConvert> currencyConverts;

    private RecyclerView.Adapter myAdapter;

    protected RequestQueue queue = null;

    private void getCurrencyConvertResult(String amountText, String fromCurrency, String toCurrency) {
        String stringURL = "https://api.getgeoapi.com/v2/currency/convert?format=json&from=" +
                fromCurrency +
                "&to=" +
                toCurrency +
                "&amount=" +
                amountText +
                "&api_key=2ae0c02cd1f783b00ce904cb3e50aed618e03b77&format=json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                response -> {
                    try {
                        JSONObject rates = response.getJSONObject("rates");
                        JSONObject rates_to_currency = rates.getJSONObject(toCurrency);
                        String rate_for_amount = rates_to_currency.getString("rate_for_amount");

                        runOnUiThread( (  )  -> {
                            binding.convertedResult.setText(rate_for_amount);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });

        queue.add(request);
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.currency_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if( item.getItemId() == R.id.item_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Help!");
            builder.setMessage("Usage Summary: \n" +
            "1. Input amount in the text field \n" +
            "2. Select which currency convert from \n" +
            "3. Select which currency convert to \n" +
            "4. Click CONVERT button to get result");
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Dialog dismissed
            });
            builder.show();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        binding = ActivityCurrencyConvertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.currencyToolbar);

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

            if (!amountInput.isEmpty()) {

                getCurrencyConvertResult(binding.amountText.getText().toString(),
                                binding.fromCurrency.getSelectedItem().toString(),
                                binding.toCurrency.getSelectedItem().toString());


                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("fromCurrency", binding.fromCurrency.getSelectedItem().toString());
                editor.putString("toCurrency", binding.toCurrency.getSelectedItem().toString());
                editor.putString("amountText", binding.amountText.getText().toString());
                editor.apply();

                Toast.makeText(CurrencyConvertActivity.this,
                        amountInput + " " + binding.fromCurrency.getSelectedItem().toString() +
                                " convert to " +
                                binding.toCurrency.getSelectedItem().toString() + " successfully",
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
                holder.messageText.setText(obj.getAmount() + " " + obj.getFromCurrency() + " equal to TBD " + obj.getToCurrency());
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