package com.vtsoriev.testmeapp;

import android.os.Build;

import java.util.Arrays;
import java.util.Objects;


class Model {

    private int numberOfCurrentAnswer;
    private int count;
    private int nRightAns;
    private int nWrongAns;
    private int nNotComp;
    private QuestionsForTest listOfQA;// = new QuestionsForTest();
    private String[][] qAforDisplay;// = listOfQA.getQuestionAndAnswers();
    //final int NUMBEROFQWESTION = listOfQA.getNumberOfQuestion();

    Model() {
        count = 0;
        nRightAns = 0;
        nWrongAns = 0;
        nNotComp = 0;
        listOfQA = new QuestionsForTest();

    }


    void letsGo() {
        if (!QuestionsForTest.correctionOfMistakes) {
            qAforDisplay = listOfQA.getQuestionAndAnswers();
        }

        if (QuestionsForTest.correctionOfMistakes) {
            if (count < listOfQA.getNumberOfQuestion()) {
                qAforDisplay = listOfQA.getQuestionAndAnswers();
            }

            if (count >= listOfQA.getNumberOfQuestion() & !listOfQA.isWrongAnswerStackEmpty()) {
                qAforDisplay = listOfQA.getQAFromWrongAnswerStack();
            }
        }
        count++;
    }

    String getCurrentQuestion() {

        return qAforDisplay[0][0];
    }

    String getCurrentAnswer(int displayNumber) {
        return qAforDisplay[displayNumber][0];
    }

    int getSizeOfqAforDisplay() {
        return qAforDisplay.length;
    }

    int getNumberOfQuestions() {
        return listOfQA.getNumberOfQuestion();
    }

    void markAnswerAsSelected(int displayNumber) {
        qAforDisplay[displayNumber][2] = "right";
    }

    void markAnswerAsUnselected(int displayNumber) {
        qAforDisplay[displayNumber][2] = "MISTAKE";
    }

    String getInfoForTV() {
        String info;
        if (listOfQA.multipleChoice()) {
            info = "Правильных ответов - " + nRightAns
                    + ".\nОшибок - " + nWrongAns
                    + ".\nНеполных ответов - " + nNotComp;
        } else {
            info = "Правильных ответов - " + nRightAns
                    + ".\nОшибок - " + nWrongAns;
        }

        return info;
    }

    String getInitialTestText() {
        String initialText;
        if (listOfQA.isEverythingAlright()) {
            String multipleOrMonoChoice;
            if (listOfQA.multipleChoice()) {
                multipleOrMonoChoice = ". Некоторые вопросы предполагают несколько правильных вариантов ответа. Будьте внимательны при выборе.";
            } else {
                multipleOrMonoChoice = ". На каждый вопрос есть только один правильный вариант ответа.";
            }
            String aboutShuffle;
            if (listOfQA.isShuffleTurnOn()) {
                aboutShuffle = " Включено перемешивание порядка следования вопросов и порядка расположения вариантов ответа на экране.";
            } else {
                aboutShuffle = " Перемешивание порядка следования вопросов и порядка расположения вариантов ответа на экране ВЫКЛЮЧЕНО.";
            }
            initialText = "Всего вопросов в тесте: " + listOfQA.getNumberOfQuestion() + multipleOrMonoChoice + aboutShuffle + "\nНачать тестирование?";
        } else {
            initialText = "Для того, чтобы программа правильно прочитала текст, вопросы и ответы должны быть обозначены следующим образом:" +
                    "\n#Q Текст вопроса" +
                    "\n#R текст правильного варианта ответа (их может быть несколько)" +
                    "\n#W текст неверного варианта ответа." +
                    " В выбранном вами файле таких пометок не найдено! Вернитесь назад и попробуйте загрузить другой файл";
        }
        return initialText;
    }

    String getFinalTestText() {

        String finalTestText = "You have reached the end of test! ТЕСТ ЗАВЕРШЕН!!!\n"
                + getInfoForTV()
                + "\nВопросы, в которых допущены ошибки:\n"
                + listOfQA.getWrongAnswersForDisplay()
                + "\nЗапустить заново?";

        return finalTestText;
    }

    void resetCounters() {
        nRightAns = 0;
        nWrongAns = 0;
        nNotComp = 0;
    }

    String checkAnswers() {

        int points = 0;
        int penalty = 0;
        int rightAns = 0;

        System.out.println(Arrays.deepToString(qAforDisplay));

        for (numberOfCurrentAnswer = 1; numberOfCurrentAnswer < qAforDisplay.length; numberOfCurrentAnswer++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                boolean eq = qAforDisplay[numberOfCurrentAnswer][1].equals(qAforDisplay[numberOfCurrentAnswer][2]);
                if ((eq) && (Objects.equals(qAforDisplay[numberOfCurrentAnswer][1], "right")) || (eq) && (Objects.equals(qAforDisplay[numberOfCurrentAnswer][1], "MISTAKE"))) {
                    points++;
                    //System.out.println("points после инкремента" + points);
                }
                if ((!eq) && (Objects.equals(qAforDisplay[numberOfCurrentAnswer][1], "MISTAKE"))) {
                    penalty++;
                    //System.out.println("penalty после инкремента" + penalty);
                }
                if ((eq) && (Objects.equals(qAforDisplay[numberOfCurrentAnswer][1], "right"))) {
                    rightAns++;
                    //System.out.println("rightAns после инкремента" + rightAns);
                }
            } else {
                boolean eq = qAforDisplay[numberOfCurrentAnswer][1].equals(qAforDisplay[numberOfCurrentAnswer][2]);
                if ((eq) && (qAforDisplay[numberOfCurrentAnswer][1] == "right") || (eq) && (qAforDisplay[numberOfCurrentAnswer][1] == "MISTAKE")) {
                    points++;
                    //System.out.println("points после инкремента" + points);
                }
                if ((!eq) && (qAforDisplay[numberOfCurrentAnswer][1] == "MISTAKE")) {
                    penalty++;
                    //System.out.println("penalty после инкремента" + penalty);
                }
                if ((eq) && (qAforDisplay[numberOfCurrentAnswer][1] == "right")) {
                    rightAns++;
                    //System.out.println("rightAns после инкремента" + rightAns);
                }
            }

        }
        if (points == (qAforDisplay.length - 1)) {
            //System.out.println("ITS RIGHT");
            nRightAns++;
            return "Вы ответили верно";


        } else {
            listOfQA.pushInWrongAnswerStack();//если ответ неверный или неполный, помещаем его в стек.
            if (rightAns > 0 && penalty == 0) {
                //System.out.println("ITS INCOMP");
                nNotComp++;
                return "Вы ответили неполно (не все правильные ответы выбраны)";
            }

            if (penalty > 0) {
                //System.out.println("ITS WRONG");
                nWrongAns++;

                return "Вы ответили НЕВЕРНО (выбрали один или более неправильный вариант ответа).";
            }
        }
        return "КТО БЫ МОГ ПОДУМАТЬ!";
    }

    boolean isItRightAnswer(int numAnsDisplay) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(qAforDisplay[numAnsDisplay][1], "right");
        } else return qAforDisplay[numAnsDisplay][1] == "right";
    }

    boolean isItWrongAnswer(int numAnsDisplay) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(qAforDisplay[numAnsDisplay][1], "MISTAKE") && Objects.equals(qAforDisplay[numAnsDisplay][2], "right");
        } else
            return (qAforDisplay[numAnsDisplay][1] == "MISTAKE" && qAforDisplay[numAnsDisplay][2] == "right");
    }

    boolean countAndCheckEndOfTest() {
        if (count > listOfQA.getNumberOfQuestion() && !QuestionsForTest.correctionOfMistakes) {
            listOfQA.setNumberQ(0);
            count=0;
            return true;
        }

        if (count > (listOfQA.getNumberOfQuestion()) && QuestionsForTest.correctionOfMistakes && listOfQA.isWrongAnswerStackEmpty()) {
            listOfQA.setNumberQ(0);
            count=0;
            return true;

        } else {
            return false;
        }
    }

    boolean isAllWell() {
        return listOfQA.isEverythingAlright();
    }
}

