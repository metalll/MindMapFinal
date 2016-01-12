package root.application.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import root.application.Model.Group;
import root.application.Model.Item;
import root.application.Model.MindMap;
import root.application.Model.User;


/**
 * Created by root on 12.12.15.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {



    private static final String     DATABASE_NAME = "mydatabase.db";
    private static final int        DATABASE_VERSION = 26;

    private static final String     DATABASE_TABLE_GROUP = "groups";
    private static final String     DATABASE_TABLE_MINDMAP = "mindMap";
    private static final String     DATABASE_TABLE_ITEM = "item";
    private static final String     DATABASE_TABLE_USER="user";

    private static final String     COLUMN_TABLE_ID="id";

    //item

    private static final String     COLUMN_CONTENT="content";
    private static final String     COLUMN_ITEM_ID="itemId";
    private static final String     COLUMN_PARENT_MINDMAP_ID="parentMindMapId";
    private static final String     COLUMN_PARENT_ITEM_ID="parentItemId";
    private static final String     COLUMN_BACKGROUND="background";
    private static final String     COLUMN_IMAGE="image";
    private static final String     COLUMN_FOREGROUND="foreground";
    private static final String     COLUMN_ANGLE="angle";

    //mindMap
    private static final String     COLUMN_MIND_MAP_ID="mindMapId";
    private static final String     COLUMN_USER="userLogin";
    private static final String     COLUMN_GROUP="groupId";

    //group
    private static final String     COLUMN_NAME="name";
    private static final String     COLUMN_GROUP_ID="groupId";

    //user
    private static final String     COLUMN_LOGIN="login";
    private static final String     COLUMN_PASSWORD="password";
    //колонка имени уже сущесвует
    private static final String     COLUMN_SURNAME="surname";
    private static final String     COLUMN_AVATAR="avatar";



    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


            //ITEM
            db.execSQL(
                    "CREATE TABLE " + DATABASE_TABLE_ITEM +
                            " (" + COLUMN_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_CONTENT + " TEXT, " +
                            COLUMN_ITEM_ID + " TEXT, " +
                            COLUMN_PARENT_MINDMAP_ID + " TEXT, " +
                            COLUMN_PARENT_ITEM_ID + " TEXT, " +
                            COLUMN_BACKGROUND + " INTEGER, " +
                            COLUMN_FOREGROUND + " INTEGER, " +
                            COLUMN_IMAGE + " TEXT, " +
                            COLUMN_ANGLE + " INTEGER" + ")"
            );

            //MINDMAP
            db.execSQL(
                    "CREATE TABLE " + DATABASE_TABLE_MINDMAP +
                            " (" + COLUMN_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_MIND_MAP_ID + " TEXT, " +
                            COLUMN_USER + " TEXT, " +
                            COLUMN_GROUP + " TEXT" + ")"
            );

            //GROUP
            db.execSQL(
                    "CREATE TABLE " + DATABASE_TABLE_GROUP +
                            " (" + COLUMN_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_NAME + " TEXT, " +
                            COLUMN_GROUP_ID + " TEXT, " +
                            COLUMN_BACKGROUND + " INTEGER, " +
                            COLUMN_FOREGROUND + " INTEGER, " +
                            COLUMN_IMAGE + " TEXT" + ")"
            );

            //USER
            db.execSQL(
                    "CREATE TABLE " + DATABASE_TABLE_USER +
                            " (" + COLUMN_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_LOGIN + " TEXT, " +
                            COLUMN_PASSWORD + " TEXT, " +
                            COLUMN_NAME + " TEXT, " +
                            COLUMN_SURNAME + " TEXT, " +
                            COLUMN_AVATAR + " TEXT" + ")"

            );
        }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_MINDMAP);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USER);

        onCreate(db);
    }

    public void addItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.e("DB","add Item");
        contentValues.put(COLUMN_CONTENT,item.getContent());
        contentValues.put(COLUMN_ITEM_ID,item.getItemId());
        contentValues.put(COLUMN_PARENT_MINDMAP_ID, item.getParentMindMapId());
        contentValues.put(COLUMN_PARENT_ITEM_ID, item.getParentItemId());
        contentValues.put(COLUMN_BACKGROUND, item.getBackground());
        contentValues.put(COLUMN_FOREGROUND, item.getForeground());
        contentValues.put(COLUMN_IMAGE, encodeTobase64(item.getImage()));
        contentValues.put(COLUMN_ANGLE, item.getAngle());



        database.insert(DATABASE_TABLE_ITEM, null, contentValues);
        database.close();

    }

    public void addMindMap(MindMap mindMap) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.e("DB", "add Mind Map");
        contentValues.put(COLUMN_MIND_MAP_ID, mindMap.getMindMapId());
        contentValues.put(COLUMN_USER, mindMap.getUserLogin());
        contentValues.put(COLUMN_GROUP, mindMap.getGroup());

        database.insert(DATABASE_TABLE_MINDMAP, null, contentValues);
        database.close();
    }

    public void addGroup(Group group){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME,group.getName());
        contentValues.put(COLUMN_GROUP_ID, group.getGroupId());
        contentValues.put(COLUMN_BACKGROUND,group.getBackround());
        contentValues.put(COLUMN_FOREGROUND,group.getForeground());
        contentValues.put(COLUMN_IMAGE,encodeTobase64(group.getImage()));

        database.insert(DATABASE_TABLE_GROUP, null, contentValues);
        database.close();

    }

    public void addUser(User user)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.e("DB","add User");
        contentValues.put(COLUMN_LOGIN,user.getLogin());
        contentValues.put(COLUMN_PASSWORD,user.getPassword());
        contentValues.put(COLUMN_NAME,user.getName());
        contentValues.put(COLUMN_SURNAME,user.getSurname());
        contentValues.put(COLUMN_AVATAR,encodeTobase64(user.getAvatar()));

        database.insert(DATABASE_TABLE_USER, null, contentValues);
        database.close();
    }

    public List<String[]> getAllUsers()
    {
        if(this.getReadableDatabase()==null) return null;
        SQLiteDatabase database = this.getReadableDatabase();

        Log.e("DB", "get Users");

        String selectQuery = "SELECT "+COLUMN_LOGIN +", "+COLUMN_PASSWORD+" FROM " + DATABASE_TABLE_USER;
        List<String[]>list=new ArrayList<>();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                list.add(new String[]{cursor.getString(0),cursor.getString(1)});
            }
            while (cursor.moveToNext());

        }




        return list;
    }

    public void updateUser(User user)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.e("DB","Update user " + user.getLogin());
        contentValues.put(COLUMN_LOGIN,user.getLogin());
        contentValues.put(COLUMN_PASSWORD,user.getPassword());
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_SURNAME,user.getSurname());
        contentValues.put(COLUMN_AVATAR, encodeTobase64(user.getAvatar()));

        database.update(DATABASE_TABLE_USER, contentValues, COLUMN_LOGIN + "=?",
                new String[]{user.getLogin()});
        database.close();
    }


    public User getUser(String login){
        if(this.getReadableDatabase()==null) return null;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(DATABASE_TABLE_USER,new String[]{COLUMN_TABLE_ID,COLUMN_LOGIN,COLUMN_PASSWORD,COLUMN_NAME,COLUMN_SURNAME,COLUMN_AVATAR},COLUMN_LOGIN+"=?",new String[] { login }, null, null, null, null);
        Log.e("DB", "get User " + login);


        if(cursor==null) {
            database.close();
            return null;
        }
        User user = null;
        if(cursor.moveToFirst())
            do {
                user = new User(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));

                user.setAvatar(decodeBase64(cursor.getString(5)));

            }
            while (cursor.moveToNext());

        return user;


    }


    public List<Item> getMindMapsView(String login)
    {
        if(this.getReadableDatabase()==null) {
            return null;}
        SQLiteDatabase database = this.getReadableDatabase();




        List<String> mindMaps= new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE_MINDMAP,new String[]{COLUMN_MIND_MAP_ID,COLUMN_USER,COLUMN_GROUP},COLUMN_USER + "=?",new String[]{login},null,null,null,null);

        if(cursor.moveToFirst()) {
            do { mindMaps.add(cursor.getString(0));
            Log.e("DB","search MindMap");
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        database.close();

        SQLiteDatabase database2 = this.getReadableDatabase();
        Cursor cursor2 = database2.query(DATABASE_TABLE_ITEM, new String[]{COLUMN_CONTENT, COLUMN_ITEM_ID, COLUMN_PARENT_MINDMAP_ID, COLUMN_PARENT_ITEM_ID, COLUMN_BACKGROUND, COLUMN_FOREGROUND, COLUMN_IMAGE, COLUMN_ANGLE}, COLUMN_PARENT_ITEM_ID + "=?", new String[]{"NULL"}, null, null, null, null);

        List<Item> rootItems = new ArrayList<>();

        if(cursor2.moveToFirst()) {
            do {

                Log.e("DB","search Item");

                for(int i=0;i<8;i++)
                {
                    Log.e("DB"," item :" + cursor2.getString(i));
                }

                    Item item = new Item(cursor2.getString(0),cursor2.getString(2),cursor2.getString(3),cursor2.getInt(4),cursor2.getInt(5),cursor2.getInt(7),decodeBase64(cursor2.getString(6)),cursor2.getString(1));
                    rootItems.add(item);


            }
            while (cursor2.moveToNext());
        }


        List<Item> result = new ArrayList<>();

        for(int i=0;i<mindMaps.size();i++) {
            for (int j=0;j<rootItems.size();j++) {
                if(rootItems.get(j).getParentMindMapId().equals(mindMaps.get(i))) {
                    result.add(rootItems.get(j));
                    Log.e("DB","Есть совпадение");
                }
            }
        }
        cursor2.close();
        database2.close();
        return result;
    }


    public List<Item> getItems(String mindMapId)
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor2 = db.query(DATABASE_TABLE_ITEM, new String[]{COLUMN_CONTENT, COLUMN_ITEM_ID, COLUMN_PARENT_MINDMAP_ID, COLUMN_PARENT_ITEM_ID, COLUMN_BACKGROUND, COLUMN_FOREGROUND, COLUMN_IMAGE, COLUMN_ANGLE}, COLUMN_PARENT_MINDMAP_ID + "=?", new String[]{mindMapId}, null, null, null, null);

        Log.e("DB","search Item");



        List<Item>items = new ArrayList<>();


        if(cursor2.moveToFirst()) {
            do {
                Log.e("DB", "search Item");

                for(int i=0;i<8;i++)
                {
                    Log.e("DB"," item :" + cursor2.getString(i));
                }





                Item item = new Item(cursor2.getString(0),cursor2.getString(2),cursor2.getString(3),cursor2.getInt(4),cursor2.getInt(5),cursor2.getInt(7),decodeBase64(cursor2.getString(6)),cursor2.getString(1));
                items.add(item);
            }
            while (cursor2.moveToNext());
        }
        cursor2.close();
        return items;
    }

    private String encodeTobase64(Bitmap image){
        if(image==null)return "NULL";

        Bitmap immagex=Bitmap.createBitmap(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private Bitmap decodeBase64(String input){

        if(input.equals("NULL")) return null;
        byte[] decodedByte = Base64.decode(input, 0);

        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
