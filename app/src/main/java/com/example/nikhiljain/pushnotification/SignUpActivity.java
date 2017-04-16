package com.example.nikhiljain.pushnotification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String COURSE = "course";
    String app_server_url = "http://192.168.56.1/push_notify/token_insert.php";
    String logintype = "", coursetype = "";
    int selectedId = 0, courseid = 0;
    private EditText email;
    private EditText password;
    private RadioGroup loginas;
    private RadioGroup course;
    private Button button;
    private RadioButton radiobutton;
    private FirebaseAuth mauth;
    //private ProgressDialog progress = new ProgressDialog(getApplicationContext());
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        mauth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email_text);
        password = (EditText) findViewById(R.id.password);
        loginas = (RadioGroup) findViewById(R.id.radiogroup);
        course = (RadioGroup) findViewById(R.id.radiocourse);
        button = (Button) findViewById(R.id.signup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRegister();
            }
        });
    }

    private void setRegister() {

        String emailtext = email.getText().toString();
        System.out.println("Email" + emailtext);
        String passwordtext = password.getText().toString();
        System.out.println("Passwod" + passwordtext);


        if (!(TextUtils.isEmpty(emailtext) || TextUtils.isEmpty(passwordtext))) {

            mauth.signInWithEmailAndPassword(emailtext, passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG);
                    } else {
                        String userid = mauth.getCurrentUser().getUid();
                        DatabaseReference userdb = ref.child(userid);
                        userdb.child("id").setValue(userid);
                        userdb.child("LoginType").setValue(logintype);
                        userdb.child("Course").setValue(coursetype);


                        if (logintype.equals("Student")) {

                            SharedPreferences sharedPreferences = getApplicationContext().
                                    getSharedPreferences(getString(R.string.NOTIFY_PREF), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(COURSE, coursetype);
                            editor.commit();

//                            SharedPreferences sharedPreferences=getApplicationContext().
//                                    getSharedPreferences(getString(R.string.NOTIFY_PREF), Context.MODE_PRIVATE);
                            final String token = sharedPreferences.getString(getString(R.string.NOTIFY_TOKEN), "");
                            final String course = sharedPreferences.getString(COURSE, "NO COURSE FOUND");

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getApplicationContext(), "LOGIN SUCCESS", Toast.LENGTH_LONG);

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
                                    params.put("course", course);
//                                       params.put("professorid",professorid);

                                    return params;
                                }
                            };
                            MySingleton.getInstance(getApplicationContext()).addtoRequestQue(stringRequest);

                        } else {
                            Toast.makeText(getApplicationContext(), "LOGIN SUCCESS PROF", Toast.LENGTH_LONG);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("course_value",coursetype);
                            intent.putExtra("login_type",logintype);
                            startActivity(intent);
                        }


                    }

                }


            });

        }
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        selectedId = loginas.getCheckedRadioButtonId();

        switch (view.getId()) {
            case R.id.radio_pirates:

                if (checked)
                    radiobutton = (RadioButton) findViewById(selectedId);
                logintype = radiobutton.getText().toString().trim();
                break;
            case R.id.radio_ninjas:
                if (checked)
                    radiobutton = (RadioButton) findViewById(selectedId);
                logintype = radiobutton.getText().toString().trim();
                break;
        }

        courseid = course.getCheckedRadioButtonId();

        switch (view.getId()) {
            case R.id.cse_600:

                if (checked)
                    radiobutton = (RadioButton) findViewById(courseid);
                coursetype = radiobutton.getText().toString().trim();
                break;
            case R.id.cse_681:
                if (checked)
                    radiobutton = (RadioButton) findViewById(courseid);
                coursetype = radiobutton.getText().toString().trim();
                break;
            case R.id.cse_687:
                if (checked)
                    radiobutton = (RadioButton) findViewById(courseid);
                coursetype = radiobutton.getText().toString().trim();
                break;
        }
    }


}
