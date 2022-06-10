package android.com.jamsand.io.thebusyshop.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.adapters.BarcodeAdapter;
import android.com.jamsand.io.thebusyshop.data.BarcodeDatabase;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.com.jamsand.io.thebusyshop.utilities.Constants;
import android.com.jamsand.io.thebusyshop.viewmodel.BarcodeViewModel;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BarcodeViewModel barcodeViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_BARCODE_REQUEST = 2;
    public static Context context;

    Barcode barcode;
    ArrayList<Barcode> modelArrayList = new ArrayList();

    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.app_shop_label));

        context = this;
        init();

        progressBar.setMessage("please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scannListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BarcodeAdapter barcodeAdapter = new BarcodeAdapter();
        recyclerView.setAdapter(barcodeAdapter);


        barcodeViewModel = new ViewModelProvider(this).get(BarcodeViewModel.class);
        barcodeViewModel.getAllBarcodes().observe(this, barcodes -> {
            progressBar.dismiss();
            //update RecyclerView
            Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

            barcodeAdapter.setBarcodes(barcodes);
        });
        barcodeAdapter.setOnItemClickListener(barcode -> {
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            intent.putExtra(Constants.EXTRA_ID, barcode.id);
            intent.putExtra(Constants.EXTRA_BARCODE, barcode.barcodeName);
            intent.putExtra(Constants.EXTRA_DESCRIPTION, barcode.description);
            intent.putExtra(Constants.EXTRA_PRICE, barcode.price);
            intent.putExtra(Constants.EXTRA_IS_CHECKED, barcode.isChecked);
            startActivityForResult(intent, EDIT_BARCODE_REQUEST);
            Log.d("Product ", barcode.toString());
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_BARCODE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Constants.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Barcode can't be saved", Toast.LENGTH_SHORT).show();
                return;
            }

            String barcodeName = data.getStringExtra(Constants.EXTRA_BARCODE);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION);
            double price = data.getDoubleExtra(Constants.EXTRA_PRICE, 1);
            boolean isChecked = data.getBooleanExtra(Constants.EXTRA_IS_CHECKED,true);
            int quantity = data.getIntExtra(Constants.EXTRA_QUANTITY,1);


                barcode = new Barcode(id,barcodeName,description,"banana",price,isChecked,quantity);
                barcode.calculateTheNumberOfFruitsPerPurchase(price,quantity);
                modelArrayList.add(barcode);
                barcodeViewModel.update(barcode);

                // saving barcode objects
            SharedPreferences appSharedPrefsSavesItems = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefsEditor = appSharedPrefsSavesItems.edit();
            Gson gson = new Gson();
            String json = gson.toJson(modelArrayList);
            prefsEditor.putString("MyObject",json);
            prefsEditor.apply();

                // saving cost per item



           Toast.makeText(this, "Barcode updated"+barcodeName, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Barcode not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // close the primary database to ensure all the transactions are merged
        BarcodeDatabase.getInstance(getApplicationContext()).close();
     //   BarcodeDatabase.getInstance(getApplicationContext()).barcodeDao().deleteAllBarcodes();
    }
    //display order summary
    public void orderSummaryLayout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Summary");
        builder.setCancelable(false);
        builder.setMessage("Apple Banana Orange ");
        builder.setPositiveButton("CONFIRM", (dialog, which) -> {

            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Check-Out", Toast.LENGTH_SHORT).show();

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> { /* ... */ });
        AlertDialog dialog = builder.create();
        //Show the AlertDialog
        dialog.show();
    }
    // TO DO LIST:
    // get online images for products(incomplete)
    // create order summary
    // eliminate duplication from order list
    // after receipt return objects to default
    //receipt option to share it via Whatsapp, Gmail etc
    //create progress bar when loading items - done
    public void init(){
        progressBar = new ProgressDialog(context);
    }
    //add MATERIAL DESIGN(available for implementation)

}