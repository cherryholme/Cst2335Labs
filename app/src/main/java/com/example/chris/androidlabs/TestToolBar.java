package com.example.chris.androidlabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

public class TestToolBar extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "TestToolBar";
    private Button snackbar;
    private String currentMessage = "You Selected item 1";
    private EditText editText;
    AlertDialog dialog;
    AlertDialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
        Toolbar toolbar = findViewById(R.id.lab8_toolbar);
        setSupportActionBar(toolbar);

        snackbar = findViewById(R.id.snackbar_id);
        snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.snackbar_id), currentMessage, Snackbar.LENGTH_SHORT).show();


            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(TestToolBar.this);
        builder.setMessage("")
                .setTitle("Do you want to go back?");



        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
           finish();




            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
      dialog = builder.create();

        AlertDialog.Builder builder2 = new AlertDialog.Builder(TestToolBar.this);
        builder2.setView(R.layout.lab8resourcefile);
        builder2.setMessage("").setTitle("Do you want to save this as current message?");
        builder2.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editText = dialog2.findViewById(R.id.ed_id);
                String tempText = editText.getText().toString();
                Log.i(ACTIVITY_NAME, currentMessage);
                currentMessage = tempText;
                Log.i(ACTIVITY_NAME, currentMessage);






            }
        });
        builder2.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        dialog2 = builder2.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id;
        id = mi.getItemId();

        switch (id) {

            case R.id.action_one:

                Log.i(ACTIVITY_NAME, "action one");
                Snackbar.make(findViewById(R.id.snackbar_id), currentMessage, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_two:
                Log.i(ACTIVITY_NAME, "action two");
                dialog.show();
                //Start an activit…
                break;
            case R.id.action_three:
                Log.i(ACTIVITY_NAME, "action three");
                currentMessage = "You Selected item 3";
                Snackbar.make(findViewById(R.id.snackbar_id), currentMessage, Snackbar.LENGTH_SHORT).show();
                dialog2.show();

                break;
            case R.id.about_id:
                Toast toast = Toast.makeText(getApplicationContext(), "Version 1.0, by Christian Fortin-Cherryholme", Toast.LENGTH_LONG);
                toast.show();
                break;


        }
        return true;
    }
}
