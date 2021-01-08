package com.binary;

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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class TextToBinaryActivity extends AppCompatActivity {

    StringBuilder binary;
    EditText etxtText;
    TextView txtBinary;
    ImageButton btnCopy;
    RelativeLayout bgl_main;
    Switch textSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_binary);

        etxtText = findViewById(R.id.etxtText);
        txtBinary = findViewById(R.id.txtBinary);
        btnCopy = findViewById(R.id.btnCopy);
        bgl_main = findViewById(R.id.bgl_main);
        textSwitch = findViewById(R.id.switch_text_binary);

        btnCopy.setVisibility(View.GONE);

        //Fetching id from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String getUserLogin = sharedPreferences.getString(constant.HOME_PAGE, "");

        if(getUserLogin.equals("1")){
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
                    editor.putString(constant.HOME_PAGE, "1");

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


        getSupportActionBar().setTitle("Text to Binary");

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
                    stringToBinary(text,true);
                    btnCopy.setVisibility(View.VISIBLE);
                }else{
                    txtBinary.setText("");
                    btnCopy.setVisibility(View.GONE);
                }
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", binary);
                clipboard.setPrimaryClip(clip);
                Snackbar.make(bgl_main,"Copied to clipboard",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    public String stringToBinary(String str, boolean pad) {
        byte[] bytes = str.getBytes();
        binary = new StringBuilder();
        for (byte b : bytes)
        {
            binary.append(Integer.toBinaryString((int) b));
            if(pad) { binary.append(' '); }
        }
        txtBinary.setText(binary);


//        System.out.println("String to Binary : "+binary);
        return binary.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.binaryToText:
                Intent intent = new Intent(getApplicationContext(),BinaryToTextActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}