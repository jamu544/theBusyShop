package android.com.jamsand.io.thebusyshop.adapters;

import android.app.Application;
import android.com.jamsand.io.thebusyshop.BR;
import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.databinding.BarcodeListItemBinding;
import android.com.jamsand.io.thebusyshop.interfaces.OnItemClickListener;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.com.jamsand.io.thebusyshop.utilities.Utils;
import android.com.jamsand.io.thebusyshop.views.MainActivity;
import android.com.jamsand.io.thebusyshop.views.ProductActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.BarcodeHolder> {

    private List<Barcode> barcodes = new ArrayList<>();
    private Context context;

    public BarcodeAdapter(Context context){
     //   this.barcodes = dataModel;
        this.context = context;
    }
    @NonNull
    @Override
    public BarcodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BarcodeListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.barcode_list_item,parent,false);

        return new BarcodeHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull BarcodeHolder holder, int position) {
        final Barcode barcode = barcodes.get(position);
        holder.bind(barcode);
        holder.itemBinding.setOnItemClick((OnItemClickListener) context);
    }
    @Override
    public int getItemCount() {
        return barcodes == null? 0: barcodes.size();
    }
    public void setBarcodes(List<Barcode> barcodes){
        this.barcodes = barcodes;
        notifyDataSetChanged();
    }

    public class BarcodeHolder extends RecyclerView.ViewHolder {
     public BarcodeListItemBinding itemBinding;

     public BarcodeHolder(BarcodeListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
        public void bind(Object obj){
            itemBinding.setVariable(BR.model, obj);
            itemBinding.executePendingBindings();
            if(itemBinding.getModel().isChecked){
                itemBinding.barcodeNumber.setTextColor(Color.parseColor("#FF0000"));
            }
            else{
                itemBinding.barcodeNumber.setTextColor(Color.parseColor("#000000"));
            }
            itemBinding.barcodeNumber.setText(itemBinding.getModel().barcodeName);
        }
    }
}
