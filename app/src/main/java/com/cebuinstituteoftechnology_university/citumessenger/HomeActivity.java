package com.cebuinstituteoftechnology_university.citumessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.AuthenticationService;
import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.ChatService;
import com.cebuinstituteoftechnology_university.citumessenger.Events.AuthenticationEvent;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Friends;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Request;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static User CURRENT_USER = null;
    FriendsFragment friendsFragment;
    InboxFragment inboxFragment;
    NotificationsFragment notificationsFragment;



    private int[] tabIcons = {
            R.drawable.inbox,
            R.drawable.friends,
            R.drawable.notification_icon
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        if(CURRENT_USER ==null)this.startActivity(new Intent(this, LoginActivity.class));
        EventBus.getDefault().register(this);

    }

    public void onEvent(Request request){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HomeActivity.this,"Request sent successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onEvent(final User []friends){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(User friend:friends){
                    Friends friendTemp = new Friends();
                    friendTemp.setUserInfo(friend);
                    HomeActivity.this.friendsFragment.addFriend(friendTemp);
                }
            }
        });
    }

    public void onEvent(final ArrayList<Serializable> serializables){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HomeActivity.this.inboxFragment.inboxAdapter.clear();
                for (Serializable serializable : serializables) {
                    if (serializable instanceof Request)
                        HomeActivity.this.notificationsFragment.notificationAdapter.addRequest((Request) serializable);
                    else
                        HomeActivity.this.inboxFragment.inboxAdapter.addConversation((Conversation) serializable);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CURRENT_USER!=null)
        {
            friendsFragment.clearFriendList();

            ChatService.startLoadingAllConversations(this, CURRENT_USER);
            AuthenticationService.getFriends(this);

        }


    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        friendsFragment = new FriendsFragment();
        inboxFragment = new InboxFragment();
        notificationsFragment = new NotificationsFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(inboxFragment, "MESSAGES");
        adapter.addFragment(friendsFragment, "FRIENDS");
        adapter.addFragment(notificationsFragment, "NOTIFS");
        viewPager.setAdapter(adapter);
    }

    public void startChatActivity(){
        Intent intent = new Intent(this,ChatActivity.class);
        this.startActivity(intent);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final Stack<Fragment> mFragmentList = new Stack<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
