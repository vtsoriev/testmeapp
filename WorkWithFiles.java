package com.vtsoriev.testmeapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;


public class WorkWithFiles extends AppCompatActivity {
    static String defaultFileName;
    int i = 0;
    private boolean qaHaveFound;
    String fileName;
    /*private String currentPath = Environment.getDataDirectory()

    File file = new File(context.getFilesDir(), "internalFile");*/


    /*public static void main(String[] args) {
        //moveFileToProgramDirectory();
        readFileFromProgramDirectory();
    }*/

    public void moveFileToProgramDirectory() {

    }


    public String[] readFileFromDirectory() {

        String[] arrayWithStringsFromFile;
        String[] err = {"#Q Ошибка при доступе к файлу", "#R "};
        String[] notFoundQA = {"#Q not found", "#R not found"};

        if (QAActivity.startTestFromDefaultFile) {
            fileName = Environment.getDataDirectory().getAbsolutePath() + "/data/com.vtsoriev.testmeapp/app_tests/defaultFileWithQA.txt";
            //fileName = "/data/data/com.vtsoriev.testmeapp/app_tests/defaultFileWithQA.txt";
        } else {
            fileName = OpenFileDialog.getNameOfSelectedFile();

        }

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                if (str.startsWith("#")) {
                    i++;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //return err;
        } catch (IOException e) {
            e.printStackTrace();
            //return err;
        } catch (Exception e) {
            e.printStackTrace();
            //return err;
        }
        if (i == 0) {
            qaHaveFound = false;
            return notFoundQA;
        } else {
            qaHaveFound = true;
        }

        try {
            arrayWithStringsFromFile = new String[i];
            // открываем поток для чтения
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            String str = "";
            i = 0;
            // читаем содержимое
            while ((str = br2.readLine()) != null) {
                if (str.startsWith("#")) {
                    arrayWithStringsFromFile[i++] = str;
                }
            }
            System.out.println(Arrays.deepToString(arrayWithStringsFromFile));
            return arrayWithStringsFromFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return err;
        } catch (IOException e) {
            e.printStackTrace();
            return err;
        }
    }

    public static void createDir(String folder) {
        File f1 = new File(folder); //Создаем файловую переменную
        if (!f1.exists()) { //Если папка не существует
            f1.mkdirs();//создаем её
        }
    }

    public static boolean copy(String from, String to) {
        try {
            File fFrom = new File(from);
            if (fFrom.isDirectory()) { // Если директория, копируем все ее содержимое
                createDir(to);
                String[] FilesList = fFrom.list();
                for (int i = 0; i <= FilesList.length; i++)
                    if (!copy(from + "/" + FilesList[i], to + "/" + FilesList[i]))
                        return false; // Если при копировании произошла ошибка
            } else if (fFrom.isFile()) { // Если файл просто копируем его
                File fTo = new File(to);
                InputStream in = new FileInputStream(fFrom); // Создаем потоки
                OutputStream out = new FileOutputStream(fTo);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close(); // Закрываем потоки
                out.close();
            }
        } catch (FileNotFoundException ex) { // Обработка ошибок
        } catch (IOException e) { // Обработка ошибок
        }
        return true; // При удачной операции возвращаем true
    }

    boolean move(Context context, String from) {
        File f = new File(context.getDir("tests", 0), "defaultFileWithQA.txt");
        String to = f.getAbsolutePath();

        try {
            File fFrom = new File(from);
            if (fFrom.isDirectory()) { // Если директория, копируем все ее содержимое
                createDir(to);
                String[] FilesList = fFrom.list();
                for (int i = 0; i <= FilesList.length; i++)
                    if (!copy(from + "/" + FilesList[i], to + "/" + FilesList[i]))
                        return false; // Если при копировании произошла ошибка
            } else if (fFrom.isFile()) { // Если файл просто копируем его
                File fTo = new File(to);
                InputStream in = new FileInputStream(fFrom); // Создаем потоки
                OutputStream out = new FileOutputStream(fTo);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close(); // Закрываем потоки
                out.close();
                defaultFileName = fTo.getAbsolutePath();
            }
        } catch (FileNotFoundException e) { // Обработка ошибок
            e.printStackTrace();
            return false;
        } catch (IOException e) { // Обработка ошибок
            e.printStackTrace();
            return false;
        }
        String deleteCmd = "rm -r " + from; //Создаем текстовую командную строку в которой удаляем начальный файл
        /*try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(deleteCmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/
        //Выполняем удаление с помощью команд
        return true; // При удачной операции возвращаем true
    }

    boolean haveQuestionsBeenFound() {
        return i > 0;
    }
}
