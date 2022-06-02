package android.com.jamsand.io.thebusyshop.data;

import android.com.jamsand.io.thebusyshop.model.Barcode;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Barcode.class}, version = 1,exportSchema = false)
public abstract class BarcodeDatabase extends RoomDatabase {

    private static BarcodeDatabase instance;


    public abstract BarcodeDao barcodeDao();

    public static synchronized BarcodeDatabase getInstance(Context context){

        if(instance == null ){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BarcodeDatabase.class,"barcode_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbTaskAsync(instance).execute();
        }
    };

    private static class PopulateDbTaskAsync extends AsyncTask<Void,Void,Void> {

        private BarcodeDao barcodeDao;
        public PopulateDbTaskAsync(BarcodeDatabase db){
            barcodeDao = db.barcodeDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
        //    Barcode(int id,String barcodeName, String description, String image, int price)
            barcodeDao.insert( new Barcode(1,"BAN258","Banana","banana.jpg",2.0,true));
            barcodeDao.insert( new Barcode(2,"APL883","Apple","apple.jpg",5.0,false));
            barcodeDao.insert( new Barcode(3,"SBR101","Strawberry","strawberry.jpg",0.5,false));
            barcodeDao.insert( new Barcode(4,"ORN750","Orange","orange.jpg",4.75,true));
            barcodeDao.insert( new Barcode(5,"WML999","Waterlemon","watermelon.jpg",38.0,true));
            barcodeDao.insert( new Barcode(6,"GPF208","Grapefruit","grapefruit.jpg",3.5,true));
            barcodeDao.insert( new Barcode(7,"PER478","Pear","pear.jpg",5.0,false));
            barcodeDao.insert( new Barcode(8,"COC378","Coconut","coconut.jpg",14.0,false));
            return null;
        }
    }

}
