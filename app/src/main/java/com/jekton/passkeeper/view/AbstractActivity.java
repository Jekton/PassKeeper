package com.jekton.passkeeper.view;

import android.support.v7.app.AppCompatActivity;

import com.jekton.passkeeper.password.PasswordManager;


/**
 * @author Jekton
 */

abstract class AbstractActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();
        PasswordManager.getInstance().onActivityIn();
    }


    @Override
    protected void onStop() {
        super.onStop();
        PasswordManager.getInstance().onActivityOut();
    }
}
