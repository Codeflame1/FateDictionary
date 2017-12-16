package com.type_moon.codeflame.fatedictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class CharacterListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> list;

    CharacterListViewAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.character_list, null);
            holder.image = view.findViewById(R.id.list_image);
            holder.frame = view.findViewById(R.id.list_frame);
            holder.name = view.findViewById(R.id.list_name);
            holder.alignment = view.findViewById(R.id.list_alignment);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Object num = list.get(i).get("number");
        int number = Integer.parseInt(num.toString());
        holder.image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+number+"_little.png"));
        holder.frame.setImageResource(ImageGet.getSmallFrame(list.get(i).get("job").toString()));
        holder.name.setText(list.get(i).get("name").toString());
        holder.alignment.setText(list.get(i).get("alignment").toString());

        return view;
    }
    static class ViewHolder{
        ImageView image;
        ImageView frame;
        TextView name;
        TextView alignment;
    }
    void refreshList(List<Map<String, Object>> list){
        this.list = list;
        notifyDataSetChanged();

    }
}
