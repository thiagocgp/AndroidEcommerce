package br.ufg.ecommerce;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.camerakit.CameraKitView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Camera extends Activity implements View.OnClickListener {

    private CameraKitView cameraKitView;
    private ImageButton btn_takePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        this.cameraKitView = findViewById(R.id.camera);
        this.btn_takePicture = findViewById(R.id.btn_takePicture);
        this.btn_takePicture.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        this.cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, byte[] capturedImage) {

                Bitmap bitmapImg = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapImg.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] img = stream.toByteArray();

                String filePath = getApplicationContext().getFilesDir().getPath() + "/photo.jpg";
                File savedPhoto = new File(filePath);

                try {
                    FileOutputStream outputStream = new FileOutputStream(savedPhoto);
                    outputStream.write(img);
                    outputStream.close();
                }
                catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                Camera.this.finish();
            }
        });
    }

}
