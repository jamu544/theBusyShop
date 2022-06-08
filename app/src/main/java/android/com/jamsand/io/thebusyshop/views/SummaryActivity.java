package android.com.jamsand.io.thebusyshop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {

     private TextView summaryInfoTextView;
     private TextView dateTextView;
     private TextView totolCalculatedTextView;
     private ArrayList<Barcode>  barcodes = new ArrayList<>();
     private double totalCalculated;

    //create a date string.
    String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setTitle("CHECKOUT");
        summaryInfoTextView = (TextView) findViewById(R.id.info_purchased);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        totolCalculatedTextView = (TextView) findViewById(R.id.info_purchased_total);


        setDate(dateTextView);





        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");

        Type type = new TypeToken<List<Barcode>>() {}.getType();
        List<Barcode> arrayList = gson.fromJson(json, type);

//        for(int i = 0;i<arrayList.size();i++){
//
//            Barcode barcode = new Barcode();
//            barcode.description = arrayList.get(i).description;
//            barcode.price = arrayList.get(i).price;
//            barcode.quantity = arrayList.get(i).quantity;
//            barcodes.add(barcode);
//
//            Log.d("Fruits",barcode.description );
//
//        }
        setTextViewFromList((ArrayList<Barcode>) arrayList,summaryInfoTextView);
        totolCalculatedTextView.setText("Total :R "+totalCalculated);
    }

    public void setTextViewFromList(ArrayList<Barcode> arraylist, TextView textview) {
        String output = "";
        for (int i = 0; i < arraylist.size(); i++) {
            //Append all the values to a string
            output += arraylist.get(i).description+"  (x"+arraylist.get(i).quantity+")" +
                    " R"+arraylist.get(i).calculateTheNumberOfFruitsPerPurchase(arraylist.get(i).price,arraylist.get(i).quantity);
            output += "\n";

            totalCalculated+=arraylist.get(i).calculateTheNumberOfFruitsPerPurchase(arraylist.get(i).price,arraylist.get(i).quantity);
        }
        textview.setText(output);
    }

    public void setDate(TextView view){
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        dateTextView.setText(date_n);
    }

}