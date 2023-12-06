package com.example.finalzammini;


public class ChatDto {
    private String text_gchat_message_me;
    private String text_gchat_message_you;


    public String getText_gchat_message_you() {
        return text_gchat_message_you;
    }

    public void setText_gchat_message_you(String text_gchat_message_you) {
        this.text_gchat_message_you = text_gchat_message_you;
    }

    public String getText_gchat_message_me() {
        return text_gchat_message_me;
    }

    public void setText_gchat_message_me(String text_gchat_message_me) {
        this.text_gchat_message_me = text_gchat_message_me;
    }

}