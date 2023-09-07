package com.otaupdate.ota.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

import org.springframework.ui.Model;

public class EncodeDecode {

    public static void encode(Model model, String fileName, String localRepo, int newVersion) throws IOException{
       File originalFile = new File(localRepo + "\\" + fileName + "_v" + newVersion + ".zip");
		String encodedBase64 = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
			byte[] bytes = new byte[(int) originalFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.getEncoder().encodeToString(bytes));
            fileInputStreamReader.close();
            model.addAttribute("encoded zip file data", encodedBase64);
            decode(encodedBase64, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static void decode(String encodedBase64, Model model){
        StringBuilder output = new StringBuilder();
        byte[] compressed = Base64.getDecoder().decode(encodedBase64);

        if ((compressed == null) || (compressed.length == 0)) {
            throw new IllegalArgumentException("Cannot unzip null or empty bytes");
        }
        if (!isZipped(compressed)) {
            System.out.println(compressed);
        }

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
                try (InputStreamReader inputStreamReader =
                             new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {
                    try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            output.append(line);
                            model.addAttribute("decoded format", output);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to unzip content", e);
        }
    }

    public static boolean isZipped(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
                && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }
}
