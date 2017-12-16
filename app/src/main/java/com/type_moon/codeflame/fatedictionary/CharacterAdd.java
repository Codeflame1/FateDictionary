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

public class CharacterAdd extends AppCompatActivity{

    private int number;
    private ImageButton add_image;
    private Spinner add_job;
    private ImageView add_jobframe;
    private TextInputLayout add_name;
    private Spinner add_sex;
    private TextInputLayout add_height;
    private TextInputLayout add_weight;
    private TextInputLayout add_origo;
    private TextInputLayout add_introduction;
    private EditText madd_name;
    private EditText madd_height;
    private EditText madd_weight;
    private EditText madd_origo;
    private Spinner add_alignment;
    private EditText madd_introduction;
    private Spinner add_stre;
    private Spinner add_endu;
    private Spinner add_agil;
    private Spinner add_magi;
    private Spinner add_luck;
    private Spinner add_skil;
    public String str1;
    private Uri imageUri;
    private static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory()+"/temp.jpg";


    private AlertDialog imagedialog;
    private static final int PHOTOBIG = 0xa0;
    private static final int PHOTOLITTLE = 0xa1;
    private static final int PHOTOB_RESULT = 0xa2;
    private static final int PHOTOL_RESULT = 0xa3;
//    public String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_characterdetail);

        Button add_confirm = findViewById(R.id.add_buttonconfirm);
        Button add_cancel = findViewById(R.id.add_buttoncancel);
        add_image = findViewById(R.id.add_image);
        add_job = findViewById(R.id.add_job);
        add_jobframe = findViewById(R.id.add_imageframe);
        add_name = findViewById(R.id.add_name);
        add_sex = findViewById(R.id.add_sex);
        add_height = findViewById(R.id.add_height);
        add_weight = findViewById(R.id.add_weight);
        add_origo = findViewById(R.id.add_origo);
        add_alignment = findViewById(R.id.add_alignment);
        add_introduction = findViewById(R.id.add_introduction);
        madd_name = add_name.getEditText();
        madd_height = add_height.getEditText();
        madd_weight = add_weight.getEditText();
        madd_origo = add_origo.getEditText();
        madd_introduction = add_introduction.getEditText();
        add_stre = findViewById(R.id.add_stre);
        add_endu = findViewById(R.id.add_endu);
        add_agil = findViewById(R.id.add_agil);
        add_magi = findViewById(R.id.add_magi);
        add_luck = findViewById(R.id.add_luck);
        add_skil = findViewById(R.id.add_skil);
        str1 = (String) add_job.getSelectedItem();
        imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));

        Cursor query = AddNumberDataBase.getInstances(CharacterAdd.this).query();
        if (query.move(1)){
            int number1 = query.getInt(query.getColumnIndex("number"));
            number = number1 + 1;
        }


        add_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str1 = (String) add_job.getSelectedItem();
                add_jobframe.setImageResource(ImageGet.getBigFrame(str1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                add_jobframe.setImageResource(ImageGet.getBigFrame(""));
            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });

        add_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拿到输入的数据
                AddNumberDataBase.getInstances(CharacterAdd.this).updata(number);
                String name = madd_name.getText().toString().trim();
                String job = (String)add_job.getSelectedItem();
                String sex = (String)add_sex.getSelectedItem();
                String height = madd_height.getText().toString().trim();
                String weight = madd_weight.getText().toString().trim();
                String origo = madd_origo.getText().toString().trim();
                String alignment = (String) add_alignment.getSelectedItem();
                String introduction = madd_introduction.getText().toString().trim();
                String stre = (String)add_stre.getSelectedItem();
                String endu = (String)add_endu.getSelectedItem();
                String agil = (String)add_agil.getSelectedItem();
                String magi = (String)add_magi.getSelectedItem();
                String luck = (String)add_luck.getSelectedItem();
                String skil = (String)add_skil.getSelectedItem();

                add_name.setErrorEnabled(false);
                add_height.setErrorEnabled(false);
                add_weight.setErrorEnabled(false);
                add_origo.setErrorEnabled(false);
                add_introduction.setErrorEnabled(false);
                if (TextUtils.isEmpty(name)) {
                    add_name.setErrorEnabled(true);
                    add_name.setError(getString(R.string.name) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(height)) {
                    add_height.setErrorEnabled(true);
                    add_height.setError(getString(R.string.height) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(weight)) {
                    add_weight.setErrorEnabled(true);
                    add_weight.setError(getString(R.string.weight) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(origo)) {
                    add_origo.setErrorEnabled(true);
                    add_origo.setError(getString(R.string.origo) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(introduction)) {
                    add_introduction.setErrorEnabled(true);
                    add_introduction.setError(getString(R.string.introduction) + getString(R.string.text_error_empty));
                } else {
                    //调用插入方法
                    CharacterDataBase.getInstances(CharacterAdd.this).insert(number, name, job, sex, height, weight, origo, alignment ,introduction, stre, endu, agil, magi, luck, skil);
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

    private void showImageDialog() {

        imagedialog = new AlertDialog.Builder(CharacterAdd.this).create();
        imagedialog.show();
        imagedialog.getWindow().setContentView(R.layout.alertdialog_image);
        imagedialog.getWindow().findViewById(R.id.dia_bigimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTOBIG);
            }
        });

        imagedialog.getWindow().findViewById(R.id.dia_littleimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTOLITTLE);
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
        }
        switch (requestCode) {
            case PHOTOBIG://如果是来自本地的
                cropBigPhoto(intent.getData());//直接裁剪图片
                break;
            case PHOTOLITTLE:
                cropLittlePhoto(intent.getData());
                break;
            case PHOTOB_RESULT:
                if (intent != null) {
                    setImageBig(intent);//设置图片框
                }
                break;
            case PHOTOL_RESULT:
                if (intent != null) {
                    setImageLittle(intent);//设置图片框
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void cropBigPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 764);
        intent.putExtra("aspectY", 1080);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 764);
        intent.putExtra("outputY", 1080);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//图像输出
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);

        startActivityForResult(intent, PHOTOB_RESULT);
    }

    public void cropLittlePhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//图像输出
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);

        startActivityForResult(intent, PHOTOL_RESULT);
    }

    private void setImageBig(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/temp.jpg");
            File nf = new File(Environment.getExternalStorageDirectory()+"/FateDictionary");
            add_image.setImageBitmap(photo);
            nf.mkdir();
            File f = new File(Environment.getExternalStorageDirectory()+"/FateDictionary", "a"+number+".png");
            FileOutputStream out = null;
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

    private void setImageLittle(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/temp.jpg");
            File nf = new File(Environment.getExternalStorageDirectory()+"/FateDictionary");
            nf.mkdir();
            File f = new File(Environment.getExternalStorageDirectory()+"/FateDictionary", "a"+number+"_little.png");

            FileOutputStream out = null;
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
