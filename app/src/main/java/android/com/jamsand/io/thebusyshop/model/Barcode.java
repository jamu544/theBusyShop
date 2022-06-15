package android.com.jamsand.io.thebusyshop.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Barcode {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int barcodeImage;
    public String barcodeName;
    public String description;
    public int productImage;
    public double price;
    public boolean isChecked;// true if the user sets quantity
    public int quantity;

    @Ignore
    public Barcode(){}

    public Barcode(int id, String barcodeName, String description, int productImage, double price, boolean isChecked, int quantity) {
        this.id = id;
        this.barcodeName = barcodeName;
        this.description = description;
        this.productImage = productImage;
        this.price = price;
        this.isChecked = isChecked;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return description+" R"+price+ " x "+quantity +"cost order "+calculateTheNumberOfFruitsPerPurchase(price,quantity);
    }
    public double calculateTheNumberOfFruitsPerPurchase(double price, int quantity){
        double sum = price*quantity;
        return sum;
    }

}
