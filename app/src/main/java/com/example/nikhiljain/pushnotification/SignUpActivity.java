package com.example.nikhiljain.pushnotification;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private RadioGroup loginas;
    private Button button;
    private RadioButton radiobutton;
    private FirebaseAuth mauth;
    //private ProgressDialog progress = new ProgressDialog(getApplicationContext());
    private DatabaseReference ref;
    String logintype="";
    int selectedId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ref= FirebaseDatabase.getInstance().getReference().child("Users");

        mauth=FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.email_text);
        password= (EditText)findViewById(R.id.password);
        loginas= (RadioGroup)findViewById(R.id.radiogroup);
        button= (Button) findViewById(R.id.signup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRegister();
            }
        });
    }

    private void setRegister() {

        String emailtext = email.getText().toString();
        System.out.println("Email"+emailtext);
        String passwordtext = password.getText().toString();
        System.out.println("Passwod"+passwordtext);


        if(!(TextUtils.isEmpty(emailtext) || TextUtils.isEmpty(passwordtext)))
        {

            mauth.signInWithEmailAndPassword(emailtext, passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG);
                    }

                    else
                    {
                        String userid=mauth.getCurrentUser().getUid();
                        DatabaseReference userdb=ref.child(userid);
                        userdb.child("LoginType").setValue(logintype);

                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();

                    }

                }
            });

        }
    }
    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        selectedId = loginas.getCheckedRadioButtonId();

        switch(view.getId()) {
            case R.id.radio_pirates:

                if (checked)
                    radiobutton = (RadioButton) findViewById(selectedId);
                    logintype= radiobutton.getText().toString().trim();
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    radiobutton = (RadioButton) findViewById(selectedId);
                    logintype = radiobutton.getText().toString().trim();
                    break;
        }
    }
}
