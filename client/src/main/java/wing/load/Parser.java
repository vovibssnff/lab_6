package wing.load;

import wing.managment.ProgramState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import common.OutputEngine;
import common.data.LabWork;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Класс для парсинга объектов коллекции, записанных в файле .json
 */
public class Parser {
    public static ArrayList<LabWork> parse() {
        Gson gson = ProgramState.getGson();
        ArrayList<LabWork> labWorkList = new ArrayList<>();
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.env");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String filename = prop.getProperty("FILE_NAME");
        try(JsonReader reader = new JsonReader(new FileReader(filename))) {
            reader.beginArray();
            while(reader.hasNext()) {
                JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
                LabWork lbwork = gson.fromJson(obj, LabWork.class);
//                if () {
//                    labWorkList.add(lbwork);
//                    System.out.println(lbwork);
//                } else {
//                    System.out.println(OutputEngine.incorrectObject());
//                }
            }
            reader.endArray();
            System.out.println(OutputEngine.successImport());
            return labWorkList;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(OutputEngine.importError());
        }
        return null;
    }

}