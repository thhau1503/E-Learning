package com.e_learning.tuvung;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.e_learning.R;

import java.util.List;

public class ChuDeTuVungAdapter extends ArrayAdapter<ChuDeTuVung> {
    private Context context;
    private List<ChuDeTuVung> items;

    public ChuDeTuVungAdapter(Context context, List<ChuDeTuVung> items) {
        super(context, R.layout.item_topic, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        }

        ChuDeTuVung currentItem = items.get(position);

        TextView indexTextView = convertView.findViewById(R.id.tvIndex);
        TextView nameTextView = convertView.findViewById(R.id.tvName);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        nameTextView.setText(currentItem.getTenchude());
        indexTextView.setText(String.valueOf(currentItem.getIdchude())+ ".");

        Bitmap bitmap = BitmapFactory.decodeByteArray(currentItem.getHinhanh(), 0, currentItem.getHinhanh().length);
        imageView.setImageBitmap(bitmap);

        return convertView;
    }
}
