package com.digiclack.visionstoreadmin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tuanchauict.intentchooser.ImageChooserMaker;
import com.tuanchauict.intentchooser.selectphoto.ImageChooser;

import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    private static final String TAG = "AddProductActivity";
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImageChooserActivity();
            }
        });
    }

    private void startImageChooserActivity() {
        Intent intent = ImageChooserMaker.newChooser(AddProductActivity.this)
                .add(new ImageChooser(true))
                .create("Select Image");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            List<Uri> imageUris = ImageChooserMaker.getPickMultipleImageResultUris(this, data);
            for (int i=0; i<imageUris.size();i++) {
                Log.e(TAG,imageUris.get(i).toString());
            }

        }
    }
}
