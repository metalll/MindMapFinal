package root.application.View.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

import root.application.BL.CurrentUser;
import root.application.Controller.Manager.MyFragmentManager;
import root.application.Data.MyDatabaseHelper;
import root.application.Model.User;
import root.application.R;
import root.application.View.Activity.MainActivity;
import root.application.View.Dialog.OpenFileDialog;



public class SetAvatarFragment extends Fragment {

    private final int CAMERA_RESULT = 0;
    private View view;
    private ImageView avatar;
    private Button next;
    private User currentUser;
    private ImageView open;
    private ImageView photo;
    private OpenFileDialog dialog;


    public SetAvatarFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new getUser().execute(CurrentUser.getInsctance().getLogin());
        view  = inflater.inflate(R.layout.fragment_set_avatar, container, false);
        avatar=(ImageView)view.findViewById(R.id.imageView);
        next = (Button) view.findViewById(R.id.button);
        open = (ImageView) view.findViewById(R.id.imageView3);
        photo = (ImageView) view.findViewById(R.id.imageView2);
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    new updateUser().execute(currentUser);
                }
            }
        });


        open.setVisibility(View.INVISIBLE);
        open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");
                startActivityForResult(intent, 12);


            }
                });



                photo.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_RESULT);


                    }
                });


                return view;
            }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT&&resultCode == MainActivity.RESULT_OK) {

            if(data!=null) {
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                avatar.setImageBitmap(thumbnailBitmap);
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
           avatar.setImageBitmap(img);

       }

    }

    private class getUser extends AsyncTask<String,Void,User>
    {

        @Override
        protected User doInBackground(String... params) {
            MyDatabaseHelper dbh = new MyDatabaseHelper(getActivity());
            User user =  dbh.getUser(params[0]);
            dbh.close();
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            currentUser=user;


        }
    }

    private class updateUser extends AsyncTask<User,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            currentUser.setAvatar(((BitmapDrawable)avatar.getDrawable()).getBitmap());
        }

        @Override
        protected Void doInBackground(User... params) {
            MyDatabaseHelper dbh = new MyDatabaseHelper(getActivity());


            dbh.updateUser(params[0]);
            dbh.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            while (MyFragmentManager.getInstance().getFragmentManager().getBackStackEntryCount() > 0) {
                MyFragmentManager.getInstance().getFragmentManager().popBackStackImmediate();
            }
            MyFragmentManager.getInstance().setFragment(R.id.layout,new MindMapAndGroupListFragment(),true);
        }
    }

}
