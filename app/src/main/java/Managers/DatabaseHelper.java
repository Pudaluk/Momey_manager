package Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ModelsForDB.Category;
import ModelsForDB.Record;


/**
 * Created by Lukas on 19.11.2017.
 *
 * Modified by Lukas on 02.12.2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";

    //version of DB
    //1 - bad table Record
    private static final int DATABASE_VERSION = 7;

    // name of BD
    private static final String DATABSE_NAME = "MoneyManagerDB";

    //Table names
    private static final String TABLE_RECORD = "records";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_RECORD_CATEGORY = "record_category";

    //common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    //Record table - columns names
    private static final String KEY_TYPE = "type";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_ACTUAL_BALANCE = "actual_balance";

    //Category table - columns names
    private static final String KEY_CATEGORY_NAME = "category_name";

    //records-category table  - columns name
    private static final String KEY_RECORD_ID = "record_id";
    private static final String KEY_CATEGORY_ID = "category_id";

    // Table Create Statements
    private static final String CREATE_TABLE_RECORD = "CREATE TABLE "
            + TABLE_RECORD + "( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TYPE + " TEXT,"
            + KEY_CREATED_AT + " DATETIME,"
            + KEY_AMOUNT + " INTEGER,"
            + KEY_ACTUAL_BALANCE + " INTEGER,"
            + KEY_CATEGORY_NAME + " TEXT"+ ")";

    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "
            + TABLE_CATEGORY + "( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_CATEGORY_NAME + " TEXT" + ")";

    private static final String CREATE_TABLE_RECORD_CATEGORY = "CREATE TABLE "
            + TABLE_RECORD_CATEGORY + "( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_RECORD_ID + " INTEGER,"
            + KEY_CATEGORY_ID + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_RECORD);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_RECORD_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD_CATEGORY);

        // create new tables
        onCreate(db);
    }

    /*
     * Creating a record
    */
    public long createRecord(Record record, long category_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, record.getType());
        values.put(KEY_CREATED_AT, record.getCreate_at());
        values.put(KEY_AMOUNT, record.getAmount());
        values.put(KEY_ACTUAL_BALANCE, record.getActual_balance());
        values.put(KEY_CATEGORY_NAME, record.getCategory());

        //insert row
        long record_id = db.insert(TABLE_RECORD, null, values);

        // assigning category to record
        createRecordCategoty(record_id, category_id);

        return record_id;
    }

    /*
     * get single record
     */
    public Record getRecord(long record_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_RECORD + " WHERE " + KEY_ID + " = " + record_id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Record rc = new Record();
        rc.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        rc.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
        rc.setCreate_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
        rc.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
        rc.setActual_balance(c.getInt(c.getColumnIndex(KEY_ACTUAL_BALANCE)));
        rc.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY_NAME)));

        c.close();

        return rc;
    }

    /*
     * getting all records
     */
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECORD;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping all recors and add to list
        if (c.moveToFirst()){
            do {
                Record rc = new Record();
                rc.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                rc.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                rc.setCreate_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                rc.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
                rc.setActual_balance(c.getInt(c.getColumnIndex(KEY_ACTUAL_BALANCE)));
                rc.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY_NAME)));

                //adding to list
                records.add(rc);
            } while (c.moveToNext());
        }
        return records;
    }

   /*
    * getting all records under single category
    */
   public List<Record> getAllRecordsByCategory(String category_name) {
       List<Record> records = new ArrayList<Record>();

       String selectQuery = "SELECT * FROM " + TABLE_RECORD + " td, "
               + TABLE_CATEGORY + " tg, " + TABLE_RECORD_CATEGORY + " tt WHERE tg. "
               +KEY_CATEGORY_NAME + " = '" + category_name + "'" + " AND tg." + KEY_ID
               + " = " + " tt." + KEY_CATEGORY_ID + " AND td." + KEY_ID + " = "
               + "tt." + KEY_RECORD_ID;

       Log.e(LOG, selectQuery);

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(selectQuery, null);

       //looping all recors and add to list
       if (c.moveToFirst()){
           do {
               Record rc = new Record();
               rc.setId(c.getInt(c.getColumnIndex(KEY_ID)));
               rc.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
               rc.setCreate_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
               rc.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
               rc.setActual_balance(c.getInt(c.getColumnIndex(KEY_ACTUAL_BALANCE)));

               //adding to list
               records.add(rc);
           } while (c.moveToNext());
       }
       return records;
   }

   /*
    * Updating Recors
    */
   public int updateRecord(Record record) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_TYPE, record.getType());
       values.put(KEY_CREATED_AT, record.getCreate_at());
       values.put(KEY_AMOUNT, record.getAmount());
       values.put(KEY_ACTUAL_BALANCE, record.getActual_balance());

       //updating row
       return db.update(TABLE_RECORD, values, KEY_ID + " = ?",
               new String[] {String.valueOf(record.getId())});
   }

   /*
    * deleting Record
    */

   public  void deleteRecord(long record_id){
       SQLiteDatabase db = this.getWritableDatabase();
       db.delete(TABLE_RECORD, KEY_ID + " = ?", new String[]{String.valueOf(record_id)});
   }

   /*
    * creating category
    */

   public long createCategory(Category cate) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_CATEGORY_NAME, cate.getCategory_name());

       long category_id = db.insert(TABLE_CATEGORY, null, values);

       return category_id;
   }

   /*
    * getting all categories
    */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT *  FROM " + TABLE_CATEGORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
           do {
               Category ca = new Category();
               ca.setId(c.getInt(c.getColumnIndex(KEY_ID)));
               ca.setCategory_name(c.getString(c.getColumnIndex(KEY_CATEGORY_NAME)));

               //add categories to list
               categories.add(ca);
           } while (c.moveToNext());
        }
        return categories;
    }

    /*
     * Update Category
     */
    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, category.getCategory_name());

        //update row
        return  db.update(TABLE_CATEGORY, values, KEY_ID + " =?",
                new String[] {String.valueOf(category.getId())});
    }

    /*
     * Deleting a  category
     */
    public void deleteCategory(Category category, boolean delete_all_cotegory_records) {
        SQLiteDatabase db = this.getWritableDatabase();

        //before deleting check if record should bz also deleted
        if (delete_all_cotegory_records) {
            List<Record> allCategoryRecords = getAllRecordsByCategory(category.getCategory_name());

            //detele all records
            for (Record record :allCategoryRecords) {
                deleteRecord(record.getId());
            }
        }

        //now delete category
        db.delete(TABLE_CATEGORY, KEY_ID + " =?",
                new String[] {String.valueOf(category.getId())});
    }

    /*
     * Creating record_category
     */
    public long createRecordCategoty(long record_id, long category_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECORD_ID, record_id);
        values.put(KEY_CATEGORY_ID, category_id);

        long id = db.insert(TABLE_RECORD_CATEGORY, null, values);

        return id;
    }

    /*
     * Updating a record category
     */
    public int updateRecordCategory(long id, long record_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECORD_ID, record_id);

        // updating row
        return db.update(TABLE_RECORD, values, KEY_ID + " =?",
                new String[]{String.valueOf(id)});
    }

    /*
     * deleting a record category
     */
    public int deleteRecordCategory( long id ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RECORD, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    //close DB
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    //get datetime
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return  dateFormat.format(date);
    }
}
