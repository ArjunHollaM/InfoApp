package com.example.infoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaring button variables
    Button btn_ifsc,btn_pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing the variables
        btn_ifsc = (Button) findViewById(R.id.button_ifsc);
        btn_ifsc.setOnClickListener(this);
        btn_pincode = (Button) findViewById(R.id.button_pincode);
        btn_pincode.setOnClickListener(this);
    }
    public void onClick(View view) {
        //Checking which button was clicked
        //Redirection to bankactivity if the button clicked was to get bank details
        if(view.equals(btn_ifsc)){
            Intent intent = new Intent(this,bankactivity.class);
            startActivity(intent);
        }
        //Redirection to pincode activity if the button clicked was to get City from pincode
        if(view.equals(btn_pincode)) {
            Intent intent = new Intent(this,pincodeactivity.class);
            startActivity(intent);
        }
    }
}