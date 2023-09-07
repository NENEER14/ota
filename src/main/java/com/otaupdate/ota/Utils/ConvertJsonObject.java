package com.otaupdate.ota.Utils;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJsonObject {
    
    public static JsonNode toJsonNode(JSONObject jsonObject) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonNode;
    }
}