package com.jekton.passkeeper.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jekton.passkeeper.R;
import com.jekton.passkeeper.password.PasswordManager;

import java.util.List;


public class MainActivity extends AbstractActivity implements PasswordManager.PasswordListener, PasswordDialog.PasswordCallback {
    private static final String TAG = "MainActivity";

    private ListView mListView;
    private PasswordListAdapter mListAdapter;
    private PasswordDialog mPasswordDialog;

    private List<Pair<String, String>> mPasswords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        PasswordManager manager = PasswordManager.getInstance();
        manager.setListener(this);
        if (!manager.isPasswordSet()) {
            mPasswordDialog.show(manager.isFirstRound(this));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        PasswordManager.getInstance().setListener(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_item) {
            PasswordItemActivity.startActivity(this);
            return true;
        }
        return false;
    }


    private void init() {
        mPasswordDialog = new PasswordDialog(this, this);
        mListView = findViewById(R.id.password_list);
        mListAdapter = new PasswordListAdapter(this);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pair<String, String> pair = mPasswords.get(position);
                PasswordItemActivity.startActivity(MainActivity.this, pair.first, pair.second);
            }
        });
    }


    @Override
    public void onPasswordChanged(List<Pair<String, String>> passwords) {
        mPasswords = passwords;
        mListAdapter.setPasswords(mPasswords);
    }


    @Override
    public void onDecodeFail() {
        Toast.makeText(this, R.string.activity_main_msg_decode_fail, Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public void onLoadPasswordSuccess() {
        // nop
    }


    @Override
    public void onLoadPasswordFail() {
        Toast.makeText(this, R.string.activity_main_msg_load_fail,
                Toast.LENGTH_SHORT).show();
        finish();
    }


    private void onAuthenticated() {
        if (checkPermission()) {
            onPermissionGranted();
        }
    }


    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    private boolean checkPermission() {
        boolean canRead = hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean canWrite = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String[] permissons;
        if (!canRead && !canWrite) {
            permissons = new String[2];
            permissons[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            permissons[1] = Manifest.permission.READ_EXTERNAL_STORAGE;
        } else if (!canRead) {
            permissons = new String[1];
            permissons[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
        } else if (!canWrite) {
            permissons = new String[1];
            permissons[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        } else {
            return true;
        }

        ActivityCompat.requestPermissions(this, permissons, 0);
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean fail = false;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                fail = true;
                break;
            }
        }
        if (fail) {
            finish();
        } else {
            onPermissionGranted();
        }
    }


    private void onPermissionGranted() {
        PasswordManager manager = PasswordManager.getInstance();
        manager.loadPasswords();
    }


    @Override
    public void onPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            finish();
            return;
        }

        PasswordManager manager = PasswordManager.getInstance();
        if (manager.isFirstRound(this)) {
            manager.firstRoundEnd(this);
        }
        manager.setPassword(password);
        onAuthenticated();
    }
}
