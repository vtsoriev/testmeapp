package com.vtsoriev.testmeapp;


import android.os.Build;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;


import static java.util.Collections.shuffle;

public class QuestionsForTest {
    Stack<String[][]> wrongAns = new Stack<>();
    Stack<String[][]> wrongAnsForDisplay = new Stack<>();
    String[][] answers;

    private String[] stringsWithQA;
    private String[][][] questionsAndAnswers;
    WorkWithFiles arrayWithQAStrings = new WorkWithFiles();
    private int lastQuestionIndexInArrayQ;
    private int maxNumberOfAnswers = 10;
    static boolean shuffleQuestions;
    static boolean shuffleAnswers;
    static boolean correctionOfMistakes;

    private boolean mainListOfQA = true;

    private int numberQ = 0;
    private int numberOfRightAnswer;
    private int numberOfQuestion = 0;
    String[][] empty = new String[1][2];

    QuestionsForTest() {
        readQuestionAndAnswerFromFile(); //инициализирует массив stringsWithQA*/
        restructureQuestionsAndAnswers();//инициализирует массив questionsAndAnswers
    }

    QuestionsForTest(boolean shuffleQ, boolean shuffleA) {
        shuffleQuestions = shuffleQ;
        shuffleAnswers = shuffleA; //если true - порядок вопросов и расположение ответов перемешиваются
        readQuestionAndAnswerFromFile(); //инициализирует массив stringsWithQA
        restructureQuestionsAndAnswers();//инициализирует массив questionsAndAnswers
    }

    public void setNumberQ(int numberQ) {
        if (numberQ < questionsAndAnswers.length)
            this.numberQ = numberQ;
    }

    private void readQuestionAndAnswerFromFile() {
        stringsWithQA = arrayWithQAStrings.readFileFromDirectory();
    }

    private void restructureQuestionsAndAnswers() {

        //int numberOfQuestion = 0;


        //System.out.println("до цикла numberOfQuestion=" + numberOfQuestion);


        for (int j = 0; j < stringsWithQA.length; j++) {
            if (stringsWithQA[j].startsWith("#Q")) {
                numberOfQuestion++;
                //System.out.println("numberOfQuestion="+numberOfQuestion);
                lastQuestionIndexInArrayQ = j;
            }
            if (stringsWithQA[j].startsWith("#R")) {
                numberOfRightAnswer++;
            }

        }
        //System.out.println("после цикла numberOfQuestion=" + numberOfQuestion);
        questionsAndAnswers = new String[numberOfQuestion][maxNumberOfAnswers + 1][2];
        //System.out.println("Количество строк- " + stringsWithQA.length);
        //System.out.println("Размер созданного массива - " + questionsAndAnswers.length);


        int questionNumber = 0;


        for (int j = 0; j <= lastQuestionIndexInArrayQ; ) {

            questionsAndAnswers[questionNumber++][0][0] = stringsWithQA[j];
            //System.out.println("Вопрос №" + questionNumber + questionsAndAnswers[questionNumber - 1][0][0] + " j=" + j);

            int answerNumber = 1;

            while (j < (stringsWithQA.length - 1) && !stringsWithQA[++j].startsWith("#Q")) {

                //System.out.print("Вопрос№" + questionNumber + " Вариант ответа " + answerNumber + "   j=" + j);
                questionsAndAnswers[questionNumber - 1][answerNumber++][0] = stringsWithQA[j];
                //System.out.println(questionsAndAnswers[questionNumber - 1][answerNumber - 1][0].toString());
            }

        }


        for (int x = 0; x < questionsAndAnswers.length; x++)

        {
            questionsAndAnswers[x][0][1] = "question";
            int w = questionsAndAnswers[x].length;
            //System.out.println(questionAndAnswer[x][0][0]);

            for (int a = 1; a < w; a++) {
                if (questionsAndAnswers[x][a][0] == null) {
                    questionsAndAnswers[x][a][0] = "ПУСТАЯ ЯЧЕЙКА";
                    questionsAndAnswers[x][a][1] = "ПУСТАЯ ЯЧЕЙКА";
                    //System.out.println("НАХРЕН БЛЯДЬ "+ questionAndAnswer[x][a][0]);
                }

                if (questionsAndAnswers[x][a][0].startsWith("#R")) {
                    questionsAndAnswers[x][a][1] = "right";
                    //System.out.println("Правильный вариант ответа: "+ questionAndAnswer[x][a][0].replace("#R ",""));
                }
                if (questionsAndAnswers[x][a][0].startsWith("#W")) {
                    questionsAndAnswers[x][a][1] = "MISTAKE";
                    //System.out.println("Неправильный вариант: " + questionAndAnswer[x][a][0].replace("#W ",""));
                }
            }
        }

        //System.out.println(lastQuestionIndexInArrayQ);

        if (shuffleQuestions) shuffle(Arrays.asList(questionsAndAnswers));


    }

    int getNumberOfQuestion() {
        return questionsAndAnswers.length;
    }

    boolean multipleChoice() {
        return numberOfRightAnswer > numberOfQuestion;
    }

    boolean isShuffleTurnOn() {
        return shuffleQuestions;
    }

    boolean isEverythingAlright() {
        return arrayWithQAStrings.haveQuestionsBeenFound();
    }

    String[][] getQuestionAndAnswers() {

        int w = questionsAndAnswers[numberQ].length;
        for (int x = 1; x < questionsAndAnswers[numberQ].length; x++) {

            if (questionsAndAnswers[numberQ][x][0].startsWith("ПУСТАЯ")) {
                w--;
            }
        }

        answers = new String[w][3];

        for (int i = 0; i < w; i++) {
            answers[i][0] = questionsAndAnswers[numberQ][i][0].replace("#W ", "")
                    .replace("#R ", "")
                    .replace("#Q ", "");
        }


        for (int a = 0; a < answers.length; a++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Objects.equals(questionsAndAnswers[numberQ][a][1], "MISTAKE")) {
                    answers[a][1] = "MISTAKE";
                    answers[a][2] = "MISTAKE";
                } else {
                    answers[a][2] = "MISTAKE";
                }
                if (Objects.equals(questionsAndAnswers[numberQ][a][1], "right")) {
                    answers[a][1] = "right";
                }

                if (Objects.equals(questionsAndAnswers[numberQ][a][1], "question")) {
                    answers[a][1] = "Question";
                    answers[a][2] = "Question";
                }
            } else {
                if (questionsAndAnswers[numberQ][a][1] == "MISTAKE") {
                    answers[a][1] = "MISTAKE";
                    answers[a][2] = "MISTAKE";
                } else {
                    answers[a][2] = "MISTAKE";
                }
                if (questionsAndAnswers[numberQ][a][1] == "right") {
                    answers[a][1] = "right";
                }

                if (questionsAndAnswers[numberQ][a][1] == "question") {
                    answers[a][1] = "Question";
                    answers[a][2] = "Question";
                }
            }
        }

        if (numberQ < (questionsAndAnswers.length - 1)) {
            numberQ++;
        } else {
            numberQ = 0;
        }

        if (shuffleAnswers) {
            int f = w - 1;
            String[][] shufAns = new String[f][];
            System.arraycopy(answers, 1, shufAns, 0, f);
            shuffle(Arrays.asList(shufAns));
            System.arraycopy(shufAns, 0, answers, 1, f);
        }


        return answers;
    }

    void pushInWrongAnswerStack() {
        wrongAns.push(answers);
        wrongAnsForDisplay.push(answers);
    }

    boolean isWrongAnswerStackEmpty() {
        return wrongAns.empty();
    }

    String[][] getQAFromWrongAnswerStack() {
        answers = wrongAns.pop();
        for (String[] s : answers) {
            s[2] = "MISTAKE";
        }
        return answers;
    }

    String getWrongAnswersForDisplay() {
        String wrongAnswers = "";
        while (!wrongAnsForDisplay.empty()) {
            wrongAnswers += wrongAnsForDisplay.pop()[0][0] + "\n";
        }
        return wrongAnswers;
    }
}

