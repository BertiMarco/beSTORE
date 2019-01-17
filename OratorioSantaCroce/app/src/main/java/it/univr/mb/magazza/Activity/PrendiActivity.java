package it.univr.mb.magazza.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import it.univr.mb.magazza.Activity.PrendiLasciaFragments.CausaleFragment;
import it.univr.mb.magazza.Activity.PrendiLasciaFragments.QrPrendiFragment;
import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.R;
import it.univr.mb.magazza.Activity.PrendiLasciaFragments.RecapFragment;

public class PrendiActivity extends AppCompatActivity {


    private Fragment nextFragment = null;
    private final int CAMERA = 99;
    private final String TAG = "Prendi activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendi);

        if (nextFragment == null)
            nextFragment = new CausaleFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_prendi, nextFragment).commit();

    }

    public void launchQrFragment() {
        nextFragment = new QrPrendiFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_prendi, nextFragment).commit();

    }

    public boolean checkCameraPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCameraPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA);

        // ACCESS_COARSE_LOCATION is an
        // app-defined int constant. The callback method gets the
        // result of the request.

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    //Toast.makeText(this, "LOCATION GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permesso fotocamera negato.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void showFoundDialog(Item item) {

        if (ObjectBuilder.getInstance().contains(item)){
            Toast.makeText(this, "Oggetto già inserito", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.found_dialog_title)
                .setMessage(item.getName());
        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            Toast.makeText(getApplicationContext(), "confermato", Toast.LENGTH_SHORT).show();
            ObjectBuilder.getInstance().addItem(item);
            dialogInterface.dismiss();
            ((QrPrendiFragment)nextFragment).startCamera();
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            Toast.makeText(getApplicationContext(), "annullato", Toast.LENGTH_SHORT).show();
            ((QrPrendiFragment)nextFragment).startCamera();
            dialogInterface.cancel();

        });

        runOnUiThread(() -> {
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        });
    }

    public void launchRecapFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        nextFragment = RecapFragment.newInstance(true);
        ft.replace(R.id.fragment_prendi, nextFragment).commit();
    }

    public void itemNotFound() {
        //TODO genera log di errore
        Toast.makeText(this, "Oggetto non registrato", Toast.LENGTH_SHORT).show();
    }

    public void dispose() {
        this.finish();
    }

    public void setEvents(ArrayList<String> events) {
        Log.d(TAG, nextFragment.toString());
        if(nextFragment instanceof CausaleFragment)
            ((CausaleFragment) nextFragment).setEvents(events);
    }
}
