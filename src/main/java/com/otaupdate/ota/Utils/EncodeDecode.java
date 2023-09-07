package com.otaupdate.ota.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import org.json.JSONArray;
import org.springframework.ui.Model;

public class EncodeDecode {

    public static void encode(JSONArray jsonArrayData, Model model, String fileName, String localRepo) throws IOException{
        String encodedString = Base64.getEncoder().encodeToString(jsonArrayData.toString().getBytes());
        File theDir = new File(localRepo);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        File newFile = new File(localRepo + "\\" + fileName);
        if(newFile.createNewFile()) {
            System.out.println("New file has been created");
            FileWriter writeToFile = new FileWriter(localRepo + "\\" + fileName);
            writeToFile.write(encodedString);
            writeToFile.close();
        }
        model.addAttribute("written to file", "Write is successful");
    }
}
