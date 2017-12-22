package com.type_moon.codeflame.fatedictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Map<String, Object>> clist;
    private List<Map<String, Object>> slist;
    private List<Map<String, Object>> cmorelist;
    private List<Map<String, Object>> smorelist;
    private CharacterListViewAdapter cadapter;
    private SkillListViewAdapter sadapter;
    private ListView mCharacterList;
    private ListView mSkillList;
//    private Spinner MusicChange;
//    private MusicService musicService;
    private Button CSChange;
    private Button mCharacterListAdd;
    private Button mSkillListAdd;
    private Button mLittletest;
    private ImageButton searchButton;
    private EditText msearch;
    private AlertDialog mcdialog;
    private AlertDialog msdialog;
//    Intent intent;
//    ServiceConnection sc;
    private int cPage;
    private int sPage;
    private int cListNum;
    private int sListNum;

    @SuppressLint("InflateParams")
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
//        MusicChange = findViewById(R.id.music_change);
        TextInputLayout characterlistsearch = findViewById(R.id.characterlistsearch);
        searchButton = findViewById(R.id.characterlistsearchButton);
        msearch = characterlistsearch.getEditText();
//        sc = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//                musicService = ((MusicService.MyBinder) iBinder).getService();
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//                musicService = null;
//            }
//        };
        if (msearch != null) {
            msearch.clearFocus();
        }
        characterlistsearch.clearFocus();
        msearch.setSelected(false);

        if (CharacterDataBase.getInstances(MainActivity.this).query().getCount() == 0) {

            FirstInsert.insertCharacter(this);
            FirstInsert.insertSkill(this);
            FirstInsert.insertImage(this);
        }
        cListNum = CharacterDataBase.getInstances(MainActivity.this).searchNum(msearch.getText().toString().trim());
        cPage = 1;
        clist = getCharacterData("",  0, 15);
        mCharacterList.setOnScrollListener(new cScrollListener());
        cadapter = new CharacterListViewAdapter(this, clist);
        mCharacterList.setAdapter(cadapter);
        mCharacterList.setTextFilterEnabled(true);

        sListNum = SkillDataBase.getInstances(MainActivity.this).searchNum(msearch.getText().toString().trim());
        sPage = 1;
        slist = getSkillData("", 0, 15);
        mSkillList.setOnScrollListener(new sScrollListener());
        sadapter = new SkillListViewAdapter(this, slist);
        mSkillList.setVisibility(View.INVISIBLE);
        mSkillList.setAdapter(sadapter);
        mSkillList.setTextFilterEnabled(true);

        CSChange.setTag("0");
        setListener();

//        intent = new Intent(MainActivity.this,MusicService.class);
//        startService(intent);
//        bindService(intent, sc, BIND_AUTO_CREATE);

    }

    private void setListener() {
//        MusicChange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String str1 = (String)MusicChange.getSelectedItem();
//                if (str1.equals("无")) {
//                    musicService.pausePlay();
//                } else if (str1.equals("权御天下")) {
//                    musicService.play1();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                musicService.pausePlay();
//            }
//        });

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
                cListNum = CharacterDataBase.getInstances(MainActivity.this).searchNum(search);
                cPage = 1;
                sListNum = SkillDataBase.getInstances(MainActivity.this).searchNum(search);
                sPage = 1;
                clist = getCharacterData(search, 0, 15);
                slist = getSkillData(search, 0, 15);
                cadapter.refreshList(clist);
                sadapter.refreshList(slist);
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
                //将得到id传入到需要的方法中
                showCharacterDialog(i);
                return true;
            }
        });

        mCharacterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object id = clist.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                Intent intent = new Intent(MainActivity.this, CharacterDetail.class);
                intent.putExtra("id", i);
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
                //将得到id传入到需要的方法中
                showSkillDialog(i);
                return true;
            }
        });

        mSkillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object id = slist.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                Intent intent = new Intent(MainActivity.this, SkillDetail.class);
                intent.putExtra("id", i);
                startActivityForResult(intent,0);
            }
        });
    }

    /**
     * 点击显示对话框选择修改或者是删除
     */
    private void showCharacterDialog(final int id) {

        mcdialog = new AlertDialog.Builder(MainActivity.this).create();
        mcdialog.show();
        mcdialog.getWindow().setContentView(R.layout.alertdialog);
        mcdialog.getWindow().findViewById(R.id.dia_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CharacterEdit.class);
                intent.putExtra("id", id);

                startActivityForResult(intent,0);
                mcdialog.dismiss();
            }
        });

        mcdialog.getWindow().findViewById(R.id.dia_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = CharacterDataBase.getInstances(MainActivity.this).searchById( id );
                cursor.moveToNext();
                String number = cursor.getInt(1) + "";
                cursor.close();
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
                clist = getCharacterData("",0,15);
                cadapter.refreshList(clist);
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

    private void showSkillDialog(final int id) {

        msdialog = new AlertDialog.Builder(MainActivity.this).create();
        msdialog.show();
        msdialog.getWindow().setContentView(R.layout.alertdialog);
        msdialog.getWindow().findViewById(R.id.dia_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SkillEdit.class);
                intent.putExtra("id", id);
                startActivityForResult(intent,0);
                msdialog.dismiss();
            }
        });

        msdialog.getWindow().findViewById(R.id.dia_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkillDataBase.getInstances(MainActivity.this).deleteById(id);
                //重新查询,然后显示
                slist = getSkillData("", 0, 15);
                sadapter.refreshList(slist);
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
    private List<Map<String, Object>> getCharacterData(String s, int startIndex, int num) {
        List<Map<String, Object>> list = new ArrayList<>();
        Cursor query = CharacterDataBase.getInstances(MainActivity.this).queryNum(s, startIndex, num);
        /*
        游标cursor默认是在-1的位置,query.moveToFirst()将游标移动到第一行,如果不写这个就会报
         Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 12
         这个问题坑爹,以后一定要注意
         */
        if (query.moveToFirst()) {
            do {
                String name = query.getString(query.getColumnIndex("name"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                list.add(map);
            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return list;
    }

    private List<Map<String, Object>> getSkillData(String s, int startIndex, int num) {
        List<Map<String, Object>> list = new ArrayList<>();
        Cursor query = SkillDataBase.getInstances(MainActivity.this).queryNum(s, startIndex, num);
        /*
        游标cursor默认是在-1的位置,query.moveToFirst()将游标移动到第一行,如果不写这个就会报
         Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 12
         这个问题坑爹,以后一定要注意
         */
        if (query.moveToFirst()) {
            do {
                String owner = query.getString(query.getColumnIndex("owner"));
                String name = query.getString(query.getColumnIndex("name"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("owner", owner);
                map.put("name", name);
                list.add(map);
            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            cListNum = CharacterDataBase.getInstances(MainActivity.this).searchNum(msearch.getText().toString().trim());
            cPage = 1;
            sListNum = SkillDataBase.getInstances(MainActivity.this).searchNum(msearch.getText().toString().trim());
            sPage = 1;
            clist = getCharacterData("",0,15);
            slist = getSkillData("",0,15);
//            cadapter = new CharacterListViewAdapter(this, data1);
//            mCharacterList.setAdapter(cadapter);
            cadapter.refreshList(clist);
            sadapter.refreshList(slist);

        }
    }

    private final class cScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == cScrollListener.SCROLL_STATE_IDLE) {
                new Thread() {
                    public void run() {
                        int num;
                        num = cPage*15;
                        int i = cListNum - cPage*15;
                        if (i>=15) {
                            cmorelist = getCharacterData(msearch.getText().toString().trim(), num, 15);
                            clist.addAll(cmorelist);
                            chandler.obtainMessage().sendToTarget();
                            cPage+=1;
                        } else if (i<15&&i>0){
                            cmorelist = getCharacterData(msearch.getText().toString().trim(), num, i);
                            clist.addAll(cmorelist);
                            chandler.obtainMessage().sendToTarget();
                            cPage+=1;
                        }
                    }
                }.start();
            }
        }
        //正在滚动时调用
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }
    private final class sScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == sScrollListener.SCROLL_STATE_IDLE) {
                new Thread() {
                    public void run() {
                        int num;
                        num = sPage*15;
                        int i = sListNum - sPage*15;
                        if (i>=15) {
                            smorelist = getSkillData(msearch.getText().toString().trim(), num, 15);
                            slist.addAll(smorelist);
                            shandler.obtainMessage().sendToTarget();
                            sPage+=1;
                        } else if (i<15&&i>0){
                            smorelist = getSkillData(msearch.getText().toString().trim(), num, i);
                            slist.addAll(smorelist);
                            shandler.obtainMessage().sendToTarget();
                            sPage+=1;
                        }
                    }
                }.start();
            }
        }
        //正在滚动时调用
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler chandler = new Handler() {
        public void handleMessage(Message msg) {
            if (cmorelist.size() != 0) {
                System.out.println(cmorelist.toString());
                cadapter.notifyDataSetChanged();
                System.out.println("加载更多数据");
            }else {
                Toast.makeText(MainActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler shandler = new Handler() {
        public void handleMessage(Message msg) {
            if (smorelist.size() != 0) {
                System.out.println(smorelist.toString());
                sadapter.notifyDataSetChanged();
                System.out.println("加载更多数据");
            }else {
                Toast.makeText(MainActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
            }
        }
    };
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //停止并释放资源
//        stopService(intent);
//    }
}