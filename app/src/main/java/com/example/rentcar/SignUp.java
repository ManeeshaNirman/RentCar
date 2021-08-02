package com.example.rentcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private String from;
    TextView signin,fname,lname,email,mobile,password1,password2;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signin=(TextView) findViewById(R.id.signinbuttonsignup);
        signin.setOnClickListener(this);



        Bundle bundle=getIntent().getExtras();
        from=bundle.get("From").toString();

        if(from.equals("Frombtn")){

           Toast.makeText(this,"Frombtn",Toast.LENGTH_SHORT).show();

        }else if(from.equals("FromGoogle")){


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signupbutton:

                break;

            case R.id.signinbuttonsignup:
                startActivity(new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

        }
    }
}