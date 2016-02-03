package com.example.simulationMe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tomer on 11/26/2015.
 */
public class Start extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);


        Button student = (Button)  findViewById(R.id.student);
        Button teacher = (Button)  findViewById(R.id.teacher);
//        Button button = (Button)  findViewById(R.id.button);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(),Student.class);
                startActivity(nextScreen);
            }


        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(),Teacher.class);
                startActivity(nextScreen);
            }


        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent nextScreen = new Intent(getApplicationContext(),cameraTest.class);
//                startActivity(nextScreen);
//            }
//
//
//        });
    }


}
