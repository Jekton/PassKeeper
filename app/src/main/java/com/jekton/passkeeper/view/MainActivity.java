package com.jekton.passkeeper.view;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.jekton.passkeeper.R;
import com.jekton.passkeeper.password.PasswordManager;

import java.util.List;


public class MainActivity extends AppCompatActivity implements PasswordManager.PasswordListener {
    private static final String TAG = "MainActivity";

    private ListView mListView;
    private PasswordListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        mListView = findViewById(R.id.password_list);
        mListAdapter = new PasswordListAdapter(this);
        mListView.setAdapter(mListAdapter);

        PasswordManager manager = PasswordManager.getInstance();
        manager.setListener(this);
        manager.setPassword("123456");
        manager.loadPasswords();
        manager.addPassword("foo", "123");
        manager.addPassword("bar", "876");
        manager.removePassword("foo");
        manager.addPassword("bar", "fdsfdsf");
        manager.addPassword("bar2", "fdsfdsf");
        manager.addPassword("bar3", "fdsfdsf");
    }


    @Override
    public void onPasswordChanged(List<Pair<String, String>> passwords) {
        Log.e(TAG, "onPasswordChanged: ");
        mListAdapter.setPasswords(passwords);
    }


    @Override
    public void onLoadPasswordSuccess() {
        Log.e(TAG, "onLoadPasswordSuccess: ");
    }


    @Override
    public void onLoadPasswordFail() {
        Log.e(TAG, "onLoadPasswordFail: ");
    }


    @Override
    public void onStorePasswordSuccess() {
        Log.e(TAG, "onStorePasswordSuccess: ");
    }


    @Override
    public void onStorePasswordFail() {
        Log.e(TAG, "onStorePasswordFail: ");
    }
}
