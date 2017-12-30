package com.type_moon.codeflame.fatedictionary.Character;


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

import com.type_moon.codeflame.fatedictionary.AddNumberDataBase;
import com.type_moon.codeflame.fatedictionary.Tool.ImageGet;
import com.type_moon.codeflame.fatedictionary.R;
import com.type_moon.codeflame.fatedictionary.Tool.Tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CharacterAdd extends AppCompatActivity{

    private int number;
    private ImageButton m_image;
    private Spinner m_job;
    private ImageView m_jobframe;
    private TextInputLayout m_name;
    private Spinner m_sex;
    private TextInputLayout m_height;
    private TextInputLayout m_weight;
    private TextInputLayout m_origo;
    private TextInputLayout m_resource;
    private TextInputLayout m_introduction;
    private EditText ed_name;
    private EditText ed_height;
    private EditText ed_weight;
    private EditText ed_origo;
    private EditText ed_resource;
    private Spinner m_alignment;
    private EditText ed_introduction;
    private Spinner m_stre;
    private Spinner m_endu;
    private Spinner m_agil;
    private Spinner m_magi;
    private Spinner m_luck;
    private Spinner m_skil;
    public String str1;
    private Uri imageUri;
    private static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory()+"/temp.jpg";
    private int flag = 0;
    private String LOCATION = Environment.getExternalStorageDirectory()+"/FateDictionary/a";
    private String[] w = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y"};
    private File file;

    private AlertDialog imagedialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_characterdetail);

        Button m_confirm = findViewById(R.id.add_buttonconfirm);
        Button m_cancel = findViewById(R.id.add_buttoncancel);
        ImageButton change = findViewById(R.id.add_change);
        m_image = findViewById(R.id.add_image);
        m_job = findViewById(R.id.add_job);
        m_jobframe = findViewById(R.id.add_imageframe);
        m_name = findViewById(R.id.add_name);
        m_sex = findViewById(R.id.add_sex);
        m_height = findViewById(R.id.add_height);
        m_weight = findViewById(R.id.add_weight);
        m_origo = findViewById(R.id.add_origo);
        m_resource = findViewById(R.id.add_resource);
        m_alignment = findViewById(R.id.add_alignment);
        m_introduction = findViewById(R.id.add_introduction);
        ed_name = m_name.getEditText();
        ed_height = m_height.getEditText();
        ed_weight = m_weight.getEditText();
        ed_origo = m_origo.getEditText();
        ed_resource = m_resource.getEditText();
        ed_introduction = m_introduction.getEditText();
        m_stre = findViewById(R.id.add_stre);
        m_endu = findViewById(R.id.add_endu);
        m_agil = findViewById(R.id.add_agil);
        m_magi = findViewById(R.id.add_magi);
        m_luck = findViewById(R.id.add_luck);
        m_skil = findViewById(R.id.add_skil);
        str1 = (String) m_job.getSelectedItem();
        imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag++;
                file = new File(LOCATION+ Tool.numDecimal(number)+w[flag]+".png");
                if (!file.exists()) {
                    flag=0;
                }
                m_image.setImageBitmap(BitmapFactory.decodeFile(LOCATION+Tool.numDecimal(number)+w[flag]+".png"));
            }
        });

        Cursor query = AddNumberDataBase.getInstances(CharacterAdd.this).query();
        if (query.move(1)){
            int number1 = query.getInt(query.getColumnIndex("number"));
            number = number1 + 1;
        }


        m_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str1 = (String) m_job.getSelectedItem();
                m_jobframe.setImageResource(ImageGet.getBigFrame(str1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                m_jobframe.setImageResource(ImageGet.getBigFrame(""));
            }
        });

        m_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });

        m_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拿到输入的数据
                String hei = ed_height.getText().toString().trim();
                String wei = ed_weight.getText().toString().trim();
                AddNumberDataBase.getInstances(CharacterAdd.this).updata(number);
                String name = ed_name.getText().toString().trim();
                int job = (int)m_job.getSelectedItemId();
                int sex = (int)m_sex.getSelectedItemId();
                int height = Integer.parseInt(hei);
                int weight = Integer.parseInt(wei);
                String origo = ed_origo.getText().toString().trim();
                String resource = ed_resource.getText().toString().trim();
                int alignment = (int) m_alignment.getSelectedItemId();
                String introduction = ed_introduction.getText().toString().trim();
                String stre = (String)m_stre.getSelectedItem();
                String endu = (String)m_endu.getSelectedItem();
                String agil = (String)m_agil.getSelectedItem();
                String magi = (String)m_magi.getSelectedItem();
                String luck = (String)m_luck.getSelectedItem();
                String skil = (String)m_skil.getSelectedItem();

                m_name.setErrorEnabled(false);
                m_height.setErrorEnabled(false);
                m_weight.setErrorEnabled(false);
                m_origo.setErrorEnabled(false);
                m_introduction.setErrorEnabled(false);
                if (TextUtils.isEmpty(name)) {
                    m_name.setErrorEnabled(true);
                    m_name.setError(getString(R.string.name) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(hei)) {
                    m_height.setErrorEnabled(true);
                    m_height.setError(getString(R.string.height) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(wei)) {
                    m_weight.setErrorEnabled(true);
                    m_weight.setError(getString(R.string.weight) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(origo)) {
                    m_origo.setErrorEnabled(true);
                    m_origo.setError(getString(R.string.origo) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(resource)) {
                    m_resource.setErrorEnabled(true);
                    m_resource.setError(getString(R.string.resource) + getString(R.string.text_error_empty));
                } else if (TextUtils.isEmpty(introduction)) {
                    m_introduction.setErrorEnabled(true);
                    m_introduction.setError(getString(R.string.introduction) + getString(R.string.text_error_empty));
                } else {
                    //调用插入方法
                    CharacterDataBase.getInstances(CharacterAdd.this).insert(number, name, job, sex, height, weight, origo, alignment , resource, introduction, stre, endu, agil, magi, luck, skil);
                    finish();
                }
            }
        });

        m_cancel.setOnClickListener(new View.OnClickListener() {
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
        imagedialog.getWindow().findViewById(R.id.dia_bigimagechange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
            }
        });

        imagedialog.getWindow().findViewById(R.id.dia_bigimageadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });

        imagedialog.getWindow().findViewById(R.id.dia_bigimagedelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 3);
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
        } if (requestCode <=2) {
            cropPhoto(intent.getData(),requestCode);
        } else if (requestCode == 4) {
            if (intent != null) {
                setImage(intent, 26);//设置图片框
            }
        } else if (requestCode == 5) {
            if (intent != null) {
                setImage(intent, flag);
                m_image.setImageBitmap(BitmapFactory.decodeFile(LOCATION+Tool.numDecimal(number)+w[flag]+".png"));//设置图片框
            }
        } else if (requestCode == 6) {
            if (intent != null) {
                int i = 0;
                file = new File(LOCATION+Tool.numDecimal(number)+w[i]+".png");
                while (file.exists()) {
                    i++;
                }
                i++;
                setImage(intent, i);//设置图片框
            }
        } else if (requestCode == 3) {
            if (intent != null) {
                int i = flag;
                file = new File(LOCATION+Tool.numDecimal(number)+w[i]+".png");
                new File(LOCATION+Tool.numDecimal(number)+w[i]+".png").delete();
                i++;
                while (file.exists()) {
                    file.renameTo(new File(LOCATION+Tool.numDecimal(number)+w[i-1]+".png"));
                    i++;
                }
                m_image.setImageBitmap(BitmapFactory.decodeFile(LOCATION+Tool.numDecimal(number)+w[flag]+".png"));
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
        i+=4;
        startActivityForResult(intent, i);
    }

    private void setImage(Intent intent, int i) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            File photo = new File(IMAGE_FILE_LOCATION);
            File nf = new File(Environment.getExternalStorageDirectory()+"/FateDictionary");
            nf.mkdir();
            File f;
            if (i==26) {
                f = new File(Environment.getExternalStorageDirectory()+"/FateDictionary", "a"+Tool.numDecimal(number)+"z.png");
            } else {
                f = new File(Environment.getExternalStorageDirectory()+"/FateDictionary", "a"+Tool.numDecimal(number)+w[i]+".png");
            }
            FileOutputStream out;
            try {
                out = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(photo);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

