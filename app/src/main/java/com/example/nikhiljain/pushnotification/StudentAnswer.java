package com.example.nikhiljain.pushnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentAnswer extends AppCompatActivity {
    private DatabaseReference ref;
    String answer = "";
    int selectedId = 0;
    private FirebaseAuth mauth;
    private RadioGroup answer_student;

    private Button button;
    private RadioButton reply;
    Bundle extras;
    TextView ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_answer);
        answer_student= (RadioGroup) findViewById(R.id.radiocourse);

        ques = (TextView) findViewById(R.id.question_student);
        extras = getIntent().getExtras();

        button= (Button) findViewById(R.id.postans);
        ques.setText(extras.getString("question"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                ref = FirebaseDatabase.getInstance().getReference().child("Answers");

                mauth = FirebaseAuth.getInstance();
                String userid = mauth.getCurrentUser().getUid();

                ref.child("student_ques_id").setValue(extras.getString("question_id"));
                ref.child("student_ans").setValue(answer);
                ref.child("user_id").setValue(userid);

            }
        });

    }


    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        selectedId = answer_student.getCheckedRadioButtonId();

        switch (view.getId()) {
            case R.id.ans_yes:

                if (checked)
                    reply = (RadioButton) findViewById(selectedId);
                answer = reply.getText().toString().trim();
                break;
            case R.id.ans_no:
                if (checked)
                    reply = (RadioButton) findViewById(selectedId);
                answer = reply.getText().toString().trim();
                break;
        }

    }
}
