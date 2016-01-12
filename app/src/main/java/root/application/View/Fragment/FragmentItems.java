package root.application.View.Fragment;

import android.app.Fragment;
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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.util.ArrayList;
import java.util.List;

import root.application.BL.CurrentUser;
import root.application.Controller.Manager.MyFragmentManager;
import root.application.Data.MyDatabaseHelper;
import root.application.Model.Item;
import root.application.Model.User;
import root.application.R;
import root.application.View.Activity.MainActivity;
import root.application.View.Dialog.CreateItemDialog.CreateItemDialog;
import root.application.View.MyCustomView.CustomImageView;
import root.application.View.MyCustomView.ItemLayout;
import root.application.View.MyCustomView.ItemView;


public class FragmentItems extends Fragment implements View.OnClickListener{
    private View view;
    private ItemView preRoot;
    private View headerView;
    private int elementsSize;

    private FloatingActionButton fab;
    private FloatingActionButton fabFull;
    public ItemLayout layout;
    private CreateItemDialog dialog;
    private ItemView root =null;
    private String idOfSaved =null;


    public List<Item> itemList = new ArrayList<>();

    public FragmentItems() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_items,container,false);

        headerView = inflater.inflate(R.layout.drawer_layout,null);
        new getUserToHeader().execute();




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






        preRoot = (ItemView) view.findViewById(R.id.currentRootItem);

        if(savedInstanceState!=null)
        {
            idOfSaved = savedInstanceState.getCharSequence("root").toString();

        }

        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        layout = (ItemLayout) view.findViewById(R.id.layoutItem);
        layout.removeAllViews();

        fabFull = (FloatingActionButton) view.findViewById(R.id.fabfull);

        elementsSize = Math.min(layout.getWidth(),layout.getHeight())/5;
        preRoot.setMinimumWidth(elementsSize);
        preRoot.setMaxWidth(elementsSize);
        preRoot.setMaxHeight(elementsSize);
        preRoot.setMaxHeight(elementsSize);
        preRoot.invalidate();
        preRoot.measure(elementsSize, elementsSize);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CreateItemDialog();
                dialog.setParentItem(((ItemView) layout.getChildAt(0)).getViewId());
                if(layout.getChildCount()<7) {

                    dialog.show(getChildFragmentManager(), null);
                }
                else
                {
                    Toast.makeText(getActivity(),"к сожалению кол-во элементов ограничено до 6",Toast.LENGTH_SHORT).show();
                }

            }
        });


        fabFull.setVisibility(View.INVISIBLE);
        fabFull.setClickable(false);
        fabFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullMindMapFragment fragment=  new FullMindMapFragment();
                fragment.setItems(itemList);
                MyFragmentManager.getInstance().setFragment(R.id.layout,fragment,true);

            }
        });

       new getItems().execute();



        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence("root",root.getViewId());
        super.onSaveInstanceState(outState);
    }

    void Update(String rootId)
    {
        Item result = null;

        for(int i=0;i<itemList.size();i++)
        {
            if(rootId.equals(itemList.get(i).getItemId()))
            {
                result=itemList.get(i);
            }
        }

        if(result!=null)
        {
            if(!result.getParentItemId().equals("NULL"))
            {
                preRoot.setVisibility(View.VISIBLE);

                for(int i=0;i<itemList.size();i++)
                {
                    if(result.getParentItemId().equals(itemList.get(i).getItemId()))
                    {


                        preRoot.setText(itemList.get(i).getContent());
                        preRoot.setBackgroundColor(itemList.get(i).getBackground());
                        preRoot.setForegroundColor(itemList.get(i).getForeground());
                        preRoot.setBitmap(itemList.get(i).getImage());
                        preRoot.setId(itemList.get(i).getItemId());
                        preRoot.setOnClickListener(this);
                    }
                }


            }
            else
            {
                preRoot.setVisibility(View.INVISIBLE);
            }

            layout.removeAllViews();

            ItemView chRoot = new ItemView(layout.getContext(),result.getContent(),result.getBackground(),result.getForeground(),result.getImage());
            chRoot.setId(result.getItemId());
            root = chRoot;
            layout.addView(chRoot,0);

            for(int i=0;i<itemList.size();i++) {
                if (itemList.get(i).getParentItemId().equals(rootId))
                {
                    ItemView view1 = new ItemView(layout.getContext(),itemList.get(i).getContent(),itemList.get(i).getBackground(),itemList.get(i).getForeground(),itemList.get(i).getImage());
                    view1.setId(itemList.get(i).getItemId());
                    view1.setOnClickListener(this);
                    layout.addView(view1);
                }
            }


        }


    }

    @Override
    public void onClick(View v) {
        ItemView re =(ItemView) v;
        root = re;
        Update(re.getViewId());
    }


    private class getItems extends AsyncTask<Void,Void,List<Item>>
    {

        @Override
        protected List<Item> doInBackground(Void... params) {
            MyDatabaseHelper dbh= new MyDatabaseHelper(getActivity());

            List<Item> items = dbh.getItems(CurrentUser.getInsctance().getMindMapId());

            dbh.close();

            return items;
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            itemList = items;
            ItemView root ;
            Item mRoot;
            int j=1;
            preRoot.setVisibility(View.INVISIBLE);
            for(int i=0;i<itemList.size();i++)
            {
                if(itemList.get(i).getParentItemId().equals("NULL"))
                {
                    mRoot=itemList.get(i);

                    root = new ItemView(layout.getContext(),mRoot.getContent(),mRoot.getBackground(),mRoot.getForeground(),mRoot.getImage());
                    root.setId(mRoot.getItemId());
                    layout.addView(root, 0);
                    FragmentItems.this.root = root;

                }

            }

            for(int z=0;z<itemList.size();z++)
            {
               if(itemList.get(z).getParentItemId().equals(FragmentItems.this.root.getViewId()))
               {
                   ItemView view = new ItemView(layout.getContext(),itemList.get(z).getContent(),itemList.get(z).getBackground(),itemList.get(z).getForeground(),itemList.get(z).getImage());
                   view.setId(itemList.get(z).getItemId());
                   view.setOnClickListener(FragmentItems.this);
                   layout.addView(view, j);
                   j++;
               }
            }
            if(idOfSaved!=null)
            {
                Update(idOfSaved);



            }
        }
    }

    private class getUserToHeader extends AsyncTask<Void,Void,User>
    {
        @Override
        protected void onPreExecute() {

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