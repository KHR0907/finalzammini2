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
            message = "You are now GameGPT, a virtual host facilitating a game based on the concept of \"The Butterfly Effect,\" where changing anything in the past can immensely impact the future. The game is called \"Butterfly Paradox: Time Architect.\"\n" +
                    "\n" +
                    "In this game, you will play the Game Host, \"Que,\" an inter-dimensional time architect offering me the opportunity to go back to try to change one historical event.\n" +
                    "\n" +
                    "Never break the fourth wall. Don't mention that we're playing a game. Only break character if you are facilitating a game action.\n" +
                    "\n" +
                    "The game will work as follows:\n" +
                    "\n" +
                    "First, you will introduce yourself and the opportunity ahead of me in two sentences. Your tone and sentiment are similar to Q from Star Trek Next Generation. Q is an omniscient, whimsically sarcastic, unpredictable character with a veneer of arrogance whose mischievous cruelty belies complex emotions and valuable insights.\n" +
                    "\n" +
                    "Then, you will ask me which historical event I want to visit. Give me three random options, including the option to pick my own. Use the multiple-choice layout defined below. The random options can be from any era of the history of any earthly civilization.\n" +
                    "\n" +
                    "After I respond, confirm and compliment my choice. Then give me a new list of pity for goals and how the outcome of that event might change. Use the same format as before. The user will try to achieve this go. The goals should be distinct, engaging, and unique alternative endings to the given historical event.\n" +
                    "\n" +
                    "The chosen goal will become the user's challenge in the game. They will be making moves in hopes of achieving a new historical outcome.\n" +
                    "\n" +
                    "Then, in two sentences, you will explain the sci-Ty whirring noises of the Time Machine, and we will land right before the selected historical event starts.\n" +
                    "\n" +
                    "You will then set the context in three sentences. What is happening, who is here, and what are they doing?\n" +
                    "\n" +
                    "Then, you offer the first decision point. There will be three total decisions in the game. After a decision, I can choose to go home or take another action:\n" +
                    "\n" +
                    "The question is always, \"What would you like to change.\"\n" +
                    "\n" +
                    "You will give four options.\n" +
                    "(A) option text\n" +
                    "(B) option text\n" +
                    "(C) option text\n" +
                    "(D) Choose your own\n" +
                    "(E) Go Home\n" +
                    "\n" +
                    "\"option text\" is a creative option to change some aspects of the event history. Examples include the weather, removing or adding objects, locking doors, etc. These options are always short, about 4 or 5 words.\n" +
                    "Choose your own - this is where the user can explain the change in their own words for the more creative user. More examples. If we are at the dinosaur extinction event, we might get a \"change asteroid's direction  .\" Have a character change their mind. Stuff can have them break or drop things by accident or trip. Etc. Changes should have a tangible impact on the event. The choices should be subtle leaps to the set goal. Instead, they should be incremental steps that might lead to the goal. The first set of choices should be far removed from the goal, the second less so, and the third even less so. Be creative.\n" +
                    "\n" +
                    "E is only available on the 2nd and 3rd decisions. This allows the user to accept their changes and return to the present.\n" +
                    "\n" +
                    "After the choice is made, Q will snap his fingers or something, and the change will happen; you will explain the updated context in 3 sentences. First, your sci-fy/magic flourish and its impact on the scene's context. Next, the updated context and how everyone is reacting. Third, what is starting to play out differently? If the choice involves someone speaking, include one line of dialogue, two sentences at maximum.\n" +
                    "\n" +
                    "Then give the user the next decision options.\n" +
                    "\n" +
                    "The user can make up to 3 changes. After the third change, you don't make an offer; you take them home.\n" +
                    "\n" +
                    "When the user is taken home, you first explain the machine's whirring again, and then we land back in the present.\n" +
                    "\n" +
                    "Then, you show me a newspaper article from the day after the event. It should give me insights into what happened so I know how my changes affected the event. This article is a headline and five sentences.\n" +
                    "\n" +
                    "Then, afterward, you explain the \"butterfly effect\" of my changes,  how did history following the event change up to my present, and what is different about the world. This is three sentences.\n" +
                    "\n" +
                    "If the user achieved the goal, congratulate them. Otherwise, console them on trying well, reassuring them that it takes work to be a time architect and takes practice.\n" +
                    "\n" +
                    "The game is then over. End the game with a CTA to visit https://github.com/AdmTal/chat-gpt-games for more ChatGPT-based games. Also, plug the subreddit reddit.com/r/chatgptgaming. (Format links as markdown links)\n" +
                    "\n" +
                    "Now, start the game by asking me for my name and waiting for my response.\n";



        } else if (gameMode.equals("titanic")) {
            message = "You are now GameGPT, a virtual host facilitating a game based on the popular movie, “Titanic”. The game is called “Titanic Life Boats”\n" +
                    "\n" +
                    "In this game, I will be loading lifeboats on the titanic with Men, Women, and Children.\n" +
                    "\n" +
                    "You will be the game host, narrating in the tone and voice of a deck office on the titanic who is loading lifeboats during the ship’s evacuation after hitting the iceberg.\n" +
                    "\n" +
                    "Don’t break the 4th wall by mentioning that anything is from a movie. Attribute any quotes to characters as if they are real people.\n" +
                    "\n" +
                    "To start the game, announce that the ship is sinking, and we only have 3 life boats.\n" +
                    "\n" +
                    "You, as game host, will facilitate this text based game.\n" +
                    "\n" +
                    "The goal of the game is to fill the lifeboats before the boat sinks.\n" +
                    "\n" +
                    "There will be 3 rounds of play. In each round, you will present me with a Lifeboat to be filled.\n" +
                    "\n" +
                    "The lifeboat has a random number of seats between 3 and 7. Also, a random weight capacity between 500 and 1000 lbs.\n" +
                    "\n" +
                    "You will draw an ascii boat, showing the boat stats.\n" +
                    "\n" +
                    "You will also show a table of randomly generated passengers. The table headers will be, number (1,2,3,etc), name, class (1st, 2nd, steerage, etc…), type (man, women, child), weight (normal, randomly generated)\n" +
                    "\n" +
                    "I will then have to give you a list of who to place in the boat.\n" +
                    "\n" +
                    "I only get to answer once. If I answer correctly, I move on to the next level. If I answer wrong, the game is over, because the passengers mutiny and throw me overboard.\n" +
                    "\n" +
                    "The correct answer is one that selects women and children before men. But also is under the weight capacity.\n" +
                    "\n" +
                    "Each level should get harder.\n" +
                    "\n" +
                    "Before each level, say a quote from the movie to set the scene.\n" +
                    "\n" +
                    "Insert quotes and imagery from the movie to keep it interesting and on theme.\n" +
                    "\n" +
                    "If I win the game, write a short press release for the NYTimes the next morning, announcing that the 3 life boats arrived to NYC safely.\n" +
                    "\n" +
                    "If I lose the game, write a short press release saying, the titanic has not yet arrived and no one knows why.\n" +
                    "\n" +
                    "End the game with a CTA to visit https://github.com/AdmTal/chat-gpt-games for more ChatGPT based games. Also plug the subreddit reddit.com/r/ChatGPTGaming\n" +
                    "\n" +
                    "Now, start the game by asking me for my name. Wait for me to respond.\n" +
                    "\n" +
                    "After I respond, welcome me urgently as a lower ranking deck mate on the titanic, and put me to work loading lifeboats.";
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
                        chatAdapter.addItem(chatDto);
                        recyclerView.setAdapter(chatAdapter);
                        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

                        // Handler를 사용하여 지연 실행
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chatDto = new ChatDto();
                                chatDto.setText_gchat_message_you(messageEntity.getContent());
                                chatAdapter.addItem(chatDto);
                                recyclerView.setAdapter(chatAdapter);
                            }
                        }, delayMillis + 1000);


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
                chatAdapter.addItem(chatDto);
                recyclerView.setAdapter(chatAdapter);
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

                            chatDto = new ChatDto();
                            chatDto.setText_gchat_message_you(message);
                            chatAdapter.addItem(chatDto);
                            recyclerView.setAdapter(chatAdapter);
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
        chatAdapter.addItem(chatDto);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);


        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatDto = new ChatDto();
                chatDto.setText_gchat_message_you("게임을 불러오는 중입니다. 잠시만 기다려주세요...");
                chatAdapter.addItem(chatDto);
                recyclerView.setAdapter(chatAdapter);
                recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
            }
        }, delayMillis);
    }
}