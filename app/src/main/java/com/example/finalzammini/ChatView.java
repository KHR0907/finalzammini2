package com.example.finalzammini;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ChatView extends AppCompatActivity {

//    private RetrofitService retrofitService = RetrofitFactory.create();

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText editText;
//    ArrayList<ChatDto> arrayList = new ArrayList<>();
    ChatDto chatDto = new ChatDto();
    ChatAdapter chatAdapter = new ChatAdapter();
    String message;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");


        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;
        ChatView.BtnOnClick btnOnClick= new ChatView.BtnOnClick();

        btn1 =findViewById(R.id.button_gchat_send);
        btn1.setOnClickListener(btnOnClick);

        editText = findViewById(R.id.edit_gchat_message);

        recyclerView = findViewById(R.id.recycler_gchat);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    class BtnOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            chatDto = new ChatDto();
            message = editText.getText().toString();
            if(message != null){
                chatDto.setText_gchat_message_me(message);
                chatAdapter.addItem(chatDto);
                recyclerView.setAdapter(chatAdapter);
//                chatAdapter.notifyItemInserted(arrayList.size()-1);
                editText.setText(null);
                Toast.makeText(getApplicationContext(), "전송"+message, Toast.LENGTH_SHORT).show();
            }

//            retrofitService.doPostUserCreate();


        }
    }


}