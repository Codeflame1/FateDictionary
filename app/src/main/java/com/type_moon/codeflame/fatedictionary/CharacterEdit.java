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
    private Spinner edit_alignment;
    private TextInputLayout edit_introduction;
    private EditText medit_name;
    private EditText medit_height;
    private EditText medit_weight;
    private EditText medit_origo;
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
        medit_introduction = edit_introduction.getEditText();

        edit_job.setSelection(SpinnerSelect.getJobSelect(job));
        edit_stre.setSelection(SpinnerSelect.getLevel(stre));
        edit_endu.setSelection(SpinnerSelect.getLevel(endu));
        edit_agil.setSelection(SpinnerSelect.getLevel(agil));
        edit_magi.setSelection(SpinnerSelect.getLevel(magi));
        edit_luck.setSelection(SpinnerSelect.getLevel(luck));
        edit_skil.setSelection(SpinnerSelect.getLevel(skil));
        medit_name.setText(name);
        edit_sex.setSelection(SpinnerSelect.getSex(sex));
        medit_height.setText(height);
        medit_weight.setText(weight);
        medit_origo.setText(origo);
        edit_alignment.setSelection(SpinnerSelect.getAlignmengt(alignment));
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
                String name = medit_name.getText().toString().trim();
                String job = (String) edit_job.getSelectedItem();
                String sex = (String) edit_sex.getSelectedItem();
                String height = medit_height.getText().toString().trim();
                String weight = medit_weight.getText().toString().trim();
                String origo = medit_origo.getText().toString().trim();
                String alignment = (String) edit_alignment.getSelectedItem();
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
                } else if (TextUtils.isEmpty(height)) {
                    edit_height.setErrorEnabled(true);
                    edit_height.setError(getString(R.string.height) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(weight)) {
                    edit_weight.setErrorEnabled(true);
                    edit_weight.setError(getString(R.string.weight) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(origo)) {
                    edit_origo.setErrorEnabled(true);
                    edit_origo.setError(getString(R.string.origo) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(introduction)) {
                    edit_introduction.setErrorEnabled(true);
                    edit_introduction.setError(getString(R.string.introduction) + getString(R.string.text_error_empty));
                } else {
                    //调用插入方法
                    CharacterDataBase.getInstances(CharacterEdit.this).updata(id, number, name, job, sex, height, weight, origo, alignment, introduction, stre, endu, agil, magi, luck, skil);
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

