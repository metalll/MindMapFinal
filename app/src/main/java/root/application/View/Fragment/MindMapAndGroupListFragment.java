package root.application.View.Fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import root.application.BL.CurrentUser;
import root.application.Controller.Adapter.FragmentSectionsPagerAdapter;
import root.application.Data.MyDatabaseHelper;
import root.application.Model.User;
import root.application.R;
import root.application.View.Activity.MainActivity;
import root.application.View.MyCustomView.CustomImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MindMapAndGroupListFragment extends Fragment {

    private View view;
    private ViewPager pager;
    private View headerView;
    ProgressDialog dialog;
    public MindMapAndGroupListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_mind_map_and_group_list, container, false);
        headerView = inflater.inflate(R.layout.drawer_layout,null);
        new getUserToHeader().execute();


        pager = (ViewPager) view.findViewById(R.id.pager);

        ((MainActivity)getActivity()).drawerResult=new Drawer()
                .withActivity(this.getActivity())
                .withToolbar(new Toolbar(this.getActivity()))
                .withActionBarDrawerToggle(true)
                .withHeader(headerView).addDrawerItems(
                        new PrimaryDrawerItem().withName("Список идей").withIcon(FontAwesome.Icon.faw_home).withBadge("0"),
                        new PrimaryDrawerItem().withName("Группы").withIcon(FontAwesome.Icon.faw_group).withBadge("0"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("О программе"),
                        new SecondaryDrawerItem().withName("Настройки"),
                        new SecondaryDrawerItem().withName("Уч. запись"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Выход")).
                withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        if (((MainActivity) getActivity()).customImageView != null) {
                            ((MainActivity) getActivity()).customImageView.hideFloatingActionButton();
                        }
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        if (((MainActivity) getActivity()).customImageView != null) {
                            ((MainActivity) getActivity()).customImageView.showFloatingActionButton();
                        }
                    }
                }).
                        build();

        pager.setAdapter(new FragmentSectionsPagerAdapter(getChildFragmentManager()));





        ((MainActivity)getActivity()).customImageView =
                new CustomImageView.Builder(getActivity()).withDrawable(getResources().getDrawable(R.drawable.mini_icon))
                        .withButtonColor(Color.TRANSPARENT)
                        .withGravity(Gravity.LEFT | Gravity.TOP)
                        .withMargins(0, 0, 0, 0)
                        .create();

        if(((MainActivity) getActivity()).drawerResult.isDrawerOpen()) {
            ((MainActivity) getActivity()).customImageView.hideFloatingActionButton();
        }

        ((MainActivity)getActivity()).customImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((MainActivity)getActivity()).drawerResult.isDrawerOpen()){
                    ((MainActivity)getActivity()).drawerResult.openDrawer();
                }

            }
        });



        return view;
    }

    private class getUserToHeader extends AsyncTask<Void,Void,User>
    {
        @Override
        protected void onPreExecute() {
            dialog =new ProgressDialog(getActivity(),R.style.AppTheme_Dialog);
            dialog.setIndeterminate(true);
            dialog.setMessage("Пожалуйста подождите...");
            dialog.show();
        }

        @Override
        protected User doInBackground(Void... params) {
            MyDatabaseHelper dbh = new MyDatabaseHelper(getActivity().getBaseContext());
            User user = dbh.getUser(CurrentUser.getInsctance().getLogin());
            dbh.close();
            return user;
        }

        @Override
        protected void onPostExecute(User user) {

            TextView nameSurname=(TextView) headerView.findViewById(R.id.nameSurname);
            nameSurname.setText(user.getName() + " " + user.getSurname());
            TextView login = (TextView) headerView.findViewById(R.id.login);
            login.setText(user.getLogin());
            ImageView imageView=(ImageView) headerView.findViewById(R.id.image);
            imageView.setImageBitmap(GetCroppedBitmap(getResizedBitmap(user.getAvatar(),200,200)));
            dialog.dismiss();
        }

        public Bitmap getResizedBitmap(Bitmap bm, float newWidth, float newHeight) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth =  newWidth / width;
            float scaleHeight = newHeight / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);


            // "RECREATE" THE NEW BITMAP


            return Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
        }

        public Bitmap GetCroppedBitmap(Bitmap bitmap) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            canvas.drawCircle(bitmap.getWidth() / 2f, bitmap.getHeight() / 2f,
                    bitmap.getWidth() / 2f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);


            return output;
        }
    }



}
