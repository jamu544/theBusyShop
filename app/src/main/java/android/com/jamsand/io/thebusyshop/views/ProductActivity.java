package android.com.jamsand.io.thebusyshop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.utilities.Utils;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        setTitle("ITEM");
        init();
        Intent intent = getIntent();
        if(intent.hasExtra(Utils.EXTRA_ID)) {
            barcodeTextView.setText(intent.getStringExtra(Utils.EXTRA_BARCODE));
            descriptionTextView.setText(intent.getStringExtra(Utils.EXTRA_DESCRIPTION));
            price = intent.getDoubleExtra(Utils.EXTRA_PRICE, 0.0);
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
        data.putExtra(Utils.EXTRA_BARCODE, barcodeName);
        data.putExtra(Utils.EXTRA_DESCRIPTION, description);
        data.putExtra(Utils.EXTRA_PRICE, price);
        data.putExtra(Utils.EXTRA_IS_CHECKED, true);
        data.putExtra(Utils.EXTRA_QUANTITY, quantity);

        int id = getIntent().getIntExtra(Utils.EXTRA_ID,-1);
        if(id != -1){
            data.putExtra(Utils.EXTRA_ID,id);
        }

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Add to Cart");
//        builder.setCancelable(false);
//        //builder.setMessage("My Message");
//        builder.setPositiveButton("OK", (dialog, which) -> {
//            setResult(RESULT_OK, data);
//            finish();
//            Toast.makeText(ProductActivity.this, "added to Cart", Toast.LENGTH_SHORT).show();
//             });
//        builder.setNegativeButton("Cancel", (dialog, which) -> { /* ... */ });
//        AlertDialog dialog = builder.create();
//        //Show the AlertDialog
//        dialog.show();

     MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder
             (ProductActivity.this,R.style.AlertDialogTheme);
        builder1.setTitle("Add to Cart!");
        builder1 .setMessage("Add this item to cart?");
        builder1 .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder1.show();
    }

    public void checkOutOnClick(View view ){
        Intent intent = new Intent(ProductActivity.this, SummaryActivity.class);
        startActivity(intent);
        Toast.makeText(ProductActivity.this, "Check-Out", Toast.LENGTH_SHORT).show();
    }
    public double calculateProduct(double price,int quantity){
        double sum = price * quantity;
        return sum;
    }



}