package com.jekton.passkeeper.password;

import com.jekton.passkeeper.util.CipherUtil;

import java.security.NoSuchAlgorithmException;


/**
 * @author Jekton
 */

class CipherParamsKeeper {

    static {
        System.loadLibrary("pwdkeeper");
    }

    public static native byte[] getIv();


    public static byte[] getKey(String password) throws NoSuchAlgorithmException {
        return CipherUtil.md5Bytes(password, getSalt());
    }


    private static native byte[] getSalt();
}
