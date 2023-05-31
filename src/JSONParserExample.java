import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JSONParserExample {
    private static final Logger LOGGER = Logger.getLogger(JSONParserExample.class.getName());
    private static final String LOG_FILE = "error.log";
    private static final String OUTPUT_FILE = "result.txt";

    public static void main(String[] args) {
        String json = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
                "{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
                "{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

        StringBuilder result = parseJSON(json);
        System.out.println(result.toString());

        writeToFile(result.toString());
    }

    private static StringBuilder parseJSON(String json) {
        StringBuilder result = new StringBuilder();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String фамилия = jsonObject.getString("фамилия");
                String оценка = jsonObject.getString("оценка");
                String предмет = jsonObject.getString("предмет");

                result.append("Студент ").append(фамилия).append(" получил ").append(оценка)
                        .append(" по предмету ").append(предмет).append(".\n");
            }
        } catch (JSONException e) {
            LOGGER.log(Level.SEVERE, "Error parsing JSON: " + e.getMessage(), e);
        }

        return result;
    }

    private static void writeToFile(String data) {
        try {
            FileWriter writer = new FileWriter(OUTPUT_FILE);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to file: " + e.getMessage(), e);
        }
    }

    static {
        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating log file handler: " + e.getMessage(), e);
        }
    }
}
