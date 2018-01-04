package com.type_moon.codeflame.fatedictionary.Skill;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.type_moon.codeflame.fatedictionary.R;

import java.util.ArrayList;
import java.util.List;

public class SkillAdd extends AppCompatActivity {

    private Spinner add_type;
    private TextInputLayout add_name;
    private TextInputLayout add_introduction;
    private EditText madd_name;
    private EditText madd_introduction;
    private Spinner add_owner;
    private Spinner add_level;
    private SkillDataBase skillDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_skilldetail);

        skillDataBase = new SkillDataBase(this);
        final Button add_confirm = findViewById(R.id.skilladd_buttonconfirm);
        Button add_cancel = findViewById(R.id.skilladd_buttoncancel);
        add_type = findViewById(R.id.skilladd_type);
        add_name = findViewById(R.id.skilladd_name);
        add_introduction = findViewById(R.id.add_introduction);
        add_owner = findViewById(R.id.skilladd_owner);
        add_level = findViewById(R.id.skilladd_level);
        madd_name = add_name.getEditText();
        madd_introduction = add_introduction.getEditText();
        List<String> data = getOwnerSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_owner.setAdapter(adapter);

        add_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拿到输入的数据
                String name = madd_name.getText().toString().trim();
                String type = (String) add_type.getSelectedItem();
                String introduction = madd_introduction.getText().toString().trim();
                String owner = (String) add_owner.getSelectedItem();
                String level = (String) add_level.getSelectedItem();

                add_name.setErrorEnabled(false);
                add_introduction.setErrorEnabled(false);
                if (TextUtils.isEmpty(name)) {
                    add_name.setErrorEnabled(true);
                    add_name.setError(getString(R.string.name) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(introduction)) {
                    add_introduction.setErrorEnabled(true);
                    add_introduction.setError(getString(R.string.introduction) + getString(R.string.text_error_empty));
                } else {
                    //调用插入方法
                    skillDataBase.insert(owner, type, name, level, introduction);
                    finish();
                }
            }
        });

        add_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<String> getOwnerSpinner() {
        List<String> ownerlist = new ArrayList<>();
        Cursor query = skillDataBase.query();
        /*
        游标cursor默认是在-1的位置,query.moveToFirst()将游标移动到第一行,如果不写这个就会报
         Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 12
         这个问题坑爹,以后一定要注意
         */
        if (query.moveToFirst()) {
            do {
                String name = query.getString(query.getColumnIndex("name"));
                ownerlist.add(name);
            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return ownerlist;
    }
}