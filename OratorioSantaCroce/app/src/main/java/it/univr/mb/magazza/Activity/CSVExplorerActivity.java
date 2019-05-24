package it.univr.mb.magazza.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univr.mb.magazza.Activity.CSVExplorerFragments.ShowCSVFragment;
import it.univr.mb.magazza.R;

public class CSVExplorerActivity extends AppCompatActivity {
    private final static String TAG = "CSVExplorerFragment";

    private Uri fileUri;
    private Fragment nextFragment;
    private Map<String, String> idRow = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csvexplorer);
        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");
        fileUri = Uri.parse(filePath);
        //String res = readFile();
        List<String> result = readFile();
        for(String s : result) {
            String[] s1 = s.split("\t");
            Log.d(TAG, "LENGTH: " + s1.length);
            StringBuilder row = new StringBuilder();
            for (int i = 1; i < s1.length; i++) {
                row.append(s1[i]).append(" | ");
            }
            Log.d(TAG, "RIGA " + row.toString());
            idRow.put(s1[9], row.toString());
        }
        Log.d(TAG, "STRINGA " + idRow);

        StringBuilder res = new StringBuilder();
        int i = 0;
        for(String id : idRow.keySet()) {
            res.append(i).append(" ").append(id).append("=").append(idRow.get(id)).append("\n");
            i++;
        }
        nextFragment = ShowCSVFragment.newInstance(res.toString());
        //nextFragment = ShowCSVFragment.newInstance(res);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_csv, nextFragment).commit();

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



        /*File file = new File(fileUri.getPath().split(":")[1]);
        Log.d(TAG, "File " + file);

        List<String[]> csvLine = new ArrayList<>();
        String[] content;
        try {
            InputStream inputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while((line = br.readLine()) != null){
                content = line.split("\n");
                csvLine.add(content);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvLine;

    }*/
}
