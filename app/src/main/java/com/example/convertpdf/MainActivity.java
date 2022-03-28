package com.example.convertpdf;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText myEditText;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myEditText = findViewById(R.id.editText);
        button1 = findViewById(R.id.button);
        myEditText = findViewById(R.id.editText);
        myEditText = findViewById(R.id.editText); myEditText = findViewById(R.id.editText);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMyPDF(view);
            }
        });
    }


    public void createMyPDF(View view){

        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String myString = myEditText.getText().toString();
        int x = 12, y=25;

        for (String line:myString.split("\n")){
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y+=myPaint.descent()-myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/myPDFFile.pdf";
        File myFile = new File(myFilePath);
        try {
            myPdfDocument.writeTo(new FileOutputStream(myFile));
        }
        catch (Exception e){
            e.printStackTrace();
            myEditText.setText("ERROR");
        }

        myPdfDocument.close();


    }
}