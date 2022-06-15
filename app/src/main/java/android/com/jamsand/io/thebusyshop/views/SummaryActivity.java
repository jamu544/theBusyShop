package android.com.jamsand.io.thebusyshop.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.databinding.ActivitySummaryBinding;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {


     private ArrayList<Barcode>  barcodes = new ArrayList<>();
     private double totalCalculated;


    private ActivitySummaryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_summary);
        setTitle("RECEIPT");


        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        binding.dateTextView.setText(date_n);



        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");

        Type type = new TypeToken<List<Barcode>>() {}.getType();
        List<Barcode> arrayList = gson.fromJson(json, type);

        setTextViewFromSharedPreferencesList((ArrayList<Barcode>) arrayList, binding.infoItemsPurchasedTextView);
        binding.infoTotalTextView.setText(getString(R.string.total)+totalCalculated);

    }

    public void setTextViewFromSharedPreferencesList(ArrayList<Barcode> arraylist, TextView textview) {
        String output = "";
        for (int i = 0; i < arraylist.size(); i++) {
            //Append all the values to a string
            output += arraylist.get(i).description+"  (x"+arraylist.get(i).quantity+")" +
                    " $"+arraylist.get(i).calculateTheNumberOfFruitsPerPurchase(arraylist.get(i).price,arraylist.get(i).quantity);
            output += "\n";

            //calculating the total of the items
            totalCalculated+=arraylist.get(i).calculateTheNumberOfFruitsPerPurchase(arraylist.get(i).price,arraylist.get(i).quantity);
        }
        textview.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check_out_confirmation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.shareButton:

                //data to be shared
                String date = binding.dateTextView .getText().toString();
                String summary = binding.infoItemsPurchasedTextView.getText().toString();
                String total = binding.infoTotalTextView .getText().toString();


                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Date : " + date +"\n" +" Summary Order : "+"\n"+ summary+"\n"+total;
                String shareSubject = "Confirmation Order";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(shareIntent,"Share using"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}