package it.univr.mb.magazza.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.univr.mb.magazza.Activity.CSVExplorerFragments.CsvFieldSelectionFragment;
import it.univr.mb.magazza.Activity.CSVExplorerFragments.ShowCSVFragment;
import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.GenericCsv;
import it.univr.mb.magazza.R;

public class CSVExplorerActivity extends AppCompatActivity {
    private final static String TAG = "CSVExplorerFragment";

    private Uri fileUri;
    private Fragment nextFragment;
    private ArrayList<GenericCsv> idRow = new ArrayList<>();
    private List<String> mResult;
    private String[] mFirstLineArray;
    private String mFirstFiedlToShow;
    private String mSecondFieldToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csvexplorer);
        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");
        fileUri = Uri.parse(filePath);
        ArrayList<String> firstLine = (ArrayList<String>) readFirstLine();
        mFirstLineArray = firstLine.toArray(new String[0]);
        mResult = readFile();

        //TODO-> verificare cosa succede ma dovrebbe essere giusto. Devo fare altri giri i sistemare però

        //nextFragment = ShowCSVFragment.newInstance(res.toString());
        //nextFragment = ShowCSVFragment.newInstance(res);
        nextFragment = CsvFieldSelectionFragment.newInstance(firstLine);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_csv, nextFragment).commit();

    }

    private List<String> readFirstLine() {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(fileUri, "r");
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(fileUri);
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        ArrayList<String> toReturn = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            line = reader.readLine();
            String[] split;
            split = line.split("\t");
            toReturn.addAll(Arrays.asList(split));
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            inputStream.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            parcelFileDescriptor.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return toReturn;
    }

    private List<String> readFile() {

        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(fileUri, "r");
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(fileUri);
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        List<String> csvLine = new ArrayList<>();
        String[] content;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            int i = 0;
            reader.readLine();
            while((line = reader.readLine()) != null) {
                //Log.d(TAG, "WHILE LINE " + i + ": " + line);
                if(line.split("\t").length != 1) {
                    csvLine.add(line);
                    Log.d(TAG, "CORRECT LINE : " + i + ": " + line);
                    i++;
                }
            }
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            inputStream.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            parcelFileDescriptor.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return csvLine;
    }

    public void createCsvItem(String id) {

        for(String s : mResult) {
            String[] s1 = s.split("\t");
            Log.d(TAG, "s1_LENGTH: " + s1.length + " fields_LENGTH " + mFirstLineArray.length + " id -> " +id);
            GenericCsv csvItem = new GenericCsv(mFirstLineArray, id, s1);
            Log.d(TAG, "RIGA " + csvItem.toString());
            idRow.add(csvItem);
        }
        Log.d(TAG, "STRINGA " + idRow);

        ObjectBuilder.getInstance().setCsvList(idRow);
    }

    public void setFiedsToShow(String first, String second) {
        mFirstFiedlToShow = first;
        mSecondFieldToShow = second;
    }

    public void launchShowFragment() {
        nextFragment = ShowCSVFragment.newInstance(mFirstFiedlToShow, mSecondFieldToShow);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_csv, nextFragment).commit();
    }
}
