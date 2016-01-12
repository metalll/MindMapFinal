package root.application.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MindMap {


    private String              mindMapId;
    private Map<String,Item>    items = null;
    private String              userLogin =null;
    private String              group;

    public MindMap(String userLogin,Item item){
        this.userLogin=userLogin;
        this.items=new HashMap<>();
        items.put(item.getItemId(),item);
        mindMapId="mindMap:"+new Date().getTime();

    }

    public MindMap(String userLogin,Item item,String mindMapId)
    {
        this(userLogin,item);
        this.mindMapId=mindMapId;


    }


    public void addItem(Item item) {
        items.put(item.getItemId(),item);
    }

    public void remove(String id) {
        items.remove(id);
    }

    public void getItem(String id) {
        items.get(id);
    }

    public String getMindMapId() {
        return mindMapId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<String,Item> getItems()
    {
        return items;
    }
}
