package root.application.View.Activity;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;

import root.application.Controller.Manager.MyFragmentManager;
import root.application.R;
import root.application.View.Fragment.Splash;
import root.application.View.MyCustomView.CustomImageView;

public class MainActivity extends Activity {

    public Drawer.Result drawerResult = null;
    public CustomImageView customImageView= null;
    public int countClose=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null) {
            MyFragmentManager.getInstance().init(this).setFragment(R.id.layout,new Splash(),true);
        }
        else {
            MyFragmentManager.getInstance().init(this);
        }



        


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(drawerResult!=null)
        if(drawerResult.isDrawerOpen())
        {
            drawerResult.closeDrawer();
        }
    }

    @Override
    public void onBackPressed() {

        if(drawerResult!=null) {
            if (drawerResult.isDrawerOpen()) {
                drawerResult.closeDrawer();
                countClose=0;
            } else {
                if (MyFragmentManager.getInstance().getFragmentManager().getBackStackEntryCount() > 1) {
                    MyFragmentManager.getInstance().getFragmentManager().popBackStack();
                    countClose = 0;
                }
            }
        }
        else {

            if (MyFragmentManager.getInstance().getFragmentManager().getBackStackEntryCount() > 1) {
                MyFragmentManager.getInstance().getFragmentManager().popBackStack();
                countClose=0;
            }
            else
            {
                if(countClose==0){
                    Toast.makeText(this, "Нажмите еще раз что бы выйти", Toast.LENGTH_LONG).show();
                    countClose =1;
                }
                else
                {
                    this.finish();
                }

            }
        }

    }


}
