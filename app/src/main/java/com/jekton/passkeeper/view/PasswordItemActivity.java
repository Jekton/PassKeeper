package com.jekton.passkeeper.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.jekton.passkeeper.R;
import com.jekton.passkeeper.password.PasswordManager;


/**
 * @author Jekton
 */

public class PasswordItemActivity extends AppCompatActivity {

    private static final String EXTRA_CREATE = "create";
    private static final String EXTRA_KEY = "key";
    private static final String EXTRA_PASSWORD = "password";

    private EditText mKey;
    private EditText mPassword;
    private boolean mCreating;


    public static void startActivity(Context context) {
        startActivity(context, "", "");
    }


    public static void startActivity(Context context, String key, String password) {
        Intent intent = new Intent(context, PasswordItemActivity.class);
        if (TextUtils.isEmpty(key)) {
            intent.putExtra(EXTRA_CREATE, true);
        }
        intent.putExtra(EXTRA_KEY, key);
        intent.putExtra(EXTRA_PASSWORD, password);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.password_item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (!mCreating) {
                    PasswordManager.getInstance().removePassword(getKey());
                }
                break;
            case R.id.action_save:
                String key = getKey();
                String password = getPassword();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, R.string.activity_password_item_key_or_password_empty,
                                   Toast.LENGTH_SHORT).show();
                    return true;
                }
                PasswordManager.getInstance().addPassword(key, password);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        finish();
        return true;
    }


    private void init() {
        mKey = findViewById(R.id.key);
        mPassword = findViewById(R.id.password);
        parseIntent(getIntent());
    }


    private void parseIntent(Intent intent) {
        mCreating = intent.getBooleanExtra(EXTRA_CREATE, false);
        if (!mCreating) {
            mKey.setEnabled(false);
        }
        String key = intent.getStringExtra(EXTRA_KEY);
        String password = intent.getStringExtra(EXTRA_PASSWORD);
        mKey.setText(key);
        mPassword.setText(password);
    }


    private String getKey() {
        return mKey.getText().toString();
    }


    private String getPassword() {
        return mPassword.getText().toString();
    }
}
