package root.application.View.Fragment;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import root.application.Controller.Manager.MyFragmentManager;
import root.application.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Splash extends Fragment {


    public Splash() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        new nextFragment().execute();
        return inflater.inflate(R.layout.fragment_splash, container, false);


    }

    private class nextFragment extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            while (MyFragmentManager.getInstance().getFragmentManager().getBackStackEntryCount()>0)
            {
                MyFragmentManager.getInstance().getFragmentManager().popBackStackImmediate();
            }
                MyFragmentManager.getInstance().setFragment(R.id.layout, new AuthorizationFragment(),true);
        }
    }
}
