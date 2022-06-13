package android.com.jamsand.io.thebusyshop.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.utilities.Utils;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProductActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView barcodeTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private NumberPicker numberPicker;

    private String barcodeName;
    private String description;
    private Double price;
    private String quantity;
    private boolean isChecked;
    int drawableId;

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

            drawableId = getResources().getIdentifier(intent.getStringExtra(Utils.EXTRA_PRODUCT_IMAGE), "drawable", this.getPackageName());
            productImage.setImageResource(drawableId);

            Toast.makeText(ProductActivity.this, ""+intent.getStringExtra(Utils.EXTRA_PRODUCT_IMAGE), Toast.LENGTH_SHORT).show();

        }
    }

    public void init(){
        productImage = findViewById(R.id.productImageView);
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

        int quantity = numberPicker.getValue();

        Intent data = new Intent();
        data.putExtra(Utils.EXTRA_PRODUCT_IMAGE, drawableId);
        data.putExtra(Utils.EXTRA_BARCODE, barcodeName);
        data.putExtra(Utils.EXTRA_DESCRIPTION, description);
        data.putExtra(Utils.EXTRA_PRICE, price);
        data.putExtra(Utils.EXTRA_IS_CHECKED, true);
        data.putExtra(Utils.EXTRA_QUANTITY, quantity);

        int id = getIntent().getIntExtra(Utils.EXTRA_ID,-1);
        if(id != -1){
            data.putExtra(Utils.EXTRA_ID,id);
        }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check_out_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.checkoutButton:
                Intent intent = new Intent(ProductActivity.this, SummaryActivity.class);
                startActivity(intent);
                Toast.makeText(ProductActivity.this, "Check-Out", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}