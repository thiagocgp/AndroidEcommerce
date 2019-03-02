package br.ufg.ecommerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.camerakit.CameraKitView;

public class Camera extends Activity implements View.OnClickListener {

    private CameraKitView cameraKitView;
    private ImageButton btn_takePicture;
    private byte[] img;

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
            public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                Intent i = new Intent();
                i.putExtra("PRODUCT_IMAGE", bytes);
                Camera.this.setResult(0, i);
                Camera.this.finish();
            }
        });
    }

}
