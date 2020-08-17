package com.example.atlit.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.atlit.R;
import com.example.atlit.Utils.Loginsharedpreference;

import java.util.ArrayList;


public class BerandaActivity extends AppCompatActivity {
    private Loginsharedpreference loginsharedpreference;
    private Toolbar toolbar;
    private Button btnProfile, btnTentang, btnMetode, btnProgramTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        loginsharedpreference = new Loginsharedpreference(this);
        initToolbar();

        ArrayList<String> perms = new ArrayList<>();
        perms.add(Manifest.permission.ACCESS_FINE_LOCATION);
        perms.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionToRequest(perms);

        ArrayList<String> reqPermission = permissionToRequest(perms);
        if(reqPermission.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(reqPermission.toArray(new String[reqPermission.size()]), 100);
            }
        }

        btnProfile = findViewById(R.id.btnProfile);
        btnTentang = findViewById(R.id.btnTentang);
        btnMetode = findViewById(R.id.btnMetode);
        btnProgramTest = findViewById(R.id.btnProgramTest);
        btnProgramTest.setOnClickListener(v -> intentToProgramTest());
        btnTentang.setOnClickListener(view -> {
            Intent intent = new Intent(BerandaActivity.this, kebugaran.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        btnMetode.setOnClickListener(view -> {
            Intent intent = new Intent(BerandaActivity.this, MetodeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginsharedpreference.getUserLogin().getAkses().equals("atlit")) {
                    Intent intent = new Intent(BerandaActivity.this, AtlitProfileActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Intent intent = new Intent(BerandaActivity.this, PelatihProfileActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    private ArrayList<String> permissionToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm))
                result.add(perm);
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    private void intentToProgramTest() {
        Intent intent = new Intent(BerandaActivity.this, ProgramTestActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new AlertDialog
                        .Builder(BerandaActivity.this)
                        .setTitle("Permission")
                        .setMessage("Permission has been granted")
                        .setPositiveButton("OK", (dialog, which) -> {

                        })
                        .show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(BerandaActivity.this)
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin logout?")
                .setPositiveButton("Ya", (dialog1, which) -> {
                    loginsharedpreference.setHasLogin(false);
                    loginsharedpreference.setPelari(null);
                    intenttoLogin();
                })
                .setNegativeButton("Tidak", (dialog12, which) -> {

                });

        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);

        MenuItem menuAccount = menu.findItem(R.id.menuAccount);
        menuAccount.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(BerandaActivity.this)
                        .setTitle("Logout")
                        .setMessage("Apakah anda yakin ingin logout?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loginsharedpreference.setHasLogin(false);
                                loginsharedpreference.setPelari(null);
                                intenttoLogin();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog.show();
                return false;
            }
        });
        return true;
    }

    private void intenttoLogin() {
        Intent intent = new Intent(BerandaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (loginsharedpreference.getUserLogin().getAkses().equals("atlit")) {
            getSupportActionBar().setTitle(R.string.menu_atlit);
        } else {
            getSupportActionBar().setTitle(R.string.menu_pelatih);
        }
    }
}
