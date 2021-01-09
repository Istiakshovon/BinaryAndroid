package com.binary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class BinaryToTextActivity extends AppCompatActivity {

    RelativeLayout bgl_binary;
    EditText etxtBinary;
    TextView txtText;
    TextView btnCopy;
    String text;
    Switch binarySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_to_text);

        bgl_binary = findViewById(R.id.bgl_binary);
        etxtBinary = findViewById(R.id.etxtBinary);
        txtText = findViewById(R.id.txtText);
        btnCopy = findViewById(R.id.btnCopy);
        binarySwitch = findViewById(R.id.switch_binary_text);

        getSupportActionBar().setTitle("Binary to Text");
        btnCopy.setVisibility(View.INVISIBLE);

        //Fetching id from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String getUserLogin = sharedPreferences.getString(constant.HOME_PAGE, "");

        if(getUserLogin.equals("0")){
            binarySwitch.setChecked(true);
        }else{
            binarySwitch.setChecked(false);
        }

        binarySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isChecked = binarySwitch.isChecked();

                if(isChecked){
                    //Creating a shared preference

                    SharedPreferences sp = getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sp.edit();
                    //Adding values to editor
                    editor.putString(constant.HOME_PAGE, "0");

                    //Saving values to editor
                    editor.apply();
                }else{
                    //Creating a shared preference

                    SharedPreferences sp = getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sp.edit();
                    //Adding values to editor
                    editor.putString(constant.HOME_PAGE, "1");

                    //Saving values to editor
                    editor.apply();
                }
            }
        });

        etxtBinary.addTextChangedListener(new TextWatcher() {

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
                    String binary = etxtBinary.getText().toString();
                    binaryToText(binary.trim());
                    btnCopy.setVisibility(View.VISIBLE);
                }else{
                    txtText.setText("");
                    btnCopy.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", text);
                clipboard.setPrimaryClip(clip);
                Snackbar.make(bgl_binary,"Copied to clipboard",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    public void binaryToText(String binaryString) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            int charCode = 0;
            // Split the string
            String[] parts = binaryString.split(" ");
            // Consider each part on its own
            for (String part : parts) {
                charCode = Integer.parseInt(part, 2);
                String returnChar = Character.toString((char) charCode);
                stringBuilder.append(returnChar);
            }
//        System.out.println("Text : "+stringBuilder.toString());
            txtText.setText(stringBuilder.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.textToBinary:
                Intent intent = new Intent(getApplicationContext(),TextToBinaryActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.decimalToBinary:
                Intent intent1 = new Intent(getApplicationContext(),DecimalToBinaryActivity.class);
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