package com.type_moon.codeflame.fatedictionary;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.ServiceConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Map<String, Object>> clist;
    private List<Map<String, Object>> slist;
    private CharacterListViewAdapter cadapter;
    private SkillListViewAdapter sadapter;
    private ListView mCharacterList;
    private ListView mSkillList;
    private Spinner MusicChange;
    private MusicService musicService;
    private Button CSChange;
    private Button mCharacterListAdd;
    private Button mSkillListAdd;
    private Button mLittletest;
    private ImageButton searchButton;
    private EditText msearch;
    private AlertDialog mcdialog;
    private AlertDialog msdialog;
    Intent intent;
    ServiceConnection sc;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCharacterList = findViewById(R.id.characterlist);
        mSkillList = findViewById(R.id.skilllist);
        mCharacterListAdd = findViewById(R.id.characterlistadd);
        mSkillListAdd = findViewById(R.id.skilllistadd);
        mLittletest = findViewById(R.id.littletestgoto);
        CSChange = findViewById(R.id.characterskillchange);
        MusicChange = findViewById(R.id.music_change);
        TextInputLayout characterlistsearch = findViewById(R.id.characterlistsearch);
        searchButton = findViewById(R.id.characterlistsearchButton);
        msearch = characterlistsearch.getEditText();
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                musicService = ((MusicService.MyBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                musicService = null;
            }
        };
        if (msearch != null) {
            msearch.clearFocus();
        }
        characterlistsearch.clearFocus();
        msearch.setSelected(false);

        FirstInsert.insertCharacter(this);
        FirstInsert.insertSkill(this);
        FirstInsert.insertImage(this);

        List<Map<String, Object>> data = getCharacterData("");
        cadapter = new CharacterListViewAdapter(this, data);
        mCharacterList.setAdapter(cadapter);
        mCharacterList.setTextFilterEnabled(true);
        cadapter.notifyDataSetChanged();

        List<Map<String, Object>> data1 = getSkillData("");
        sadapter = new SkillListViewAdapter(this, data1);
        mSkillList.setVisibility(View.INVISIBLE);
        mSkillList.setAdapter(sadapter);
        mSkillList.setTextFilterEnabled(true);
        sadapter.notifyDataSetChanged();

        CSChange.setTag("0");
        setListener();

        intent = new Intent(MainActivity.this,MusicService.class);
        startService(intent);

    }

    private void setListener() {
        MusicChange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str1 = (String)MusicChange.getSelectedItem();
                if (str1.equals("无")) {
                    musicService.pausePlay();
                } else if (str1.equals("权御天下")) {
                    musicService.play1();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                musicService.pausePlay();
            }
        });

        CSChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (CSChange.getTag() == "0"){
                    CSChange.setBackgroundResource(R.mipmap.skillpng);
                    CSChange.setTag("1");
                    mSkillList.setVisibility(View.VISIBLE);
                    mCharacterList.setVisibility(View.INVISIBLE);
                } else {
                    CSChange.setBackgroundResource(R.mipmap.characterpng);
                    CSChange.setTag("0");
                    mSkillList.setVisibility(View.INVISIBLE);
                    mCharacterList.setVisibility(View.VISIBLE);
                }
            }
        });
        
        //搜索按钮
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = msearch.getText().toString().trim();
                List<Map<String, Object>> data1 = getCharacterData(search);
                List<Map<String, Object>> data2 = getSkillData(search);
                cadapter.refreshList(data1);
                sadapter.refreshList(data2);
            }
        });

        //点击跳转英灵增加
        mCharacterListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CharacterAdd.class);
                startActivityForResult(intent, 0);
            }
        });

        //点击跳转宝具增加
        mSkillListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SkillAdd.class);
                startActivityForResult(intent, 0);
            }
        });

        //点击跳转小测试
        mLittletest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LittleTest.class);
                startActivityForResult(intent, 0);
            }
        });
        //英灵list的监听事件
        mCharacterList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            private int id;
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
//                //删除是要拿到当前行的id值才能删除当前行,下面的操作都是点击某个item拿到对应item的id字段
//                //拿到当前position的 item的所有数据
                Object id = clist.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                Object num = clist.get(position).get("number");
                int number = Integer.parseInt(num.toString());
                String name = clist.get(position).get("name").toString();
                String job = clist.get(position).get("job").toString();
                String sex = clist.get(position).get("sex").toString();
                String height = clist.get(position).get("height").toString();
                String weight = clist.get(position).get("weight").toString();
                String origo = clist.get(position).get("origo").toString();
                String alignment = clist.get(position).get("alignment").toString();
                String introduction = clist.get(position).get("introduction").toString();
                String stre = clist.get(position).get("stre").toString();
                String endu = clist.get(position).get("endu").toString();
                String agil = clist.get(position).get("agil").toString();
                String magi = clist.get(position).get("magi").toString();
                String luck = clist.get(position).get("luck").toString();
                String skil = clist.get(position).get("skil").toString();

                //将得到id传入到需要的方法中
                showCharacterDialog(i, number, name, job, sex, height, weight, origo, alignment ,introduction, stre, endu, agil, magi, luck, skil);
                return true;
            }
        });

        mCharacterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> cadapterView, View view, int position, long l) {
                Object id = clist.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                Object num = clist.get(position).get("number");
                int number = Integer.parseInt(num.toString());
                String name = clist.get(position).get("name").toString();
                String job = clist.get(position).get("job").toString();
                String sex = clist.get(position).get("sex").toString();
                String height = clist.get(position).get("height").toString();
                String weight = clist.get(position).get("weight").toString();
                String origo = clist.get(position).get("origo").toString();
                String alignment = clist.get(position).get("alignment").toString();
                String introduction = clist.get(position).get("introduction").toString();
                String stre = clist.get(position).get("stre").toString();
                String endu = clist.get(position).get("endu").toString();
                String agil = clist.get(position).get("agil").toString();
                String magi = clist.get(position).get("magi").toString();
                String luck = clist.get(position).get("luck").toString();
                String skil = clist.get(position).get("skil").toString();
                Intent intent = new Intent(MainActivity.this, CharacterDetail.class);
                intent.putExtra("id", i)
                        .putExtra("number", number)
                        .putExtra("name", name)
                        .putExtra("job", job)
                        .putExtra("sex", sex)
                        .putExtra("height", height)
                        .putExtra("weight", weight)
                        .putExtra("origo", origo)
                        .putExtra("alignment", alignment)
                        .putExtra("introduction", introduction)
                        .putExtra("stre", stre)
                        .putExtra("endu", endu)
                        .putExtra("agil", agil)
                        .putExtra("magi", magi)
                        .putExtra("luck", luck)
                        .putExtra("skil", skil);
                startActivityForResult(intent,0);
            }
        });

        //宝具跳转
        mSkillList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //            private int id;
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
//                //删除是要拿到当前行的id值才能删除当前行,下面的操作都是点击某个item拿到对应item的id字段
//                //拿到当前position的 item的所有数据
                Object id = slist.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                String owner = slist.get(position).get("owner").toString();
                String type = slist.get(position).get("type").toString();
                String name = slist.get(position).get("name").toString();
                String level = slist.get(position).get("level").toString();
                String introduction = slist.get(position).get("introduction").toString();

                //将得到id传入到需要的方法中
                showSkillDialog(i, owner, type, name, level, introduction);
                return true;
            }
        });

        mSkillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> cadapterView, View view, int position, long l) {
                Object id = slist.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                String owner = slist.get(position).get("owner").toString();
                String type = slist.get(position).get("type").toString();
                String name = slist.get(position).get("name").toString();
                String level = slist.get(position).get("level").toString();
                String introduction = slist.get(position).get("introduction").toString();
                Intent intent = new Intent(MainActivity.this, SkillDetail.class);
                intent.putExtra("id", i)
                        .putExtra("owner", owner)
                        .putExtra("type", type)
                        .putExtra("name", name)
                        .putExtra("level", level)
                        .putExtra("introduction", introduction);
                startActivityForResult(intent,0);
            }
        });
    }

    /**
     * 点击显示对话框选择修改或者是删除
     */
    private void showCharacterDialog(final int id, final int number, final String name, final String job, final String sex, final String height, final String weight, final String origo, final String alignment, final String introduction, final  String stre, final String endu, final String agil, final String magi, final String luck, final String skil) {

        mcdialog = new AlertDialog.Builder(MainActivity.this).create();
        mcdialog.show();
        mcdialog.getWindow().setContentView(R.layout.alertdialog);
        mcdialog.getWindow().findViewById(R.id.dia_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CharacterEdit.class);
                intent.putExtra("id", id)
                        .putExtra("number", number)
                        .putExtra("name", name)
                        .putExtra("job", job)
                        .putExtra("sex", sex)
                        .putExtra("height", height)
                        .putExtra("weight", weight)
                        .putExtra("origo", origo)
                        .putExtra("stre", stre)
                        .putExtra("endu", endu)
                        .putExtra("agil", agil)
                        .putExtra("magi", magi)
                        .putExtra("luck", luck)
                        .putExtra("skil", skil);
                startActivityForResult(intent,0);
                mcdialog.dismiss();
            }
        });

        mcdialog.getWindow().findViewById(R.id.dia_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f1 = new File(Environment.getExternalStorageDirectory()+"/FateDictionary", "a"+number+".png");
                File f2 = new File(Environment.getExternalStorageDirectory()+"/FateDictionary", "a"+number+"_little.png");
                if(f1.isFile()){
                    f1.delete();
                }
                if(f2.isFile()){
                    f2.delete();
                }
                CharacterDataBase.getInstances(MainActivity.this).deleteById(id);
                //重新查询,然后显示
                List<Map<String, Object>> data = getCharacterData("");
                cadapter.refreshList(data);
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                mcdialog.dismiss();
            }
        });
        //设置一个标题
        mcdialog.getWindow().findViewById(R.id.dia_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcdialog.dismiss();
            }
        });
    }

    private void showSkillDialog(final int id, final String owner, final String type, final String name, final String level, final String introduction) {

        msdialog = new AlertDialog.Builder(MainActivity.this).create();
        msdialog.show();
        msdialog.getWindow().setContentView(R.layout.alertdialog);
        msdialog.getWindow().findViewById(R.id.dia_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SkillEdit.class);
                intent.putExtra("id", id)
                        .putExtra("owner", owner)
                        .putExtra("type", type)
                        .putExtra("name", name)
                        .putExtra("level", level)
                        .putExtra("introduction", introduction);
                startActivityForResult(intent,0);
                msdialog.dismiss();
            }
        });

        msdialog.getWindow().findViewById(R.id.dia_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkillDataBase.getInstances(MainActivity.this).deleteById(id);
                //重新查询,然后显示
                List<Map<String, Object>> data = getSkillData("");
                sadapter.refreshList(data);
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                msdialog.dismiss();
            }
        });
        //设置一个标题
        msdialog.getWindow().findViewById(R.id.dia_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msdialog.dismiss();
            }
        });
    }

    /**
     * 通过查找数据库,拿到里面的数据
     */
    private List<Map<String, Object>> getCharacterData(String s) {
        clist = new ArrayList<>();
        Cursor query = CharacterDataBase.getInstances(MainActivity.this).query();
        /*
        游标cursor默认是在-1的位置,query.moveToFirst()将游标移动到第一行,如果不写这个就会报
         Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 12
         这个问题坑爹,以后一定要注意
         */
        if (query.moveToFirst()) {
            do {
                String name = query.getString(query.getColumnIndex("name"));
                String job = query.getString(query.getColumnIndex("job"));
                String sex = query.getString(query.getColumnIndex("sex"));
                String height = query.getString(query.getColumnIndex("height"));
                String weight = query.getString(query.getColumnIndex("weight"));
                String origo = query.getString(query.getColumnIndex("origo"));
                String alignment = query.getString(query.getColumnIndex("alignment"));
                String introduction = query.getString(query.getColumnIndex("introduction"));
                String stre = query.getString(query.getColumnIndex("stre"));
                String endu = query.getString(query.getColumnIndex("endu"));
                String agil = query.getString(query.getColumnIndex("agil"));
                String magi = query.getString(query.getColumnIndex("magi"));
                String luck = query.getString(query.getColumnIndex("luck"));
                String skil = query.getString(query.getColumnIndex("skil"));
                int number = query.getInt(query.getColumnIndex("number"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();


                if (s.isEmpty()) {
                    map.put("id", id);
                    map.put("number", number);
                    map.put("name", name);
                    map.put("job", job);
                    map.put("sex", sex);
                    map.put("height", height);
                    map.put("weight", weight);
                    map.put("origo", origo);
                    map.put("alignment", alignment);
                    map.put("introduction", introduction);
                    map.put("stre", stre);
                    map.put("endu", endu);
                    map.put("agil", agil);
                    map.put("magi", magi);
                    map.put("luck", luck);
                    map.put("skil", skil);
                    clist.add(map);
                } else {
                    if (name.contains(s)||job.contains(s)||sex.contains(s)||height.contains(s)||weight.contains(s)) {
                        map.put("id", id);
                        map.put("number", number);
                        map.put("name", name);
                        map.put("job", job);
                        map.put("sex", sex);
                        map.put("height", height);
                        map.put("weight", weight);
                        map.put("origo", origo);
                        map.put("alignment", alignment);
                        map.put("introduction", introduction);
                        map.put("stre", stre);
                        map.put("endu", endu);
                        map.put("agil", agil);
                        map.put("magi", magi);
                        map.put("luck", luck);
                        map.put("skil", skil);
                        clist.add(map);
                    }
                }

            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return clist;
    }

    private List<Map<String, Object>> getSkillData(String s) {
        slist = new ArrayList<>();
        Cursor query = SkillDataBase.getInstances(MainActivity.this).query();
        /*
        游标cursor默认是在-1的位置,query.moveToFirst()将游标移动到第一行,如果不写这个就会报
         Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 12
         这个问题坑爹,以后一定要注意
         */
        if (query.moveToFirst()) {
            do {
                String owner = query.getString(query.getColumnIndex("owner"));
                String type = query.getString(query.getColumnIndex("type"));
                String name = query.getString(query.getColumnIndex("name"));
                String level = query.getString(query.getColumnIndex("level"));
                String introduction = query.getString(query.getColumnIndex("introduction"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();


                if (s.isEmpty()) {
                    map.put("id", id);
                    map.put("owner", owner);
                    map.put("type", type);
                    map.put("name", name);
                    map.put("level", level);
                    map.put("introduction", introduction);
                    slist.add(map);
                } else {
                    if (name.contains(s)||level.contains(s)) {
                        map.put("id", id);
                        map.put("owner", owner);
                        map.put("type", type);
                        map.put("name", name);
                        map.put("level", level);
                        map.put("introduction", introduction);
                        slist.add(map);
                    }
                }

            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return slist;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            List<Map<String, Object>> data1 = getCharacterData("");
            List<Map<String, Object>> data2 = getSkillData("");
//            cadapter = new CharacterListViewAdapter(this, data1);
//            mCharacterList.setAdapter(cadapter);
            cadapter.refreshList(data1);
            sadapter.refreshList(data2);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止并释放资源
        stopService(intent);
    }
}