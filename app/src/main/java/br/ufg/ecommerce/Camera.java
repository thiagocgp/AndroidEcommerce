package br.ufg.ecommerce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.camerakit.CameraKitView;

import java.io.File;
import java.io.FileOutputStream;

public class Camera extends Activity implements View.OnClickListener, CameraKitView.ImageCallback {

    private CameraKitView cameraKitView;
    private ImageButton btn_takePicture;
    private byte[] img;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        this.cameraKitView = findViewById(R.id.camera);
        this.btn_takePicture = findViewById(R.id.btn_takePicture);
        this.btn_takePicture.setOnClickListener(this);
        this.intent = getIntent();
        Log.e("","C_CREATE");
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
        Log.e("","C_START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
        Log.e("","C_RESUME");
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
        Log.e("","C_PAUSE");
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
        Log.e("","C_STOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("","C_DESTROY");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        this.cameraKitView.captureImage(this);
    }

    @Override
    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
        this.img = capturedImage;
        closeCamera();
    }

    public void closeCamera(){
        Intent i = new Intent(this, Register.class);
        i.putExtra("PRODUCT_IMAGE", this.img);
        //setResult(0, i);
        //finish();
        startActivity(i);
    }
}
