package com.example.hardik.myapplication.viewPager;

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
                FriendRequestTab friendRequestTab=new FriendRequestTab();
                return friendRequestTab;
            case 1:
                FriendChatsTab friendChatsTab=new FriendChatsTab();
                return  friendChatsTab;
            case 2:
                FriendsTab friendsTab=new FriendsTab();
                return friendsTab;
            default:
                return null;
        }

    }

    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "request";
            case 1:
                return "chat";
            case 2:
                return "friends";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
