package cl.managment;

import cl.io.CommandHandler;
import cl.io.CommandType;
import cl.io.Mode;
import cl.load.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ProgramState {
    private static CommandType commandType;
    private static Mode md;
    private static Scanner sc;
    private static Scanner keyboardScanner = new Scanner(System.in);
    private static Scanner fileScanner;
    private static UsrInputReceiver usrInputReceiver;
    private static LabWorkService labWorkService;
    private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
    private static CommandHandler commandHandler = new CommandHandler();


    public static void setMode(Mode mode) {
        md = mode;
    }
    public static void setCommandType(CommandType ct) {commandType=ct;}
    public static void setScanner(Scanner scanner) {
        sc = scanner;
    }
    public static void setUsrInputReceiver(UsrInputReceiver usrInputReceiver) {ProgramState.usrInputReceiver=usrInputReceiver;}
    public static void setLabWorkService(LabWorkService labWorkService) {ProgramState.labWorkService=labWorkService;}
    public static void setFileScanner(Scanner fileScanner) {ProgramState.fileScanner=fileScanner;}

    public static CommandType getCommandType() {return commandType;}
    public static Mode getMode() {
        return md;
    }
    public static Scanner getScanner() {
        return sc;
    }
    public static Scanner getKeyboardScanner() {return keyboardScanner;}
    public static UsrInputReceiver getUsrInputReceiver() {
        return usrInputReceiver;
    }

    public static LabWorkService getLabWorkService() {
        return labWorkService;
    }
    public static Gson getGson() {return ProgramState.gson;}
    public static Scanner getFileScanner() {return  ProgramState.fileScanner;}
    public static CommandHandler getCommandHandler() {return ProgramState.commandHandler;}
}
