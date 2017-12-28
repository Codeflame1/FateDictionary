package com.type_moon.codeflame.fatedictionary;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CharacterEdit extends AppCompatActivity {

    private int id;
    private int number;
    private ImageButton edit_image;
    private Spinner edit_job;
    private ImageView edit_jobframe;
    private TextInputLayout edit_name;
    private Spinner edit_sex;
    private TextInputLayout edit_height;
    private TextInputLayout edit_weight;
    private TextInputLayout edit_origo;
    private TextInputLayout edit_resource;
    private Spinner edit_alignment;
    private TextInputLayout edit_introduction;
    private EditText medit_name;
    private EditText medit_height;
    private EditText medit_weight;
    private EditText medit_origo;
    private EditText medit_resource;
    private EditText medit_introduction;
    private Spinner edit_stre;
    private Spinner edit_endu;
    private Spinner edit_agil;
    private Spinner edit_magi;
    private Spinner edit_luck;
    private Spinner edit_skil;
    public String str1;
    private Uri imageUri;
    private static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory()+"/temp.jpg";

    private AlertDialog imagedialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_characterdetail);
        id = getIntent().getIntExtra("id", 0);
        Cursor cursor = CharacterDataBase.getInstances(CharacterEdit.this).searchById( id );
        cursor.moveToNext();
        number = cursor.getInt(cursor.getColumnIndex("number"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        int job = cursor.getInt(cursor.getColumnIndex("job"));
        int sex = cursor.getInt(cursor.getColumnIndex("sex"));
        String height = cursor.getString(cursor.getColumnIndex("height"));
        int weight = cursor.getInt(cursor.getColumnIndex("weight"));
        int origo = cursor.getInt(cursor.getColumnIndex("origo"));
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

        final Button edit_confirm = findViewById(R.id.edit_buttonconfirm);
        Button edit_cancel = findViewById(R.id.edit_buttoncancel);
        edit_image = findViewById(R.id.edit_image);
        edit_job = findViewById(R.id.edit_job);
        edit_jobframe = findViewById(R.id.edit_imageframe);
        edit_name = findViewById(R.id.edit_name);
        edit_sex = findViewById(R.id.edit_sex);
        edit_height = findViewById(R.id.edit_height);
        edit_weight = findViewById(R.id.edit_weight);
        edit_origo = findViewById(R.id.edit_origo);
        edit_resource = findViewById(R.id.edit_resource);
        edit_alignment = findViewById(R.id.edit_alignment);
        edit_introduction = findViewById(R.id.edit_introduction);
        edit_stre = findViewById(R.id.edit_stre);
        edit_endu = findViewById(R.id.edit_endu);
        edit_agil = findViewById(R.id.edit_agil);
        edit_magi = findViewById(R.id.edit_magi);
        edit_luck = findViewById(R.id.edit_luck);
        edit_skil = findViewById(R.id.edit_skil);
        medit_name = edit_name.getEditText();
        medit_height = edit_height.getEditText();
        medit_weight = edit_weight.getEditText();
        medit_origo = edit_origo.getEditText();
        medit_resource = edit_resource.getEditText();
        medit_introduction = edit_introduction.getEditText();

        edit_job.setSelection(job);
        edit_stre.setSelection(SpinnerSelect.getLevel(stre));
        edit_endu.setSelection(SpinnerSelect.getLevel(endu));
        edit_agil.setSelection(SpinnerSelect.getLevel(agil));
        edit_magi.setSelection(SpinnerSelect.getLevel(magi));
        edit_luck.setSelection(SpinnerSelect.getLevel(luck));
        edit_skil.setSelection(SpinnerSelect.getLevel(skil));
        medit_name.setText(name);
        edit_sex.setSelection(sex);
        medit_height.setText(height);
        medit_weight.setText(weight);
        medit_origo.setText(origo);
        medit_resource.setText(resource);
        edit_alignment.setSelection(alignment);
        medit_introduction.setText(introduction);
        edit_image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/FateDictionary/"+"a"+Tool.numDecimal(number)+"a.png"));
        str1 = (String) edit_job.getSelectedItem();
        imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));

        edit_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str1 = (String) edit_job.getSelectedItem();
                edit_jobframe.setImageResource(ImageGet.getBigFrame(str1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                edit_jobframe.setImageResource(ImageGet.getBigFrame(""));
            }
        });

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });

        edit_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拿到输入的数据
                String hei = medit_height.getText().toString().trim();
                String wei = medit_weight.getText().toString().trim();
                String name = medit_name.getText().toString().trim();
                int job = (int) edit_job.getSelectedItemId();
                int sex = (int) edit_sex.getSelectedItemId();
                int height = Integer.parseInt(hei);
                int weight = Integer.parseInt(wei);
                String origo = medit_origo.getText().toString().trim();
                String resource = medit_resource.getText().toString().trim();
                int alignment = (int) edit_alignment.getSelectedItemId();
                String introduction = medit_introduction.getText().toString().trim();
                String stre = (String) edit_stre.getSelectedItem();
                String endu = (String) edit_endu.getSelectedItem();
                String agil = (String) edit_agil.getSelectedItem();
                String magi = (String) edit_magi.getSelectedItem();
                String luck = (String) edit_luck.getSelectedItem();
                String skil = (String) edit_skil.getSelectedItem();

                edit_name.setErrorEnabled(false);
                edit_height.setErrorEnabled(false);
                edit_weight.setErrorEnabled(false);
                edit_origo.setErrorEnabled(false);
                edit_introduction.setErrorEnabled(false);
                if (TextUtils.isEmpty(name)) {
                    edit_name.setErrorEnabled(true);
                    edit_name.setError(getString(R.string.name) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(hei)) {
                    edit_height.setErrorEnabled(true);
                    edit_height.setError(getString(R.string.height) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(wei)) {
                    edit_weight.setErrorEnabled(true);
                    edit_weight.setError(getString(R.string.weight) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(origo)) {
                    edit_origo.setErrorEnabled(true);
                    edit_origo.setError(getString(R.string.origo) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(resource)) {
                    edit_resource.setErrorEnabled(true);
                    edit_resource.setError(getString(R.string.resource) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(introduction)) {
                    edit_introduction.setErrorEnabled(true);
                    edit_introduction.setError(getString(R.string.introduction) + getString(R.string.text_error_empty));
                } else {
                    //调用插入方法
                    CharacterDataBase.getInstances(CharacterEdit.this).updata(id, number, name, job, sex, height, weight, origo, alignment, resource, introduction, stre, endu, agil, magi, luck, skil);
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
//
    }

    private void showImageDialog() {

        imagedialog = new AlertDialog.Builder(CharacterEdit.this).create();
        imagedialog.show();
        imagedialog.getWindow().setContentView(R.layout.alertdialog_image);
        imagedialog.getWindow().findViewById(R.id.dia_bigimage_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
            }
        });

        imagedialog.getWindow().findViewById(R.id.dia_bigimage_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });

        imagedialog.getWindow().findViewById(R.id.dia_bigimage_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 3);
            }
        });

        imagedialog.getWindow().findViewById(R.id.dia_bigimage_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 4);
            }
        });
        imagedialog.getWindow().findViewById(R.id.dia_littleimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 0);
            }
        });
        //设置一个标题
        imagedialog.getWindow().findViewById(R.id.dia_cancelimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagedialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 处理结果 处理缩放过后的图片
        if (resultCode == RESULT_CANCELED) {//取消
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        } if (requestCode <=4) {
            cropPhoto(intent.getData(),requestCode);
        }  else if (requestCode >4) {
            if (intent != null) {
                setImage(intent, requestCode);//设置图片框
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void cropPhoto(Uri uri, int i) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");
        if (i==0) {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
        } else {
            intent.putExtra("aspectX", 764);
            intent.putExtra("aspectY", 1080);
            intent.putExtra("outputX", 764);
            intent.putExtra("outputY", 1080);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//图像输出
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        i+=5;
        startActivityForResult(intent, i);
    }

    private void setImage(Intent intent, int i) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = BitmapFactory.decodeFile(IMAGE_FILE_LOCATION);
            File nf = new File(Environment.getExternalStorageDirectory()+"/FateDictionary");
            String code = null;
            edit_image.setImageBitmap(photo);
            nf.mkdir();
            if (i==5) {
                code="l.png";
            } else if (i==6) {
                code="a.png";
            } else if (i==7) {
                code="b.png";
            } else if (i==8) {
                code="c.png";
            } else if (i==9) {
                code="d.png";
            }
            File f = new File(Environment.getExternalStorageDirectory()+"/FateDictionary", "a"+Tool.numDecimal(number)+code);
            FileOutputStream out;
            try {
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 100, out);

                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

