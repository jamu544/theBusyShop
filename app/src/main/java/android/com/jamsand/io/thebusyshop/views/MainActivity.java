package android.com.jamsand.io.thebusyshop.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.adapters.BarcodeAdapter;
import android.com.jamsand.io.thebusyshop.data.BarcodeDatabase;
import android.com.jamsand.io.thebusyshop.databinding.ActivityMainBinding;
import android.com.jamsand.io.thebusyshop.interfaces.OnItemClickListener;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.com.jamsand.io.thebusyshop.utilities.Utils;
import android.com.jamsand.io.thebusyshop.viewmodel.BarcodeViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private BarcodeViewModel barcodeViewModel;

    public static Context context;

    Barcode barcode;
    ArrayList<Barcode> modelArrayList = new ArrayList();

    ProgressDialog progressBar;

    private BarcodeAdapter barcodeAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

      //  binding.setTitle(getString(R.string.app_shop_label));

        context = this;
        init();

        progressBar.setMessage("please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();

        barcodeAdapter = new BarcodeAdapter(context);
        binding.setAdapter(barcodeAdapter);


        barcodeViewModel = new ViewModelProvider(this).get(BarcodeViewModel.class);
        barcodeViewModel.getAllBarcodes().observe(this, barcodes -> {
            progressBar.dismiss();
            //update RecyclerView
            Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

            barcodeAdapter.setBarcodes(barcodes);

        });
//        barcodeAdapter.onItemClick( barcode -> {
//            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
//
//            intent.putExtra(Utils.EXTRA_ID, barcode.);
//            intent.putExtra(Utils.EXTRA_PRODUCT_IMAGE, barcode.productImage);
//            intent.putExtra(Utils.EXTRA_BARCODE, barcode.barcodeName);
//            intent.putExtra(Utils.EXTRA_DESCRIPTION, barcode.description);
//            intent.putExtra(Utils.EXTRA_PRICE, barcode.price);
//            intent.putExtra(Utils.EXTRA_IS_CHECKED, barcode.isChecked);
//            startActivityForResult(intent, EDIT_BARCODE_REQUEST);
//
//            Log.d("Product ", barcode.toString());
//        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utils.EDIT_BARCODE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Utils.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Barcode can't be saved", Toast.LENGTH_SHORT).show();
                return;
            }

            String productImage = data.getStringExtra(Utils.EXTRA_PRODUCT_IMAGE);
            String barcodeName = data.getStringExtra(Utils.EXTRA_BARCODE);
            String description = data.getStringExtra(Utils.EXTRA_DESCRIPTION);
            double price = data.getDoubleExtra(Utils.EXTRA_PRICE, 1);
            boolean isChecked = data.getBooleanExtra(Utils.EXTRA_IS_CHECKED, true);
            int quantity = data.getIntExtra(Utils.EXTRA_QUANTITY, 1);


            barcode = new Barcode(id, barcodeName, description, productImage, price, isChecked, quantity);
            barcode.calculateTheNumberOfFruitsPerPurchase(price, quantity);
            modelArrayList.add(barcode);
            barcodeViewModel.update(barcode);

            // saving barcode objects
            SharedPreferences appSharedPrefsSavesItems = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefsEditor = appSharedPrefsSavesItems.edit();
            Gson gson = new Gson();
            //eliminate duplicates
            String json = gson.toJson(Utils.clearListFromDuplicateFirstName(modelArrayList));
            prefsEditor.putString("MyObject", json);
            prefsEditor.apply();

            // saving cost per item


            Toast.makeText(this, "Barcode updated" + barcodeName, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Barcode not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // close the primary database to ensure all the transactions are merged
        BarcodeDatabase.getInstance(getApplicationContext()).close();
    }

    public void init() {
        progressBar = new ProgressDialog(context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.summary_checkout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.summaryButton:

                SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("MyObject", "");

                Type type = new TypeToken<List<Barcode>>() {
                }.getType();
                List<Barcode> arrayList = gson.fromJson(json, type);

                MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder
                        (MainActivity.this, R.style.AlertDialogTheme);
                builder1.setTitle("Summary Order");
                builder1.setMessage(arrayList.toString());
                builder1.setPositiveButton("CHECK-OUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Check-Out", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder1.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Barcode barcode) {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
//
            intent.putExtra(Utils.EXTRA_ID, barcode.id);
            intent.putExtra(Utils.EXTRA_PRODUCT_IMAGE, barcode.productImage);
            intent.putExtra(Utils.EXTRA_BARCODE, barcode.barcodeName);
            intent.putExtra(Utils.EXTRA_DESCRIPTION, barcode.description);
            intent.putExtra(Utils.EXTRA_PRICE, barcode.price);
            intent.putExtra(Utils.EXTRA_IS_CHECKED, barcode.isChecked);
            startActivityForResult(intent, Utils.EDIT_BARCODE_REQUEST);


        Toast.makeText(context,barcode.barcodeName,
                Toast.LENGTH_SHORT).show();
    }
    // TO DO LIST:
    // get online images for products(incomplete)
    // after receipt return objects to default
    // clean


}