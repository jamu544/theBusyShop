package android.com.jamsand.io.thebusyshop.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.adapters.BarcodeAdapter;
import android.com.jamsand.io.thebusyshop.data.BarcodeDatabase;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.com.jamsand.io.thebusyshop.viewmodel.BarcodeViewModel;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BarcodeViewModel barcodeViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Annex");

        context = this;

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scannListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BarcodeAdapter barcodeAdapter = new BarcodeAdapter();
        recyclerView.setAdapter(barcodeAdapter);


        barcodeViewModel = new ViewModelProvider(this).get(BarcodeViewModel.class);
        barcodeViewModel.getAllBarcodes().observe(this, new Observer<List<Barcode>>() {
            @Override
            public void onChanged(List<Barcode> barcodes) {
                //update RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();

                barcodeAdapter.setBarcodes(barcodes);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // close the primary database to ensure all the transactions are merged
        BarcodeDatabase.getInstance(getApplicationContext()).close();
    }
}