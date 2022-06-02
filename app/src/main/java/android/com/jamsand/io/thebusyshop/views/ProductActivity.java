package android.com.jamsand.io.thebusyshop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.utilities.Constants;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity {

    public TextView barcodeTextView;
    public TextView descriptionTextView;
    public TextView priceTextView;
    public NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
        Intent intent = getIntent();
        barcodeTextView.setText(intent.getStringExtra(Constants.EXTRA_BARCODE));
        descriptionTextView.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION));
        priceTextView.setText("R "+intent.getDoubleExtra(Constants.EXTRA_PRICE, 0.0));
    }

    public void init(){
        barcodeTextView = findViewById(R.id.barcodeProductActivity);
        descriptionTextView = findViewById(R.id.detailProductName);
        priceTextView = findViewById(R.id.detailProductPrice);
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
    }

    public void addToCartOnClick(View view){
//        String title = editTextTitle.getText().toString();
//        String description = editTextDescription.getText().toString();
        int quantity = numberPicker.getValue();
//
//        if (title.trim().isEmpty() || description.trim().isEmpty()) {
//            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT);
//            return;
//        }
//        Intent data = new Intent();
//        data.putExtra(EXTRA_TITLE, title);
//        data.putExtra(EXTRA_DESCRIPTION, description);
//        data.putExtra(EXTRA_PRIORITY, priority);
//
//        int id = getIntent().getIntExtra(EXTRA_ID,-1);
//        if(id != -1){
//            data.putExtra(EXTRA_ID,id);
//        }
//
//        setResult(RESULT_OK, data);
//        finish();

        Toast.makeText(ProductActivity.this, "added to Cart"+quantity, Toast.LENGTH_SHORT).show();

    }

}