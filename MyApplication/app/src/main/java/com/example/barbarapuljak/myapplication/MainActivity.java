package com.example.barbarapuljak.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView output;
    EditText input;
    Button btn_calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         output = findViewById(R.id.result1);
         input = findViewById(R.id.input1);
         btn_calc = findViewById(R.id.calcbutton);

         btn_calc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 calculator();
             }
         });

    }

    public void calculator(){
          Integer a;
          String value = input.toString();
          a = Integer.parseInt(value);

          a = a * 2;
          output.setText(a);
    }


}
