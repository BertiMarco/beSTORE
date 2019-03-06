package it.univr.mb.magazza.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.time.LocalDateTime;

import it.univr.mb.magazza.Activity.MainFragments.AccessFragment;
import it.univr.mb.magazza.Activity.MainFragments.EventsFragmentTab;
import it.univr.mb.magazza.Activity.MainFragments.PrendiLasciaFragment;
import it.univr.mb.magazza.Activity.PrendiLasciaFragments.RecapFragment;
import it.univr.mb.magazza.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOGGED = "logged" ;
    private static final int PHONE_STATE = 100;
    private static final String TAG = "MainActivity";
    private Fragment nextFragment = null;
    private DrawerLayout mDrawer;
    private boolean logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate. bundle = " + savedInstanceState);

        if(savedInstanceState != null)
            logged = savedInstanceState.getBoolean(LOGGED);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d(TAG, "onCreate. LOGGED = " + logged);
        if (!logged) {
            nextFragment = new AccessFragment();
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        else
            nextFragment = new PrendiLasciaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, nextFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_prendi_lascia) {
            nextFragment = new PrendiLasciaFragment();
        }
        else if (id == R.id.nav_liste) {
            nextFragment = new EventsFragmentTab();
        }
        else if (id == R.id.nav_slideshow) {

        }
        else if (id == R.id.nav_manage) {

        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, nextFragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchPrendiActivity() {
        Intent intent = new Intent(getApplicationContext(), PrendiActivity.class);
        startActivity(intent);
    }

    public boolean checkPhonePermission() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    public void requestPhonePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    //Toast.makeText(this, "LOCATION GRANTED", Toast.LENGTH_SHORT).show();
                    try {
                        ((AccessFragment)nextFragment).getImei();
                    } catch (Exception e){

                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permesso telefono negato.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void launchPrendiLasciaFragment() {
        logged = true;
        nextFragment = new PrendiLasciaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, nextFragment).commit();
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "salvo stato. LOGGED = " + logged);
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOGGED, logged);
    }

    public void launchLasciaActivity() {
        Intent intent = new Intent(getApplicationContext(), LasciaActivity.class);
        startActivity(intent);

    }

}
