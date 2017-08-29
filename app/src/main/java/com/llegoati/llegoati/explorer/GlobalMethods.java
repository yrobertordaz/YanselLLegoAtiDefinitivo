package com.llegoati.llegoati.explorer;

import android.content.Context;
import android.os.Environment;

import org.eclipse.jdt.core.IField;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Richard on 01/09/2016.
 */
public class GlobalMethods {

    public static ArrayList<FileInformation> getFileList(String patch){


        String global = patch;

        File mFile = new File(patch);

        ArrayList<FileInformation> lsFileInformation = new ArrayList<>();
        if(mFile.isDirectory() && mFile.exists()){

            File[] mListFiles = mFile.listFiles();



            if (Environment.isExternalStorageEmulated(mFile)){

            }

            if (mListFiles!=null) {

                String[] mnamesFiles = mFile.list();
                ArrayList<String> names = new ArrayList<>();
                ArrayList<File> directorys = new ArrayList<>();
                ArrayList<File> ficheros = new ArrayList<>();

                for (int i = 0; i < mListFiles.length; i++) {
                    if (mListFiles[i].isDirectory()) {
                        directorys.add(mListFiles[i]);
                    } else {
                        ficheros.add(mListFiles[i]);
                    }
                }

                for (int i = 0; i < directorys.size(); i++) {
                    final String tempFile = directorys.get(i).getName();
                    if (tempFile.toCharArray()[0] != '.') {
                        final File temp2 = directorys.get(i);
                        final FileInformation mFileInformation = new FileInformation(tempFile, temp2.isDirectory(), global);
                        lsFileInformation.add(mFileInformation);
                    }

                }
                for (int i = 0; i < ficheros.size(); i++) {

                    final String tempFile = ficheros.get(i).getName();
                    if (tempFile.toCharArray()[0] != '.') {
                        final File temp2 = ficheros.get(i);
                        final FileInformation mFileInformation = new FileInformation(tempFile, temp2.isDirectory(), global);
                        lsFileInformation.add(mFileInformation);
                    }
                }

                return lsFileInformation;
            }else {

                return lsFileInformation;
            }

        }else{

            File n = new File(patch);

            for (String nam : n.list()){

                //if(nam.contains("sdcard")){
                    final FileInformation mFileInformation = new FileInformation(nam,true,global);
                    lsFileInformation.add(mFileInformation);
                //}

            }


            /*final FileInformation mFileInformation = new FileInformation("sdcard0",true,global);
            final FileInformation mFileInformation2 = new FileInformation("sdcard1",true,global);
            lsFileInformation.add(mFileInformation);
            lsFileInformation.add(mFileInformation2);*/
            return lsFileInformation;

        }


    }


}
