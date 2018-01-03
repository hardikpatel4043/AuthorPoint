package com.example.hardik.myapplication.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Hardik on 12/29/2017.
 */

public class InboxActivityAdapter extends FragmentPagerAdapter {
    public InboxActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                FriendChatsTab friendChatsTab=new FriendChatsTab();
                return  friendChatsTab;
            case 1:
                FriendsTab friendsTab=new FriendsTab();
                return friendsTab;
            case 2:
                FriendRequestTab friendRequestTab=new FriendRequestTab();
                return friendRequestTab;
            default:
                return null;
        }

    }

    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "chat";
            case 1:
                return "friends";
            case 2:
                return "request";

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
