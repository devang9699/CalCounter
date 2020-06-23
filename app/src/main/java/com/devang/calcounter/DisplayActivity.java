package com.devang.calcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devang.calcounter.data.CustomListViewAdapter;
import com.devang.calcounter.data.DatabaseHelper;
import com.devang.calcounter.model.Food;
import com.devang.calcounter.utils.Utils;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    DatabaseHelper dba;
    ArrayList<Food> mFoods=new ArrayList<>();
    CustomListViewAdapter foodAdapter;
    ListView mListView;

    private Food mFood;
    private TextView totalCals,totalFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        mListView=findViewById(R.id.foodListView);
        totalCals=findViewById(R.id.totalCalories);
        totalFood=findViewById(R.id.foodItems);

        refreshData();
    }

    private void refreshData() {
        mFoods.clear();

        dba=new DatabaseHelper(this);

        ArrayList<Food> foodsFromDb=dba.getAllFoods();

        int cal=dba.totalCaloriesConsumed();
        int totalItems=dba.getTotalItems();

        String cals= Utils.numberFormat(cal);
        String totalItem=Utils.numberFormat(totalItems);

        totalCals.setText("Total Calories: "+cals);
        totalFood.setText("Total Foods: "+totalItem);

        for(int i =0 ;i<foodsFromDb.size();i++)
        {
            String name=foodsFromDb.get(i).getFoodName();
            String dateText=foodsFromDb.get(i).getRecordDate();
            int calss=foodsFromDb.get(i).getCalories();
            int foodsId=foodsFromDb.get(i).getFoodId();

            Log.v("Food IDs",String.valueOf(foodsId));

            Food myFood=new Food();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(calss);
            myFood.setFoodId(foodsId);


            mFoods.add(myFood);


        }
        dba.close();
        foodAdapter=new CustomListViewAdapter(this,R.layout.list_row_layout,mFoods);


        mListView.setAdapter(foodAdapter);







    }

}