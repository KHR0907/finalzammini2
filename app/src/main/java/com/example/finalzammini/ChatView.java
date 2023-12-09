package com.example.finalzammini;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ChatView extends AppCompatActivity {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText editText;
    private MessageEntity[] messages;
    private long delayMillis = 2000;
    private ChatDto chatDto = new ChatDto();
    private ChatAdapter chatAdapter = new ChatAdapter();
    private String userrole = "user";
    private String systemrole = "system";
    private String model = "gpt-3.5-turbo";
    private String message;



    private static MessageEntity[] Add(MessageEntity[] originArray, MessageEntity Val) {

        MessageEntity[] newArray = null;
        // 순서 1. (원본 배열의 크기 + 1)를 크기를 가지는 배열을 생성
        if (originArray == null) {
            newArray = new MessageEntity[0];
        } else {
            newArray = new MessageEntity[originArray.length + 1];
        }


        // 순서 2. 새로운 배열에 값을 순차적으로 할당
        if (originArray != null) {
            for (int index = 0; index < originArray.length; index++) {
                newArray[index] = originArray[index];
            }
        }

        if (originArray != null) {
            // 순서 3. 새로운 배열의 마지막 위치에 추가하려는 값을 할당
            newArray[originArray.length] = Val;
        }
        // 순서 4. 새로운 배열을 반환
        return newArray;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String gameMode = intent.getStringExtra("gameMode");


        Button btn1;
        ChatView.BtnOnClick btnOnClick = new ChatView.BtnOnClick();

        btn1 = findViewById(R.id.button_gchat_send);
        btn1.setOnClickListener(btnOnClick);

        editText = findViewById(R.id.edit_gchat_message);

        recyclerView = findViewById(R.id.recycler_gchat);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        guideText();
        if (gameMode.equals("butterfly")) {
            message = getResources().getString(R.string.gamescript_butterfly);;



        } else if (gameMode.equals("titanic")) {
            message = getResources().getString(R.string.gamescript_titanic);
        }

        if (message != null) {

            MessageEntity[] messageEntity = new MessageEntity[1];
            messageEntity[0] = new MessageEntity();
            messageEntity[0].setContent(message);
            messageEntity[0].setRole(systemrole);
            messages = Add(messages, messageEntity[0]);

            JsonRequestDto jsonRequestDto = new JsonRequestDto();
            jsonRequestDto.setModel(model);
            jsonRequestDto.setMessage(messageEntity);


            String BASE_URL = "https://api.openai.com/v1/";
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(150, TimeUnit.SECONDS)
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


                    if (response.isSuccessful()) {
                        JsonResponseDto resout = response.body();
                        ChoicesEntity[] choicesEntity = resout.getChoices();
                        MessageEntity messageEntity = choicesEntity[0].getMessage();
                        String message = messageEntity.getContent();
                        messages = Add(messages, messageEntity);


                        chatDto = new ChatDto();
                        chatDto.setText_gchat_message_you("게임 로딩 완료!");
                        chatAdapter.setMode(0);
                        chatAdapter.addItem(chatDto);
                        recyclerView.setAdapter(chatAdapter);
                        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

                        // Handler를 사용하여 지연 실행
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String[] singleChat = message.split("\n\n");
                                for (String s : singleChat) {
                                    chatDto = new ChatDto();
                                    chatDto.setText_gchat_message_you(s);
                                    chatAdapter.addItem(chatDto);
                                    chatAdapter.setMode(0);
                                    recyclerView.setAdapter(chatAdapter);
                                    recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                                }
                            }
                        }, delayMillis + 200);


                    } else {
                        System.out.println(response.errorBody());
                    }
                    recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                }

                @Override
                public void onFailure(Call<JsonResponseDto> call, Throwable t) {
                    System.out.println("onFailure");
                    System.out.println(t.getMessage());
                }
            });


        }

    }

    class BtnOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            chatDto = new ChatDto();
            message = editText.getText().toString();
            if (message != null) {
                chatDto.setText_gchat_message_me(message);
                chatAdapter.setMode(1);
                chatAdapter.addItem(chatDto);
                recyclerView.setAdapter(chatAdapter);
                recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                editText.setText(null);
                MessageEntity[] messageEntity = new MessageEntity[1];
                messageEntity[0] = new MessageEntity();
                messageEntity[0].setContent(message);
                messageEntity[0].setRole(userrole);
                messages = Add(messages, messageEntity[0]);

                JsonRequestDto jsonRequestDto = new JsonRequestDto();
                jsonRequestDto.setModel(model);
                jsonRequestDto.setMessage(messages);

                //RetrofitService retrofitService = RetrofitFactory.create();
                String BASE_URL = "https://api.openai.com/v1/";
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .readTimeout(300, TimeUnit.SECONDS)
                        .writeTimeout(150, TimeUnit.SECONDS)
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
                        System.out.println("onResponse success : "+response.isSuccessful());
                        if (response.isSuccessful()) {
                            JsonResponseDto resout = response.body();
                            ChoicesEntity[] choicesEntity = resout.getChoices();
                            MessageEntity messageEntity = choicesEntity[0].getMessage();
                            String message = messageEntity.getContent();
                            messages = Add(messages, messageEntity);

                            String[] singleChat = message.split("\n\n");
                            for (String s : singleChat) {
                            chatDto = new ChatDto();
                            chatDto.setText_gchat_message_you(message);
                            chatAdapter.setMode(0);
                            chatAdapter.addItem(chatDto);
                            recyclerView.setAdapter(chatAdapter);
                            recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

                            }

                        } else {
                            System.out.println(response.errorBody());
                        }
                        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                    }

                    @Override
                    public void onFailure(Call<JsonResponseDto> call, Throwable t) {
                        System.out.println("onFailure");
                        System.out.println(t.getMessage());

                    }
                });


            }

        }
    }

    private void guideText() {

        chatDto = new ChatDto();
        chatDto.setText_gchat_message_you("게임에 입장하신걸 환영합니다!");
        chatAdapter.setMode(0);
        chatAdapter.addItem(chatDto);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);


        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatDto = new ChatDto();
                chatDto.setText_gchat_message_you("게임을 불러오는 중입니다. 잠시만 기다려주세요...");
                chatAdapter.setMode(0);
                chatAdapter.addItem(chatDto);
                recyclerView.setAdapter(chatAdapter);
                recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

                Handler handler = new Handler(Looper.getMainLooper());
                // Handler를 사용하여 지연 실행
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        chatDto = new ChatDto();
                        chatDto.setText_gchat_message_you("잠시후 게임설명과 함께 게임이 시작됩니다.");
                        chatAdapter.setMode(0);
                        chatAdapter.addItem(chatDto);
                        recyclerView.setAdapter(chatAdapter);
                        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                    }
                }, delayMillis);
            }


        }, delayMillis);
    }
}