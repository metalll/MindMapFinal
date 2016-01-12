package root.application.View.Dialog.CreateMindMapDialog;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import root.application.Model.Item;
import root.application.R;
import root.application.View.MyCustomView.ItemView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTextFragment extends Fragment {
    private View view;
    private ItemView rootItemView;
    private EditText editText;

    public EditTextFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_edit_text, container, false);
       rootItemView = (ItemView)view.findViewById(R.id.itemView);
       editText = (EditText) view.findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() <= 12) {
                    rootItemView.setText(editText.getText().toString());

                    Item item = ((CreateMindMapDialog) getParentFragment()).rootItem;

                    item.setContent(rootItemView.getText());
                } else
                {

                    editText.setError("название идеи не должно превышать 12 символов");
                    editText.setText(s.subSequence(0, 12));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        update();
       return view;
    }

    public void update()
    {
        Item item = ((CreateMindMapDialog)getParentFragment()).rootItem;

        rootItemView.setText(item.getContent());
        rootItemView.setBackgroundColor(item.getBackground());
        rootItemView.setForegroundColor(item.getForeground());
        rootItemView.setBitmap(item.getImage());
        rootItemView.invalidate();


    }

}
