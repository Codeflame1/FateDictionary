package com.type_moon.codeflame.fatedictionary;

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

import java.util.ArrayList;
import java.util.List;

public class SkillEdit extends AppCompatActivity {

    private int id;
    private Spinner edit_type;
    private TextInputLayout edit_name;
    private TextInputLayout edit_introduction;
    private EditText medit_name;
    private EditText medit_introduction;
    private Spinner edit_owner;
    private Spinner edit_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_skilldetail);
        id = getIntent().getIntExtra("id", 0);
        Cursor cursor = SkillDataBase.getInstances(SkillEdit.this).searchById( id );
        cursor.moveToNext();
        String owner = cursor.getString(1);
        String name = cursor.getString(3);
        String type = cursor.getString(2);
        String level = cursor.getString(4);
        String introduction = cursor.getString(5);
        cursor.close();

        final Button edit_confirm = findViewById(R.id.skilledit_buttonconfirm);
        Button edit_cancel = findViewById(R.id.skilledit_buttoncancel);
        edit_type = findViewById(R.id.skilledit_type);
        edit_name = findViewById(R.id.skilledit_name);
        edit_introduction = findViewById(R.id.edit_introduction);
        edit_level = findViewById(R.id.skilledit_level);
        edit_owner = findViewById(R.id.skilledit_owner);
        medit_name = edit_name.getEditText();
        medit_introduction = edit_introduction.getEditText();
        List<String> data = getOwnerSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_owner.setAdapter(adapter);

        edit_type.setSelection(SpinnerSelect.getType(type));
        edit_owner.setSelection(adapter.getPosition(owner));
        edit_level.setSelection(SpinnerSelect.getLevel(level));
        medit_name.setText(name);
        medit_introduction.setText(introduction);


        edit_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拿到输入的数据
                String name = medit_name.getText().toString().trim();
                String type = (String) edit_type.getSelectedItem();
                String introduction = medit_introduction.getText().toString().trim();
                String owner = (String) edit_owner.getSelectedItem();
                String level = (String) edit_level.getSelectedItem();

                edit_name.setErrorEnabled(false);
                edit_introduction.setErrorEnabled(false);
                if (TextUtils.isEmpty(name)) {
                    edit_name.setErrorEnabled(true);
                    edit_name.setError(getString(R.string.name) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(introduction)) {
                    edit_introduction.setErrorEnabled(true);
                    edit_introduction.setError(getString(R.string.introduction) + getString(R.string.text_error_empty));
                } else {
                    //调用插入方法
                    SkillDataBase.getInstances(SkillEdit.this).updata(id, owner, type, name, level, introduction);
                    finish();
                }
            }
        });

        edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<String> getOwnerSpinner() {
        List<String> ownerlist = new ArrayList<>();
        Cursor query = CharacterDataBase.getInstances(SkillEdit.this).query();
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