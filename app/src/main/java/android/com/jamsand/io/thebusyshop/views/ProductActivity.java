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

    public String barcodeName;
    public String description;
    public Double price;
    public String quantity;
    public boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
        Intent intent = getIntent();
        if(intent.hasExtra(Constants.EXTRA_ID)) {
            barcodeTextView.setText(intent.getStringExtra(Constants.EXTRA_BARCODE));
            descriptionTextView.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION));
            price = intent.getDoubleExtra(Constants.EXTRA_PRICE, 0.0);
            priceTextView.setText(getString(R.string.ZAR) + price);
        }
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

        String barcodeName = barcodeTextView.getText().toString();
        String description = descriptionTextView.getText().toString();
       // double price = Double.parseDouble(priceTextView.getText().toString());
      //  boolean isChecked = true;

//        String title = editTextTitle.getText().toString();
//        String description = editTextDescription.getText().toString();
        int quantity = numberPicker.getValue();
//
//        if (title.trim().isEmpty() || description.trim().isEmpty()) {
//            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT);
//            return;
//        }
        Intent data = new Intent();
        data.putExtra(Constants.EXTRA_BARCODE, barcodeName);
        data.putExtra(Constants.EXTRA_DESCRIPTION, description);
        data.putExtra(Constants.EXTRA_PRICE, price);
        data.putExtra(Constants.EXTRA_IS_CHECKED, true);
//
        int id = getIntent().getIntExtra(Constants.EXTRA_ID,-1);
        if(id != -1){
            data.putExtra(Constants.EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();

        Toast.makeText(ProductActivity.this, "added to Cart"+quantity, Toast.LENGTH_SHORT).show();

    }

}