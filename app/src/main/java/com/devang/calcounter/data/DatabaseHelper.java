package com.devang.calcounter.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.devang.calcounter.model.Food;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

 public class DatabaseHelper extends SQLiteOpenHelper {

    private final ArrayList<Food> mFoodArrayList=new ArrayList<>();


    public DatabaseHelper(Context context)
    {
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE="CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.FOOD_NAME +" TEXT, "
                + Constants.FOOD_CALORIES_NAME + " INT, " + Constants.DATE_TIME + " LONG);";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }
    //get total item saved
    public int getTotalItems()
    {
        int totalItems=0;
        String query="SELECT * FROM "+Constants.TABLE_NAME;
        SQLiteDatabase dba=this.getReadableDatabase();
        Cursor cursor=dba.rawQuery(query,null);
        totalItems=cursor.getCount();

        return  totalItems;
    }

    //get total calories consumed
    public int totalCaloriesConsumed()
    {
        int cal=0;
        SQLiteDatabase dba=this.getReadableDatabase();
        String query="SELECT SUM(" + Constants.FOOD_CALORIES_NAME + " ) " + " FROM "+ Constants.TABLE_NAME;
        Cursor cursor=dba.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            cal=cursor.getInt(0);
        }
        cursor.close();
        dba.close();

      return cal;
    }
    //delete item
    public void deleteFood(int id)
    {
        SQLiteDatabase dba=this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME,Constants.KEY_ID + "= ?",
                new String[]{ String.valueOf(id) });
    }
    //Add item
    public void addFood(Food food)
    {

        SQLiteDatabase dba =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Constants.FOOD_NAME ,food.getFoodName());
        contentValues.put(Constants.FOOD_CALORIES_NAME ,food.getCalories());
        contentValues.put(Constants.DATE_TIME ,System.currentTimeMillis());

        dba.insert(Constants.TABLE_NAME,null,contentValues);
       Log.d("FoodApp","Data Added success");
        dba.close();
    }
    //get All Foods
    public ArrayList<Food> getAllFoods()
    {
        SQLiteDatabase dba=this.getReadableDatabase();
        Cursor cursor=dba.query(Constants.TABLE_NAME,new String[]
                {
                    Constants.KEY_ID,
                    Constants.FOOD_NAME, Constants.FOOD_CALORIES_NAME,
                        Constants.DATE_TIME
                },null,null,null,null,Constants.DATE_TIME +" DESC ");

        //loop through
        if(cursor.moveToFirst())
        {
            do{
                Food food=new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NAME)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat=DateFormat.getDateInstance();
                String date=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_TIME))));
                food.setRecordDate(date);

                mFoodArrayList.add(food);

            }
            while(cursor.moveToNext());

        }

        cursor.close();
        dba.close();
        return mFoodArrayList;
    }
}
