package com.devang.calcounter.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devang.calcounter.FoodDetails;
import com.devang.calcounter.R;
import com.devang.calcounter.model.*;

import java.util.ArrayList;
import java.util.List;


public class CustomListViewAdapter extends ArrayAdapter<Food> {

    private int layoutResource;
    private Activity mActivity;
    private List<Food> mFoodList=new ArrayList<>();

    public CustomListViewAdapter(Activity act,int layout,ArrayList<Food> foods)
    {
        super(act,layout,foods);
        mActivity=act;
        layoutResource=layout;
        mFoodList=foods;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFoodList.size();
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return mFoodList.get(position);
    }

    @Override
    public int getPosition(@Nullable Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view=convertView;
       final ViewHolder holder;


       if(view == null || (view.getTag() == null ) )
       {


           LayoutInflater inflater=LayoutInflater.from(mActivity);
           view=inflater.inflate(layoutResource,null);

           holder=new ViewHolder();

           holder.foodName=(TextView)view.findViewById(R.id.foodName);
           holder.foodDate=(TextView)view.findViewById(R.id.date);
           holder.foodCalories=(TextView)view.findViewById(R.id.calNumber);

           view.setTag(holder);
       }
       else
       {
           holder= (ViewHolder) view.getTag();
       }

       holder.food=getItem(position);

       holder.foodName.setText(holder.food.getFoodName());
       holder.foodDate.setText(holder.food.getRecordDate());
       holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent i=new Intent(mActivity, FoodDetails.class);
               Bundle bundle=new Bundle();
               bundle.putSerializable("userObj",holder.food);
               i.putExtras(bundle);
               mActivity.startActivity(i);
           }
       });



       return view;
    }

    public class ViewHolder
    {
        Food food;
        TextView foodName;
        TextView foodCalories;
        TextView foodDate;
    }
}
