package com.example.chris.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private Button chatSend;
    private ListView chatArray;
    private EditText chat;
    ArrayList<String> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "OnCreate");
        setContentView(R.layout.activity_chat_window);
        chatSend = (Button) findViewById(R.id.SendButton);
        chatArray = (ListView) findViewById(R.id.chatListView);
        chat = (EditText) findViewById(R.id.Chat);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        chatArray.setAdapter(messageAdapter);
        ChatDataBaseHelper dbOpener = new ChatDataBaseHelper(this);
        final SQLiteDatabase db = dbOpener.getWritableDatabase();
        Cursor results = db.query(ChatDataBaseHelper.TEXT_MESSAGE_TABLE, new String[]{ChatDataBaseHelper.KEY_ID, ChatDataBaseHelper.KEY_MESSAGES}, null, null, null, null, null);
        int numResults = results.getCount(); //how many rows
        int messagesColumn = results.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGES);
        int idColumn = results.getColumnIndex(ChatDataBaseHelper.KEY_ID);
        results.moveToFirst(); //read from first row

        while (!results.isAfterLast()) {
            String msg = results.getString(messagesColumn);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + msg);
            myList.add(msg);
            results.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + results.getColumnCount());

        for (int i = 0; i < results.getColumnNames().length; i++) {
            Log.i(ACTIVITY_NAME, results.getColumnName(i));
        }

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempText = chat.getText().toString();
                myList.add(tempText);
                messageAdapter.notifyDataSetChanged();
                chat.setText("");

                ContentValues newRow = new ContentValues();
                newRow.put(ChatDataBaseHelper.KEY_MESSAGES, tempText);
                //ready to insert into database:
                db.insert(ChatDataBaseHelper.TEXT_MESSAGE_TABLE, "ReplacementValue", newRow);
                messageAdapter.notifyDataSetChanged(); // data has changed

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "onDestroy()");
    }

}
