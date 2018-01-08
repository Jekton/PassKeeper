package com.jekton.passkeeper.password.params;

/**
 * @author Jekton
 */

public interface CipherParamsProvider {

    void setExternalKey(boolean uesExternalKey);

    byte[] getIv();
    byte[] getKey(String password);

}
