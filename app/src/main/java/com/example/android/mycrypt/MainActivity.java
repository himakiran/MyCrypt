package com.example.android.mycrypt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * below variables hold the clear,cipher text and a secret key.
     * for now the sceret key is genr from a hardcode passwd in function @generatekey.
     */
    public static String clearText;
    public static String cipherText;
    public static SecretKey secret;
    public static boolean clearBool;
    public static boolean cipherBool;

    /**
     *
     * @param view gets the clearText view and then checks if text is available.
     *             if yes sets the bool var to true and stores the text in clearText var.
     */
    public void readEditText(View view)
    {
        EditText editText = (EditText) findViewById(R.id.clearText);
        if(!(editText.getText().toString()==""))
        {
            clearText = editText.getText().toString();
            clearBool = true;
            Log.d("clearText",clearText);
        }
        else
        {
            clearBool = false;
        }
    }

    /**
     *
     * @param view gets the cryptText view and then checks if text is available.
     *             if yes sets the bool var to true and stores the text in cipherText var.
     */
    public void readCipherText(View view)
    {
        EditText editText = (EditText) findViewById(R.id.cryptText);
        if(!(editText.getText().toString()==""))
        {
            cipherText = editText.getText().toString();
            cipherBool = true;
            Log.d("clearText",clearText);
        }
        else
        {
            cipherBool = false;
        }

    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String pwd ="password";
        SecretKey secret;
        return secret = new SecretKeySpec(pwd.getBytes(), "AES");
    }

    public static String encryptMsg(String message, SecretKey secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
/* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        String encryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return encryptString;
    }
    public static String decryptMsg(byte[] cipherText, SecretKey secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

    /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }

    public static void encrypt(){

        try {
            secret = generateKey();
        }
        catch (Exception a) {
            Log.d("algo", a.getMessage());

        }
        try{
            cipherText = encryptMsg(clearText,secret);
        }
        catch(Exception a){
            Log.d("algo",a.getMessage());

        }



    }
    public static void decrypt() {

        try {
            secret = generateKey();
        } catch (Exception a) {
            Log.d("algo", a.getMessage());

        }
        try {
            byte[] cipherTextBytes = cipherText.getBytes("UTF-8");
            clearText = decryptMsg(cipherTextBytes, secret);
        } catch (Exception a) {
            Log.d("algo", a.getMessage());

        }
    }
/**
 * This method is called when the order button is clicked.
 */
    public static void encryptOrDecrypt(View view){

        if(clearBool && !cipherBool){

            encrypt();
        }
        else if(!clearBool && cipherBool){
            decrypt();
        }

        else if(!clearBool && !cipherBool){
            //throw a messagebox asking user to enter text
        }
        else
        {
            //throw a message box asking user to clear selection
        }

    }



}
