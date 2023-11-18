package com.example.finalzammini;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.CustomViewHolder> {
    private ArrayList<ChatDto> arrayList = new ArrayList<>();


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        //        protected TextView text_gchat_date_me;
//        protected TextView card_gchat_message_me;
        protected TextView text_gchat_message_me;
//        protected TextView text_gchat_timestamp_me;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
//            this.text_gchat_date_me = (TextView) itemView.findViewById(R.id.text_gchat_date_me);
//            this.card_gchat_message_me = (TextView) itemView.findViewById(R.id.card_gchat_message_me);
            this.text_gchat_message_me = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
//            this.text_gchat_timestamp_me = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
        }
    }

//    ChatAdapter(ArrayList<ChatDto> arrayList) {
//        this.arrayList = arrayList ;
//    }




    @NonNull
    @Override
    public ChatAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mychatbox, parent, false);
        ChatAdapter.CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatAdapter.CustomViewHolder holder, int position) {
//        holder.text_gchat_date_me.setText(arrayList.get(position).getText_gchat_date_me());
//        holder.card_gchat_message_me.setText(arrayList.get(position).getCard_gchat_message_me());
        holder.text_gchat_message_me.setText(arrayList.get(position).getText_gchat_message_me());
//        holder.text_gchat_timestamp_me.setText(arrayList.get(position).getText_gchat_timestamp_me());

    }


    public void addItem(ChatDto chatDto) {
        arrayList.add(chatDto);
    }
    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

}
