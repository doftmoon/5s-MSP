package by.uni.lab4_activityintentfs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<ItemData> {
    private Context context;
    private List<ItemData> items;

    public ItemAdapter(Context context, List<ItemData> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemData item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        }

        TextView titleText = convertView.findViewById(R.id.titleText);
        TextView authorText = convertView.findViewById(R.id.authorText);
        TextView yearText = convertView.findViewById(R.id.yearText);

        titleText.setText(item.getTitle());
        authorText.setText(item.getAuthor());
        yearText.setText(item.getYear());

        return convertView;
    }
}
