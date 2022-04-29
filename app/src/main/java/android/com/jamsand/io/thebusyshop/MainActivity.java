package android.com.jamsand.io.thebusyshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.com.jamsand.io.thebusyshop.adapters.BarcodeAdapter;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        ArrayList<Barcode> barcodeArrayList = new ArrayList<>();
        barcodeArrayList.add(new Barcode("1011210", R.drawable.barcode));
        barcodeArrayList.add(new Barcode("5842000", R.drawable.barcode));
        barcodeArrayList.add(new Barcode("5845254", R.drawable.barcode));
        barcodeArrayList.add(new Barcode("1011210", R.drawable.barcode));
        barcodeArrayList.add(new Barcode("1011000", R.drawable.barcode));
        barcodeArrayList.add(new Barcode("10188810", R.drawable.barcode));
        barcodeArrayList.add(new Barcode("10116666", R.drawable.barcode));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scannListView);
        BarcodeAdapter adapter = new BarcodeAdapter(barcodeArrayList,context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
}