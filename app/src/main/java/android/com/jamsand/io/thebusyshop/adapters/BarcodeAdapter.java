package android.com.jamsand.io.thebusyshop.adapters;

import android.com.jamsand.io.thebusyshop.R;
import android.com.jamsand.io.thebusyshop.interfaces.OnItemClickListener;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.BarcodeHolder>{

    private List<Barcode> barcodes = new ArrayList<>();

    public OnItemClickListener listener;

    @NonNull
    @Override
    public BarcodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.barcode_list_item,parent,false);
        return new BarcodeHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BarcodeHolder holder, int position) {
        final Barcode barcode = barcodes.get(position);
    //    holder.barcodeImage.setImageResource(barcode.barcodeImage);
        holder.barcodeName.setText(barcode.barcodeName);
    }
    @Override
    public int getItemCount() {
        return barcodes == null? 0: barcodes.size();
    }
    public void setBarcodes(List<Barcode> barcodes){
        this.barcodes = barcodes;
        notifyDataSetChanged();
    }
    public Barcode getBarcodeAt(int position){
        return barcodes.get(position);
    }
    public class BarcodeHolder extends RecyclerView.ViewHolder {
   //     public ImageView barcodeImage;
        public TextView barcodeName;
        public LinearLayout linearLayout;
        public BarcodeHolder(@NonNull View itemView) {
            super(itemView);
     //       this.barcodeImage = (ImageView) itemView.findViewById(R.id.barcodeImage);
            this.barcodeName = (TextView) itemView.findViewById(R.id.barcodeNumber);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.barcodeLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();// ALT CTRL L to format
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(barcodes.get(position));
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
    }


}
