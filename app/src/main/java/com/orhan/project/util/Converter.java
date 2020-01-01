package com.orhan.project.util;

import android.content.Context;

import com.orhan.project.R;
import com.orhan.project.model.Input;
import com.orhan.project.model.InputType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Converter {

    public static ArrayList<Input> getJsonDataAsArray(Context context) {

        ArrayList<Input> inputList = new ArrayList<>();

        try {
            String jsonDataString = read(context);
            JSONArray jsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String title = object.getString("title");

                InputType inputType = InputType.NOTHING;
                if (object.getString("type").equals("String")) {
                    inputType = InputType.TEXTBOX;
                }

                Input input = new Input(title, inputType);
                inputList.add(input);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return inputList;
    }


    private static String read(Context context) throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonString;
            inputStream = context.getResources().openRawResource(R.raw.data);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }
}
