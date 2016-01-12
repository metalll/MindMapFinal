package root.application.Controller.Manager;

import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class MyFragmentManager {
    private static MyFragmentManager instance;

    public static MyFragmentManager getInstance() {
        if(instance==null)
        {
            instance=new MyFragmentManager();
        }
        return instance;
    }

    private Activity activity;
    private FragmentManager fragmentManager;

    public MyFragmentManager() {
    }

    public MyFragmentManager init(Activity activity){
        this.activity = activity;
        fragmentManager = activity.getFragmentManager();
        return this;
        }

    public void setFragment(int containerViewId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.replace(containerViewId, fragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
            fragmentTransaction.commit();
        }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
        }

}
