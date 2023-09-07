package com.otaupdate.ota.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class JsonValidation {

    public boolean validateJson(MultipartFile file, Model model) throws IOException {
        boolean fileSuccess = true;
        List<String> errors = new ArrayList<>();
        JSONObject jsonObject;
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance( SpecVersion.VersionFlag.V201909 ); 
        InputStream schemaStream = new FileInputStream( new File(getClass().getResource("/schema.json").getFile()));
        JsonSchema schema = schemaFactory.getSchema(schemaStream);

        //load the uploaded file
        JSONArray jsonDataFromFileArray = new JSONArray(new JSONTokener(file.getInputStream()));
        for(int i = 0; i < jsonDataFromFileArray.length(); i++) {
            jsonObject = jsonDataFromFileArray.getJSONObject(i);
            Set<ValidationMessage> validationResult = schema.validate(ConvertJsonObject.toJsonNode(jsonObject));
            String str = "JSON Validation Error for VIN # " + jsonObject.get("vin_num") + " and record # " + (i + 1) + " : ";
            validationResult.forEach(vm -> errors.add(str + vm.getMessage()));
            if(!validationResult.isEmpty()) {
                fileSuccess = false;
            }
        }
        model.addAttribute("errors", errors);
        return fileSuccess;
    }
}
