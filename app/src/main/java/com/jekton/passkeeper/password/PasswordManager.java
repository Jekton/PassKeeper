package com.jekton.passkeeper.password;

import android.content.Context;
import android.os.Environment;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.jekton.passkeeper.R;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * @author Jekton
 */

public class PasswordManager implements PasswordKeeper.OnPasswordChangedListener {

    public interface PasswordListener extends PasswordKeeper.OnPasswordChangedListener {
        void onLoadPasswordSuccess();
        void onLoadPasswordFail();
    }



    private static final String TAG = "PasswordManager";
    private static final String PASSWORD_FILE = "passkeeper.dat";
    private static final String PREF_FIRST_ROUND = "PasswordManager.first_round";

    private static final PasswordManager sInstance = new PasswordManager();

    private Context mContext;
    private PasswordKeeper mPasswordKeeper;
    private PasswordListener mListener;

    private boolean mDataLoaded;
    private List<Pair<String, String>> mPasswords;

    private int mNumActivity;


    public static PasswordManager getInstance() {
        return sInstance;
    }


    private PasswordManager() {
    }


    public void setContext(Context context) {
        mContext = context;
    }


    public void onActivityIn() {
        if (++mNumActivity == 1) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            String passwordPath = path + File.separator + PASSWORD_FILE;
            mPasswordKeeper = new PasswordKeeper(this, passwordPath);
        }
    }


    public void onActivityOut() {
        if (--mNumActivity == 0) {
            try {
                mPasswordKeeper.storePasswords();
            } catch (NoSuchAlgorithmException | IllegalBlockSizeException |
                    InvalidKeyException | InvalidAlgorithmParameterException |
                    BadPaddingException | NoSuchPaddingException e) {
                if (mContext != null) {
                    Toast.makeText(mContext, R.string.password_msg_store_fail,
                                   Toast.LENGTH_SHORT).show();
                }
            }
            mPasswordKeeper.destroy();
            mPasswordKeeper = null;
            mPasswords = null;
            mDataLoaded = false;
            if (mListener != null) {
                mListener.onPasswordChanged(null);
            }
        }
    }


    public boolean isFirstRound(Context context) {
        boolean firstRound = PrefUtil.getBoolean(context, PREF_FIRST_ROUND, true);
        if (firstRound) {
            File file = new File(mPasswordKeeper.getPasswordFile());
            if (file.exists()) {
                firstRoundEnd(context);
                return false;
            }
        }
        return firstRound;
    }


    public void firstRoundEnd(Context context) {
        PrefUtil.setBoolean(context, PREF_FIRST_ROUND, false);
    }


    public void setPassword(String password) {
        if (mPasswordKeeper != null) {
            mPasswordKeeper.setPassword(password);
        }
    }


    public boolean isPasswordSet() {
        if (mPasswordKeeper != null) {
            return mPasswordKeeper.isPasswordSet();
        }
        return false;
    }


    public void loadPasswords() {
        if (mPasswordKeeper == null || mDataLoaded) return;

        boolean success = false;
        try {
            mPasswordKeeper.loadPasswords();
            success = true;
            mDataLoaded = true;
        } catch (NoSuchAlgorithmException |
                IllegalBlockSizeException |
                BadPaddingException |
                InvalidKeyException |
                InvalidAlgorithmParameterException |
                NoSuchPaddingException e) {
            Logger.e(e, "load password fail");
        }
        if (mListener != null) {
            if (success) {
                mListener.onLoadPasswordSuccess();
            } else {
                mListener.onLoadPasswordFail();
            }
        }
    }


    public boolean addPassword(String key, String password) {
        return mPasswordKeeper != null && mPasswordKeeper.addPassword(key, password);
    }


    public boolean updatePassword(String key, String password) {
        return mPasswordKeeper != null && mPasswordKeeper.updatePassword(key, password);
    }


    public boolean removePassword(String key) {
        return mPasswordKeeper == null || mPasswordKeeper.removePassword(key);
    }


    public void setListener(PasswordListener listener) {
        mListener = listener;
        if (mListener != null && mPasswords != null) {
            mListener.onPasswordChanged(mPasswords);
        }
    }


    @Override
    public void onDecodeFail() {
        if (mListener != null) {
            mListener.onDecodeFail();
        }
    }


    @Override
    public void onPasswordChanged(List<Pair<String, String>> passwords) {
        mPasswords = passwords;
        if (mListener != null) {
            mListener.onPasswordChanged(mPasswords);
        }
    }
}
