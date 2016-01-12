package root.application.View.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import root.application.BL.CurrentUser;
import root.application.Controller.Adapter.GridViewAdapter;
import root.application.Controller.Manager.MyFragmentManager;
import root.application.Data.MyDatabaseHelper;
import root.application.Model.Item;
import root.application.R;
import root.application.View.Dialog.CreateMindMapDialog.CreateMindMapDialog;


public class MindMapListFragment extends Fragment {
    private View view;
    private GridViewAdapter gridViewAdapter;
    private GridView gridView;
    private FloatingActionButton floatingActionButton;
    private CreateMindMapDialog dialog;
    private Context gridViewAdaptConrt;
    public MindMapListFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mind_map_list, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);

        gridViewAdaptConrt = view.getContext();

        int size = view.getWidth()<view.getHeight()?view.getWidth():view.getHeight();

        final int Gsize = size/4;
        gridView.setColumnWidth(Gsize);

        int numCol;

        if(getScreenOrientation().equals("port"))
        {
            numCol = 3;
        }
        else
        {
            numCol = 5;
        }
        gridView.setNumColumns(numCol);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item = (Item)parent.getItemAtPosition(position);
                CurrentUser.getInsctance().setMindMapId(item.getParentMindMapId());

                MyFragmentManager.getInstance().setFragment(R.id.layout,new FragmentItems(),true);
            }
        });



        gridViewAdapter = new GridViewAdapter(gridViewAdaptConrt,new ArrayList<Item>());
        gridView.setAdapter(gridViewAdapter);
        floatingActionButton= (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setClickable(true);
        floatingActionButton.setFocusable(true);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CreateMindMapDialog();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {



                    }
                });
                dialog.show(getChildFragmentManager(), null);

            }
        });

        new GetMindMaps().execute();
        return view;
    }

    public void Update()
    {
        gridView.setAdapter(null);


        new GetMindMaps().execute();
    }

    private class GetMindMaps extends AsyncTask<Void,Void,List<Item>>
    {



        @Override
        protected List<Item> doInBackground(Void... params) {
            MyDatabaseHelper dbh=new MyDatabaseHelper(getActivity());
            List<Item> itemList = dbh.getMindMapsView(CurrentUser.getInsctance().getLogin());
            dbh.close();

            return itemList;
        }

        @Override
        protected void onPostExecute(List<Item> items) {



                gridViewAdapter.setRootItems(items);
                Log.e("DB", "Обновление");
                gridView.setAdapter(gridViewAdapter);
            }
        }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

    private String getScreenOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return "port";
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return "land";
        else
            return "";
    }
}
