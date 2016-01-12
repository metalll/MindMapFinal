package root.application.Model;

import android.graphics.Bitmap;

import java.util.Date;

public class Item {
    private static int id = 0;

    private String content;
    private String itemId;
    private String parentMindMapId;
    private String parentItemId;


    private int    background;
    private int    foreground;
    private int    angle;

    private Bitmap image;


    public Item(String content,String parentMindMapId,String parentItemId,int background,int foreground,int angle,Bitmap image){
        this.content=content;
        this.parentMindMapId=parentMindMapId;
        this.parentItemId=parentItemId;
        this.background=background;
        this.foreground=foreground;
        this.angle=angle;
        this.image=image;
        this.itemId="item:"+new Date().getTime();
        id++;

    }

    public Item(String content,String parentMindMapId,String parentItemId,int background,int foreground,int angle,Bitmap image,String itemId){
        this(content,parentMindMapId,parentItemId,background,foreground,angle,image);
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getItemId() {
        return itemId;
    }

    public String getParentMindMapId() {
        return parentMindMapId;
    }

    public void setParentMindMapId(String parentMindMapId) {
        this.parentMindMapId = parentMindMapId;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getForeground() {
        return foreground;
    }

    public void setForeground(int foreground) {
        this.foreground = foreground;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    public static class Builder {

        public Builder(){}

        private String      content;
        private String      parentMindMapId;
        private String      parentItemId;

        private int         background;
        private int         foreground;
        private int         angle;

        private Bitmap      image;

        private String itemId=null;

        public Builder text(String content)  {
            this.content=content;
            return this;
        }

        public Builder parentMindMapId(String parentMindMapId) {
            this.parentMindMapId=parentMindMapId;
            return this;
        }

        public Builder parentItemId(String parentItemId) {
            this.parentItemId=parentItemId;
            return this;
        }

        public Builder background(int color) {
            this.background=color;
            return this;
        }

        public Builder foreground(int color){
            this.foreground=color;
            return this;
        }

        public Builder angle(int angle){
            this.angle=angle;
            return this;
        }

        public Builder image(Bitmap image) {
            this.image=image;
            return this;
        }

        public Builder itemId(String itemId)
        {
            this.itemId = itemId;
            return this;
        }

        public Item create(){
            if(itemId==null) return new Item(content,parentMindMapId,parentItemId,background,foreground,angle,image);
            else return new Item(content,parentMindMapId,parentItemId,background,foreground,angle,image,itemId);
        }

    }
}
