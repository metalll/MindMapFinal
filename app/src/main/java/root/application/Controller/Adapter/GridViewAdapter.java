package root.application.Controller.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import root.application.Model.Item;
import root.application.View.MyCustomView.ItemView;

/**
 * Created by root on 30.12.15.
 */
public class GridViewAdapter extends BaseAdapter {

    private List<Item> rootItems;
    private Context context;

    public GridViewAdapter(Context context,List<Item> rootItems) {
        this.context=context;
        this.rootItems = rootItems;
    }

    public void setRootItems(List<Item> rootItems)
    {
        this.rootItems=rootItems;
    }

    @Override
    public int getCount() {
       return rootItems.size();
    }

    @Override
    public Item getItem(int position) {
        return rootItems.get(position);// (MindMap)CurrentUser.getInsctance().getUser().getMindMaps().values().toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView view ;
        if (convertView == null) {view = new ItemView(context,rootItems.get(position).getContent(),rootItems.get(position).getBackground(),rootItems.get(position).getForeground(),rootItems.get(position).getImage());}
        else { view =(ItemView) convertView;}
        view.setId(position);
        return view;
    }

}
