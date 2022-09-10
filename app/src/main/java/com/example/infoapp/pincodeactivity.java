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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class pincodeactivity extends AppCompatActivity {
    // creating variables for edi text,
    // button and our text views.
    private EditText pinCodeEdt;
    private Button getDataBtn;
    private TextView pinCodeDetailsTV;

    // creating a variable for our string.
    String pinCode;

    // creating a variable for request queue.
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pincodeactivity);

        // initializing our variables.
        pinCodeEdt = findViewById(R.id.idedtPinCode);
        getDataBtn = findViewById(R.id.idBtnGetCityandState);
        pinCodeDetailsTV = findViewById(R.id.idTVPinCodeDetails);

        // initializing our request que variable with request
        // queue and passing our context to it.
        mRequestQueue = Volley.newRequestQueue(pincodeactivity.this);

        // initialing on click listener for our button.
        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting string from EditText.
                pinCode = pinCodeEdt.getText().toString();

                // validating if the text is empty or not.
                if (TextUtils.isEmpty(pinCode)) {
                    // displaying a toast message if the
                    // text field is empty
                    Toast.makeText(pincodeactivity.this, "Please enter valid pin code", Toast.LENGTH_SHORT).show();
                } else {
                    // calling a method to display
                    // our pincode details.
                    getDataFromPinCode(pinCode);
                }
            }
        });
    }

    private void getDataFromPinCode(String pinCode) {

        // clearing our cache of request queue.
        mRequestQueue.getCache().clear();

        // below is the url from where we will be getting
        // our response in the json format.
        String url = "https://api.postalpincode.in/pincode/"+pinCode;

        // below line is use to initialize our request queue.
        RequestQueue queue = Volley.newRequestQueue(pincodeactivity.this);

        // in below line we are creating a
        // object request using volley.
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // inside this method we will get two methods
                // such as on response method
                // inside on response method we are extracting
                // data from the json format.
                try {
                    // we are getting data of post office
                    // in the form of JSON file.

                    JSONObject postOfficeArray = response.getJSONObject(0);



                        // if the status is success we are calling this method
                        // in which we are getting data from post office object
                        // here we are calling first object of our json array.
                        JSONArray ob=postOfficeArray.getJSONArray("PostOffice");
                        JSONObject obj = ob.getJSONObject(0);

                        // inside our json array we are getting district name,
                        // state and country from our data.
                        String district = obj.getString("District");
                        String state = obj.getString("State");
                        String country = obj.getString("Country");

                        if (district.equals(""))
                        {
                            district = "N/A";
                        }
                        if (state.equals(""))
                        {
                            state = "N/A";
                        }
                        if (country.equals(""))
                        {
                            country = "N/A";
                        }

                        // after getting all data we are setting this data in
                        // our text view on below line.
                        pinCodeDetailsTV.setText("Details of pin code is : \n" + "District is : " + district + "\n" + "State : "
                                + state + "\n" + "Country : " + country);

                } catch (JSONException e) {
                    // if we gets any error then it
                    // will be printed in log cat.
                    e.printStackTrace();
                    pinCodeDetailsTV.setText("Pin code is  not valid");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // below method is called if we get
                // any error while fetching data from API.
                // below line is use to display an error message.
                error.printStackTrace();
                Toast.makeText(pincodeactivity.this, "Pin code is not valid.", Toast.LENGTH_SHORT).show();
                pinCodeDetailsTV.setText("Pin code is not valid");
            }
        });
        // below line is use for adding object
        // request to our request queue.
        queue.add(objectRequest);
    }
}
