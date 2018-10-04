package com.example.chris.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private Button chatSend;
    private ListView chatArray;
    private EditText chat;
    ArrayList<String> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatSend = (Button) findViewById(R.id.SendButton);
        chatArray = (ListView) findViewById(R.id.chatListView);
        chat = (EditText) findViewById(R.id.Chat);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        chatArray.setAdapter(messageAdapter);

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myList.add(chat.getText().toString());
                messageAdapter.notifyDataSetChanged();
                chat.setText("");

            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {

            super(ctx, 0);

        }

        public int getCount() {
            return myList.size();
        }

        public String getItem(int position) {
            return myList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);

            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            }
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;


        }

        public long getItemId(int position) {
            return position;
        }
    }


}
