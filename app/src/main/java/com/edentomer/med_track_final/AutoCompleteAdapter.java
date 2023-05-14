package com.edentomer.med_track_final;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<Med> implements Filterable {

    private ArrayList<Med> fullList;
    private ArrayList<Med> mOriginalValues;
    private ArrayFilter mFilter;

    public AutoCompleteAdapter(Context context, int resource, int textViewResourceId, List<Med> objects) {

        super(context, resource, textViewResourceId, objects);
        fullList = (ArrayList<Med>) objects;
        mOriginalValues = new ArrayList<Med>(fullList);

    }

    @Override
    public int getCount() {
        return fullList.size();
    }


    @Nullable
    @Override
    public Med getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }


    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (lock) {
                    mOriginalValues = new ArrayList<Med>(fullList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    ArrayList<Med> list = new ArrayList<Med>(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<Med> values = mOriginalValues;
                int count = values.size();

                ArrayList<Med> newValues = new ArrayList<Med>(count);

                for (int i = 0; i < count; i++) {

                    String item = values.get(i).getItemname();

                    Log.e("pppp", "performFiltering: "+ item);

                    if (item.toLowerCase().contains(prefixString)) {

                        Log.e("dddd", "performFiltering: "+ item);

                        Med item1 = values.get(i);

                        newValues.add(item1);
                    }

                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results.values!=null){
                fullList = (ArrayList<Med>) results.values;
            }else{
                fullList = new ArrayList<Med>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
