package android.com.jamsand.io.thebusyshop.data;

import android.com.jamsand.io.thebusyshop.model.Barcode;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BarcodeDao {

    @Insert
    void insert(Barcode barcode);
    @Update
    void update(Barcode barcode);
    @Delete
    void delete(Barcode barcode);
    @Query("DELETE FROM Barcode")
    void deleteAllBarcodes();
    @Query("SELECT * FROM Barcode ORDER BY id DESC")
    LiveData<List<Barcode>> getAllBarcodes();

}
