package com.example.absolute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import results.ExperimentsJournal;


public class OperationCharacteristicsAdapter extends ArrayAdapter {
    private List<ExperimentsJournal> items;

    @Override
    public int getViewTypeCount() {
        return items.size();
    }

    public OperationCharacteristicsAdapter(Context context, int resource, List<ExperimentsJournal> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ExperimentsJournal experimentsJournal = items.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        viewHolder = new ViewHolder(textView);
        convertView.setTag(viewHolder);
        convertView.setBackgroundColor(experimentsJournal.getColor());
        viewHolder.getText().setText(experimentsJournal.getNameExperiment());

        return convertView;
    }
}
