package root.application.View.Fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
import root.application.Model.User;
import root.application.R;

import static android.view.View.*;


public class RegistrationFragment extends Fragment {
    private View view;
    private Context mContext;
    private EditText name;
    private EditText surname;
    private EditText login;
    private EditText password;
    private EditText validatePassword;
    private Button registration;
    private TextView linkAuth;
    private ProgressDialog dialog;

    public RegistrationFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        name = (EditText) view.findViewById(R.id.input_name);
        surname = (EditText) view.findViewById(R.id.input_surname);
        login = (EditText) view.findViewById(R.id.input_login_reg);
        password = (EditText) view.findViewById(R.id.input_password);
        validatePassword = (EditText) view.findViewById(R.id.retry_input_password);
        registration = (Button) view.findViewById(R.id.btn_signup);
        linkAuth = (TextView) view.findViewById(R.id.link_login);


        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()<=3)
                {
                    surname.requestFocus();
                    surname.setError("фамилия не должена быть не менее 4 символов");

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0) {
                    surname.requestFocus();
                    surname.setError("введите логин");
                }
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)==' ')
                    {
                        surname.setError("пробелы запрещены");
                        String corInput = s.toString().replace(" ","");
                        surname.setText(corInput);
                    }

                }
            }



            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0){
                    surname.requestFocus();
                    surname.setError("Введите фамилию");

                    if(s.length()<=3)
                    {
                        surname.requestFocus();
                        surname.setError("логин должен быть не менее 4 символов");

                    }
                }

            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()<=3)
                {
                    name.requestFocus();
                    name.setError("имя не должено быть не менее 4 символов");

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)==' ')
                    {
                        name.setError("пробелы запрещены");
                        String corInput = s.toString().replace(" ","");
                        name.setText(corInput);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() <= 3) {
                    login.requestFocus();
                    login.setError("логин должен быть не менее 4 символов");

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)==' ')
                    {
                        login.setError("пробелы запрещены");
                        String corInput = s.toString().replace(" ","");
                        login.setText(corInput);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()<=4)
                {
                    password.requestFocus();
                    password.setError("пароль должен быть не менее 5 символов");

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)==' ')
                    {
                        password.setError("пробелы запрещены");
                        String corInput = s.toString().replace(" ","");
                        password.setText(corInput);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        validatePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!s.equals(password.getText()))
                {
                    password.requestFocus();
                    password.setError("пароли должены совпадать");
                    validatePassword.requestFocus();
                    validatePassword.setError("пароли должены совпадать");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(int i=0;i<s.length();i++)
                {
                    if(s.charAt(i)==' ')
                    {
                        validatePassword.setError("пробелы запрещены");
                        String corInput = s.toString().replace(" ","");
                        validatePassword.setText(corInput);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                registration.setEnabled(false);
                if (preRegistr()) {
                    registration();
                } else {

                    Toast.makeText(getActivity(), "Проверьте введеные данные", Toast.LENGTH_SHORT).show();

                    registration.setEnabled(true);

                }


            }
        });

        linkAuth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFragmentManager.getInstance().getFragmentManager().popBackStack();
            }
        });

        return view;

    }


    private boolean preRegistr(){
        final Toast a=Toast.makeText(getActivity(),getText(R.string.error_input_text),Toast.LENGTH_SHORT);


        boolean isValid=true;

        String strName = name.getText().toString();
        String strSurname = surname.getText().toString();
        String strLogin = login.getText().toString();
        String strPass = password.getText().toString();
        String strPreValPass = validatePassword.getText().toString();

        if(strName.isEmpty()||strName.length()<3||strName.length()>24) {
           if(strName.isEmpty()) {
             name.setError("введите имя");
           }
           else {
               name.setError("имя должно быть не менее 3 и не более 24 символов");
           }
            isValid=false;
        }

        if(strSurname.isEmpty()||strSurname.length()<3||strSurname.length()>24) {
            if(strName.isEmpty()) {
                surname.setError("введите фамилию");
            }
            else {
                surname.setError("фамилия должна быть не менее 3 и не более 24 символов");
            }
            isValid=false;
        }

        if(strLogin.isEmpty()||strLogin.length()<4||strLogin.length()>20) {
            if(strName.isEmpty()) {
                login.setError("введите логин");
            }
            else {
                login.setError("логин должен быть не менее 4 и не более 20 символов");
            }
            isValid=false;
        }

        if(strPass.isEmpty()||strPass.length()<5||strPass.length()>20) {
            if(strName.isEmpty()) {
                password.setError("введите пароль");
            }
            else {
                password.setError("пароль должен быть не менее 5 и не более 20 символов");
            }
            isValid=false;
        }

        if(!strPass.equals(strPreValPass))
        {
            password.setError("пароль должен совпадать с предыдущим");
            isValid=false;
        }

        if(isValid)
        {
            a.cancel();
        }
        else
        {
            a.show();
        }

        return isValid;
    }

    public void registration()
    {
        User user = new User(login.getText().toString(),password.getText().toString(),name.getText().toString(),surname.getText().toString());
        user.setAvatar(BitmapFactory.decodeResource(getResources(), R.drawable.default_user));

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        new AddToDatabase().execute(user);


    }

    private class AddToDatabase extends AsyncTask<User,Void,Boolean>
    {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity(),R.style.AppTheme_Dialog);
            dialog.setIndeterminate(true);
            dialog.setMessage("Регистрация...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(User... params) {


            MyDatabaseHelper dbh = new MyDatabaseHelper(getActivity().getBaseContext());


            if(dbh.getReadableDatabase()!=null){
                List<String[]> users=dbh.getAllUsers();
            for (int i=0;i<users.size();i++) {
                if(users.get(i)[0].equals(params[0].getLogin()))
                    return false;
            }
            }

            dbh.addUser(params[0]);
            dbh.close();
            CurrentUser.getInsctance().setLogin(params[0].getLogin());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {

            if(bool) {
                while (MyFragmentManager.getInstance().getFragmentManager().getBackStackEntryCount() > 0) {
                    MyFragmentManager.getInstance().getFragmentManager().popBackStackImmediate();
                }
                dialog.dismiss();
                MyFragmentManager.getInstance().setFragment(R.id.layout, new SetAvatarFragment(), true);
            }
            else
            {
                dialog.dismiss();
                Toast.makeText(getActivity(),"логин занят, придумайте другой",Toast.LENGTH_LONG).show();
                registration.setEnabled(true);
            }
        }
    }


}
