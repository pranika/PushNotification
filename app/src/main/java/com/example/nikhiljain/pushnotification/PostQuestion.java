package com.example.nikhiljain.pushnotification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PostQuestion extends AppCompatActivity {

    private RadioGroup questiontype;

    private Button button;
    private RadioButton reply;
    String type;
    int selectedId=0;
    Intent intent=new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        final String course=extras.getString("course_value");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);
        questiontype=(RadioGroup)findViewById(R.id.radiogroup);
        button= (Button) findViewById(R.id.posttype);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("YES / NO"))
                {
                    intent = new Intent(getApplicationContext(), YesNo.class);
                    intent.putExtra("course_ques",course);
                    startActivity(intent);

                }
                else if(type.endsWith("Multiple Choice"))
                {

                    intent = new Intent(getApplicationContext(), MCQ.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Select Option",Toast.LENGTH_LONG).show();

            }
        });

    }
    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        selectedId = questiontype.getCheckedRadioButtonId();


        switch (view.getId()) {
            case R.id.yes_no:

                if (checked)

                    reply = (RadioButton) findViewById(selectedId);
                    type = reply.getText().toString().trim();

                break;
            case R.id.mcq:
                if (checked)
                    reply = (RadioButton) findViewById(selectedId);
                type = reply.getText().toString().trim();
                break;
        }
    }
}
