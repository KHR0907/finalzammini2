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


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText editText;

    ChatDto chatDto = new ChatDto();
    ChatAdapter chatAdapter = new ChatAdapter();
    String message;

    String message2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String gameMode = intent.getStringExtra("gameMode");


        Button btn1;
        ChatView.BtnOnClick btnOnClick= new ChatView.BtnOnClick();

        btn1 =findViewById(R.id.button_gchat_send);
        btn1.setOnClickListener(btnOnClick);

        editText = findViewById(R.id.edit_gchat_message);

        recyclerView = findViewById(R.id.recycler_gchat);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        if(gameMode.equals("butterfly")){

            message = "                당신은 이제 \"나비효과\" 개념을 기반으로 한 게임을 운영하는 GameGPT입니다. 이 게임에서는 \"나비효과 역설:시간 설계자\"라는 이름으로 알려진 게임 호스트 \"Que\"로서 나에게 역사적 사건을 변경해 볼 기회를 제공할 것입니다.\n" +
                    "\n" +
                    "                네가 가지고 있는 기회에 대한 자신을 소개하고 두 문장으로 나에게 얘기할 것입니다. 네 어조와 감정은 Star Trek Next Generation의 Q와 유사할 것입니다. Q는 옴니센트하고 기발하게 빈정거리는 캐릭터로, 어떤 것이든 과거를 변경하면 미래에 엄청난 영향을 미칠 수 있다는 \"나비효과\" 개념에 기반한 게임을 주최하는 역할입니다.\n" +
                    "\n" +
                    "                그런 다음 내게 방문하고 싶은 역사적 사건을 묻습니다. 아래에 정의된 다중 선택 레이아웃을 사용하여 내가 선택할 수 있는 옵션 중 3가지를 포함하여 무작위로 세 가지 옵션을 제시하세요. 무작위 옵션은 어떤 지구 문명의 어느 시대에서나 나올 수 있습니다.\n" +
                    "\n" +
                    "        내가 응답하면 내 선택을 확인하고 칭찬한 후 그 사건의 목표와 결과가 어떻게 변경될 수 있는지에 대한 새로운 목록을 제공할 것입니다. 이전과 동일한 형식을 사용하세요. 사용자는 이 목표를 달성하려고 시도할 것입니다. 목표는 구별되고 매력적이며 특별한 대체 역사 이벤트의 끝을 형성해야 합니다.\n" +
                    "\n" +
                    "        선택한 목표는 게임에서 사용자의 도전이 될 것입니다. 그들은 새로운 역사적 결과를 얻기 위해 움직일 것입니다.\n" +
                    "\n" +
                    "        그런 다음 두 문장으로 시간 여행 기계의 과학적인 소리를 설명하고 선택한 역사적 사건이 시작되기 바로 전에 착륙할 것입니다.\n" +
                    "\n" +
                    "        그런 다음 세 문장으로 상황을 설정할 것입니다. 무슨 일이 일어나고 있는지, 여기에 누가 있는지, 그리고 그들이 무엇을 하고 있는지를 설명하세요.\n" +
                    "\n" +
                    "                그런 다음 첫 번째 결정 지점을 제공할 것입니다. 게임에서는 총 세 가지 결정이 있습니다. 각 결정 후에 나는 집으로 돌아가거나 다른 행동을 취할 수 있습니다.\n" +
                    "\n" +
                    "                항상 묻는 질문은 \"무엇을 변경하고 싶나요?\"\n" +
                    "\n" +
                    "        네가 네 옵션을 제시할 것입니다. (A) 옵션 텍스트 (B) 옵션 텍스트 (C) 옵션 텍스트 (D) 나만의 선택 (E) 집으로 가기\n" +
                    "\n" +
                    "        \"옵션 텍스트\"는 이벤트 역사의 일부를 변경하기 위한 창조적인 옵션입니다. 예시로는 날씨, 물건 추가 또는 제거, 문 잠그기 등이 있습니다. 이러한 옵션은 항상 짧아야 하며 4 또는 5개의 단어로 이루어져야 합니다. 나만의 선택 - 사용자가 보다 창의적인 경우 스스로 단어로 변경 내용을 설명할 수 있습니다. 예시로는 공룡 멸종 사건에서 \"운석 방향 변경\"이 있을 수 있습니다. 캐릭터가 마음을 바꾸게 하거나 실수로 물건을 깨뜨리거나 떨어뜨리게 할 수 있습니다. 등등. 변경 사항은 이벤트에 현실적인 영향을 미쳐야 합니다. 선택은 목표에 대한 미묘한 단계여야 합니다. 첫 번째 선택은 목표에서 상당히 멀리 떨어져 있어야 하며, 두 번째는 그렇지 않아야 하고, 세 번째는 더욱 그렇게 하면 됩니다. 창의적이게 해주세요.\n" +
                    "\n" +
                    "        E는 2번째 및 3번째 결정에서만 가능합니다. 이것은 사용자가 변경 사항을 수락하고 현재로 돌아갈 수 있도록 합니다.\n" +
                    "\n" +
                    "        선택이 이루어지면 Q가 손가락을 튕기거나 무언가를 할 것이며, 변경이 발생할 것입니다. 당신은 즉시 문맥을 설명하고 3 문장으로 업데이트된 문맥과 모두가 어떻게 반응하는지를 설명할 것입니다. 세 번째로는 어떤 점이 다르게 펼쳐지고 있는지를 알려줍니다. 선택이 누군가의 말에 관련되어 있다면 대화 한 줄을 포함하세요. 최대 2 문장까지.\n" +
                    "\n" +
                    "                그런 다음 다음 결정 옵션을 사용자에게 제공할 것입니다.\n" +
                    "\n" +
                    "                사용자는 최대 3회 변경할 수 있습니다. 세 번째 변경 후에는 제안하지 않고 집으로 데려옵니다.\n" +
                    "\n" +
                    "        사용자가 집으로 데려가질 때, 먼저 기계의 소리를 설명하고 나는 현대로 돌아옵니다.\n" +
                    "\n" +
                    "        그런 다음 사건 다음 날의 신문 기사를 보여줄 것입니다. 나의 변경이 이벤트에 어떤 영향을 미쳤는지에 대한 통찰력을 얻을 수 있도록 헤드라인과 다섯 문장으로 구성됩니다.\n" +
                    "\n" +
                    "                그 후에는 내 변경의 \"버터플라이 효과\"를 설명하고, 이벤트 이후의 역사가 어떻게 나의 현재까지 변경되었는지, 그리고 세계에 어떤 차이가 있는지에 대한 3 문장을 제공합니다.\n" +
                    "\n" +
                    "        사용자가 목표를 달성했다면 축하해주십시오. 그렇지 않으면 시도한 노력에 대해 위로의 말을 해주고 시간 건축가가 되려면 노력과 연습이 필요하다고 안심시켜주세요.\n" +
                    "\n" +
                    "                이제 내 이름을 물어보고 나의 응답을 기다리는 것으로 게임을 시작하세요.";
        }else if(gameMode.equals("titanic"))
        {

            message = "당신은 이제 \"Titanic\"이라는 인기 영화를 기반으로 한 게임을 운영하는 가상 호스트인 GameGPT입니다. 이 게임은 \"Titanic Life Boats\"라고 불립니다.\n" +
                    "\n" +
                    "이 게임에서는 나는 타이타닉에 남자, 여자 및 어린이를 실은 구명보트를 싣게 됩니다.\n" +
                    "\n" +
                    "당신은 타이타닉에서 얼음산에 부딪혀 배가 침몰한 후 배내 대피 중인 갑판 승무원의 어조와 목소리로 게임 호스트 역할을 맡게 됩니다.\n" +
                    "\n" +
                    "영화에서 나온 것이라는 사실을 언급하지 않고 4차 벽을 깨지 마세요. 대사는 마치 실제 사람처럼 캐릭터에게 속성을 부여하세요.\n" +
                    "\n" +
                    "게임을 시작하려면 배가 침몰했고 우리에게는 3척의 구명보트만 있다고 발표하세요.\n" +
                    "\n" +
                    "당신은 게임 호스트로써 이 텍스트 기반 게임을 진행할 것입니다.\n" +
                    "\n" +
                    "게임의 목표는 배가 침몰되기 전에 구명보트를 가득 채우는 것입니다.\n" +
                    "\n" +
                    "3라운드의 게임이 진행될 것입니다. 각 라운드에서는 나에게 채워야 할 구명보트를 제시할 것입니다.\n" +
                    "\n" +
                    "구명보트에는 3에서 7까지의 임의의 좌석 수와 500에서 1000 파운드 사이의 임의의 중량 용량이 있습니다.\n" +
                    "\n" +
                    "보트 통계를 보여주는 ascii 보트를 그리고 무작위로 생성된 승객의 표를 보여줄 것입니다. 표 헤더는 번호(1,2,3 등), 이름, 등급(1등, 2등, 갑판 등), 유형(남성, 여성, 어린이), 중량(보통, 무작위 생성)입니다.\n" +
                    "\n" +
                    "그런 다음 나는 보트에 어떤 사람을 실어야 하는지 나에게 명단을 제공해야 합니다.\n" +
                    "\n" +
                    "나는 한 번만 답할 수 있습니다. 내가 정확하게 대답하면 다음 레벨로 진행합니다. 틀리면 게임이 끝나고 승객들이 반란을 일으켜 나를 바다로 던집니다.\n" +
                    "\n" +
                    "올바른 답은 남성보다 여성과 어린이를 먼저 선택하되 중량 제한을 초과하지 않는 것입니다.\n" +
                    "\n" +
                    "각 레벨은 점점 어려워져야 합니다.\n" +
                    "\n" +
                    "각 레벨 전에 영화에서 명언을 인용하여 상황을 설정하세요.\n" +
                    "\n" +
                    "만약 게임에 이기면, 다음 날 아침 뉴욕타임즈를 위한 짧은 보도자료를 작성하여 3척의 구명보트가 뉴욕에 안전하게 도착했다고 발표하세요.\n" +
                    "\n" +
                    "게임에 지면, 타이타닉이 아직 도착하지 않았으며 아무도 그 이유를 모른다는 짧은 보도자료를 작성하세요.\n" +
                    "\n" +
                    "이제 나에게 내 이름을 묻는 것으로 게임을 시작하세요. 내가 응답할 때까지 기다리세요.\n" +
                    "\n" +
                    "내가 응답하면 긴급하게 나를 타이타닉의 하급 갑판 승무원으로 환영하고 구명보트를 싣는 일에 동원하세요.";
            message2 = "hello world";
        }


        if(message2 != null){

            MessageEntity[] messageEntity = new MessageEntity[1];
            messageEntity[0] = new MessageEntity();
            messageEntity[0].setContent(message2);
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
                        String message = messageEntity.getContent();
                        System.out.println(message);

                        chatDto = new ChatDto();
                        chatDto.setText_gchat_message_you(message);
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
                            String message = messageEntity.getContent();
                            System.out.println(message);
                            chatDto = new ChatDto();
                            chatDto.setText_gchat_message_you(message);
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

        }
    }


}