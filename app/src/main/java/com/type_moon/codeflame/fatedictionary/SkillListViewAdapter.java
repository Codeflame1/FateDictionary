package com.type_moon.codeflame.fatedictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class SkillListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> list;

    SkillListViewAdapter(Context context, List<Map<String, Object>> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.skill_list, viewGroup, false);
            holder.name = view.findViewById(R.id.slist_name);
            holder.owner = view.findViewById(R.id.slist_owner);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(list.get(i).get("name").toString());
        holder.owner.setText(list.get(i).get("owner").toString());

        return view;
    }
    static class ViewHolder {
        TextView name;
        TextView owner;
    }
    void refreshList(List<Map<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();

    }
}
