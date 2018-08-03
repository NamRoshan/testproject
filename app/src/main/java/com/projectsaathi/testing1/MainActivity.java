package com.projectsaathi.testing1;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    ListView mListView,mListView1;
    ArrayList<Model> mList1;
    ArrayList<Model> mList;
    //    RecordListAdapter1 mAdapter1=null;
//    RecordListAdapter mAdapter=null;
    List<String> strings;

    ImageView imageViewIcon;

    public static SqLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //create database
        mSQLiteHelper = new SqLiteHelper(this, "RECORDDB.sqlite", null, 1);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT ,name VARCHAR ,age  VARCHAR,phone VARCHAR,image BLOB)");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Record List");

        strings = new ArrayList<>();
        mList1 = new ArrayList<>();
        mList = new ArrayList<>();

        Cursor cursor = MainActivity.mSQLiteHelper.getData("SELECT *FROM RECORD");
        Cursor totalValues = MainActivity.mSQLiteHelper.getTotalValues();
        String total = totalValues.getString(0);
        mList1.add(new Model(1, "", "", "", null, total));

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String age = cursor.getString(2);
            String phone = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            mList.add(new Model(id, name, age, phone, image, total));

        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(adapter);


        if (mList.size() == 0) {
            Toast.makeText(this, "no record found..", Toast.LENGTH_SHORT).show();
        }
//
        if (mList.size() == 0) {
            Toast.makeText(this, "no record found..", Toast.LENGTH_SHORT).show();
        }
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
//                //alert diaglog
//                final CharSequence[]items={"Update","Delete"};
//                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
//                dialog.setTitle("Choose an action");
//                dialog.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (i==0){
//                            //update
//                            Cursor c=MainActivity.mSQLiteHelper.getData("SELECT id FROM RECORD");
//                            ArrayList<Integer> arrID=new ArrayList<Integer>();
//                            while (c.moveToNext()){
//                                arrID.add(c.getInt(0));
//                            }
//                            //show update dialog
//                            showDialogUpdate(MainActivity.this,arrID.get(position));
//                        }
//                        if(i==1){
//                            //delete
//                            Cursor c=MainActivity.mSQLiteHelper.getData("SELECT id FROM RECORD");
//                            ArrayList<Integer> arrID=new ArrayList<Integer>();
//                            while(c.moveToNext()){
//                                arrID.add(c.getInt(0));
//                            }
//                            showDialogDelete(arrID.get(position));
//                        }
//                    }
//                });
//                dialog.show();
//                return true;
//            }
//        });
//    }
//
//
//    private void showDialogDelete(final int idRecord) {
//        AlertDialog.Builder dialogDelete=new AlertDialog.Builder(MainActivity.this);
//        dialogDelete.setTitle("Warning");
//        dialogDelete.setMessage("Are you sure to delete ?");
//        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                try {
//                    MainActivity.mSQLiteHelper.deleteData(idRecord);
//                    Cursor totalValues = MainActivity.mSQLiteHelper.getTotalValues();
//                    mList1.clear();
//                    String total=totalValues.getString(0);
//                    mList1.add(new Model1(total));
//                    mAdapter1.notifyDataSetChanged();
//
//                    Toast.makeText(MainActivity.this,"Delete Successfully",Toast.LENGTH_SHORT).show();
//
//                }
//                catch (Exception e){
//                    Log.e("error",e.getMessage());
//                }
//                updateRecordList();
//            }
//        });
//        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        dialogDelete.show();
//    }
//
//    private void showDialogUpdate(final Activity activity, final int position){
//        final Dialog dialog=new Dialog(activity);
//        dialog.setContentView(R.layout.update_dialog);
//        dialog.setTitle("Update");
//        imageViewIcon=dialog.findViewById(R.id.imageViewRecord);
//        final EditText edtName=dialog.findViewById(R.id.edtName);
//        final EditText edtAge=dialog.findViewById(R.id.edtAge);
//        final EditText edtPhone=dialog.findViewById(R.id.edtPhone);
//        final Button btnUpdate=dialog.findViewById(R.id.btnUpdate);
//
//        //get all data
//        Cursor cursor=MainActivity.mSQLiteHelper.getData("SELECT *FROM RECORD WHERE id="+position);
//        mList.clear();
//        while (cursor.moveToNext()){
//            int id=cursor.getInt(0);
//            String name=cursor.getString(1);
//            edtName.setText(name);
//            String age =cursor.getString(2);
//            edtAge.setText(age);
//            String phone=cursor.getString(3);
//            edtPhone.setText(phone);
//            byte[] image=cursor.getBlob(4);
//            imageViewIcon.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
//
//            mList.add(new Model(id,name,age,phone,image));
//
//        }
//
//        //set width of dialog
//        int width=(int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
//        //set height of dailog
//        int height=(int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);
//        dialog.getWindow().setLayout(width,height);
//        dialog.show();
//
//        //in update dialog click image view to update image
//        imageViewIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityCompat.requestPermissions(
//                        MainActivity.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        888);
//            }
//
//        });
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    MainActivity.mSQLiteHelper.updateData(
//                            edtName.getText().toString().trim(),
//                            edtAge.getText().toString().trim(),
//                            edtPhone.getText().toString().trim(),
//                            MainActivity.imageViewToByte(imageViewIcon),
//                            position
//                    );
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(),"update successfully",Toast.LENGTH_SHORT).show();
//
//                }
//                catch (Exception error){
//                    Log.e("Update error",error.getMessage());
//                }
//                updateRecordList();
//            }
//
//
//        });
//    }
//    private void updateRecordList() {
//        Cursor cursor=MainActivity.mSQLiteHelper.getData("SELECT *FROM RECORD");
//        Cursor totalValues = MainActivity.mSQLiteHelper.getTotalValues();
//        mList1.clear();
//        mList.clear();
//        while (cursor.moveToNext()){
//            int id=cursor.getInt(0);
//            String name=cursor.getString(1);
//            String age=cursor.getString(2);
//            String phone=cursor.getString(3);
//            byte[] image=cursor.getBlob(4);
//            mList.add(new Model(id,name,age,phone,image));
//
//        }
//        String total=totalValues.getString(0);
//        mList1.add(new Model1(total));
//        mAdapter.notifyDataSetChanged();
//        mAdapter1.notifyDataSetChanged();
//    }
//
//    public static byte[] imageViewToByte(ImageView image) {
//        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream =new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
//        byte[]byteArray=stream.toByteArray();
//        return byteArray;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode==888){
//            if(grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent,888);
//
//            }
//            else{
//                Toast.makeText(this,"Don't have permission to access file location",Toast.LENGTH_SHORT).show();
//
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (resultCode==888 && resultCode==RESULT_OK){
//            Uri imageUri=data.getData();
//            if (requestCode==RESULT_OK){
//                imageViewIcon.setImageURI(imageUri);
//            }
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    }
}
 