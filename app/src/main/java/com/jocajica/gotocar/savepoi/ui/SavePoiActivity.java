package com.jocajica.gotocar.savepoi.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.jocajica.gotocar.GotoCarApp;
import com.jocajica.gotocar.R;
import com.jocajica.gotocar.savepoi.SavePoiPresenter;
import com.jocajica.gotocar.savepoi.ui.adapters.SavePoiSectionsPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavePoiActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_RESOLVE_ERROR = 0;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    private boolean resolvingError = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Inject
    SavePoiPresenter presenter;
    @Inject
    SavePoiSectionsPagerAdapter adapter;
    @Inject
    SharedPreferences sharedPreferences;

    private GotoCarApp app;
    private Location lastLocation;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savepoi);
        ButterKnife.bind(this);

        app = (GotoCarApp) getApplication();

        setupInjection();
        setupNavigation();
        setupGoogleAPIClient();

        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_savepoi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Open shared preferences
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void setupGoogleAPIClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void setupNavigation() {
        toolbar.setTitle("Goto Car");
        setSupportActionBar(toolbar);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupInjection() {
        String[] titles = new String[]{
                getString(R.string.savepoi_title_details),
                getString(R.string.savepoi_title_map)};

        Fragment[] fragments = new Fragment[]{new PhotoListFragment(),
                new PhotoMapFragment()};

        GotoCarApp app = (GotoCarApp) getApplication();
        app.getMainComponent(this, getSupportFragmentManager(), fragments, titles).inject(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, PERMISSIONS_REQUEST_LOCATION);
            }

            return;
        }

        if (LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient).isLocationAvailable()) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }
        else {
            Snackbar.make(viewPager, R.string.savepoi_error_location_notavailable, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient).isLocationAvailable()) {
                        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    }
                    else {
                        Snackbar.make(viewPager, R.string.savepoi_error_location_notavailable, Snackbar.LENGTH_SHORT).show();
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (resolvingError) {
            return;
        }
        else if (connectionResult.hasResolution()) {
            resolvingError = true;
            try {
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
        else {
            resolvingError = true;
            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), REQUEST_RESOLVE_ERROR).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == REQUEST_RESOLVE_ERROR) {
            resolvingError = false;

            if (resultCode == RESULT_OK) {
                if (!googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
                    googleApiClient.connect();
                }

            }
        }
    }
}
