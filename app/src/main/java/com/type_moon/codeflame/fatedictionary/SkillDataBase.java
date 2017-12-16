package com.type_moon.codeflame.fatedictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SkillDataBase extends SQLiteOpenHelper {
    //数据库名字
    private static final String DB_NAME = "Skill.db";
    //数据库版本
    private static final int DB_VERSION = 1;
    //表名
    private static final String TABLE_NAME = "skill";
    static SkillDataBase myDataBase;

    /**
     * 单例模式返回数据库
     *
     * @param context 上下文
     * @return 数据库对象
     */
    static SkillDataBase getInstances(Context context) {
        if (myDataBase == null) {
            return new SkillDataBase(context);
        } else {
            return myDataBase;
        }
    }


    //上下文,数据库名字,数据库工厂,版本号
    private SkillDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //此方法中创建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table" + " " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,owner text,type text,name text,level text,introduction text);");

    }

    /**
     * 用来更新数据库版本的
     * onCreate方法只是在第一次安装app的时候会调用,之后的数据库要更改的话,必须使用数据库版本上升,或者卸载了重新安装
     *
     * @param sqLiteDatabase 数据库
     * @param oldVersion     老的版本号
     * @param newVersion     更新后的版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            //删除老表
            sqLiteDatabase.execSQL("drop table"+" "+TABLE_NAME);
            //重新创建表
            onCreate(sqLiteDatabase);
        }

    }

    /**
     * 创建一个用来插入数据的方法
     */
    void insert(String owner, String type, String name, String level, String introduction) {
        //让数据库可写
        SQLiteDatabase database = getWritableDatabase();
        /*
        类似于HashMap 都有键值对
        key 对应的列表中的某一列的名称,字段
        value 对应字段要插入的值
         */
        ContentValues values = new ContentValues();
        values.put("owner", owner);
        values.put("type", type);
        values.put("name", name);
        values.put("level", level);
        values.put("introduction", introduction);
        //插入
        database.insert(TABLE_NAME, "type", values);
        //插入完成后关闭,以免造成内存泄漏
        database.close();

    }


    /**
     * 创建一个查找数据库的方法
     *
     * public  Cursor query(String table,String[] columns,String selection,String[]  selectionArgs,String groupBy,String having,String orderBy,String limit);
     各个参数的意义说明：
     参数table:表名称
     参数columns:列名称数组
     参数selection:条件字句，相当于where
     参数selectionArgs:条件字句，参数数组
     参数groupBy:分组列
     参数having:分组条件
     参数orderBy:排序列
     参数limit:分页查询限制
     参数Cursor:返回值，相当于结果集ResultSet
     Cursor是一个游标接口，提供了遍历查询结果的方法，如移动指针方法move()，获得列值方法getString()等.
     */
    Cursor query() {
        //数据库可读
        SQLiteDatabase database = getReadableDatabase();
        //查找
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }

//    Cursor rawQuery(String name) {
//        SQLiteDatabase database = getReadableDatabase();
//        return database.query(TABLE_NAME, null, "name='"+name+"'", null, null, null, null);
//    }
//
    Cursor searchQuery(String s, String type) {
        SQLiteDatabase database = getReadableDatabase();
        String selection = type + "=?";
        String[] Args = {s};
        return database.query(TABLE_NAME, null, selection, Args, null, null, null);
    }
//
//    /**
//     * 创建一个删除数据的方法,传入的参数越多,删除时越精确的找到要删除的哪一行
//     */
//    public void delete(int id, String type, String name, String level, String introduction) {
//        SQLiteDatabase database = getWritableDatabase();
//        /*
//        删除的条件,当id = 传入的参数id时,sex = 传入的参数sex时,age = 传入的age,hobby = 传入的hobby时
//        当条件都满足时才删除这行数据,一个条件不满足就删除失败
//         */
//        String where = "id=? and type = ? and name = ? and level = ? and introduction = ? ";
//        //删除条件的参数
//        String[] whereArgs = {id + "", type, name, level, introduction};
//        database.delete(TABLE_NAME, where, whereArgs);
//        database.close();
//    }

    /**
     * 再创建一个删除一个删除的方法,条件只有一个
     */
    void deleteById(int id) {
        SQLiteDatabase database = getWritableDatabase();
        //当条件满足id = 传入的参数的时候,就删除那整行数据,有可能有好几行都满足这个条件,满足的全部都删除
        String where = "id = ?";
        String[] whereArgs = {id + ""};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }

    /**
     * 创建一个修改数据的方法
     */
    void updata(int id, String owner, String type, String name, String level, String introduction) {
        SQLiteDatabase database = getWritableDatabase();
//        update(String table,ContentValues values,String  whereClause, String[]  whereArgs)
        String where = "id = ?";
        String[] whereArgs = {id+""};
        ContentValues values = new ContentValues();
        values.put("owner", owner);
        values.put("type", type);
        values.put("name", name);
        values.put("level", level);
        values.put("introduction", introduction);
        //参数1  表名称  参数2  跟行列ContentValues类型的键值对Key-Value
        // 参数3  更新条件（where字句）    参数4  更新条件数组
        database.update(TABLE_NAME, values,where, whereArgs);
        database.close();
    }
}
