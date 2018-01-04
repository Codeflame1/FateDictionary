package com.type_moon.codeflame.fatedictionary.Skill;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.type_moon.codeflame.fatedictionary.Tool.ImageGet;
import com.type_moon.codeflame.fatedictionary.R;


public class SkillDetail extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skilldetail);

        TextView detailname = findViewById(R.id.skilldetail_name);
        TextView detailowner = findViewById(R.id.skilldetail_owner);
        TextView detailtype = findViewById(R.id.skilldetail_type);
        TextView detaillevel = findViewById(R.id.skilldetail_level);
        TextView detailintroduction = findViewById(R.id.skilldetail_introduction);
        ImageView detaillevellv = findViewById(R.id.skilldetail_levellv);
        ImageButton detailback = findViewById(R.id.skillback);
        SkillDataBase skillDataBase = new SkillDataBase(this);

        int id = getIntent().getIntExtra("id", 0);
        Cursor cursor = skillDataBase.searchById( id );
        cursor.moveToNext();
        String owner = cursor.getString(1);
        String name = cursor.getString(3);
        String type = cursor.getString(2);
        String level = cursor.getString(4);
        String introduction = cursor.getString(5);
        cursor.close();
        detailname.setText(name);
        detailowner.setText(owner);
        detailtype.setText(type);
        detaillevel.setText(level);
        detailintroduction.setText(introduction);
        detaillevellv.setImageResource(ImageGet.getLevelImage(level));
        detailback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
