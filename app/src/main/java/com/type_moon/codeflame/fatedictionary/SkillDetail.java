package com.type_moon.codeflame.fatedictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


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

        String name = getIntent().getStringExtra("name");
        String owner = getIntent().getStringExtra("owner");
        String type = getIntent().getStringExtra("type");
        String level = getIntent().getStringExtra("level");
        String introduction = getIntent().getStringExtra("introduction");
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
