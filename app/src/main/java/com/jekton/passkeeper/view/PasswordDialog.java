package com.jekton.passkeeper.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jekton.passkeeper.R;

/**
 * @author jekton
 */

class PasswordDialog {

    public interface PasswordCallback {
        void onPassword(String password);
    }

    private static final String TAG = "PasswordDialog";

    private final Activity mActivity;
    private final PasswordCallback mCallback;

    private Dialog mDialog;
    private EditText mPassword;
    private boolean mCreateNewOne;


    public PasswordDialog(Activity activity, PasswordCallback callback) {
        mActivity = activity;
        mCallback = callback;
    }


    public void show(boolean createNewOne) {
        mCreateNewOne = createNewOne;
        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(mActivity)
                    .setView(R.layout.dialog_password)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PasswordDialog.this.onCancel();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            PasswordDialog.this.onCancel();
                        }
                    })
                    .create();
            // Oh, please, don't automatically hide dialog
            // see https://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked/9523257
            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onOk();
                        }
                    });
                    mPassword = mDialog.findViewById(R.id.password);
                }
            });
        }

        if (createNewOne) {
            mDialog.setTitle(R.string.dialog_password_title_new_password);
        } else {
            mDialog.setTitle(R.string.dialog_password_title_input_password);
        }
        mDialog.show();
    }


    public void hide() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    private void onOk() {
        String password = getPassword();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mActivity, R.string.dialog_password_msg_password_is_empty,
                    Toast.LENGTH_SHORT).show();
        } else {
            mCallback.onPassword(password);
            hide();
        }
    }


    private void onCancel() {
        mCallback.onPassword("");
        hide();
    }


    private String getPassword() {
        return mPassword != null ? mPassword.getText().toString() : "";
    }
}
