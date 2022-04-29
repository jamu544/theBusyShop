package android.com.jamsand.io.thebusyshop.adapters;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.BarcodeHolder>{

    public Context context;
    public ArrayList<Barcode> barcodeList;

    public BarcodeAdapter(ArrayList<Barcode> barcodeList, Context context){
        this.context =context;
        this.barcodeList = barcodeList;
    }
    @NonNull
    @Override
    public BarcodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.barcode_list_item,parent,false);
        BarcodeHolder holder = new BarcodeHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull BarcodeHolder holder, int position) {
        final Barcode barcode = barcodeList.get(position);
        holder.barcodeImage.setImageResource(barcode.barcodeImage);
        holder.barcodeName.setText(barcode.barcodeName);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"barcode clicked-> "+barcode.barcodeName,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return barcodeList == null? 0:barcodeList.size();
    }
    public static class BarcodeHolder extends RecyclerView.ViewHolder {

        public ImageView barcodeImage;
        public TextView barcodeName;
        public LinearLayout linearLayout;

        public BarcodeHolder(@NonNull View itemView) {
            super(itemView);
            this.barcodeImage = (ImageView) itemView.findViewById(R.id.barcodeImage);
            this.barcodeName = (TextView) itemView.findViewById(R.id.barcodeNumber);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.barcodeLayout);
        }
    }
}
