package root.application.View.Dialog.CreateMindMapDialog;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import root.application.Model.Item;
import root.application.R;
import root.application.View.MyCustomView.ItemView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditForegroundFragment extends Fragment {
    private View view;
    private ItemView rootItemView;
    private ColorPicker picker ;
    private SaturationBar saturationBar;
    private ValueBar valueBar;

    public EditForegroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_foreground, container, false);
        rootItemView = (ItemView) view.findViewById(R.id.itemView);

        picker=(ColorPicker)view.findViewById(R.id.picker);
        saturationBar=(SaturationBar)view.findViewById(R.id.saturationbar);
        valueBar=(ValueBar)view.findViewById(R.id.valuebar);


        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);



        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                (((CreateMindMapDialog) getParentFragment())).rootItem.setForeground(color);
                rootItemView.setForegroundColor(color);
                rootItemView.invalidate();

            }
        });

        update();

        return view;
    }

    public void update() {
        Item item = ((CreateMindMapDialog)getParentFragment()).rootItem;

        picker.setColor(item.getForeground());
        rootItemView.setText(item.getContent());
        rootItemView.setBackgroundColor(item.getBackground());
        rootItemView.setForegroundColor(item.getForeground());
        rootItemView.setBitmap(item.getImage());
        rootItemView.invalidate();


    }


}
