package root.application.View.Dialog.CreateMindMapDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
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
import root.application.Controller.Adapter.CreateMindMapDialogSectionsPagerAdapter;
import root.application.Data.MyDatabaseHelper;
import root.application.Model.Item;
import root.application.Model.MindMap;
import root.application.R;
import root.application.View.Fragment.MindMapListFragment;

/**
 * Created by root on 30.12.15.
 */
public class CreateMindMapDialog extends DialogFragment{
    //Listener
    private DialogInterface.OnDismissListener mListener;
    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        mListener = listener;
    }

    public Item rootItem = null ;




    public Dialog onCreateDialog(final Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }


    private View view;
    private CreateMindMapDialogSectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private Button button;
    ProgressDialog dialog ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dialog_create_mind_map, container);
        rootItem = new Item.Builder().parentItemId(null).angle(0).background(getResources().getColor(R.color.primary_darker)).foreground(getResources().getColor(R.color.white)).image(null).parentMindMapId(null).text("Идея").create();
        rootItem.setContent("Идея");

        sectionsPagerAdapter=new CreateMindMapDialogSectionsPagerAdapter(getChildFragmentManager());
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
                MindMap mindMap = new MindMap(CurrentUser.getInsctance().getLogin(),rootItem);
                new AddMindMap().execute(mindMap,rootItem);
            }
        });
        return view;
    }

    private class AddMindMap extends AsyncTask<Object,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity(),R.style.AppTheme_Dialog);
            dialog.setIndeterminate(true);
            dialog.setMessage("Пожалуйста подождите...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Object... params) {
            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(getActivity());
            MindMap mindMap =(MindMap)params[0];
            mindMap.setUserLogin(CurrentUser.getInsctance().getLogin());

            databaseHelper.addMindMap(mindMap);
            Item item = (Item) params[1];
            item.setParentItemId("NULL");
            item.setParentMindMapId(mindMap.getMindMapId());
            item.setAngle(0);
            databaseHelper.addItem(item);
            databaseHelper.close();

            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            ((MindMapListFragment)getParentFragment()).Update();
            CreateMindMapDialog.this.dismiss();
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener !=null) {
            mListener.onDismiss(dialog);
        }
    }
}
