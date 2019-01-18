package it.univr.mb.magazza.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import it.univr.mb.magazza.Activity.LasciaFragments.QrLasciaFragment;
import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.R;

public class LasciaActivity extends AppCompatActivity {

    private static final int CAMERA = 99;
    private Fragment nextFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lascia);
        //qui vado a selezionare dove lascaire gli oggetti

        if (!checkCameraPermission())
            requestCameraPermissions();

        if (nextFragment == null)
            nextFragment = new QrLasciaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_lascia, nextFragment).commit();
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

    public void showToLeaveDialog(Item item) {

/*        if (ObjectBuilder.getInstance().contains(item)){
            Toast.makeText(this, "Oggetto già inserito", Toast.LENGTH_SHORT).show();
            return;
        }*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.leave_dialog_title)
                .setMessage(item.getName() + " -> " + item.getStoreLocation());
        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            Toast.makeText(getApplicationContext(), "confermato", Toast.LENGTH_SHORT).show();
            //TODO-> Rimuovere dalla tabella prestito ed eventiualemnte aggiungere a storico
            ObjectBuilder.getInstance().addItemToLeave(item);
            dialogInterface.dismiss();
            ((QrLasciaFragment)nextFragment).startCamera();
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            Toast.makeText(getApplicationContext(), "annullato", Toast.LENGTH_SHORT).show();
            //TODO-> non fare nulla
            ((QrLasciaFragment)nextFragment).startCamera();
            dialogInterface.cancel();

        });

        runOnUiThread(() -> {
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        });

    }

    public void commitLeave() {
        ObjectBuilder.getInstance().commitLeave(this);

    }

    public void dispose() {
        this.finish();
    }
}
