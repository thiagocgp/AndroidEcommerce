package br.ufg.ecommerce;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductDetails extends AppCompatActivity {

    private StorageReference mStorageRef;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Product product;
    private TextView txtProductName;
    private TextView txtProductPrice;
    private TextView txtProductDescription;
    private TextView txtContactName;
    private TextView txtContactPhone;
    private ImageView ivProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar_product_details);
        toolbar.setTitle("Ecommerce");
        setSupportActionBar(toolbar);

        initFirebase();

        this.product = (Product) getIntent().getSerializableExtra("PRODUCT");

        setFields();
    }

    private void setFields(){
        final long maxDownloadSizeBytes = 1024 * 1024;
        mStorageRef.child("images/" + product.getUid() + ".jpg").getBytes(maxDownloadSizeBytes).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ivProductImage = findViewById(R.id.iv_photoImg_details);
                ivProductImage.setImageBitmap(bmp);
            }
        });
        txtProductName = findViewById(R.id.txt_productName_details);
        txtProductName.setText(product.getName());
        txtProductPrice = findViewById(R.id.txt_productPrice_details);
        txtProductPrice.setText(product.getPrice()+"");
        txtProductDescription = findViewById(R.id.txt_productDescription_details);
        txtProductDescription.setText(product.getDescription());
        txtContactName = findViewById(R.id.txt_contactName_details);
        txtContactName.setText(product.getContactName());
        txtContactPhone = findViewById(R.id.txt_contactPhone_details);
        txtContactPhone.setText(product.getContactTel());
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        this.mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.product_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.btn_delete){
            databaseReference.child(product.getUid()).removeValue();
            mStorageRef.child("images/" + product.getUid() + ".jpg").delete();
            Toast.makeText(this,"Produto apagado!",Toast.LENGTH_LONG).show();
            ProductDetails.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
