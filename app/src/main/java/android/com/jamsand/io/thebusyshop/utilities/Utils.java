package android.com.jamsand.io.thebusyshop.utilities;

import android.com.jamsand.io.thebusyshop.model.Barcode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static final String EXTRA_ID ="id";
    public static final String EXTRA_BARCODE ="barcode";
    public static final String EXTRA_DESCRIPTION ="description";
    public static final String EXTRA_PRICE ="price";
    public static final String EXTRA_QUANTITY ="quantity";
    public static final String EXTRA_IS_CHECKED ="isChecked";

    public static List<Barcode> clearListFromDuplicateFirstName(List<Barcode> list1) {

        Map<String, Barcode> cleanMap = new LinkedHashMap<String, Barcode>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).barcodeName, list1.get(i));
        }
        List<Barcode> list = new ArrayList<Barcode>(cleanMap.values());
        return list;
    }
}
