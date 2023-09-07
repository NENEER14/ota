package com.otaupdate.ota.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.springframework.ui.Model;

public class CompressAndDecompress {

    public static void compressFile(String fileName, Model model, int newVersion, String localRepo ) throws IOException{
        int length;
        File readFile = new File(localRepo + "\\" + fileName);
        FileInputStream fileInputStream = new FileInputStream(readFile);
        FileOutputStream fileOutputStream= new FileOutputStream(localRepo + "\\" + fileName + "_v" + newVersion + ".zip");
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
        byte[] buffer = new byte[1024];
        while((length=fileInputStream.read(buffer)) != -1){
            gzipOutputStream.write(buffer,0,length);
        }
        gzipOutputStream.close();
        fileOutputStream.close();
        fileInputStream.close();
        model.addAttribute("zip file ==================", "Successful ");
        readFile.delete();
;    }
}
