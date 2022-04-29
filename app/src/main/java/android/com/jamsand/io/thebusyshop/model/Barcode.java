package android.com.jamsand.io.thebusyshop.model;

public class Barcode {
    public int barcodeImage;
    public String barcodeName;

    public Barcode(String barcodeImage, int barcode) {
        this.barcodeName = barcodeImage;
        this.barcodeImage = barcode;
    }
}
