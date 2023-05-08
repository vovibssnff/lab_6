package wing.managment;

import com.google.gson.Gson;

import java.util.Scanner;

public class ServerState {
    private static Mode md;
    private static Scanner sc;
    private static Gson gson;

    public static void setMode(Mode mode) {
        md = mode;
    }

    public static void setScanner(Scanner scanner) {
        sc = scanner;
    }
    public static void setGson(Gson gs) {gson = gs;}

    public static Mode getMode() {
        return md;
    }

    public static Scanner getScanner() {
        return sc;
    }

    public static Gson getGson() {return gson;}
}
