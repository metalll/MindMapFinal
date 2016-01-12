package root.application.View.Dialog.CreateItemDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import root.application.BL.CurrentUser;
import root.application.Controller.Adapter.CreateItemDialogSectionsPagerAdapter;
import root.application.Data.MyDatabaseHelper;
import root.application.Model.Item;
import root.application.R;
import root.application.View.Fragment.FragmentItems;
import root.application.View.MyCustomView.ItemView;

/**
 * Created by root on 09.01.16.
 */
public class CreateItemDialog extends DialogFragment{

    //Listener
    private DialogInterface.OnDismissListener mListener;
    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        mListener = listener;
    }


    private View view;
    private CreateItemDialogSectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private Button button;
    private String parentItem;



    public Item rootItem = null ;

    public Dialog onCreateDialog(final Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(savedInstanceState!=null)
        {
            parentItem = (String) savedInstanceState.getCharSequence("root");
        }

        return dialog;
    }


    ProgressDialog dialog ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dialog_create_mind_map, container);
        rootItem = new Item.Builder().parentItemId(parentItem).angle(0).background(getResources().getColor(R.color.primary_darker)).foreground(getResources().getColor(R.color.white)).image(null).parentMindMapId(CurrentUser.getInsctance().getMindMapId()).text("Идея").create();
        rootItem.setContent("Идея");
        sectionsPagerAdapter=new CreateItemDialogSectionsPagerAdapter(getChildFragmentManager());
        button = (Button) view.findViewById(R.id.create);
        viewPager=(ViewPager)view.findViewById(R.id.pager);

        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ((EditTextFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem())).update();
                        break;
                    case 1:
                        ((EditForegroundFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem())).update();
                        break;
                    case 2:
                        ((EditBackgroundFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem())).update();
                        break;
                    case 3:
                        ((EditBitmapFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem())).update();
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddItem().execute(rootItem);
            }
        });
        return view;
    }

    public String getParentItem() {
        return parentItem;
    }

    public void setParentItem(String parentItem) {
        this.parentItem = parentItem;
    }

    private class AddItem extends AsyncTask<Object,Void,Item>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Item doInBackground(Object... params) {
            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(getActivity());
            rootItem.setParentItemId(parentItem);
            rootItem.setParentMindMapId(CurrentUser.getInsctance().getMindMapId());

            databaseHelper.addItem(rootItem);


            return rootItem;
        }


        @Override
        protected void onPostExecute(Item item) {
            Context context = ((FragmentItems)getParentFragment()).layout.getContext();

            ItemView view1 = new ItemView(context,rootItem.getContent(),rootItem.getBackground(),rootItem.getForeground(),rootItem.getImage());
            ((FragmentItems)getParentFragment()).itemList.add(rootItem);
            view1.setClickable(true);
            view1.setFocusable(true);
            view1.setId(item.getItemId());
            view1.setOnClickListener((FragmentItems) getParentFragment());
            ((FragmentItems)getParentFragment()).layout.addView(view1);
            CreateItemDialog.this.dismiss();


        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener !=null) {
            mListener.onDismiss(dialog);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("root",parentItem);
    }
}




