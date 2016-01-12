package root.application.View.Dialog.CreateItemDialog;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

import root.application.Model.Item;
import root.application.R;
import root.application.View.Activity.MainActivity;
import root.application.View.MyCustomView.ItemView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditBitmapFragment extends Fragment {




    private View view;
    private ItemView rootItemView;
    private ImageView camera;
    private ImageView delete;
    private ImageView folder;

    public EditBitmapFragment() {
        // Required empty public constructor
    }

    private final int CAMERA_RESULT = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_bitmap, container, false);

        rootItemView = (ItemView) view.findViewById(R.id.itemView);

        camera = (ImageView) view.findViewById(R.id.camera);

        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_RESULT);
            }
        });

        delete = (ImageView) view.findViewById(R.id.delete);

        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                (((CreateItemDialog) getParentFragment())).rootItem.setImage(null);
                rootItemView.setBitmap(null);
                rootItemView.invalidate();
                update();
            }
        });



        folder = (ImageView) view.findViewById(R.id.folder);
        folder.setVisibility(View.INVISIBLE);

        folder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");
                startActivityForResult(intent, 12);
            }
        });

        update();

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_RESULT && resultCode == MainActivity.RESULT_OK) {

            if (data != null) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");



                (((CreateItemDialog) getParentFragment())).rootItem.setImage(bmp);
                rootItemView.setBitmap(bmp);
                rootItemView.invalidate();
            }
        }
            if(requestCode == 12&&resultCode == MainActivity.RESULT_OK)
            {
                Bitmap img = null;
                Uri selectedImage = data.getData();
                try {
                    img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                (((CreateItemDialog) getParentFragment())).rootItem.setImage(img);
                rootItemView.setBitmap(img);
                rootItemView.invalidate();

            }



    }

    public void update() {
        Item item = ((CreateItemDialog)getParentFragment()).rootItem;



        rootItemView.setText(item.getContent());
        rootItemView.setBackgroundColor(item.getBackground());
        rootItemView.setForegroundColor(item.getForeground());
        rootItemView.setBitmap(item.getImage());
        rootItemView.invalidate();
        rootItemView.setId(item.getItemId());


    }



}
