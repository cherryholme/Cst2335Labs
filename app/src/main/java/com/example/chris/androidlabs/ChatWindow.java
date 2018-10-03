package com.example.chris.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends Activity {
    Context ctx = this;
    private Button chatSend;
    private ListView chatArray;
    private EditText chat;
    ListView myList = (ListView) findViewById(R.id.chatListView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatSend = findViewById(R.id.SendButton);
        chatArray = findViewById(R.id.chatListView);
        chat = findViewById(R.id.Chat);
        final List<String> chatlist = new ArrayList<>();

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = chat.getText().toString();
                chatlist.add(getInput);


            }
        });
    }

    private class MyListAdapter extends BaseAdapter<String> {

        public ChatAdapter(Context ctx) {

            super(ctx, 0);

        }


    }


}
