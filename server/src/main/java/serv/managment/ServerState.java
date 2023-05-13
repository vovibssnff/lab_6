package serv.managment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import serv.load.LocalDateTimeAdapter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ServerState {
    private static Mode md;
    private static Scanner sc;
    private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
    private static CollectionReceiver collectionReceiver;
    private static File tmpFile;
    private static Integer port = 2222;

    public static void setMode(Mode mode) {
        md = mode;
    }

    public static void setScanner(Scanner scanner) {
        sc = scanner;
    }
    public static void setCollectionReceiver(CollectionReceiver receiver) {collectionReceiver = receiver;}
    public static void setTmpFile(File file) {tmpFile = file;}
    public static void setPort(Integer prt) {port = prt;}
    public static Mode getMode() {
        return md;
    }
    public static Integer getPort() {
        return port;
    }


    public static Gson getGson() {return gson;}
    public static CollectionReceiver getCollectionReceiver() {return collectionReceiver;}
    public static File getTmpFile() {return tmpFile;}
    public static Scanner getScanner() {
        return sc;
    }

}
