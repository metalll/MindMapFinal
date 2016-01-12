package root.application.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import root.application.Model.Item;
import root.application.R;
import root.application.View.MyCustomView.FullMindMapView;
import root.application.View.MyCustomView.ItemView;
import root.application.View.MyCustomView.ZoomableLayout;





public class FullMindMapFragment extends Fragment {
    private View view;
    private List<Item> items;


    public FullMindMapFragment() {

    }




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_full_mind_map, container, false);

        FullMindMapView view1 = (FullMindMapView)view.findViewById(R.id.fullMindMapView);
        view1.setItems(items);




        for (int i=0;i<items.size();i++)
        {
            Item item = items.get(i);
            ItemView itemView = new ItemView(view.getContext(),item.getContent(),item.getBackground(),item.getForeground(),item.getImage());
            itemView.setId(item.getItemId());

            view1.addView(itemView,i);
        }

        ZoomableLayout myZoomView = new ZoomableLayout(getActivity());
        myZoomView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        myZoomView.invalidate();
        myZoomView.addView(view);
        return myZoomView;
    }


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
