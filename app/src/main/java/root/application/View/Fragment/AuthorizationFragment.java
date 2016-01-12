package root.application.View.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import root.application.BL.CurrentUser;
import root.application.Controller.Manager.MyFragmentManager;
import root.application.Data.MyDatabaseHelper;
import root.application.R;

public class AuthorizationFragment extends Fragment {
    private View view;
    private EditText inputLogin;
    private EditText inputPassword;
    private TextView linkSignUp;
    private Button  btnLogin;
    private ProgressDialog dialog;



    public AuthorizationFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_authorization, container, false);

        inputLogin=(EditText)view.findViewById(R.id.input_login);
        inputLogin.setFocusable(true);
        inputLogin.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()<=3)
                {
                    inputLogin.requestFocus();
                    inputLogin.setError("логин должен быть не менее 4 символов");

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0) {
                    inputLogin.requestFocus();
                    inputLogin.setError("введите логин");
                }
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)==' ')
                    {
                    inputLogin.setError("пробелы запрещены");
                    String corInput = s.toString().replace(" ","");
                    inputLogin.setText(corInput);
                    }

                }
            }



            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0){
                    inputLogin.requestFocus();
                    inputLogin.setError("Введите логин");

                    if(s.length()<=3)
                    {
                        inputLogin.requestFocus();
                        inputLogin.setError("логин должен быть не менее 4 символов");

                    }
                }

            }
        });

        inputPassword=(EditText)view.findViewById(R.id.input_password);
        inputPassword.setFocusable(true);
        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()<=4)
                {
                    inputPassword.requestFocus();
                    inputPassword.setError("пароль должен быть не менее 5 символов");

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)==' ')
                    {
                        inputPassword.setError("пробелы запрещены");
                        String corInput = s.toString().replace(" ","");
                        inputPassword.setText(corInput);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        linkSignUp=(TextView)view.findViewById(R.id.link_signup);
        btnLogin=(Button)view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });



        linkSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(getActivity()!=null) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
                MyFragmentManager.getInstance().setFragment(R.id.layout, new RegistrationFragment(), true);
            }
        });


        return view;
    }



    private void login()
    {
        String login=inputLogin.getText().toString();
        String pass=inputPassword.getText().toString();

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(getActivity()!=null) {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        new getUsers().execute(new String[]{login,pass});


    }

    private class getUsers extends AsyncTask<String,Void,Boolean>
    {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity(),R.style.AppTheme_Dialog);
            dialog.setIndeterminate(true);
            dialog.setMessage(getText(R.string.enter));
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            MyDatabaseHelper dbh = new MyDatabaseHelper(getActivity().getBaseContext());
            List<String[]>users=dbh.getAllUsers();
            int i=0;
            while (i<users.size())
            {
                if(users.get(i)[0].equals(params[0])&&users.get(i)[1].equals(params[1]))
                {
                    CurrentUser.getInsctance().init(params[0]);
                    dbh.close();
                    return true;
                }
                i++;
            }
            dbh.close();
            return false;
        }



        @Override
        protected void onPostExecute(Boolean aBoolean) {
           if(aBoolean){

                while (MyFragmentManager.getInstance().getFragmentManager().getBackStackEntryCount()>0) {
                    MyFragmentManager.getInstance().getFragmentManager().popBackStackImmediate();
                }
                dialog.dismiss();
                MyFragmentManager.getInstance().setFragment(R.id.layout,new MindMapAndGroupListFragment(),true);
           }
           else {
               dialog.dismiss();
               Toast.makeText(getActivity(),"Неверный логин или пароль",Toast.LENGTH_LONG).show();
           }
        }
    }
}
