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
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        String gameMode = intent.getStringExtra("gameMode");


        if(gameMode.equals("butterfly")){

        }else if(gameMode.equals("titanic")){

        }

        Button btn1;
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
                MessageEntity[] messageEntity = new MessageEntity[1];
                messageEntity[0] = new MessageEntity();
                messageEntity[0].setContent(message);
                messageEntity[0].setRole("user");

                JsonRequestDto jsonRequestDto = new JsonRequestDto();
                jsonRequestDto.setModel("gpt-3.5-turbo");
                jsonRequestDto.setMessage(messageEntity);

                //RetrofitService retrofitService = RetrofitFactory.create();
                String BASE_URL = "https://api.openai.com/v1/";
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitService retrofitService = retrofit.create(RetrofitService.class);
                final Call<JsonResponseDto> call = retrofitService.sendChat(jsonRequestDto);
                call.enqueue(new Callback<JsonResponseDto>() {
                    @Override
                    public void onResponse(Call<JsonResponseDto> call, Response<JsonResponseDto> response) {
                        System.out.println("onResponse");
                        System.out.println(response.isSuccessful());

                        if(response.isSuccessful()){
                            JsonResponseDto resout = response.body();
                            ChoicesEntity[] choicesEntity = resout.getChoices();
                            MessageEntity messageEntity = choicesEntity[0].getMessage();
                            System.out.println(messageEntity.getContent());
                            chatDto = new ChatDto();
                            //chatDto.setText_gchat_message_you(response.body().getChoices().getMessage());
                            chatAdapter.addItem(chatDto);
                            recyclerView.setAdapter(chatAdapter);
                        }
                        else{
                            System.out.println(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponseDto> call, Throwable t) {
                        System.out.println("onFailure");
                        System.out.println(t.getMessage());

                    }
                });


            }

//            retrofitService.doPostUserCreate();


        }
    }


}