package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_final_project.R;
import algonquin.cst2335.group_final_project.databinding.ActivityCurrencyConvertBinding;
import algonquin.cst2335.group_final_project.databinding.OneCurrencyConvertBinding;
/**
 * This class represents the Currency Convert Activity, which allows users to convert currencies.
 */
public class CurrencyConvertActivity extends AppCompatActivity {
    /** Assgined -1 to constant INVALID_INDEX */
    private final int INVALID_INDEX = -1;
    /** the reference of the object with CurrencyConvertDao */
    CurrencyConvertDao myDAO;
    /** the reference of the object with ActivityCurrencyConvertBinding used to bing id */
    ActivityCurrencyConvertBinding binding;
    /** the reference of the object with CurrencyViewModel */
    CurrencyViewModel currencyViewModel;
    /** the ArrayList of currency convert objects */
    ArrayList<CurrencyConvert> currencyConverts;
    /** selected position */
    int selectedPosition = INVALID_INDEX;
    /** the reference of the object with RecyclerView.Adapter that related date with RecyclerView */
    private RecyclerView.Adapter myAdapter;

    //private Menu mMenu;
    /**The RequestQueue used for handling network requests in this activity. */
    protected RequestQueue queue = null;

    /**
     * Gets the currency conversion result for the given CurrencyConvert object.
     * This method sends a network request to a currency conversion API and updates the CurrencyConvert
     * object with the conversion result.
     * @param cc The CurrencyConvert object containing the currency conversion details.
     * @param arrayIndex The array index of the CurrencyConvert object in the list (or INVALID_INDEX if not applicable).
     */
    private void getCurrencyConvertResult(CurrencyConvert cc, int arrayIndex) {
        String stringURL = "https://api.getgeoapi.com/v2/currency/convert?format=json&from=" +
                cc.fromCurrency +
                "&to=" +
                cc.toCurrency +
                "&amount=" +
                cc.fromAmount +
                "&api_key=2ae0c02cd1f783b00ce904cb3e50aed618e03b77&format=json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                response -> {
                    try {
                        JSONObject rates = response.getJSONObject("rates");
                        JSONObject rates_to_currency = rates.getJSONObject(cc.toCurrency);
                        cc.toAmount = rates_to_currency.getString("rate_for_amount");
                        // when you click convert button
                        if (arrayIndex == INVALID_INDEX ) {
                            runOnUiThread( (  )  -> {
                                binding.convertedResult.setText(cc.toAmount);
                            });
                        }
                        // when you click update button
                        else {
                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() ->
                            {
                                myDAO.updateConvert(cc);
                            });

                            runOnUiThread( (  )  -> {
                                myAdapter.notifyItemChanged(arrayIndex);
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });

        queue.add(request);
    }
    /**
     * ViewHolder class used in the RecyclerView to display currency conversion data.
     * This class holds references to the views for a single row in the RecyclerView.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        /**the currency need to be converted */
        TextView fromAmountText;
        /**the amount of converted currency*/
        TextView toAmountText;
        /**the amount of currency need to be converted*/
        TextView fromCurrencyText;
        /**the converted currency*/
        TextView toCurrencyText;
        /**the image of currency need to be converted */
        ImageView fromCurrencyImg;
        /**the image of converted currency*/
        ImageView toCurrencyImg;
        /**
         * Constructs a new MyRowHolder with the provided itemView.
         * @param itemView The View representing a single row in the RecyclerView.
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            fromAmountText = itemView.findViewById(R.id.fromCurrencyAmountText);
            toAmountText = itemView.findViewById(R.id.toCurrencyAmountText);
            fromCurrencyText = itemView.findViewById(R.id.fromCurrencyText);
            toCurrencyText = itemView.findViewById(R.id.toCurrencyText);
            fromCurrencyImg = itemView.findViewById(R.id.fromCurrencyImageView);
            toCurrencyImg = itemView.findViewById(R.id.toCurrencyImageView);

            itemView.setOnClickListener(clk -> {
                selectedPosition = getAbsoluteAdapterPosition();
                CurrencyConvert selected = currencyConverts.get(selectedPosition);

                /*MenuItem menuItem = mMenu.findItem(R.id.item_delete);
                menuItem.setVisible(true);*/

                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                CurrencyConvertDetailsFragment chatFragment = new CurrencyConvertDetailsFragment(selected);
                tx.replace(R.id.fragmentLocation, chatFragment);
                tx.addToBackStack("anything here");
                tx.commit();
            });
        }
    }
    /**
     * Initialize the contents of the Activity's options menu.
     * This method is called during the creation of the menu to populate it with items.
     * @param menu The options menu in which you place your items.
     * @return true to display the menu; false to prevent it from being displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.currency_menu, menu);
        //mMenu = menu;

        return true;
    }
    /**
     * Handles the options menu item selection.
     * @param item The selected MenuItem.
     * @return true if the event was handled successfully; false otherwise.
     */
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
        else if (item.getItemId() == R.id.item_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (selectedPosition == INVALID_INDEX) {
                builder.setTitle("Warning!");
                builder.setMessage("Please select a item to delete!");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    // Dialog dismissed
                });
                builder.show();
            }
            else {
                int positionToDelete = selectedPosition;
                CurrencyConvert removedCurrencyConvert = currencyConverts.get(positionToDelete);
                builder.setMessage("Do you want to delete the convert: " + removedCurrencyConvert.id)
                        .setTitle("Question:")
                        .setNegativeButton("No", (dialog, cl) -> {
                        })
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() ->
                            {
                                myDAO.deleteConvert(removedCurrencyConvert);
                            });
                            currencyConverts.remove(positionToDelete);
                            myAdapter.notifyItemRemoved(positionToDelete);

                            Snackbar.make(binding.getRoot(), "You deleted message #" + positionToDelete, Snackbar.LENGTH_LONG)
                                    .show();

                            /*MenuItem menuItem = mMenu.findItem(R.id.item_delete);
                            menuItem.setVisible(false);*/

                            // Reset to unselected value -1
                            selectedPosition = INVALID_INDEX;
                        })
                        .create().show();
            }
        }

        return true;
    }
    /**
     * This method is called when the activity is created. It sets up the layout, views, adapters, and click listeners.
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        binding = ActivityCurrencyConvertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load Menu toolbar
        setSupportActionBar(binding.currencyToolbar);

        // Load saved date from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedFromCurrency = prefs.getString("fromCurrency", "CAD");
        Spinner fromCurrency = binding.fromCurrency;
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) fromCurrency.getAdapter();
        int positionToSet = adapter.getPosition(savedFromCurrency);
        if (positionToSet != INVALID_INDEX) {
            fromCurrency.setSelection(positionToSet);
        }
        String savedToCurrency = prefs.getString("toCurrency", "CAD");
        Spinner toCurrency = binding.toCurrency;
        adapter = (ArrayAdapter<String>) toCurrency.getAdapter();
        positionToSet = adapter.getPosition(savedToCurrency);
        if (positionToSet != INVALID_INDEX) {
            toCurrency.setSelection(positionToSet);
        }
        String savedAmountText = prefs.getString("amountText", "");
        binding.amountText.setText(savedAmountText);

        // Load database and DAO
        CurrencyConvertDatabase db = Room.databaseBuilder(getApplicationContext(), CurrencyConvertDatabase.class, "MyCurrencyConvertDatabase").build();
        myDAO = db.ccDao();

        // Prepare "currencyConverts"
        currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        currencyConverts = currencyViewModel.currencyConverts.getValue();
        if(currencyConverts == null)
        {
            currencyViewModel.currencyConverts.postValue( currencyConverts = new ArrayList<CurrencyConvert>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                currencyConverts.addAll( myDAO.getAllConverts() );
            });
        }

        // Setup recycleView
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
                CurrencyConvert obj = currencyConverts.get(position);
                holder.fromAmountText.setText(obj.getFromAmount());
                holder.toAmountText.setText(obj.getToAmount());
                holder.fromCurrencyText.setText(obj.getFromCurrency());
                holder.toCurrencyText.setText(obj.getToCurrency());

                int fromImg = getResources().getIdentifier(obj.getFromCurrency().toLowerCase(), "drawable", getPackageName());
                holder.fromCurrencyImg.setImageResource(fromImg);
                int toImg = getResources().getIdentifier(obj.getToCurrency().toLowerCase(), "drawable", getPackageName());
                holder.toCurrencyImg.setImageResource(toImg);
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

        // Setup Buttons listener function
        binding.convertButton.setOnClickListener(click -> {
            String amountInput = binding.amountText.getText().toString();
            String fromCurrencyText = binding.fromCurrency.getSelectedItem().toString();
            String toCurrencyText = binding.toCurrency.getSelectedItem().toString();

            if (!amountInput.isEmpty()) {
                CurrencyConvert newCc = new CurrencyConvert(fromCurrencyText, toCurrencyText, amountInput, "");

                getCurrencyConvertResult(newCc, INVALID_INDEX);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("fromCurrency", fromCurrencyText);
                editor.putString("toCurrency", toCurrencyText);
                editor.putString("amountText", amountInput);
                editor.apply();

                Toast.makeText(CurrencyConvertActivity.this,
                        amountInput + " " + fromCurrencyText + " convert to " + toCurrencyText + " successfully",
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

        binding.saveButton.setOnClickListener(click -> {
            String amountInput = binding.amountText.getText().toString();
            String convertedResult = binding.convertedResult.getText().toString();

            if (!amountInput.isEmpty() && !convertedResult.isEmpty() ) {
                CurrencyConvert currencyConvert =
                        new CurrencyConvert(
                                binding.fromCurrency.getSelectedItem().toString(),
                                binding.toCurrency.getSelectedItem().toString(),
                                amountInput,
                                convertedResult
                        );

                // Insert to database
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->
                {
                    currencyConvert.id = myDAO.insertConvert(currencyConvert);
                });

                currencyConverts.add(currencyConvert);
                myAdapter.notifyItemInserted(currencyConverts.size()-1);
                //clear the previous text
                binding.amountText.setText("");
                binding.convertedResult.setText("");

                Snackbar.make(binding.getRoot(), "Currency convert saved!", Snackbar.LENGTH_LONG).show();
            } else {
                // Handle the case where no input is provided
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning!");
                builder.setMessage("Amount input and converted amount can not be empty!");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    // Dialog dismissed
                });
                builder.show();
            }
        });

        binding.updateButton.setOnClickListener(click -> {
            for (int index = 0; index < currencyConverts.size(); index++) {
                CurrencyConvert record = currencyConverts.get(index);
                getCurrencyConvertResult(record, index);
            }
            Snackbar.make(binding.getRoot(), "All saved currency converts updated!", Snackbar.LENGTH_LONG).show();
        });
    }
}