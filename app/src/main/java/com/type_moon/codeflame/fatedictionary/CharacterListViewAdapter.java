package com.type_moon.codeflame.fatedictionary;

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

import com.type_moon.codeflame.fatedictionary.Character.CharacterDataBase;
import com.type_moon.codeflame.fatedictionary.Tool.ImageGet;
import com.type_moon.codeflame.fatedictionary.Tool.Tool;

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.character_list, viewGroup, false);
            holder.image = view.findViewById(R.id.list_image);
            holder.frame = view.findViewById(R.id.list_frame);
            holder.name = view.findViewById(R.id.list_name);
            holder.alignment = view.findViewById(R.id.list_alignment);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int id = Integer.parseInt(list.get(i).get("id").toString());
        Cursor cursor = CharacterDataBase.getInstances(context).searchById(id);
        cursor.moveToNext();
        int number = cursor.getInt(cursor.getColumnIndex("number"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        int job = cursor.getInt(cursor.getColumnIndex("job"));
        int alignment = cursor.getInt(cursor.getColumnIndex("alignment"));
        cursor.close();
        holder.image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+ Tool.numDecimal(number)+"z.png"));
        holder.frame.setImageResource(ImageGet.getSmallFrame(Tool.getJob(job)));
        holder.name.setText(name);
        holder.alignment.setText(Tool.getAlignment(alignment));

        return view;
    }
    static class ViewHolder {
        ImageView image;
        ImageView frame;
        TextView name;
        TextView alignment;
    }
    void refreshList(List<Map<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
