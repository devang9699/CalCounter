package com.devang.calcounter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devang.calcounter.data.DatabaseHelper;

import com.devang.calcounter.model.Food;

public class MainActivity extends AppCompatActivity {

    EditText foodName,foodCalories;
    Button submit;
    private DatabaseHelper dba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba=new DatabaseHelper(this);
        foodName=findViewById(R.id.foodEditText);
        foodCalories=findViewById(R.id.calEditText);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToDb();
            }
        });
    }

    private void saveDataToDb() {
        Food food=new Food();

        String foodname=foodName.getText().toString().trim();
        String cals=foodCalories.getText().toString().trim();
        int cal=Integer.parseInt(cals);

        if (foodname.equals("") || cals.equals("") )
        {
            Toast.makeText(this, "No empty fields allowed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            food.setFoodName(foodname);
            food.setCalories(cal);

         dba.addFood(food);
         dba.close();

         // clear inputs
            foodName.setText("");
            foodCalories.setText("");

           startActivity(new Intent(MainActivity.this,DisplayActivity.class));

        }


    }
}