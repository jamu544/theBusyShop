package android.com.jamsand.io.thebusyshop.repository;

import android.app.Application;
import android.com.jamsand.io.thebusyshop.data.BarcodeDao;
import android.com.jamsand.io.thebusyshop.data.BarcodeDatabase;
import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BarcodeRepository {

    private BarcodeDao  barcodeDao;
    private LiveData<List<Barcode>> allBarcodes;
    private LiveData<List<Barcode>> checkedBarcodes;

    public BarcodeRepository(Application application) {
        BarcodeDatabase database = BarcodeDatabase.getInstance(application);
        barcodeDao = database.barcodeDao();
        allBarcodes = barcodeDao.getAllBarcodes();
        checkedBarcodes = barcodeDao.getCheckedBarcodes();
    }
    public void insert(Barcode barcode){
        new InsertBarcodeAsyncTask(barcodeDao).execute(barcode);

    }
    public void update(Barcode barcode){
        new UpdateBacodeAsyncTask(barcodeDao).execute(barcode);

    }
    public void delete(Barcode barcode){
        new DeleteBarcodeAsyncTask(barcodeDao).execute(barcode);
    }

    public void deleteAllBarcodes(){
        new DeleteAllBarcodesAsyncTask(barcodeDao).execute();
    }

    public LiveData<List<Barcode>> getAllBarcodes(){
        return allBarcodes;
    }
    public LiveData<List<Barcode>> getCheckedBarcodes(){
        return checkedBarcodes;
    }

    // insert async tasks
    private static class InsertBarcodeAsyncTask extends AsyncTask<Barcode,Void,Void> {
        private BarcodeDao barcodeDao;
        private InsertBarcodeAsyncTask(BarcodeDao barcodeDao){
            this.barcodeDao = barcodeDao;
        }
        @Override
        protected Void doInBackground(Barcode... barcodes) {
            barcodeDao.insert(barcodes[0]);
            return null;
        }
    }
    // update async tasks
    private static class UpdateBacodeAsyncTask extends AsyncTask<Barcode,Void,Void> {
        private BarcodeDao barcodeDao;
        private UpdateBacodeAsyncTask(BarcodeDao barcodeDao){
            this.barcodeDao = barcodeDao;
        }
        @Override
        protected Void doInBackground(Barcode... barcodes) {
            barcodeDao.update(barcodes[0]);
            return null;
        }
    }
    // delete async tasks
    private static class DeleteBarcodeAsyncTask extends AsyncTask<Barcode,Void,Void> {
        private BarcodeDao barcodeDao;
        private DeleteBarcodeAsyncTask(BarcodeDao barcodeDao){
            this.barcodeDao = barcodeDao;
        }
        @Override
        protected Void doInBackground(Barcode... barcodes) {
            barcodeDao.delete(barcodes[0]);
            return null;
        }
    }
    // delete all aync
    private static class DeleteAllBarcodesAsyncTask extends AsyncTask<Void,Void,Void> {
        private BarcodeDao barcodeDao;
        private DeleteAllBarcodesAsyncTask(BarcodeDao barcodeDao){
            this.barcodeDao = barcodeDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            barcodeDao.deleteAllBarcodes();
            return null;
        }
    }
}
