package cl.io;

import cmn.cmd.*;
import cl.managment.*;
import cmn.OutputEngine;
import cl.ClientConnectionService;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class InputEngine {
    private static final UsrInputReceiver usrInputReceiver = new UsrInputReceiver();
    private static final LabWorkService labWorkService = new LabWorkService();
    private static final File tmpFile = new File("unsaved.tmp");
    public static String resp = null;
    public static void launchInvoke(Command command, String arg, Scanner scanner, Mode mode) {
        //Collections.addCommand(command);
        command.setUsrInputReceiver(ProgramState.getUsrInputReceiver());
        command.setLabWorkService(ProgramState.getLabWorkService());
        command.setArg(arg);

        command.execute();
    }
    public static void init() {
        ClientConnectionService.initConnection();
        CollectionsEngine.addElemToCommandMap(AddCmd.getName(), new AddCmd());
        CollectionsEngine.addElemToCommandMap(HelpCmd.getName(), new HelpCmd());
        CollectionsEngine.addElemToCommandMap(SoutCollectionCmd.getName(), new SoutCollectionCmd());
        CollectionsEngine.addElemToCommandMap(HistoryCmd.getName(), new HistoryCmd());
        CollectionsEngine.addElemToCommandMap(PrintUniqueAuthorCmd.getName(), new PrintUniqueAuthorCmd());
        CollectionsEngine.addElemToCommandMap(ClearCmd.getName(), new ClearCmd());
        CollectionsEngine.addElemToCommandMap(HeadCmd.getName(), new HeadCmd());
        CollectionsEngine.addElemToCommandMap(InfoCmd.getName(), new InfoCmd());
        CollectionsEngine.addElemToCommandMap(ExitCmd.getName(), new ExitCmd());
        CollectionsEngine.addElemToCommandMap(UpdateCmd.getName(), new UpdateCmd());
        CollectionsEngine.addElemToCommandMap(PrintFieldDescendingMinimalPointCmd.getName(), new PrintFieldDescendingMinimalPointCmd());
        CollectionsEngine.addElemToCommandMap(CountLessThanMinimalPointCmd.getName(), new CountLessThanMinimalPointCmd());
        CollectionsEngine.addElemToCommandMap(RemoveLowerCmd.getName(), new RemoveLowerCmd());
        CollectionsEngine.addElemToCommandMap(RemoveByIdCmd.getName(), new RemoveByIdCmd());
        CollectionsEngine.addElemToCommandMap(ExecuteScriptCmd.getName(), new ExecuteScriptCmd());
        System.out.println(OutputEngine.greeting_msg());
        Scanner keyboardScanner = new Scanner(System.in);

        Pattern pattern = Pattern.compile("^[yn]$");
        //Восстановление старых данных
        modeSwitcher(keyboardScanner, Mode.DEFAULT, null, null);
    }
    public static void scanCommand(Scanner scanner, String[] tokens, Command currentCommand, File tmpFile, Mode mode) {
        String input = scanner.nextLine().trim();
        tokens = input.split(" ");
        Command command = CollectionsEngine.searchCommand(tokens[0]);

        try {
            currentCommand = command.getClass().getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (tokens.length<2) {
            launchInvoke(currentCommand, null, scanner, mode);
        } else {
            launchInvoke(currentCommand, tokens[1], scanner, mode);
        }
    }
    public static void modeSwitcher(Scanner sc, Mode mode, Command currentCommand, String filename) {
        String[] tokens = new String[0];
        File file = null;
        try {
            file = new File(filename);
        } catch (NullPointerException e) {
            System.out.print(OutputEngine.prompt());
        }
        switch (mode) {

            //Режим чтения команд с клавиатуры
            case DEFAULT -> {

                //Основной сканер
                while (true) {
                    try {
                        System.out.print(OutputEngine.prompt());
                        scanCommand(sc, tokens, currentCommand, tmpFile, mode);
                    } catch (NullPointerException e) {
                        System.out.println(OutputEngine.incorrectCommand());
                    }
                }
            }

            //Режим чтения команд из скрипта
            case FILE -> {

                Scanner fileScanner = null;
                try {
                    fileScanner = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.getStackTrace();
                }
                if (!file.exists() || !file.isFile() || !file.canRead()) {
                    System.out.println(OutputEngine.accessError());
                    return;
                }
                while (true) {
                    assert fileScanner != null;
                    if (!fileScanner.hasNextLine()) break;
                    scanCommand(fileScanner, tokens, currentCommand, tmpFile, mode);
                }
            }
        }
    }
}
