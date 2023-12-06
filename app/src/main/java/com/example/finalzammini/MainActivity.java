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

        Button btn1, btn2;
        BtnOnClick btnOnClick = new BtnOnClick();

        btn1 = findViewById(R.id.Button1);
        btn1.setOnClickListener(btnOnClick);
        btn2 = findViewById(R.id.Button2);
        btn2.setOnClickListener(btnOnClick);
    }

    class BtnOnClick implements View.OnClickListener {
        Bundle bundle = new Bundle();

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ChatView.class);
            int id = v.getId();
            if (id == R.id.Button1) {
                intent.putExtra("bundle", bundle);
                intent.putExtra("gameMode", "butterfly");
            } else if (id == R.id.Button2) {
                intent.putExtra("bundle", bundle);
                intent.putExtra("gameMode", "titanic");
            }

            //액티비티 변환
            startActivity(intent);
        }
    }
}


