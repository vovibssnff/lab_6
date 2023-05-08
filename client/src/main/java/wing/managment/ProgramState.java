package wing.managment;

import wing.io.Mode;
import wing.load.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ProgramState {
    private static Mode md;
    private static Scanner sc;
    private static UsrInputReceiver usrInputReceiver;
    private static LabWorkService labWorkService;
    private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();


    public static void setMode(Mode mode) {
        md = mode;
    }

    public static void setScanner(Scanner scanner) {
        sc = scanner;
    }

    public static void setUsrInputReceiver(UsrInputReceiver usrInputReceiver) {ProgramState.usrInputReceiver=usrInputReceiver;}
    public static void setLabWorkService(LabWorkService labWorkService) {ProgramState.labWorkService=labWorkService;}
    public static Mode getMode() {
        return md;
    }

    public static Scanner getScanner() {
        return sc;
    }

    public static UsrInputReceiver getUsrInputReceiver() {
        return usrInputReceiver;
    }

    public static LabWorkService getLabWorkService() {
        return labWorkService;
    }
    public static Gson getGson() {return ProgramState.gson;}
}
