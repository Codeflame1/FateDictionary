package com.type_moon.codeflame.fatedictionary.Character;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.type_moon.codeflame.fatedictionary.Tool.ImageGet;
import com.type_moon.codeflame.fatedictionary.R;

import java.util.List;
import java.util.Map;

public class CharacterSkillListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> list;

    public CharacterSkillListViewAdapter(Context context, List<Map<String, Object>> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.characterdetail_skill, null);
            holder.name = view.findViewById(R.id.characterskilldetail_name);
            holder.type = view.findViewById(R.id.characterskilldetail_type);
            holder.level = view.findViewById(R.id.characterskilldetail_level);
            holder.leveli = view.findViewById(R.id.characterskilldetail_levellv);
            holder.introduction = view.findViewById(R.id.characterskilldetail_introduction);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(list.get(i).get("name").toString());
        holder.level.setText(list.get(i).get("level").toString());
        holder.leveli.setImageResource(ImageGet.getLevelImage(list.get(i).get("level").toString()));
        holder.type.setText(list.get(i).get("type").toString());
        holder.introduction.setText(list.get(i).get("introduction").toString());

        return view;
    }
    static class ViewHolder{
        TextView name;
        TextView type;
        TextView level;
        TextView introduction;
        ImageView leveli;
    }
    void refreshList(List<Map<String, Object>> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
