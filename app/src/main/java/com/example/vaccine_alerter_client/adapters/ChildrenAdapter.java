package com.example.vaccine_alerter_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.vaccine_alerter_client.R;
import com.example.vaccine_alerter_client.activities.ChildDetailsActivity;
import com.example.vaccine_alerter_client.models.ChildModel;

import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ChildModel> childrenData;
    //private View view;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView gender_view,
                name_view,
                id_view,
                icon;
        ImageView icon_profile;
        RelativeLayout child_row_layout;

        public ViewHolder(View v){
            super(v);

            gender_view = (TextView) v.findViewById(R.id.list_gender);
            id_view = (TextView) v.findViewById(R.id.list_id);
            name_view = (TextView) v.findViewById(R.id.list_name);
            icon  = (TextView) v.findViewById(R.id.icon_text);
            icon_profile = (ImageView) v.findViewById(R.id.icon_profile);
            child_row_layout = (RelativeLayout) v.findViewById(R.id.children_list_layout);



        }


    }
    public ChildrenAdapter(ArrayList<ChildModel> data){

        childrenData = new ArrayList<>();
        childrenData= data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_list_row,parent,false);
        context =parent.getContext();
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final ChildModel childrenModelHolder = childrenData.get(position);
        String firstName = childrenModelHolder.getFirstName();
        String lastName = childrenModelHolder.getLastName();
        String combinedName  =  firstName + " "+ lastName;
        String initials = firstName.substring(0, 1).toUpperCase() + lastName.substring(0, 1).toUpperCase();
        final String id  = String.valueOf(childrenModelHolder.getId());
        String displayId = "#"+id;

        holder.gender_view.setText(childrenModelHolder.getGender());
        holder.name_view.setText(combinedName);
        holder.icon.setText(initials);
        holder.id_view.setText(displayId );
        if (childrenModelHolder.getVaccineDue()) {

            holder.icon_profile.setBackgroundResource(R.drawable.bg_red_circle);
        } else {

            holder.icon_profile.setBackgroundResource(R.drawable.bg_blue_circle);
        }


        holder.child_row_layout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChildDetailsActivity.class);
                intent.putExtra("childId", id);

                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return childrenData.size();
    }
}
