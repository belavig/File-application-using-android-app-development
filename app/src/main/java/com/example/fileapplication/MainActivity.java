package com.example.fileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2, b3;
    EditText t1;
    boolean fileCreated=false;
    String createFileName=null;
    String previousFileName=null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.btnCreate);
        b1.setOnClickListener(this);
        b2 = (Button)findViewById(R.id.btnOpen);
        b2.setOnClickListener(this);
        b3 = (Button)findViewById(R.id.btnSave);
        b3.setOnClickListener(this);
        t1 = (EditText)findViewById(R.id.txtResult);
    }

    public void writeToFile(String filename,String content)
    {
        try {
            FileOutputStream fos= new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/"+filename);
            fos.write(content.getBytes());
            fos.close();
        }
        catch(Exception e){
            Toast.makeText(getBaseContext(),"Write error"+e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    public String readFromFile(String filename)
    {
        try {
            FileInputStream fis= new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/"+filename);
            byte[] data= new byte[fis.available()];
            fis.read(data);
            t1.setText(data.toString());
            fis.close();
            return new String(data);
        }
        catch(Exception e)
        {
            Toast.makeText(getBaseContext(),"Read error"+e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }
    public void onClick(View v)
    {
        if(v.equals(b1))
        {
            createFileName="pqr.txt";
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/"+createFileName);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    t1.setText(f.getAbsolutePath().toString());
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(),"Create error",
                            Toast.LENGTH_LONG).show();
                }
            }
            fileCreated=true;t1.setText(f.getAbsolutePath().toString());
        }

 else if(v.equals(b2))
    {
        if(previousFileName!=null){
            String data= readFromFile(previousFileName);
            t1.setText(data);
        }
        else
            t1.setText(previousFileName);
    }
    else if(v.equals(b3))
    {
        if(fileCreated){
            writeToFile(createFileName,t1.getText().toString());
            t1.setText("");
            previousFileName=createFileName;
            createFileName=null;
            Toast.makeText(getBaseContext(),"Data saved", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getBaseContext(),"first create a file",
                    Toast.LENGTH_LONG).show();
        }
    }
    }
}