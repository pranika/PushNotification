package com.example.nikhiljain.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    private static final String COURSE = "course";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private Firebase mref;
    EditText title;
    EditText message;

    String app_server_url = "http://192.168.56.1/push_notify/send_notification.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (EditText) findViewById(R.id.ques);
        message = (EditText) findViewById(R.id.ans);
//
//        user = new User();
////        mref = new Firebase("https://pushnotification-a0791.firebaseio.com/Users");
////        mref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//
//                    HashMap users = (HashMap) userSnapshot.getValue();
//                    System.out.println(users);
        Bundle extras = getIntent().getExtras();


        if (extras.getString("login_type").equals("Instructor")) {
                        final String coursetype = extras.getString("course_value").toString();
                      //  final String professorid = users.get("id").toString();
                        final String title_text = title.getText().toString();
                        final String message_text = message.getText().toString();


                        SharedPreferences sharedPreferences = getApplicationContext().
                                getSharedPreferences(getString(R.string.NOTIFY_PREF), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(COURSE, coursetype);
                        editor.putString(TITLE, title_text);
                        editor.putString(MESSAGE, message_text);
                        editor.commit();


                        System.out.println("Course" + coursetype + "id");
                        button = (Button) findViewById(R.id.token_button);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sharedPreferences = getApplicationContext().
                                        getSharedPreferences(getString(R.string.NOTIFY_PREF), Context.MODE_PRIVATE);
//                               final  String token = sharedPreferences.getString(getString(R.string.NOTIFY_TOKEN),"");
                                final String course = sharedPreferences.getString(COURSE, "NO COURSE FOUND");
                                final String ques = sharedPreferences.getString(TITLE, "NO TITLE FOUND");
                                final String ans = sharedPreferences.getString(MESSAGE, "no message found");
                                Log.d("course token", course);
                                //   System.out.println("COURSE"+course);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
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

                                        params.put("course", course);
                                        params.put("title", ques);
                                        params.put("message", ans);
//                                       params.put("professorid",professorid);

                                        return params;
                                    }
                                };
                                MySingleton.getInstance(MainActivity.this).addtoRequestQue(stringRequest);
                            }
                        });

                    }


//                }
//
//
//            }

//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });


    }
}
