package com.example.emotion_detect;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
Button btnTakePicture,btnProcess;
ImageView imageView;
EmotionServiceClient restClient = new EmotionServiceRestClient("dc0c4014d98a421989f5932e41c3adf0");
int TAKE_PICTURE_CODE = 100;
Bitmap mbitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniviews();
    }

    private void iniviews() {
        btnProcess = findViewById(R.id.btnProcess);
        btnTakePicture = findViewById(R.id.btnTakePic);
        imageView = findViewById(R.id.imageview);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureFromGallery();
            }
        });
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processImage();
            }
        });
    }

    private void processImage() {
        //convert image to stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mbitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        //async task to process data

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE_CODE) {
            Uri selectedImageUri = data.getData();
            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(selectedImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mbitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(mbitmap);
        }
    }

    private void takePictureFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,TAKE_PICTURE_CODE);
    }
}