package android.com.jamsand.io.thebusyshop.viewmodel;

import android.app.Application;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.com.jamsand.io.thebusyshop.repository.BarcodeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BarcodeViewModel extends AndroidViewModel {
    private BarcodeRepository repository;
    private LiveData<List<Barcode>> allBarcodes;
    private LiveData<List<Barcode>> checkedBarcodes;
    public BarcodeViewModel(@NonNull Application application) {
        super(application);
        repository = new BarcodeRepository(application);
        allBarcodes = repository.getAllBarcodes();
        checkedBarcodes = repository.getCheckedBarcodes();

    }
    public void insert(Barcode barcode){
        repository.insert(barcode);
    }
    public void update(Barcode barcode){
        repository.update(barcode);
    }
    public void delete(Barcode barcode){
        repository.delete(barcode);
    }
    public void deleteAllBarcodes(){
        repository.deleteAllBarcodes();
    }
    public LiveData<List<Barcode>> getAllBarcodes() {
        return allBarcodes;
    }

    public LiveData<List<Barcode>> getCheckedBarcodes(){
        return checkedBarcodes;
    }
}
