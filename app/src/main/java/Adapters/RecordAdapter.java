package Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.luk.puda.momey_manager.R;

import java.util.ArrayList;
import java.util.List;

import ModelsForDB.Record;

/**
 * Created by Lukas on 08.12.2017.
 */

public class RecordAdapter extends ArrayAdapter {
    private Context context;
    private List<Record> record;

    public RecordAdapter(Context context, int textViewResourceId, List objects) {
        super(context,textViewResourceId, objects);

        this.context= context;
        record=objects;

    }

    private class ViewHolder
    {
        TextView type;
        TextView date;
        TextView amount;
        TextView category;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.record_detail, null);

            holder = new ViewHolder();
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.amount=(TextView)convertView.findViewById(R.id.amount);
            holder.category=(TextView)convertView.findViewById(R.id.category);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Record individualRecord = record.get(position);
        holder.type.setText(individualRecord.getType());
        holder.date.setText(individualRecord.getCreate_at());
        if(individualRecord.getType().toString().equals("Income")){
            holder.amount.setText("+" + individualRecord.getAmount() + " Kč");
            holder.amount.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.amount.setText("-" + individualRecord.getAmount() + " Kč");
            holder.amount.setTextColor(getContext().getResources().getColor(R.color.Expense));
        }

        holder.category.setText(individualRecord.getCategory());
        return convertView;
    }
}
