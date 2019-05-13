package it.univr.mb.magazza.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.univr.mb.magazza.Activity.CSVExplorerFragments.ShowCSVFragment;
import it.univr.mb.magazza.R;

public class CSVExplorerActivity extends AppCompatActivity {
    private final static String TAG = "CSVExplorerFragment";

    private Uri fileUri;
    private Fragment nextFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csvexplorer);
        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");
        fileUri = Uri.parse(filePath);
        List<String[]> result = readFile();
        StringBuilder toShow = new StringBuilder();
        for(String[] s : result) {
            for (String s1 : s) {
                Log.d(TAG, "RIGA" + s1);
                toShow.append(s1);
            }
            toShow.append("\n");
        }
        Log.d(TAG, "STRINGA " + toShow);


        nextFragment = ShowCSVFragment.newInstance(toShow.toString());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_csv, nextFragment).commit();

    }

    private List<String[]> readFile() {

        File file = new File(fileUri.getPath().split(":")[1]);
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

    }
}
