package com.jekton.passkeeper.password.params;

/**
 * @author Jekton
 */

class ExternalParamsProvider implements CipherParamsProvider {

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
        return new byte[0];
    }


    @Override
    public byte[] getKey(String password) {
        return new byte[0];
    }
}
