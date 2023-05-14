package com.edentomer.med_track_final;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class MedSearchAdapter extends RecyclerView.Adapter<MedSearchAdapter.ViewHolder> implements Filterable {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    List<Med> stationList2;
    List<Med> stationList2Full;
    Context context;


    // data is passed into the constructor
    public MedSearchAdapter(Context context, List<Med> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        stationList2 = data;
        stationList2Full = new ArrayList<>(data);

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = stationList2.get(position).getItemname();



        holder.tvName.setText("Name:"+ name);
        holder.viewitembarcode.setText("Barcode: "+ stationList2.get(position).getItembarcode());
        holder.viewitemstock.setText("Stock: "+stationList2.get(position).getItemstock());


        Picasso.with(context.getApplicationContext()).load(stationList2.get(position).itemimage).into(holder.itemimage);

        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context,CheckStockActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("image",stationList2.get(position).itemimage);
                intent.putExtra("stock",stationList2.get(position).getItemstock());
                intent.putExtra("dose",stationList2.get(position).getItemdose());
                intent.putExtra("leaf",stationList2.get(position).getItemleaf());

                context.startActivity(intent);


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
            List<Med> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stationList2Full);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Med item : stationList2Full) {
                    if (item.getItembarcode().toLowerCase().contains(filterPattern)) {
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName,viewitembarcode,viewitemstock;
        RoundedImageView itemimage;

        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.viewitemname);
            itemimage = itemView.findViewById(R.id.itemimage);
            viewitembarcode = itemView.findViewById(R.id.viewitembarcode);
            viewitemstock = itemView.findViewById(R.id.viewitemstock);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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