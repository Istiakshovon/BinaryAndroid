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

public class DecimalToBinaryActivity extends AppCompatActivity {

    String binary;
    EditText etxtDecimal;
    TextView txtBinary;
    TextView btnCopy;
    RelativeLayout bgl_decimal_to_binary;
    Switch textDecimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decimal_to_binary);

        etxtDecimal = findViewById(R.id.etxtDecimal);
        txtBinary = findViewById(R.id.txtBinary);
        btnCopy = findViewById(R.id.btnCopy);
        bgl_decimal_to_binary = findViewById(R.id.bgl_decimal_to_binary);
        textDecimal = findViewById(R.id.switch_decimal_binary);

        btnCopy.setVisibility(View.INVISIBLE);

        getSupportActionBar().setTitle("Decimal to Binary");

        //Fetching id from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String getUserLogin = sharedPreferences.getString(constant.HOME_PAGE, "");

        if(getUserLogin.equals("2")){
            textDecimal.setChecked(true);
        }else{
            textDecimal.setChecked(false);
        }

        etxtDecimal.addTextChangedListener(new TextWatcher() {

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
                    try {
                        String text = etxtDecimal.getText().toString();
                        txtBinary.setText(Integer.toBinaryString(Integer.parseInt(text)));
                        binary = Integer.toBinaryString(Integer.parseInt(text));
                        btnCopy.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    txtBinary.setText("");
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
                Snackbar.make(bgl_decimal_to_binary,"Copied to clipboard",Snackbar.LENGTH_SHORT).show();
            }
        });

        textDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textDecimal.isChecked()){
                    //Creating a shared preference

                    SharedPreferences sp = getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sp.edit();
                    //Adding values to editor
                    editor.putString(constant.HOME_PAGE, "2");

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.textToBinary:
                Intent intent = new Intent(getApplicationContext(), TextToBinaryActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.binaryToText:
                Intent intent1 = new Intent(getApplicationContext(), BinaryToTextActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.binaryToDecimal:
                Intent intent2 = new Intent(getApplicationContext(), BinaryToDecimalActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.textToHash:
                Intent intent3 = new Intent(getApplicationContext(), StringToHashActivity.class);
                startActivity(intent3);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}