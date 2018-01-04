//
// Created by Jekton Luo on 1/4/18.
//
#include "com_jekton_passkeeper_password_CipherParamsKeeper.h"

const uint8_t sIv[] = {
        (uint8_t) 0x9b, (uint8_t) 0x6f, (uint8_t) 0xe6, (uint8_t) 0x18,
        (uint8_t) 0x59, (uint8_t) 0x6b, (uint8_t) 0x20, (uint8_t) 0xe3,
        (uint8_t) 0x42, (uint8_t) 0x68, (uint8_t) 0xd8, (uint8_t) 0x40,
        (uint8_t) 0xa0, (uint8_t) 0xf6, (uint8_t) 0xb9, (uint8_t) 0xb9,
};

const uint8_t sSalt[] = {
        (uint8_t) 0x53, (uint8_t) 0x16, (uint8_t) 0x8a, (uint8_t) 0x86,
        (uint8_t) 0xc3, (uint8_t) 0x62, (uint8_t) 0x25, (uint8_t) 0xa6,
        (uint8_t) 0x72, (uint8_t) 0x51, (uint8_t) 0xb7, (uint8_t) 0x78,
        (uint8_t) 0x0a, (uint8_t) 0x9e, (uint8_t) 0x68, (uint8_t) 0x46,
};


namespace {
jbyteArray makeCopy(JNIEnv *env, const uint8_t *src, size_t len);
}


JNIEXPORT jbyteArray JNICALL
Java_com_jekton_passkeeper_password_CipherParamsKeeper_getIv(
        JNIEnv *env, jclass) {
    size_t len = sizeof(sIv) / sizeof(*sIv);
    return makeCopy(env, sIv, len);
}


JNIEXPORT jbyteArray JNICALL
Java_com_jekton_passkeeper_password_CipherParamsKeeper_getSalt(
        JNIEnv *env, jclass) {
    size_t len = sizeof(sSalt) / sizeof(*sSalt);
    return makeCopy(env, sSalt, len);
}


namespace {

jbyteArray makeCopy(JNIEnv *env, const uint8_t *src, size_t len) {
    jbyteArray array = env->NewByteArray((jsize) len);
    jbyte *bytes = env->GetByteArrayElements(array, 0);
    for (int i = 0; i < len; ++i) {
        bytes[i] = src[i];
    }
    env->ReleaseByteArrayElements(array, bytes, 0);
    return array;
}

}



