package com.jekton.passkeeper.password.params;

/**
 * @author Jekton
 */

public class CipherParamsProviderImpl implements CipherParamsProvider {


    @Override
    public void setExternalKey(boolean uesExternalKey) {

    }


    @Override
    public byte[] getIv() {
        return new byte[0];
    }


    @Override
    public byte[] getKey(String password) {
        return new byte[0];
    }
}
