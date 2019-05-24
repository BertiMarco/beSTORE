package it.univr.mb.magazza.Activity.MainFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.univr.mb.magazza.Activity.MainActivity;
import it.univr.mb.magazza.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadCSVFragment extends Fragment {
    private final static int ACTIVITY_CHOOSE_FILE = 98;
    private final static String TAG = "LoadCSVFragment";

    private String[] path;
    private Uri uri;
    private TextView pathTextView;
    private Button browse;
    private Button analyze;

    public LoadCSVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_csv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pathTextView = view.findViewById(R.id.path_textview);
        browse = view.findViewById(R.id.browse_button);
        browse.setOnClickListener(v-> onBrowseButtonCLicked());

        analyze = view.findViewById(R.id.analyze_button);
        analyze.setOnClickListener(v-> onAnalyzeButtonClicked());



    }

    private void onAnalyzeButtonClicked() {
        if(path == null)
            Toast.makeText(getContext(), "Scegli un file in formato CSV", Toast.LENGTH_SHORT).show();
        else {
            ((MainActivity)getActivity()).launchCSVActivity(uri);
            /*String extension = path[1].substring(path[1].lastIndexOf(".")).toLowerCase();
            Log.d(TAG, extension);
            if (!extension.equals(".csv"))
                Toast.makeText(getContext(), "Formato non valido", Toast.LENGTH_SHORT).show();
            else
                ((MainActivity)getActivity()).launchCSVActivity(uri);*/
        }

    }

    private void onBrowseButtonCLicked() {

        if(!((MainActivity)getActivity()).checkStoragePermission())
            ((MainActivity)getActivity()).requestStoragePermission();

        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("text/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String filePath;

        if(resultCode == RESULT_OK && requestCode == ACTIVITY_CHOOSE_FILE)
        {
            uri = data.getData();
            Log.d(TAG, "Uri: " + uri.getPath());
            path = uri.toString().split(":");
            filePath = path[1];
            pathTextView.setText(filePath);
            Log.d(TAG, "File Path: " + path);

        }
    }


}
