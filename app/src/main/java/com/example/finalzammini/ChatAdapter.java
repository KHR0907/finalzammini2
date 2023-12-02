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

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatDto> arrayList = new ArrayList<>();
    private static final int VIEW_TYPE_ONE = 1;
    private static final int VIEW_TYPE_TWO = 2;


    @Override
    public int getItemViewType(int position) {
        // 여기서 각 항목에 대한 뷰 타입을 반환합니다.
        return position % 2 == 0 ? VIEW_TYPE_ONE : VIEW_TYPE_TWO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴

        switch (viewType) {
            case VIEW_TYPE_ONE:
                View viewOne = LayoutInflater.from(parent.getContext()).inflate(R.layout.mychatbox, parent, false);
                return new ViewHolderOne(viewOne);

            case VIEW_TYPE_TWO:
                View viewTwo = LayoutInflater.from(parent.getContext()).inflate(R.layout.otherchatbox, parent, false);
                return new ViewHolderTwo(viewTwo);

            default:
                // 예외 처리 등을 수행할 수 있습니다.
                return null;
        }
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        // 각 뷰 홀더에 데이터를 바인딩합니다.
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_ONE:
                ViewHolderOne viewHolderOne =(ViewHolderOne)holder ;
                viewHolderOne.text_gchat_message_me.setText(arrayList.get(position).getText_gchat_message_me());
                break;

            case VIEW_TYPE_TWO:
                ViewHolderTwo viewHolderTwo =(ViewHolderTwo) holder ;
                viewHolderTwo.text_gchat_message_you.setText(arrayList.get(position).getText_gchat_message_you());
                break;
        }
    }

    public void addItem(ChatDto chatDto) {
        arrayList.add(chatDto);
    }
    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }



    // ViewHolder 클래스들을 정의합니다.
    private static class ViewHolderOne extends RecyclerView.ViewHolder {
        protected TextView text_gchat_message_me;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            this.text_gchat_message_me = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
        }
    }

    private static class ViewHolderTwo extends RecyclerView.ViewHolder {
        protected TextView text_gchat_message_you;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);

            this.text_gchat_message_you = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
        }
    }


}
