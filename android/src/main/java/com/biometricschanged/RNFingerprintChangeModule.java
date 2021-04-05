package com.biometricschanged;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyPermanentlyInvalidatedException;

import java.security.InvalidKeyException;

import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricConstants;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.security.KeyStore;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;

import androidx.fragment.app.FragmentActivity;
import android.view.SurfaceHolder.Callback;

public class RNFingerprintChangeModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final String LAST_KEY_ID = "LAST_KEY_ID";
    private SharedPreferences spref;

    public RNFingerprintChangeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    /**
     * Using reflection to access the fingerprint internal api.
     *
     * @param context
     * @return
     * @throws InvalidKeyException
     * @throws KeyPermanentlyInvalidatedException
     */
    @ReactMethod
    public void hasFingerPrintChanged(final Promise pm) {
    Cipher cipher = getCipher();
    SecretKey secretKey = getSecretKey();
    if (getSecretKey() == null){
        generateSecretKey(new KeyGenParameterSpec.Builder(
                "fingerPrintKey",
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(true)
                .setInvalidatedByBiometricEnrollment(true)
                .build());
            }
        try {
             secretKey = getSecretKey();
             cipher.init(Cipher.ENCRYPT_MODE, secretKey);
             pm.resolve(false);
         }
          catch (KeyPermanentlyInvalidatedException e) {
                generateSecretKey(new KeyGenParameterSpec.Builder(
                "fingerPrintKey",
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(true)
                .setInvalidatedByBiometricEnrollment(true)
                .build());
                pm.resolve(true);
        } catch (InvalidKeyException e) {
            pm.resolve("Error");
        }
    }   

    private void generateSecretKey(KeyGenParameterSpec keyGenParameterSpec) {
    try{
    KeyGenerator keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
    keyGenerator.init(keyGenParameterSpec);
    keyGenerator.generateKey();
     }
    catch(Exception e){
    }
}

private SecretKey getSecretKey() {
    try{
    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
    keyStore.load(null);
    return ((SecretKey)keyStore.getKey("fingerPrintKey", null));
    }
    catch(Exception e){
        return null;
    }
}

private Cipher getCipher() {
    try{
    return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
            + KeyProperties.BLOCK_MODE_CBC + "/"
            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
    }
    catch(Exception e){
        return null;
    }
}

    
    @Override
    public String getName() {
        return "RNFingerprintChange";
    }

}