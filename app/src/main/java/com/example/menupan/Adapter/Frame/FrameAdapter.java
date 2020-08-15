package com.example.menupan.Adapter.Frame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FrameAdapter extends FragmentPagerAdapter {
    public FrameAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return Frame_Map.newInstance();
            case 1:
                return Frame_Menu.newInstance();
            case 2:
                return Frame_Info.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "지도";
            case 1:
                return "메뉴판";
            case 2:
                return "가게 정보";
        }
        return null;
    }
}
