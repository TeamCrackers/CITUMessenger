package com.cebuinstituteoftechnology_university.citumessenger.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cebuinstituteoftechnology_university.citumessenger.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.friendsMenuItem)         ImageButton friendMenu;
    @Bind(R.id.searchFriendsMenuItem)   ImageButton searchMenu;
    @Bind(R.id.preferenceMenuItem)      ImageButton prefMenu;
    @Bind(R.id.messageList)             ListView messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.post(new Runnable() {
            @Override
            public void run() {
                int width = ((LinearLayout)friendMenu.getParent()).getMeasuredWidth();
                friendMenu.getLayoutParams().width = width / 3;
                searchMenu.getLayoutParams().width = width / 3;
                prefMenu.getLayoutParams().width = width / 3;
            }
        });
    }

    protected void addMessage(String str){
        ArrayAdapter<String> adapter = (ArrayAdapter<String>)messageList.getAdapter();
        if(adapter == null)
           adapter = new ArrayAdapter<String>(this.getBaseContext(),R.layout.support_simple_spinner_dropdown_item);
        adapter.add(str + adapter.getCount());
        messageList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //int x = menuContainer.getMeasuredWidth();
    }


    @OnClick(R.id.friendsMenuItem)
    public void friendsMenuClick(View view){
        Snackbar.make(view, "Chuya friends", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        try {
            addMessage("Friends Clicked");
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        String s = Environment.getDataDirectory().getAbsolutePath();
        System.out.println(s);
    }

    @OnClick(R.id.searchFriendsMenuItem)
    public void searchMenuClick(View view){
        Snackbar.make(view, "Chuya search", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    @OnClick(R.id.preferenceMenuItem)
    public void preferenceMenuClick(View view){
        Snackbar.make(view, "Chuya preference", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MainActivityFragment extends android.support.v4.app.Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

    /**
     * Created by ianosias on 11/11/15.
     */
    public static class MessageList extends Fragment{

        public MessageList() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_message, container, false);
        }

    }

    /**
     * Created by ianosias on 11/11/15.
     */
    public static class MessagingMenu extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_messaging_menu, container, false);
        }
    }
}
