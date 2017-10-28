package com.vtsoriev.testmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    private String currentPath = Environment.getExternalStorageDirectory().getPath();
    private static String selectedFile;
    Button startTestActivity;
    Button openFile;
    Button moveFile;
    TextView mainDisplay;
    Switch turnOnShuffleQ;
    Switch turnOnShuffleA;
    Switch correctionOfMistakes;
    WorkWithFiles workWithFiles = new WorkWithFiles();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTestActivity = (Button) findViewById(R.id.startTestActivity);
        startTestActivity.setOnClickListener(this);
        openFile = (Button) findViewById(R.id.openFile);
        openFile.setOnClickListener(this);
        moveFile = (Button) findViewById(R.id.moveFile);
        moveFile.setOnClickListener(this);

        mainDisplay = (TextView) findViewById(R.id.mainDisplay);
        mainDisplay.setOnClickListener(this);
        turnOnShuffleQ = (Switch) findViewById(R.id.shuffleQ);
        turnOnShuffleQ.setOnClickListener(this);
        turnOnShuffleA = (Switch) findViewById(R.id.shuffleA);
        turnOnShuffleA.setOnClickListener(this);
        correctionOfMistakes = (Switch) findViewById(R.id.correctionOfMistakes);
        correctionOfMistakes.setOnClickListener(this);

        if (!(OpenFileDialog.getNameOfSelectedFile() == null)) {
            mainDisplay.setText(OpenFileDialog.getNameOfSelectedFile());
            QAActivity.startTestFromDefaultFile = false;

            startTestActivity.setEnabled(true);
            startTestActivity.setVisibility(View.VISIBLE);
        } else {
            QAActivity.startTestFromDefaultFile = true;
            mainDisplay.setText(R.string.msgForMainDisplay1);
            openFile.setEnabled(true);
            openFile.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openFile:
                final OpenFileDialog fileDialog = new OpenFileDialog(this).setFilter(".*\\.txt")
                        .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                            @Override
                            public void OnSelectedFile(String fileName) {
                                Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
                                openFile.setText(fileName);
                                startTestActivity.setEnabled(true);
                                startTestActivity.setVisibility(View.VISIBLE);
                                mainDisplay.setText(fileName);

                            }

                        });
                fileDialog.show();
                QAActivity.startTestFromDefaultFile = false;
                break;

            case R.id.startTestActivity:
                if (!(OpenFileDialog.getNameOfSelectedFile() == null)||QAActivity.startTestFromDefaultFile) {
                    Intent intent = new Intent(this, QAActivity.class);
                    startActivity(intent);
                    QuestionsForTest.shuffleQuestions = turnOnShuffleQ.isChecked();
                    QuestionsForTest.shuffleAnswers = turnOnShuffleA.isChecked();
                    QuestionsForTest.correctionOfMistakes = correctionOfMistakes.isChecked();

                    break;

                } else {
                    mainDisplay.setText("Файл не выбран!");
                }
                break;

            case R.id.moveFile:
                final OpenFileDialog theFileDialog = new OpenFileDialog(this).setFilter(".*\\.txt")
                        .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                            @Override
                            public void OnSelectedFile(String fileName) {
                                Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
                                if (workWithFiles.move(getApplicationContext(), fileName))
                                    mainDisplay.setText("Ф а й л \n" + fileName + "\nбыл перенесен в: " + WorkWithFiles.defaultFileName);
                                else
                                    mainDisplay.setText("ОШИБКА! Файл " + fileName + " не получилось перенести в директорию программы");
                            }

                        });
                theFileDialog.show();

                break;

            default:
                break;
        }
    }
}
