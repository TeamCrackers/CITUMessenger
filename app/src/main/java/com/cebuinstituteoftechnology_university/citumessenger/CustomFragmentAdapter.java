package com.cebuinstituteoftechnology_university.citumessenger;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;

/**
 * Created by ianosias on 11/12/15.
 */
public class CustomFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Integer> imageResIds;
    private ArrayList<String> tabTitles;
    private Context context;
    ArrayList<android.support.v4.app.Fragment> fragments;

    public CustomFragmentAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
        tabTitles = new ArrayList<>();
        fragments = new ArrayList<>();
        imageResIds = new ArrayList<>();
    }

    public void addFragment(android.support.v4.app.Fragment fragment, String title,int resId){
        fragments.add(fragment);
        tabTitles.add(title);
        imageResIds.add(resId);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        int x = android.R.drawable.ic_menu_day;
        int y = imageResIds.get(position);
        Drawable image = ContextCompat.getDrawable(context, imageResIds.get(position));
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" "+tabTitles.get(position));
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
