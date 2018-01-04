package com.type_moon.codeflame.fatedictionary.Character;

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

import com.type_moon.codeflame.fatedictionary.Tool.ImageGet;
import com.type_moon.codeflame.fatedictionary.R;
import com.type_moon.codeflame.fatedictionary.Skill.SkillDataBase;
import com.type_moon.codeflame.fatedictionary.Tool.Tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterDetail extends AppCompatActivity {

    private ImageView mimage;
    private CharacterSkillListViewAdapter sadapter;
    private int number;
    private SkillDataBase skillDataBase;
    private int flag = 0;
    private String LOCATION = Environment.getExternalStorageDirectory()+"/FateDictionary/a";
    private String[] w = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y"};
    private File file;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterdetail);

        mimage = findViewById(R.id.detail_image);
        ImageView mframe = findViewById(R.id.detail_imageframe);
        TextView mname = findViewById(R.id.detail_name);
        ImageView msex = findViewById(R.id.detail_sex);
        TextView mhweight = findViewById(R.id.detail_hweight);
        TextView morigo = findViewById(R.id.detail_origo);
        TextView malignment = findViewById(R.id.detail_alignment);
        TextView mresource = findViewById(R.id.detail_resource);
        TextView mintroduction = findViewById(R.id.detail_introduction);
        TextView mstre = findViewById(R.id.detail_stre);
        TextView mendu = findViewById(R.id.detail_endu);
        TextView magil = findViewById(R.id.detail_agil);
        TextView mmagi = findViewById(R.id.detail_magi);
        TextView mluck = findViewById(R.id.detail_luck);
        TextView mskil = findViewById(R.id.detail_skil);
        ImageView mstrelv = findViewById(R.id.detail_strelv);
        ImageView mendulv = findViewById(R.id.detail_endulv);
        ImageView magillv = findViewById(R.id.detail_agillv);
        ImageView mmagilv = findViewById(R.id.detail_magilv);
        ImageView mlucklv = findViewById(R.id.detail_lucklv);
        ImageView mskillv = findViewById(R.id.detail_skillv);
        ListView skillList = findViewById(R.id.detail_skilllist);
        ImageButton mback = findViewById(R.id.back);
        ImageButton mchange = findViewById(R.id.change);

        CharacterDataBase characterDataBase = new CharacterDataBase(this);
        skillDataBase = new SkillDataBase(this);
        int id = getIntent().getIntExtra("id", 0);
        Cursor cursor = characterDataBase.searchById(id);
        cursor.moveToNext();
        number = cursor.getInt(cursor.getColumnIndex("number"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        int job = cursor.getInt(cursor.getColumnIndex("job"));
        int sex = cursor.getInt(cursor.getColumnIndex("sex"));
        int height = cursor.getInt(cursor.getColumnIndex("height"));
        int weight = cursor.getInt(cursor.getColumnIndex("weight"));
        String origo = cursor.getString(cursor.getColumnIndex("origo"));
        int alignment = cursor.getInt(cursor.getColumnIndex("alignment"));
        String resource = cursor.getString(cursor.getColumnIndex("resource"));
        String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
        String stre = cursor.getString(cursor.getColumnIndex("stre"));
        String endu = cursor.getString(cursor.getColumnIndex("endu"));
        String agil = cursor.getString(cursor.getColumnIndex("agil"));
        String magi = cursor.getString(cursor.getColumnIndex("magi"));
        String luck = cursor.getString(cursor.getColumnIndex("luck"));
        String skil = cursor.getString(cursor.getColumnIndex("skil"));
        cursor.close();

        List<Map<String, Object>> data = getSkillListData(name);
        sadapter = new CharacterSkillListViewAdapter(this, data);
        skillList.setAdapter(sadapter);
        setListViewHeightBasedOnChildren(skillList);
        sadapter.notifyDataSetChanged();

        mname.setText(name);
        String hei = height+"";
        String wei = weight+"";
        if (height==0) {
            hei = "??";
        }
        if (weight==0) {
            wei = "??";
        }
        mhweight.setText(hei+"cm·"+wei+"kg");
        if (sex==0) {
            msex.setImageResource(R.mipmap.male);
        } else {
            msex.setImageResource(R.mipmap.female);
        }
        mimage.setImageBitmap(BitmapFactory.decodeFile(LOCATION+ Tool.numDecimal(number)+"a.png"));
        mframe.setImageResource(ImageGet.getBigFrame(Tool.getJob(job)));

        morigo.setText(origo);
        malignment.setText(Tool.getAlignment(alignment));
        mresource.setText(resource);
        mintroduction.setText(introduction);
        mstre.setText(stre);
        mendu.setText(endu);
        magil.setText(agil);
        mmagi.setText(magi);
        mluck.setText(luck);
        mskil.setText(skil);
        mstrelv.setImageResource(ImageGet.getLevelImage(stre));
        mendulv.setImageResource(ImageGet.getLevelImage(endu));
        magillv.setImageResource(ImageGet.getLevelImage(agil));
        mmagilv.setImageResource(ImageGet.getLevelImage(magi));
        mlucklv.setImageResource(ImageGet.getLevelImage(luck));
        mskillv.setImageResource(ImageGet.getLevelImage(skil));
        mchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag++;
                file = new File(LOCATION+Tool.numDecimal(number)+w[flag]+".png");
                if (!file.exists()) {
                    flag=0;
                }
                mimage.setImageBitmap(BitmapFactory.decodeFile(LOCATION+Tool.numDecimal(number)+w[flag]+".png"));
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<Map<String, Object>> getSkillListData(String s) {
        List<Map<String, Object>> slist = new ArrayList<>();
        Cursor query = skillDataBase.searchOwner(s);
        if (query.moveToFirst()) {
            do {
                String type = query.getString(query.getColumnIndex("type"));
                String name = query.getString(query.getColumnIndex("name"));
                String level = query.getString(query.getColumnIndex("level"));
                String introduction = query.getString(query.getColumnIndex("introduction"));
                Map<String, Object> map = new HashMap<>();
                map.put("type", type);
                map.put("name", name);
                map.put("level", level);
                map.put("introduction", introduction);
                slist.add(map);
            } while (query.moveToNext());
        }
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));//listView.getDividerHeight()获取子项间分隔符占用的高度，params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            List<Map<String, Object>> data1 = getSkillListData("");
            sadapter.refreshList(data1);

        }
    }
}
