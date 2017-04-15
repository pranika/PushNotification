package com.example.nikhiljain.pushnotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button button;
    String app_server_url = "http://www.example.com/push_notify/token_insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.token_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getApplicationContext().
                        getSharedPreferences(getString(R.string.NOTIFY_PREF), Context.MODE_PRIVATE);
              final  String token = sharedPreferences.getString(getString(R.string.NOTIFY_TOKEN),"");
                StringRequest stringRequest=new StringRequest(Request.Method.POST,app_server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("notify_token", token);
                        return params;
                    }
                };
                MySingleton.getInstance(MainActivity.this).addtoRequestQue(stringRequest);
            }
        });

    }
}
