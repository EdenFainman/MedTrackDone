package com.edentomer.med_track_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductSearchDeleteAdapter extends ArrayAdapter<Med> {
    private ArrayList<Med> items;
    private ArrayList<Med> itemsAll;
    private ArrayList<Med> suggestions;
    private int viewResourceId;
    TextView resultdeleteview;

    @SuppressWarnings("unchecked")
    public ProductSearchDeleteAdapter(Context context, int viewResourceId, ArrayList<Med> items,TextView resultdeleteview) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<Med>) items.clone();
        this.suggestions = new ArrayList<Med>();
        this.viewResourceId = viewResourceId;
        this.resultdeleteview = resultdeleteview;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Med product = items.get(position);
        if (product != null) {
            TextView productLabel = (TextView)  v.findViewById(android.R.id.text1);
            if (productLabel != null) {
                productLabel.setText(product.getItemname());
                resultdeleteview.setText(product.getItembarcode());
                //Toast.makeText(getContext(), "asdasdasda", Toast.LENGTH_SHORT).show();
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            String str = ((Med) (resultValue)).getItemname();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Med product : itemsAll) {
                    if (product.getItemname().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<Med> filteredList = (ArrayList<Med>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Med c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}