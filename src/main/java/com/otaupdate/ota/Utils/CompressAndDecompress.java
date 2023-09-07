package com.otaupdate.ota.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public class CompressAndDecompress {

    public static void compressFile(MultipartFile file, Model model, int newVersion, String localRepo) throws IOException{
        File theDir = new File(localRepo);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        int length;
        File readFile = new File(localRepo + "\\" + file.getOriginalFilename());
        file.transferTo(readFile);
        FileInputStream fileInputStream = new FileInputStream(readFile);
        FileOutputStream fileOutputStream= new FileOutputStream(localRepo + "\\" + file.getOriginalFilename() + "_v" + newVersion + ".zip");
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
        byte[] buffer = new byte[1024];
        while((length=fileInputStream.read(buffer)) != -1){
            gzipOutputStream.write(buffer,0,length);
        }
        gzipOutputStream.close();
        fileOutputStream.close();
        fileInputStream.close();
        readFile.delete();

    }
}
