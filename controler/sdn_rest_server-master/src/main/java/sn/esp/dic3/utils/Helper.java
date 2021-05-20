package sn.esp.dic3.utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.Random;

public class Helper {

    public static String getRandomMacAddress() {
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        macAddr[0] = (byte)(macAddr[0] & (byte)254);  //zeroing last 2 bytes to make it unicast and locally adminstrated

        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){

            if(sb.length() > 0)
                sb.append(":");

            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static JSONObject getInputStreamAsJson(InputStream inputStream) {
        JSONTokener tokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(tokener);
        return jsonObject;
    }

    public static JSONObject createJSONErrorObject(String message, String details) {
        JSONObject jsonErrorObject = new JSONObject();
        jsonErrorObject.put("message", message);
        jsonErrorObject.put("details", details);
        return jsonErrorObject;
    }
}
