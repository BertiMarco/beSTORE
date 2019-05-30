package it.univr.mb.magazza.Model;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class GenericCsv {

    private static final String TAG = "GenericCSV";
    private Map<String, String> mFields = new HashMap<>();
    private String mId;

    public GenericCsv(String[] fields, String idField, String[] values) {
        //Log.d(TAG, "fields: " +fields.length + " values: " + values.length + " id: " + idField);
        for (int i = 0; i < fields.length ; i++) {
            mFields.put(fields[i], values[i]);
        }
            mId = idField;
    }

    public GenericCsv(String[] fields, String[] values) {
        for (int i = 0; i < fields.length ; i++) {
            mFields.put(fields[i], values[i]);
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Map<String, String> getFields() {
        return mFields;
    }

    public String getField(String field) {
        Log.d(TAG, "field: " + field + "result: " + mFields.get(field));
        return mFields.get(field);
    }
}
