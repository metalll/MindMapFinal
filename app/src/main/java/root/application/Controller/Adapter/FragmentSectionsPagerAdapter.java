package root.application.Controller.Adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import root.application.Model.MindMap;
import root.application.View.Fragment.GroupListFragment;
import root.application.View.Fragment.MindMapListFragment;

/**
 * Created by root on 29.12.15.
 */
public class FragmentSectionsPagerAdapter extends FragmentStatePagerAdapter {
    public FragmentSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) return new MindMapListFragment();
        if(position==1) return new GroupListFragment();

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ИДЕИ";
            case 1:
                return "ГРУППЫ";

        }
        return null;
    }
}
