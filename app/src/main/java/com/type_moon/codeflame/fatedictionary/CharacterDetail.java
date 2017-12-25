package com.type_moon.codeflame.fatedictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterDetail extends AppCompatActivity{

    private CharacterSkillListViewAdapter sadapter;
    private int number;
    private int flag = 0;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterdetail);

        ImageButton detailimage = findViewById(R.id.detail_image);
        ImageView detailframe = findViewById(R.id.detail_imageframe);
        TextView detailname = findViewById(R.id.detail_name);
        ImageView detailsex = findViewById(R.id.detail_sex);
        TextView detailheight = findViewById(R.id.detail_height);
        TextView detailweight = findViewById(R.id.detail_weight);
        TextView detailorigo = findViewById(R.id.detail_origo);
        TextView detailalignment = findViewById(R.id.detail_alignment);
        TextView detailintroduction = findViewById(R.id.detail_introduction);
        TextView detailstre = findViewById(R.id.detail_stre);
        TextView detailendu = findViewById(R.id.detail_endu);
        TextView detailagil = findViewById(R.id.detail_agil);
        TextView detailmagi = findViewById(R.id.detail_magi);
        TextView detailluck = findViewById(R.id.detail_luck);
        TextView detailskil = findViewById(R.id.detail_skil);
        ImageView detailstrelv = findViewById(R.id.detail_strelv);
        ImageView detailendulv = findViewById(R.id.detail_endulv);
        ImageView detailagillv = findViewById(R.id.detail_agillv);
        ImageView detailmagilv = findViewById(R.id.detail_magilv);
        ImageView detaillucklv = findViewById(R.id.detail_lucklv);
        ImageView detailskillv = findViewById(R.id.detail_skillv);
        ListView skillList = findViewById(R.id.detail_skilllist);
        ImageButton detailback = findViewById(R.id.back);


        int id = getIntent().getIntExtra("id", 0);
        Cursor cursor = CharacterDataBase.getInstances(CharacterDetail.this).searchById( id );
        cursor.moveToNext();
        number = cursor.getInt(cursor.getColumnIndex("number"));
        String name = cursor.getString(2);
        String job = cursor.getString(3);
        String sex = cursor.getString(4);
        String height = cursor.getString(5);
        String weight = cursor.getString(6);
        String origo = cursor.getString(7);
        String alignment = cursor.getString(8);
        String introduction = cursor.getString(9);
        String stre = cursor.getString(10);
        String endu = cursor.getString(11);
        String agil = cursor.getString(12);
        String magi = cursor.getString(13);
        String luck = cursor.getString(14);
        String skil = cursor.getString(15);
        cursor.close();

        List<Map<String, Object>> data = getSkillListData(name);
        sadapter = new CharacterSkillListViewAdapter(this, data);

        skillList.setAdapter(sadapter);
        setListViewHeightBasedOnChildren(skillList);
        sadapter.notifyDataSetChanged();

        if (height.equals("0"))
            height = "?";
        if (weight.equals("0"))
            weight = "?";


        detailimage.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"a.png"));
        detailframe.setImageResource(ImageGet.getBigFrame(job));
        detailname.setText(name);

        if (sex.equals("男")) {
            detailsex.setImageResource(R.mipmap.male);
        } else {
            detailsex.setImageResource(R.mipmap.female);
        }
        detailheight.setText(height + "cm");
        detailweight.setText(weight + "kg");
        detailorigo.setText(origo);
        detailalignment.setText(alignment);
        detailintroduction.setText(introduction);
        detailstre.setText(stre);
        detailendu.setText(endu);
        detailagil.setText(agil);
        detailmagi.setText(magi);
        detailluck.setText(luck);
        detailskil.setText(skil);
        detailstrelv.setImageResource(ImageGet.getLevelImage(stre));
        detailendulv.setImageResource(ImageGet.getLevelImage(endu));
        detailagillv.setImageResource(ImageGet.getLevelImage(agil));
        detailmagilv.setImageResource(ImageGet.getLevelImage(magi));
        detaillucklv.setImageResource(ImageGet.getLevelImage(luck));
        detailskillv.setImageResource(ImageGet.getLevelImage(skil));
        detailimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f1 = new File(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"b.png");
                File f2 = new File(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"c.png");
                File f3 = new File(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"d.png");
                if (flag==0&& f1.exists()) {
                    ((ImageButton)view).setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"b.png"));
                    flag=1;
                } else if (flag==1&& f2.exists()) {
                    ((ImageButton)view).setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"c.png"));
                    flag=2;
                } else if (flag==2&& f3.exists()) {
                    ((ImageButton)view).setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/a"+Tool.numDecimal(number)+"d.png"));
                    flag=3;
                }
            }
        });
        detailback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<Map<String, Object>> getSkillListData(String s) {
        List<Map<String, Object>> slist = new ArrayList<>();
        Cursor query = SkillDataBase.getInstances(CharacterDetail.this).query();
        if (query.moveToFirst()) {
            do {
                String owner = query.getString(query.getColumnIndex("owner"));
                String type = query.getString(query.getColumnIndex("type"));
                String name = query.getString(query.getColumnIndex("name"));
                String level = query.getString(query.getColumnIndex("level"));
                String introduction = query.getString(query.getColumnIndex("introduction"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();


                if (s.equals(owner)) {
                    map.put("id", id);
                    map.put("owner", owner);
                    map.put("type", type);
                    map.put("name", name);
                    map.put("level", level);
                    map.put("introduction", introduction);
                    slist.add(map);
                }

            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return slist;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            List<Map<String, Object>> data1 = getSkillListData("");
//            cadapter = new CharacterListViewAdapter(this, data1);
//            mCharacterList.setAdapter(cadapter);
            sadapter.refreshList(data1);

        }
    }
}
