package com.binary.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.binary.R;
import com.binary.constant;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringToHashActivity extends AppCompatActivity {

    StringBuilder binary;
    EditText etxtText;
    TextView txtHash;
    TextView btnCopy;
    RelativeLayout bgl_string_to_hash;
    Switch textSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_to_hash);

        etxtText = findViewById(R.id.etxtText);
        txtHash = findViewById(R.id.txtHash);
        btnCopy = findViewById(R.id.btnCopy);
        bgl_string_to_hash = findViewById(R.id.bgl_string_to_hash);
        textSwitch = findViewById(R.id.switch_text_binary);

        btnCopy.setVisibility(View.INVISIBLE);

        //Fetching id from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String getUserLogin = sharedPreferences.getString(constant.HOME_PAGE, "");

        if(getUserLogin.equals("4")){
            textSwitch.setChecked(true);
        }else{
            textSwitch.setChecked(false);
        }

        textSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isChecked = textSwitch.isChecked();

                if(isChecked){
                    //Creating a shared preference

                    SharedPreferences sp = getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sp.edit();
                    //Adding values to editor
                    editor.putString(constant.HOME_PAGE, "4");

                    //Saving values to editor
                    editor.apply();
                }else{
                    //Creating a shared preference

                    SharedPreferences sp = getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sp.edit();
                    //Adding values to editor
                    editor.putString(constant.HOME_PAGE, "0");

                    //Saving values to editor
                    editor.apply();
                }
            }
        });


        getSupportActionBar().setTitle("Text to MD5 Hash");

        etxtText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    String text = etxtText.getText().toString();
                    md5Hash(text);
                    btnCopy.setVisibility(View.VISIBLE);
                }else{
                    txtHash.setText("");
                    btnCopy.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", binary);
                clipboard.setPrimaryClip(clip);
                Snackbar.make(bgl_string_to_hash,"Copied to clipboard",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void md5Hash(String input){
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            txtHash.setText(hashtext);
            Log.d("STRING_TO_HASH", hashtext);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.textToBinary:
                Intent intent0 = new Intent(getApplicationContext(), TextToBinaryActivity.class);
                startActivity(intent0);
                finish();
                return true;
            case R.id.binaryToText:
                Intent intent = new Intent(getApplicationContext(), BinaryToTextActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.decimalToBinary:
                Intent intent1 = new Intent(getApplicationContext(), DecimalToBinaryActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.binaryToDecimal:
                Intent intent2 = new Intent(getApplicationContext(), BinaryToDecimalActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}