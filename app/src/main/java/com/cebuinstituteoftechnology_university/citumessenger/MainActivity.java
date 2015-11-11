package com.cebuinstituteoftechnology_university.citumessenger;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab)     FloatingActionButton fab;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    ActionBarDrawerToggle drawerToggle;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.tabLayout)TabLayout tabLayout;
    @Bind(R.id.viewPager)ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        MainActivity.setActivityTitleWithImage(this, "CITUMessenger", android.R.drawable.ic_menu_manage);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        initializeTabs();
    }

    public static void setActivityTitleWithImage(Activity activity,String title,int resId){
        Drawable image = ContextCompat.getDrawable(activity.getBaseContext(), resId);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString("   "+title);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        activity.setTitle(sb);
    }

    protected void initializeTabs(){
        CustomFragmentAdapter adapter = new CustomFragmentAdapter(getSupportFragmentManager(),MainActivity.this);
        adapter.addFragment(AccountSettings.newInstance("", ""), "Feed", android.R.drawable.ic_menu_day);
        adapter.addFragment(Messages.newInstance("",""),"Messages",android.R.drawable.ic_dialog_email);
        adapter.addFragment(Friends.newInstance("", ""), "Friends", android.R.drawable.ic_menu_myplaces);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabLayoutListener();
    }
    @OnClick(R.id.tabLayout)
    protected void setTabLayoutListener(){
        int x = 0;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_friends) {
            tabLayout.getTabAt(0).select();

        } else if (id == R.id.nav_classes) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
