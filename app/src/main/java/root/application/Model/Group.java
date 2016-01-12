package root.application.Model;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 26.12.15.
 */
public class Group {
    private static int id = 0;

    private String name;
    private String groupId;

    private Map<String,MindMap>mindMaps;

    private int backround;
    private int foreground;
    private Bitmap image;

    public Group(String name,int backround,int foreground) {

        this.name=name;
        this.backround=backround;
        this.foreground=foreground;

        groupId="group:"+id;

        mindMaps=new HashMap<>();

    }


    public Map<String, MindMap> getMindMaps() {
        return mindMaps;
    }

    public void setMindMaps(Map<String, MindMap> mindMaps) {
        this.mindMaps = mindMaps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getBackround() {
        return backround;
    }

    public void setBackround(int backround) {
        this.backround = backround;
    }

    public int getForeground() {
        return foreground;
    }

    public void setForeground(int foreground) {
        this.foreground = foreground;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
