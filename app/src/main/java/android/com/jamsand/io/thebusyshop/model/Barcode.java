package android.com.jamsand.io.thebusyshop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Barcode {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int barcodeImage;
    public String barcodeName;
    public String description;
    public String image;
    public double price;
    public boolean isChecked;// true if the user sets quantity

    public Barcode(int id,String barcodeName, String description, String image, double price,boolean isChecked) {
        this.id = id;
        this.barcodeName = barcodeName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.isChecked = isChecked;
    }

    public Barcode(String barcodeImage, int barcode) {
        this.barcodeName = barcodeImage;
        this.barcodeImage = barcode;
    }

}
