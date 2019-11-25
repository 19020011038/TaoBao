package com.example.taobao;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class main extends AppCompatActivity implements View.OnClickListener {
    private TextView mText_show_nickname;
    private TextView mText_show_location;
    private TextView mText_show_introduction;
    private Button mBtn_jump_shop;
    private Button mBtn_jump_list;
    private Button mBtn_jump_edit_introduction;
    private Button mBtn_jump_login;
    private Button mBtn_change_password;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();
        mBtn_jump_shop.setOnClickListener(this);
        mBtn_jump_list.setOnClickListener(this);
        mBtn_jump_edit_introduction.setOnClickListener(this);
        mBtn_jump_login.setOnClickListener(this);
        mBtn_change_password.setOnClickListener(this);
        show();
        //拍照作为头像
        Button take_photo = (Button)findViewById(R.id.take_photo);
        picture = (ImageView)findViewById(R.id.picture);


        Intent intent = getIntent();
        String id_user = intent.getStringExtra("id_user");
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(main.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        Cursor cursor = database.query("user", new String[]{"picture", "id_user"}, "id_user=?", new String[]{id_user}, null, null, null);
        if (cursor.moveToFirst()) {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("picture"));//Ctrl+P
            Bitmap bitmap = getBitmapFromByte(blob);
            picture.setImageBitmap(bitmap);
        }
        cursor.close();//游标关闭!!!!
        database.close();


        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建File对象，用于存储拍照后图片
                File outputImage = new File(getExternalCacheDir(),
                        "output_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(main.this,
                            "com.example.taobao.fileprovider",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        //从相册选择
        Button chooseFromAlbum = (Button)findViewById(R.id.choose_from_album);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(main.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(main.this,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
                }else {
                    openAlbum();
                }
            }
        });
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"您拒绝了请求",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if(Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        Intent intent = getIntent();
                        String id_user = intent.getStringExtra("id_user");
                        //将拍摄的图片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Bitmap compress_bitmap = compressImage(bitmap);
                        byte[] blob = getBitmapByte(compress_bitmap);
                        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(main.this);
                        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("picture", blob);//第一个"name" 是字段名字  第二个是对应字段的数据
                        database.update("user", values, "id_user=?", new String[]{id_user});
                        database.close();
                        picture.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if(imagePath != null){
            Intent intent = getIntent();
            String id_user = intent.getStringExtra("id_user");
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            byte[] blob = getBitmapByte(bitmap);
            MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(main.this);
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("picture", blob);//第一个"name" 是字段名字  第二个是对应字段的数据
            database.update("user", values, "id_user=?", new String[]{id_user});
            database.close();
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"获取图片失败！",Toast.LENGTH_SHORT).show();
        }
    }

    private void initUI() {
        mText_show_nickname = findViewById(R.id.show_nickname);
        mText_show_location = findViewById(R.id.show_location);
        mText_show_introduction = findViewById(R.id.show_introduction);
        mBtn_jump_shop = findViewById(R.id.jump_shop);
        mBtn_jump_list = findViewById(R.id.jump_list);
        mBtn_jump_edit_introduction = findViewById(R.id.jump_edit_introduction);
        mBtn_jump_login = findViewById(R.id.jump_login);
        mBtn_change_password = findViewById(R.id.change_password);
    }

    private void show() {
        Intent intent = getIntent();
        String id_user = intent.getStringExtra("id_user");
        MyDataBaseHelper dataBaseHelper = new MyDataBaseHelper(main.this);
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

        Cursor cursor = database.query("user", new String[]{"nickname", "location","introduction","id_user"}, "id_user=?",new String[]{id_user}, null, null, null);

        if (cursor.moveToFirst()) {
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));//Ctrl+P

            mText_show_nickname.setText(nickname);
            mText_show_location.setText(location);
            mText_show_introduction.setText(introduction);
        }
        else{}

        cursor.close();//游标关闭!!!!
        database.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.jump_shop:
                Intent jump1 = new Intent(main.this,shop.class);
                Intent intent1 = getIntent();
                String id1 = intent1.getStringExtra("id_user");
                Bundle bundle1 = new Bundle();
                bundle1.putString("id1",id1);
                jump1.putExtras(bundle1);
                startActivity(jump1);
                finish();
                break;

            case R.id.jump_list:
                Intent jump2 = new Intent(main.this,list.class);
                Intent intent2 = getIntent();
                String id2 = intent2.getStringExtra("id_user");
                Bundle bundle2 = new Bundle();
                bundle2.putString("id2",id2);
                jump2.putExtras(bundle2);
                startActivity(jump2);
                finish();
                break;
            case R.id.jump_edit_introduction:
                Intent jump3 = new Intent(main.this,edit_introduction.class);
                Intent intent3 = getIntent();
                String id3 = intent3.getStringExtra("id_user");
                Bundle bundle3 = new Bundle();
                bundle3.putString("id3",id3);
                jump3.putExtras(bundle3);
                startActivity(jump3);
                finish();
                break;
            case R.id.jump_login:
                Intent jump4 = new Intent(main.this,login.class);
                startActivity(jump4);
                finish();
                break;
            case R.id.change_password:
                Intent intent5 = getIntent();
                String id5 = intent5.getStringExtra("id_user");
                Intent jump5 = new Intent(main.this,change_password.class);
                jump5.putExtra("id5",id5);
                startActivity(jump5);
                finish();
                break;


        }
    }
    public byte[] getBitmapByte(Bitmap bitmap){   //将bitmap转化为byte[]类型也就是转化为二进制
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    public Bitmap getBitmapFromByte(byte[] temp){   //将二进制转化为bitmap
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


}
