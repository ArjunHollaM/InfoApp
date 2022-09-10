package com.example.infoapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class bankactivity extends AppCompatActivity {

    // creating variables for edit text
    // and our text views.
    private EditText ifscCodeEdt;
    private TextView bankDetailsTV;

    // creating a variable for
    // our ifsc code string.
    String ifscCode;

    // creating a variable for request queue.
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bankactivity);

        // initializing our variables.
        ifscCodeEdt = findViewById(R.id.idedtIfscCode);
        Button getBankDetailsBtn = findViewById(R.id.idBtnGetBankDetails);
        bankDetailsTV = findViewById(R.id.idTVBankDetails);

        // initializing our request que variable with request queue
        // and passing our context to it.
        mRequestQueue = Volley.newRequestQueue(bankactivity.this);

        // initialing on click listener for our button.
        getBankDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting string from edittext.
                ifscCode = ifscCodeEdt.getText().toString();

                // validating if the edit text
                // is empty or not.
                if (TextUtils.isEmpty(ifscCode)) {
                    // displaying a toast message if the text field is empty
                    Toast.makeText(bankactivity.this, "Please enter valid IFSC code", Toast.LENGTH_SHORT).show();
                } else {
                    // calling a method to display
                    // our ifsc code details.
                    getDataFromIFSCCode(ifscCode);
                }
            }
        });
    }

    private void getDataFromIFSCCode(String ifscCode) {

        // clearing our cache of request queue.
        mRequestQueue.getCache().clear();

        // below is the url from where we will be getting
        // our response in the json format.
        String url = "https://ifsc.razorpay.com/" + ifscCode;

        // below line is use to initialize our request queue.
        RequestQueue queue = Volley.newRequestQueue(bankactivity.this);

        // creating a json object request for our API.
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                // this method is used to get
                // the response from the API.


                // if the status is successful we are
                // extracting data from JSON file
//
                String state = response.optString("CENTRE");
                String bankName = response.optString("BANK");
                String branch = response.optString("BRANCH");
                String address = response.optString("ADDRESS");
                String contact = response.optString("CONTACT");
                String bankcode = response.optString("BANKCODE");
                String city = response.optString("CITY");

                if(contact.equals("")){
                    contact = "N/A";
                }
                if(state.equals("")){
                    state = "N/A";
                }
                if(bankName.equals("")){
                    bankName = "N/A";
                }
                if(branch.equals("")){
                    branch = "N/A";
                }
                if(address.equals("")){
                    address = "N/A";
                }
                if(bankcode.equals("")){
                    bankcode = "N/A";
                }
                if(city.equals("")){
                    city = "N/A";
                }

                // after extracting this data we are displaying
                // that data in our text view.
                bankDetailsTV.setText("Bank Name : " + bankName + "\nBranch : " + branch + "\nAddress : " + address + "\nBank Code : " + bankcode + "\nCity : " + city + "\nState : " + state + "\nContact : " + contact);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // if we get any error while loading json
                // data we are setting our text to invalid IFSC code.
                bankDetailsTV.setText("Invalid IFSC Code");
            }
        });
        // below line is use for adding object
        // request to our request queue.
        queue.add(objectRequest);
    }
}
