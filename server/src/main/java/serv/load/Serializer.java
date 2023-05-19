package serv.load;
import cmn.OutputEngine;
import cmn.data.LabWork;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import serv.managment.CollectionService;
import serv.managment.Validator;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Класс для сериализации объектов из коллекции для последующего сохранения в файл .json
 */
public class Serializer {
    public static void save(ArrayDeque<LabWork> collection) {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();

        try (PrintWriter out = new PrintWriter(new FileWriter("elements.json"))) {
            JsonWriter writer = gson.newJsonWriter(out);
            writer.setIndent("\t");
            writer.setSerializeNulls(true);
            writer.beginArray();
            for (LabWork elem : collection) {
                gson.toJson(elem, LabWork.class, writer);
            }
            writer.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parse() {
        CollectionService.clearSets();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
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
                if (Validator.checkName(lbwork.getName())&&
                        Validator.checkId(lbwork.getId())&&
                        Validator.checkDifficulty(lbwork.getDifficulty())&&
                        Validator.checkMinimalPoint(lbwork.getMinimalPoint())&&
                        Validator.checkCreationDate(lbwork.getCreationDate().toString())&&
                        Validator.checkCoordinatesX(lbwork.getCoordinates().getX())&&
                        Validator.checkCoordinatesY(lbwork.getCoordinates().getY())&&
                        Validator.checkAuthorName(lbwork.getAuthor().getName())&&
                        Validator.checkPassportId(lbwork.getAuthor().getPassportID())&&
                        Validator.checkColor(lbwork.getAuthor().getEyeColor())&&
                        Validator.checkLocationX(lbwork.getAuthor().getLocation().getX())&&
                        Validator.checkLocationY(lbwork.getAuthor().getLocation().getY())) {
                    labWorkList.add(lbwork);
                    //System.out.println(lbwork);
                } else {
                    System.out.println(OutputEngine.incorrectObject());
                }
            }
            reader.endArray();
            System.out.println(OutputEngine.successImport());
            CollectionService.addElemsFromList(labWorkList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(OutputEngine.importError());
        }
    }
}
