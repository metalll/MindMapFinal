package root.application.Model;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 12.12.15.
 */
public class User {
    private String login;
    private String password;
    private String name;
    private String surname;
    private Bitmap avatar;

    private Map<String,Group> groups;
    private Map<String,MindMap> mindMaps;

    public User(String login,String password,String name,String surname)
    {
        this.login=login;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.groups=new HashMap<>();
        this.mindMaps=new HashMap<>();
    }


    public Map<String, MindMap> getMindMaps() {
        return mindMaps;
    }

    public void setMindMaps(Map<String, MindMap> mindMaps) {
        this.mindMaps = mindMaps;
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, Group> groups) {
        this.groups = groups;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}