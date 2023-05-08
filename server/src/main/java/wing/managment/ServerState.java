package wing.managment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import wing.load.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ServerState {
    private static Mode md;
    private static Scanner sc;
    private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
    private static CollectionReceiver collectionReceiver;

    public static void setMode(Mode mode) {
        md = mode;
    }

    public static void setScanner(Scanner scanner) {
        sc = scanner;
    }
    public static void setGson(Gson gs) {gson = gs;}
    public static void setCollectionReceiver(CollectionReceiver receiver) {collectionReceiver = receiver;}

    public static Mode getMode() {
        return md;
    }

    public static Scanner getScanner() {
        return sc;
    }

    public static Gson getGson() {return gson;}
    public static CollectionReceiver getCollectionReceiver() {return collectionReceiver;}
}
