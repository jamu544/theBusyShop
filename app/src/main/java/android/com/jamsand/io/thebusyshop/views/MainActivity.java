package android.com.jamsand.io.thebusyshop.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.adapters.BarcodeAdapter;
import android.com.jamsand.io.thebusyshop.data.BarcodeDatabase;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.com.jamsand.io.thebusyshop.utilities.Constants;
import android.com.jamsand.io.thebusyshop.viewmodel.BarcodeViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BarcodeViewModel barcodeViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static Context context;

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
        barcodeAdapter.setOnItemClickListener(barcode -> {
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            intent.putExtra(Constants.EXTRA_BARCODE, barcode.barcodeName);
            intent.putExtra(Constants.EXTRA_DESCRIPTION, barcode.description);
            intent.putExtra(Constants.EXTRA_PRICE, barcode.price);
            startActivity(intent);
            Log.d("Product ", barcode.toString());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
//            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
//            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
//            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
//
//            Note note = new Note(title, description, priority);
//            noteViewModel.insert(note);
//            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
//        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
//            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
//
//            if (id == -1) {
//                Toast.makeText(this, "Note can't be saved", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
//            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
//            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
//
//            Note note = new Note(title, description, priority);
//            note.setId(id);
//            noteViewModel.update(note);
//            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
//
//
//        } else {
//            Toast.makeText(this, "Noted not saved", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // close the primary database to ensure all the transactions are merged
        BarcodeDatabase.getInstance(getApplicationContext()).close();
    }

}