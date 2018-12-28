package com.example.administrator.weixin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weixin.data.Data;
import com.example.administrator.weixin.file.FileUtilcll;

import java.io.File;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_3 extends Fragment {
    private TextView username;
    private TextView nickname;
    private ImageView imageView;
    private LinearLayout music;
    private View view;

    private static final int CAMERA_RESULT_CODE = 0;// 拍照
    private static final int CROP_RESULT_CODE = 1;// 裁剪
    private static final int SCAN_OPEN_PHONE = 2;// 相册

    public Fragment_3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_3, container, false);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.loading_anim);
        
        initObject();
        initValue();
        
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AnimActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MusicActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initObject() {
        this.username = (TextView)view.findViewById(R.id.username);
        this.nickname = (TextView)view.findViewById(R.id.nickname);
        this.imageView = (ImageView)view.findViewById(R.id.myImage);
        this.music = (LinearLayout)view.findViewById(R.id.music);
    }

    private void initValue() {
        this.username.setText(Data.user.getUserId());
        this.nickname.setText(Data.user.getNickname());
    }

    /**
     * 打开图库
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SCAN_OPEN_PHONE);
    }

    /**
     * 裁剪图片
     *
     * @param data
     */
    private void cropPhoto(Uri data) {
        if (data == null) {
            return;
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(data, "image/*");

        // 开启裁剪：打开的Intent所显示的View可裁剪
        cropIntent.putExtra("crop", "true");
        // 裁剪宽高比
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // 裁剪输出大小
        cropIntent.putExtra("outputX", 320);
        cropIntent.putExtra("outputY", 320);
        cropIntent.putExtra("scale", true);
        /**
         * return-data
         * 这个属性决定我们在 onActivityResult 中接收到的是什么数据，
         * 如果设置为true 那么data将会返回一个bitmap
         * 如果设置为false，则会将图片保存到本地并将对应的uri返回，当然这个uri得有我们自己设定。
         * 系统裁剪完成后将会将裁剪完成的图片保存在我们所这设定这个uri地址上。我们只需要在裁剪完成后直接调用该uri来设置图片，就可以了。
         */
        cropIntent.putExtra("return-data", true);
        // 当 return-data 为 false 的时候需要设置这句
   //     cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // 图片输出格式
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 头像识别 会启动系统的拍照时人脸识别
        cropIntent.putExtra("noFaceDetection", true);
        startActivityForResult(cropIntent, CROP_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_RESULT_CODE:  //拍照返回的code
                    File tempFile = new File(Environment.getExternalStorageDirectory(), "WEIXINpaizhao"+new Date().getTime());
                    System.out.println(tempFile.getAbsolutePath());
                    cropPhoto(Uri.fromFile(tempFile));
                    break;

                case SCAN_OPEN_PHONE:   //选择图片后的code
                    cropPhoto(data.getData()); //进行裁剪
                    break;

                case CROP_RESULT_CODE:  //裁剪完成的code
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            Bitmap bitmap = bundle.getParcelable("data");
                            imageView.setImageBitmap(bitmap);
                            // 把裁剪后的图片保存至本地 返回路径
                            String urlpath = FileUtilcll.saveFile(getContext(), "crop.jpg", bitmap);
                        }
                    }
                    break;
            }
        }
    }


}
