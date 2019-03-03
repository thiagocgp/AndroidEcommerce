package br.ufg.ecommerce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

        this.imageView = findViewById(R.id.iv_photoImg);

        this.btnCamera = findViewById(R.id.btn_camera);
        this.btnCamera.setOnClickListener(this);

        if (this.product == null){
            this.product = new Product();
            this.product.setUid(UUID.randomUUID().toString());
        }

        this.btnSubmit = findViewById(R.id.btn_submit);
        this.btnSubmit.setOnClickListener(this);

        initFirebase();

        String filePath = getApplicationContext().getFilesDir().getPath() + "/photo.bmp";
        File file = new File(filePath);
        file.delete();
    }

    @Override
    protected void onResume(){
        super.onResume();
        String filePath = getApplicationContext().getFilesDir().getPath() + "/photo.bmp";
        File file = new File(filePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte aux[] = new byte[(int)file.length()];

            while ((nRead = fileInputStream.read(aux, 0, aux.length)) != -1) {
                buffer.write(aux, 0, nRead);
            }
            this.img = buffer.toByteArray();
            Bitmap bitmapImg = BitmapFactory.decodeByteArray(this.img, 0, this.img.length);
            this.imageView.setImageBitmap(bitmapImg);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera: {
                Intent intent = new Intent(this, Camera.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_submit: {
                getFieldsValues();
                databaseReference.child(this.product.getUid()).setValue(this.product);
                Toast.makeText(this,"Produto Cadastrado!",Toast.LENGTH_LONG).show();
                clearFields();
                String filePath = getApplicationContext().getFilesDir().getPath() + "/photo.bmp";
                File file = new File(filePath);
                file.delete();
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

        ArrayList<Integer> imgList = new ArrayList<>();
        for (int i = 0; i < this.img.length; i++){
            imgList.add((int)this.img[i]);
        }
        this.product.setImage(imgList);
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
        String filePath = getApplicationContext().getFilesDir().getPath() + "/photo.bmp";
        File file = new File(filePath);
        file.delete();
        this.finish();
    }


}
