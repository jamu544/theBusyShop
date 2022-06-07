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
import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

     private TextView summaryInfoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        summaryInfoTextView = (TextView) findViewById(R.id.info_purchased);

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");

        Type type = new TypeToken<List<Barcode>>() {}.getType();
        List<Barcode> arrayList = gson.fromJson(json, type);

        for (Barcode p : arrayList) {
            summaryInfoTextView.setText(arrayList.toString());
        }








    }
}