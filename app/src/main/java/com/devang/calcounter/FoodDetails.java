package com.devang.calcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devang.calcounter.data.DatabaseHelper;
import com.devang.calcounter.model.Food;

import java.io.Serializable;

public class FoodDetails extends AppCompatActivity {

    TextView foodName,foodCalories,foodTakenDate;
    int foodid;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        foodName=findViewById(R.id.detsFoodName);
        foodCalories=findViewById(R.id.detsCaloriesValue);
        foodTakenDate=findViewById(R.id.detsDateText);
        shareButton=findViewById(R.id.shareButton);

        final Food food= (Food) getIntent().getSerializableExtra("userObj");

        foodName.setText(food.getFoodName());
        foodCalories.setText(String.valueOf(food.getCalories()));
        foodTakenDate.setText("Consumed on "+food.getRecordDate());

        foodid=food.getFoodId();

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                StringBuilder dataString=new StringBuilder();

                String name=food.getFoodName();
                String cal=String.valueOf(food.getCalories());
                String date=food.getRecordDate();

                dataString.append(" Food: "+ name +"\n");
                dataString.append(" Calories : "+ cal +"\n");
                dataString.append(" Eaten On : "+ date +"\n");

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, " My Caloric Intake");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, (Serializable) dataString);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_item_delete,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.deleteMenu)
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Delete ?");
            alert.setMessage("Are you sure you want to delete this item ?");
            alert.setNegativeButton("No",null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseHelper dba=new DatabaseHelper(getApplicationContext());
                    dba.deleteFood(foodid);

                    Toast.makeText(FoodDetails.this, "Food item deleted", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(FoodDetails.this,DisplayActivity.class));

                    //remove activity from stack
                    FoodDetails.this.finish();
                }

            });
            alert.show();
        }
        return false;
    }
}