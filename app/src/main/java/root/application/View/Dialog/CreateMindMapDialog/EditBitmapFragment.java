package root.application.View.Dialog.CreateMindMapDialog;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

import root.application.Model.Item;
import root.application.R;
import root.application.View.Activity.MainActivity;
import root.application.View.Dialog.OpenFileDialog;
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
    private OpenFileDialog dialog;
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
                (((CreateMindMapDialog) getParentFragment())).rootItem.setImage(null);
                rootItemView.setBitmap(null);
                rootItemView.invalidate();
                update();
            }
        });

        folder = (ImageView) view.findViewById(R.id.folder);
        dialog = new OpenFileDialog(getActivity());
        dialog.setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
            @Override
            public void OnSelectedFile(String fileName) {
                Bitmap image = BitmapFactory.decodeFile(fileName);

                (((CreateMindMapDialog) getParentFragment())).rootItem.setImage(image);

                rootItemView.setBitmap(rootItemView.GetCroppedBitmap(image));

                rootItemView.invalidate();
                update();

            }
        });
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
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                (((CreateMindMapDialog) getParentFragment())).rootItem.setImage(thumbnailBitmap);
                rootItemView.setBitmap(thumbnailBitmap);

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






                (((CreateMindMapDialog) getParentFragment())).rootItem.setImage(img);
                rootItemView.setBitmap(img);


            }



    }

    public void update() {
        Item item = ((CreateMindMapDialog)getParentFragment()).rootItem;

        rootItemView.setText(item.getContent());
        rootItemView.setBackgroundColor(item.getBackground());
        rootItemView.setForegroundColor(item.getForeground());
        rootItemView.setBitmap(item.getImage());
        rootItemView.invalidate();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);


    }



}
