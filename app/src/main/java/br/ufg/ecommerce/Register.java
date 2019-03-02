package br.ufg.ecommerce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class Register extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_CODE = 12;
    private ImageButton btnCamera;
    private Button btnSubmit;
    private Product product;
    private ImageView imageView;
    private byte[] img;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.btnCamera = findViewById(R.id.btn_camera);
        this.btnCamera.setOnClickListener(this);

        if (this.product == null){
            this.product = new Product();
            this.product.setUid(UUID.randomUUID().toString());
        }

        this.btnSubmit = findViewById(R.id.btn_submit);
        this.btnSubmit.setOnClickListener(this);

        initFirebase();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == 0){
                imageView = findViewById(R.id.iv_photoImg);
                byte[] capturedImage = data.getByteArrayExtra("PRODUCT_IMAGE");
                if (capturedImage != null){
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.length);
                    imageView.setImageBitmap(imageBitmap);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera: {
                Intent intent = new Intent(this, Camera.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            }
            case R.id.btn_submit: {
                getFieldsValues();
                databaseReference.child(this.product.getUid()).setValue(this.product);
                Toast.makeText(this,"Produto Cadastrado!",Toast.LENGTH_LONG).show();
                clearFields();
                Register.this.finish();
                break;
            }
        }
    }

    private void getFieldsValues(){
        TextView textView = findViewById(R.id.txt_productName);
        this.product.setName(textView.getText().toString());

        textView = findViewById(R.id.txt_productDescription);
        this.product.setDescription(textView.getText().toString());

        textView = findViewById(R.id.txt_productPrice);
        this.product.setPrice(Double.parseDouble(textView.getText().toString()));

        textView = findViewById(R.id.txt_contactName);
        this.product.setContactName(textView.getText().toString());

        textView = findViewById(R.id.txt_contactPhone);
        this.product.setContactTel(textView.getText().toString());

        this.product.setImage(this.img);
    }

    private void clearFields(){
        TextView textView = findViewById(R.id.txt_productName);
        textView.setText("");

        textView = findViewById(R.id.txt_productDescription);
        textView.setText("");

        textView = findViewById(R.id.txt_productPrice);
        textView.setText("");

        textView = findViewById(R.id.txt_contactName);
        textView.setText("");

        textView = findViewById(R.id.txt_contactPhone);
        textView.setText("");
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

}
