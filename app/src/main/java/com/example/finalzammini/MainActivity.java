package com.example.finalzammini;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Zammini");

        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;
        BtnOnClick btnOnClick= new BtnOnClick();

        btn1 =findViewById(R.id.button1);
        btn1.setOnClickListener(btnOnClick);
    }

    class BtnOnClick implements View.OnClickListener {
        Bundle bundle = new Bundle();
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ChatView.class);
            //intent.putExtra("매개변수명",데이터);
            intent.putExtra("bundle",bundle);
            //액티비티 변환
            startActivity(intent);
        }
    }
}


