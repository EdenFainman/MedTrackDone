package com.edentomer.med_track_final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> implements Filterable {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    List<Request> stationList2;
    List<Request> stationList2Full;
    Context context;

    Calendar c;
    String todaysDate;
    String currentTime;
    String noteCategory;
    // data is passed into the constructor
    public AppointmentAdapter(Context context, List<Request> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        stationList2 = data;
        stationList2Full = new ArrayList<>(data);

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.appointments, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = stationList2.get(position).getStatus();

        SharedPreferences prfs = context.getSharedPreferences("ROLE", Context.MODE_PRIVATE);
        String role = prfs.getString("role", "user");

        String user_id ="";
        if(role.equals("user")){
            user_id = stationList2.get(position).getUserIdTherapist();
        }else if(role.equals("doctor")){
            user_id = stationList2.get(position).getUserIdClient();
        }

        holder.tvStatus.setText("Status :"+stationList2.get(position).getStatus());
        FirebaseDatabase.getInstance().getReference().child("users").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String userName = dataSnapshot.child("name").getValue().toString();
                String userThumbImage = dataSnapshot.child("image").getValue().toString();
                String userStatus =dataSnapshot.child("status").getValue().toString();

                holder.displayName.setText(userName);
                holder.displayStatus.setText(stationList2.get(position).getTime());
                Picasso.with(holder.displayImage.getContext()).load(userThumbImage).placeholder(R.drawable.user_img).into(holder.displayImage);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.tvStatus.setText(name);
        holder.displayName.setText(name);

        if(role.equals("user")){
            holder.cancel.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.GONE);

        }else if(role.equals("doctor")){

            holder.cancel.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.VISIBLE);

        }
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stationList2.get(position).setStatus("Cancelled");
                holder.tvStatus.setText("Cancelled");

                DatabaseReference DD = FirebaseDatabase.getInstance().getReference().child("appointments").child(stationList2.get(position).getRequest_type());
                DD.child("status").setValue("Cancelled");
                Toast.makeText(context.getApplicationContext(), "Cancelled",Toast.LENGTH_SHORT).show();
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stationList2.get(position).setStatus("Accepted");
                holder.tvStatus.setText("Accepted");

                DatabaseReference DD = FirebaseDatabase.getInstance().getReference().child("appointments").child(stationList2.get(position).getRequest_type());
                DD.child("status").setValue("Accepted");
                Toast.makeText(context.getApplicationContext(), "Accepted",Toast.LENGTH_SHORT).show();


            }
        });





    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return stationList2.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Request> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stationList2Full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Request item : stationList2Full) {
                    if (item.getTime().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stationList2.clear();
            stationList2.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView displayName;
        public TextView displayStatus,tvStatus,cancel,accept;
        public RoundedImageView displayImage;
        public ImageView imageView;

        RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);


            rl = itemView.findViewById(R.id.rl);
            tvStatus = (TextView)itemView.findViewById(R.id.tvStatus);
            cancel = (TextView)itemView.findViewById(R.id.cancel);
            accept = (TextView)itemView.findViewById(R.id.accept);

            displayName = (TextView)itemView.findViewById(R.id.textViewSingleListName);
            displayStatus = (TextView) itemView.findViewById(R.id.textViewSingleListStatus);
            displayImage = (RoundedImageView)itemView.findViewById(R.id.circleImageViewUserImage);
            imageView = (ImageView)itemView.findViewById(R.id.userSingleOnlineIcon);
            imageView.setVisibility(View.INVISIBLE);

        }
    }



    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }




}