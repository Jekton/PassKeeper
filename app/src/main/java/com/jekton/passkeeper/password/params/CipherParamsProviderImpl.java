package com.jekton.passkeeper.password.params;

import android.content.Context;

import com.jekton.passkeeper.util.PrefUtil;


/**
 * @author Jekton
 */

public class CipherParamsProviderImpl implements CipherParamsProvider {

    private final static String PREF_USE_EXTERNAL_KEY = "CipherParamsProvider.USE_EXTERNAL_KEY";

    private final Context mContext;

    private final CipherParamsProvider mInternalParamsProvider;
    private final CipherParamsProvider mExternalParamsProvider;
    private CipherParamsProvider mCipherParamsProvider;


    public CipherParamsProviderImpl(Context context) {
        mContext = context;
        mInternalParamsProvider = new InternalParamsProvider();
        mExternalParamsProvider = new ExternalParamsProvider();
        mCipherParamsProvider = useExternalKey() ? mExternalParamsProvider
                                                 : mInternalParamsProvider;
    }


    @Override
    public boolean useExternalKey() {
        return PrefUtil.getBoolean(mContext, PREF_USE_EXTERNAL_KEY, false);
    }


    @Override
    public void setExternalKey(boolean uesExternalKey) {

    }


    @Override
    public byte[] getIv() {
        return mCipherParamsProvider.getIv();
    }


    @Override
    public byte[] getKey(String password) {
        return mCipherParamsProvider.getKey(password);
    }
}
