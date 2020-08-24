package com.will.serializabledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String FILE_NAME = "myFile_data";
    private EditText editTextName,editTextAge,editTextMath,editTextEnglish,editTextChinese;
    private Button buttonSave,buttonLoad;
    private TextView textViewGrade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextMath = findViewById(R.id.editTextMath);
        editTextEnglish = findViewById(R.id.editTextEnglish);
        editTextChinese = findViewById(R.id.editTextChinese);

        buttonSave = findViewById(R.id.buttonSave);
        buttonLoad = findViewById(R.id.buttonLoad);

        textViewGrade = findViewById(R.id.textViewGrade);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strMath = editTextMath.getText().toString().trim();
                int math = 0;
                if (!strMath.equals("") && strMath != null && !strMath.isEmpty()) {
                    math = Integer.valueOf(strMath);
                }
                String strEnglish = editTextEnglish.getText().toString().trim();
                int english = 0;
                if (!strEnglish.equals("") && strEnglish != null && !strEnglish.isEmpty()) {
                    english = Integer.valueOf(strEnglish);
                }

                String strChinese = editTextChinese.getText().toString().trim();
                int chinese = 0;
                if (!strChinese.equals("") && strChinese != null && !strChinese.isEmpty()) {
                    chinese = Integer.valueOf(strChinese);
                }
                Score score = new Score(math,english,chinese);

                String name = editTextName.getText().toString().trim();
                String strAge = editTextAge.getText().toString().trim();
                int age = 0;
                if (!strAge.equals("") && strAge != null && !strAge.isEmpty()) {
                    age = Integer.valueOf(strAge);
                }

                Student student = new Student(name,age,score);

                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput(FILE_NAME,MODE_PRIVATE));
                    outputStream.writeObject(student);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(MainActivity.this, "添加数据", Toast.LENGTH_SHORT).show();
                    editTextName.getText().clear();
                    editTextAge.getText().clear();
                    editTextChinese.getText().clear();
                    editTextEnglish.getText().clear();
                    editTextMath.getText().clear();
                    textViewGrade.setText("-");
                } catch (IOException e) {
                    Log.e(TAG, "onClick: ",e);
                }
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(openFileInput(FILE_NAME));
                    Student student = (Student) inputStream.readObject();
                    if (student != null){
                        editTextName.setText(student.getName());
                        editTextAge.setText(String.valueOf(student.getAge()));
                        editTextChinese.setText(String.valueOf(student.getScore().getChinese()));
                        editTextEnglish.setText(String.valueOf(student.getScore().getEnglish()));
                        editTextMath.setText(String.valueOf(student.getScore().getMath()));
                        textViewGrade.setText(student.getScore().getGrade());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Log.e(TAG, "onClick: ",e);
                }
            }
        });
    }
}