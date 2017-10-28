package com.vtsoriev.testmeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class QAActivity extends AppCompatActivity implements OnClickListener {
    private TextView questionDisplay;
    private CheckBox answerDisplay1;
    private CheckBox answerDisplay2;
    private CheckBox answerDisplay3;
    private CheckBox answerDisplay4;
    private CheckBox answerDisplay5;
    private CheckBox answerDisplay6;
    private CheckBox answerDisplay7;
    private ProgressBar progressTest;
    TextView tV;
    Button buttonReply;
    Button buttonNext;


    //static int i = 0;
    static boolean startTestFromDefaultFile;

    private Model qAforDisplays = new Model();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);

        answerDisplay1 = (CheckBox) findViewById(R.id.answerDisplay1);
        answerDisplay2 = (CheckBox) findViewById(R.id.answerDisplay2);
        answerDisplay3 = (CheckBox) findViewById(R.id.answerDisplay3);
        answerDisplay4 = (CheckBox) findViewById(R.id.answerDisplay4);
        answerDisplay5 = (CheckBox) findViewById(R.id.answerDisplay5);
        answerDisplay6 = (CheckBox) findViewById(R.id.answerDisplay6);
        answerDisplay7 = (CheckBox) findViewById(R.id.answerDisplay7);
        progressTest = (ProgressBar) findViewById(R.id.progressBar);
        tV = (TextView) findViewById(R.id.tV);
        questionDisplay = (TextView) findViewById(R.id.questionDisplay);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonReply = (Button) findViewById(R.id.buttonReply);
        //buttonChoice = (Button) findViewById(R.id.buttonChoice);

        buttonNext.setText("I SEE, LET'S GO");
        questionDisplay.setText(qAforDisplays.getInitialTestText());
        progressTest.setMax(qAforDisplays.getNumberOfQuestions());

        answerDisplay1.setVisibility(View.INVISIBLE);
        answerDisplay2.setVisibility(View.INVISIBLE);
        answerDisplay3.setVisibility(View.INVISIBLE);
        answerDisplay4.setVisibility(View.INVISIBLE);
        answerDisplay5.setVisibility(View.INVISIBLE);
        answerDisplay6.setVisibility(View.INVISIBLE);
        answerDisplay7.setVisibility(View.INVISIBLE);

        tV.setVisibility(View.INVISIBLE);
        buttonReply.setVisibility(View.INVISIBLE);

        answerDisplay1.setOnClickListener(this);
        answerDisplay2.setOnClickListener(this);
        answerDisplay3.setOnClickListener(this);
        answerDisplay4.setOnClickListener(this);
        answerDisplay5.setOnClickListener(this);
        answerDisplay6.setOnClickListener(this);
        answerDisplay7.setOnClickListener(this);
        buttonReply.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        progressTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonReply: {

                if (areAnyAnswersSelected()) {
                    disableAllAnswerDisplays();
                    buttonReply.setVisibility(View.INVISIBLE);
                    buttonNext.setVisibility(View.VISIBLE);
                    buttonNext.setEnabled(true);
                    qAforDisplays.checkAnswers();
                    markAnswerDisplay();
                    tV.setText(qAforDisplays.getInfoForTV());

                    break;



                } else {
                    tV.setText("Вы не ответили!");
                    buttonNext.setEnabled(false);
                    break;

                }

            }//end of case R.id.buttonCurrent

            case R.id.buttonNext: {
                if (qAforDisplays.isAllWell()) {
                    buttonNext.setText("GO ON");
                    qAforDisplays.letsGo();
                    refreshView();
                    showQuestionAndAnswers();

                } else {
                    OpenFileDialog.setSelectedFileName(null);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                }


            }//end of case R.id.buttonNext

        }//end switch (v.getId())


        switch (v.getId()) {
            case R.id.answerDisplay1: {
                if (answerDisplay1.isChecked()) {
                    qAforDisplays.markAnswerAsSelected(1);
                } else {
                    qAforDisplays.markAnswerAsUnselected(1);
                }
                break;
            }
            case R.id.answerDisplay2: {
                if (answerDisplay2.isChecked()) {
                    qAforDisplays.markAnswerAsSelected(2);
                } else {
                    qAforDisplays.markAnswerAsUnselected(2);
                }
                break;
            }
            case R.id.answerDisplay3: {
                if (answerDisplay3.isChecked()) {
                    qAforDisplays.markAnswerAsSelected(3);
                } else {
                    qAforDisplays.markAnswerAsUnselected(3);
                }
                break;
            }
            case R.id.answerDisplay4: {
                if (answerDisplay4.isChecked()) {
                    qAforDisplays.markAnswerAsSelected(4);
                } else {
                    qAforDisplays.markAnswerAsUnselected(4);
                }
                break;
            }
            case R.id.answerDisplay5: {
                if (answerDisplay5.isChecked()) {
                    qAforDisplays.markAnswerAsSelected(5);
                } else {
                    qAforDisplays.markAnswerAsUnselected(5);
                }
                break;
            }
            case R.id.answerDisplay6: {
                if (answerDisplay6.isChecked()) {
                    qAforDisplays.markAnswerAsSelected(6);
                } else {
                    qAforDisplays.markAnswerAsUnselected(6);
                }
                break;
            }
            case R.id.answerDisplay7: {
                if (answerDisplay7.isChecked()) {
                    qAforDisplays.markAnswerAsSelected(7);
                } else {
                    qAforDisplays.markAnswerAsUnselected(7);
                }
            }
        }

        if (qAforDisplays.countAndCheckEndOfTest()) {
            showEndOfTestView();
        }
    }

    private boolean areAnyAnswersSelected() {
        return answerDisplay1.isChecked() || answerDisplay2.isChecked()
                || answerDisplay3.isChecked() || answerDisplay4.isChecked()
                || answerDisplay5.isChecked() || answerDisplay6.isChecked()
                || answerDisplay7.isChecked();
    }

    private void showQuestionAndAnswers() {
        progressTest.incrementProgressBy(1);

        questionDisplay.setText(qAforDisplays.getCurrentQuestion());

        int u = 1;

        if (u < qAforDisplays.getSizeOfqAforDisplay()) {
            answerDisplay1.setVisibility(View.VISIBLE);
            answerDisplay1.setText(qAforDisplays.getCurrentAnswer(u));
            u++;
        }

        if (u < qAforDisplays.getSizeOfqAforDisplay()) {
            answerDisplay2.setVisibility(View.VISIBLE);
            answerDisplay2.setText(qAforDisplays.getCurrentAnswer(u));
            u++;
        }


        if (u < qAforDisplays.getSizeOfqAforDisplay()) {
            answerDisplay3.setVisibility(View.VISIBLE);
            answerDisplay3.setText(qAforDisplays.getCurrentAnswer(u));
            u++;
        } else {
            answerDisplay3.setVisibility(View.INVISIBLE);
            answerDisplay4.setVisibility(View.INVISIBLE);
            answerDisplay5.setVisibility(View.INVISIBLE);
            answerDisplay6.setVisibility(View.INVISIBLE);
            answerDisplay7.setVisibility(View.INVISIBLE);
        }

        if (u < qAforDisplays.getSizeOfqAforDisplay()) {
            answerDisplay4.setVisibility(View.VISIBLE);
            answerDisplay4.setText(qAforDisplays.getCurrentAnswer(u));
            u++;
        } else {
            answerDisplay4.setVisibility(View.INVISIBLE);
            answerDisplay5.setVisibility(View.INVISIBLE);
            answerDisplay6.setVisibility(View.INVISIBLE);
            answerDisplay7.setVisibility(View.INVISIBLE);
        }


        if (u < qAforDisplays.getSizeOfqAforDisplay()) {
            answerDisplay5.setVisibility(View.VISIBLE);
            answerDisplay5.setText(qAforDisplays.getCurrentAnswer(u));
            u++;
        } else {
            answerDisplay5.setVisibility(View.INVISIBLE);
            answerDisplay6.setVisibility(View.INVISIBLE);
            answerDisplay7.setVisibility(View.INVISIBLE);
        }


        if (u < qAforDisplays.getSizeOfqAforDisplay()) {
            answerDisplay6.setVisibility(View.VISIBLE);
            answerDisplay6.setText(qAforDisplays.getCurrentAnswer(u));
            u++;
        } else {
            answerDisplay6.setVisibility(View.INVISIBLE);
            answerDisplay7.setVisibility(View.INVISIBLE);
        }
        if (u < qAforDisplays.getSizeOfqAforDisplay()) {
            answerDisplay7.setVisibility(View.VISIBLE);
            answerDisplay7.setText(qAforDisplays.getCurrentAnswer(u));
            u++;
        } else {
            answerDisplay7.setVisibility(View.INVISIBLE);
        }
    }

    private void markAnswerDisplay() {
        int ch;
        for (ch = 1; ch < qAforDisplays.getSizeOfqAforDisplay(); ch++) {

            if (qAforDisplays.isItRightAnswer(ch)) {

                if (ch == 1) {
                    answerDisplay1.setTextColor(getResources().getColor(R.color.colorGreen));
                    answerDisplay1.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));

                }
                if (ch == 2) {
                    answerDisplay2.setTextColor(getResources().getColor(R.color.colorGreen));
                    answerDisplay2.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                }
                if (ch == 3) {
                    answerDisplay3.setTextColor(getResources().getColor(R.color.colorGreen));
                    answerDisplay3.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                }
                if (ch == 4) {
                    answerDisplay4.setTextColor(getResources().getColor(R.color.colorGreen));
                    answerDisplay4.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                }
                if (ch == 5) {
                    answerDisplay5.setTextColor(getResources().getColor(R.color.colorGreen));
                    answerDisplay5.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                }
                if (ch == 6) {
                    answerDisplay6.setTextColor(getResources().getColor(R.color.colorGreen));
                    answerDisplay6.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                }
                if (ch == 7) {
                    answerDisplay7.setTextColor(getResources().getColor(R.color.colorGreen));
                    answerDisplay7.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                }

            }

            if (qAforDisplays.isItWrongAnswer(ch)) {

                if (ch == 1) {
                    //answerDisplay1.setTextColor(getResources().getColor(R.color.colorRed));
                    answerDisplay1.setBackgroundColor(getResources().getColor(R.color.colorRed));
                }
                if (ch == 2) {
                    answerDisplay2.setBackgroundColor(getResources().getColor(R.color.colorRed));
                }
                if (ch == 3) {
                    answerDisplay3.setBackgroundColor(getResources().getColor(R.color.colorRed));
                }
                if (ch == 4) {
                    answerDisplay4.setBackgroundColor(getResources().getColor(R.color.colorRed));
                }
                if (ch == 5) {
                    answerDisplay5.setBackgroundColor(getResources().getColor(R.color.colorRed));
                }
                if (ch == 6) {
                    answerDisplay6.setBackgroundColor(getResources().getColor(R.color.colorRed));
                }
                if (ch == 7) {
                    answerDisplay7.setBackgroundColor(getResources().getColor(R.color.colorRed));
                }
            }
        }


        /*for (int ans = 1; ans < (qAforDisplays.getSizeOfqAforDisplay()); ans++) {
            System.out.println("Ответ№" + ans + "Правильный? " + qAforDisplays.isItRightAnswer(ans));
            if (qAforDisplays.isItRightAnswer(ans)) {
                System.out.println("Правильный выбор - ответ№" + ans);
                switch (ans) {
                    case (1):
                        answerDisplay1.markAsRight();
                        break;
                    case (2):
                        answerDisplay2.markAsRight();
                        break;
                    case (3):
                        answerDisplay3.markAsRight();
                        break;
                    case (4):
                        answerDisplay4.markAsRight();
                        break;
                    case (5):
                        answerDisplay5.markAsRight();
                        break;
                    case (6):
                        answerDisplay6.markAsRight();
                        break;
                    case (7):
                        answerDisplay7.markAsRight();
                        break;
                }
            }
            if (qAforDisplays.isItWrongAnswer(ans)) {
                System.out.println("Неправильный выбор - ответ№" + ans);
                switch (ans) {
                    case (1):
                        answerDisplay1.markAsWrong();
                        break;
                    case (2):
                        answerDisplay2.markAsWrong();
                        break;
                    case (3):
                        answerDisplay3.markAsWrong();
                        break;
                    case (4):
                        answerDisplay4.markAsWrong();
                        break;
                    case (5):
                        answerDisplay5.markAsWrong();
                        break;
                    case (6):
                        answerDisplay6.markAsWrong();
                        break;
                    case (7):
                        answerDisplay7.markAsWrong();
                        break;
                }
            }
        }*/
    }

    private void refreshView() {
        answerDisplay1.setEnabled(true);
        answerDisplay2.setEnabled(true);
        answerDisplay3.setEnabled(true);
        answerDisplay4.setEnabled(true);
        answerDisplay5.setEnabled(true);
        answerDisplay6.setEnabled(true);
        answerDisplay7.setEnabled(true);

        answerDisplay1.setChecked(false);
        answerDisplay2.setChecked(false);
        answerDisplay3.setChecked(false);
        answerDisplay4.setChecked(false);
        answerDisplay5.setChecked(false);
        answerDisplay6.setChecked(false);
        answerDisplay7.setChecked(false);

        answerDisplay1.setTextColor(Color.BLACK);
        answerDisplay2.setTextColor(Color.BLACK);
        answerDisplay3.setTextColor(Color.BLACK);
        answerDisplay4.setTextColor(Color.BLACK);
        answerDisplay5.setTextColor(Color.BLACK);
        answerDisplay6.setTextColor(Color.BLACK);
        answerDisplay7.setTextColor(Color.BLACK);

        answerDisplay1.setBackgroundColor(Color.WHITE);
        answerDisplay2.setBackgroundColor(Color.WHITE);
        answerDisplay3.setBackgroundColor(Color.WHITE);
        answerDisplay4.setBackgroundColor(Color.WHITE);
        answerDisplay5.setBackgroundColor(Color.WHITE);
        answerDisplay6.setBackgroundColor(Color.WHITE);
        answerDisplay7.setBackgroundColor(Color.WHITE);


        buttonReply.setVisibility(View.VISIBLE);
        buttonReply.setEnabled(true);
        tV.setVisibility(View.VISIBLE);
        tV.setText(qAforDisplays.getInfoForTV());
        buttonNext.setEnabled(false);
        buttonNext.setVisibility(View.INVISIBLE);

        hideAllAnswerDisplays();
    }

    private void hideAllAnswerDisplays() {
        answerDisplay1.setVisibility(View.INVISIBLE);
        answerDisplay2.setVisibility(View.INVISIBLE);
        answerDisplay3.setVisibility(View.INVISIBLE);
        answerDisplay4.setVisibility(View.INVISIBLE);
        answerDisplay5.setVisibility(View.INVISIBLE);
        answerDisplay6.setVisibility(View.INVISIBLE);
        answerDisplay7.setVisibility(View.INVISIBLE);

    }

    private void disableAllAnswerDisplays() {
        answerDisplay1.setEnabled(false);
        answerDisplay2.setEnabled(false);
        answerDisplay3.setEnabled(false);
        answerDisplay4.setEnabled(false);
        answerDisplay5.setEnabled(false);
        answerDisplay6.setEnabled(false);
        answerDisplay7.setEnabled(false);
    }

    private void showEndOfTestView() {
        disableAllAnswerDisplays();
        hideAllAnswerDisplays();
        buttonReply.setVisibility(View.INVISIBLE);
        tV.setText("");
        questionDisplay.setText(qAforDisplays.getFinalTestText());
        qAforDisplays.resetCounters();
        progressTest.setProgress(0);
        buttonNext.setEnabled(true);
        buttonNext.setText("RESTART");
        buttonNext.setVisibility(View.VISIBLE);
    }

}
