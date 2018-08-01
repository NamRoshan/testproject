package com.projectsaathi.testing1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;



public class InsertActivity extends AppCompatActivity {
    EditText mEdtName,mEdtAge,mEdtPhone;
    Button mBntAdd,mBtnList;
    ImageView mImageView;
    final int REQUEST_CODE_GALLERY=999;

    public static  SqLiteHelper mSQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //create database
        mSQLiteHelper =new SqLiteHelper(this,"RECORDDB.sqlite",null,1);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR ,age  VARCHAR,phone VARCHAR,image BLOB)");

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("new Record");

        mEdtName=findViewById(R.id.edtName);
        mEdtAge=findViewById(R.id.edtAge);
        mEdtPhone=findViewById(R.id.edtPhone);
        mImageView=findViewById(R.id.imageView);
        mBntAdd=findViewById(R.id.btnAdd);
        mBtnList=findViewById(R.id.btnList);
        //select image by image view click
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        InsertActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
        mBntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mSQLiteHelper.insertData(
                            mEdtName.getText().toString().trim(),
                            mEdtAge.getText().toString().trim(),
                            mEdtPhone.getText().toString().trim(),
                            imageViewToByte(mImageView)
                    );
                    Toast.makeText(InsertActivity.this,"Added successfully",Toast.LENGTH_SHORT).show();
                    mEdtName.setText("");
                    mEdtAge.setText("");
                    mEdtPhone.setText("");
                    mImageView.setImageResource(R.drawable.addphoto);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }


        });

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[]byteArray=stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,REQUEST_CODE_GALLERY);

            }
            else{
                Toast.makeText(this,"Don't have permission to access file location",Toast.LENGTH_SHORT).show();

            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK){
            Uri imageUri=data.getData();
            if (requestCode==RESULT_OK){
                mImageView.setImageURI(imageUri);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
