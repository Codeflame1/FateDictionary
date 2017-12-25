package com.type_moon.codeflame.fatedictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
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
        int id = Integer.parseInt(list.get(i).get("id").toString());
        Cursor cursor = CharacterDataBase.getInstances(context).searchById( id );
        cursor.moveToNext();
        int number = cursor.getInt(cursor.getColumnIndex("number"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String job = cursor.getString(cursor.getColumnIndex("job"));
        String alignment = cursor.getString(cursor.getColumnIndex("alignment"));
        cursor.close();
        holder.image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"l.png"));
        holder.frame.setImageResource(ImageGet.getSmallFrame(job));
        holder.name.setText(name);
        holder.alignment.setText(alignment);

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
