package com.jekton.passkeeper.password.params;

import com.jekton.passkeeper.util.CipherUtil;
import com.orhanobut.logger.Logger;

import java.security.NoSuchAlgorithmException;


/**
 * @author Jekton
 */

class InternalParamsProvider implements CipherParamsProvider {

    static {
        System.loadLibrary("pwdkeeper");
    }


    @Override
    public boolean useExternalKey() {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setExternalKey(boolean uesExternalKey) {
        throw new UnsupportedOperationException();
    }


    @Override
    public byte[] getIv() {
        return doGetIv();
    }


    @Override
    public byte[] getKey(String password) {
        try {
            return CipherUtil.md5Bytes(password, getSalt());
        } catch (NoSuchAlgorithmException e) {
            Logger.e(e, "Fail to make key");
            throw new RuntimeException(e);
        }
    }


    private native byte[] doGetIv();
    private native byte[] getSalt();
}
