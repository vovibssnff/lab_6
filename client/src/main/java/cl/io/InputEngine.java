package cl.io;

import cmn.cmd.*;
import cl.managment.*;
import cmn.OutputEngine;
import cl.ClientConnectionService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class InputEngine {

    private static final File tmpFile = new File("unsaved.tmp");
    public static String resp = null;
    public static void init() {
        ProgramState.setUsrInputReceiver(new UsrInputReceiver());
        ProgramState.setLabWorkService(new LabWorkService());
        ClientConnectionService.initConnection();
        CollectionsEngine.addElemToCommandMap(AddCmd.getName(), AddCmd.class);
        CollectionsEngine.addElemToCommandMap(HelpCmd.getName(), HelpCmd.class);
        CollectionsEngine.addElemToCommandMap(SoutCollectionCmd.getName(), SoutCollectionCmd.class);
        CollectionsEngine.addElemToCommandMap(HistoryCmd.getName(), HistoryCmd.class);
        CollectionsEngine.addElemToCommandMap(PrintUniqueAuthorCmd.getName(), PrintUniqueAuthorCmd.class);
        CollectionsEngine.addElemToCommandMap(ClearCmd.getName(), ClearCmd.class);
        CollectionsEngine.addElemToCommandMap(HeadCmd.getName(), HelpCmd.class);
        CollectionsEngine.addElemToCommandMap(InfoCmd.getName(), InfoCmd.class);
        CollectionsEngine.addElemToCommandMap(ExitCmd.getName(), ExitCmd.class);
        CollectionsEngine.addElemToCommandMap(UpdateCmd.getName(), UpdateCmd.class);
        CollectionsEngine.addElemToCommandMap(PrintFieldDescendingMinimalPointCmd.getName(), PrintFieldDescendingMinimalPointCmd.class);
        CollectionsEngine.addElemToCommandMap(CountLessThanMinimalPointCmd.getName(), CountLessThanMinimalPointCmd.class);
        CollectionsEngine.addElemToCommandMap(RemoveLowerCmd.getName(), RemoveLowerCmd.class);
        CollectionsEngine.addElemToCommandMap(RemoveByIdCmd.getName(), RemoveByIdCmd.class);
        CollectionsEngine.addElemToCommandMap(ExecuteScriptCmd.getName(), ExecuteScriptCmd.class);
        System.out.println(OutputEngine.greeting_msg());
        ProgramState.setScanner(ProgramState.getKeyboardScanner());
        ProgramState.setMode(Mode.DEFAULT);
        //Pattern pattern = Pattern.compile("^[yn]$");
        while (true) {
            try {
                System.out.print(OutputEngine.prompt());
                String input = ProgramState.getKeyboardScanner().nextLine().trim();
                Command command = CommandHandler.castCommand(input);
                CommandHandler.launchInvoke(command);
            } catch (NullPointerException e) {
                System.out.println(OutputEngine.incorrectCommand());
            }
        }
        //Восстановление старых данных
        //modeSwitcher(null, null);
    }

    //TODO REMOVE THIS FUCKING DEPRECATED SHIT
    public static void modeSwitcher(Command currentCommand, File file) {
        String[] tokens = new String[0];
        switch (ProgramState.getMode()) {

            //Режим чтения команд с клавиатуры
            case DEFAULT -> {
                ProgramState.setScanner(ProgramState.getKeyboardScanner());
                //Основной сканер
                while (true) {

                }
            }

            //Режим чтения команд из скрипта
            case FILE -> {
                Scanner fileScanner = null;
                try {
                    fileScanner = new Scanner(file);
                    ProgramState.setMode(Mode.FILE);
                    ProgramState.setScanner(fileScanner);
                } catch (FileNotFoundException e) {
                    e.getStackTrace();
                }
                while (true) {
                    assert fileScanner != null;
                    if (!fileScanner.hasNextLine()) {
                        ExecuteScriptCmd.removeFile(file);
//                        ProgramState.setMode(Mode.DEFAULT);
//                        ProgramState.setScanner(ProgramState.getKeyboardScanner());
                        break;
                    }
                    //CommandHandler.castCommand(null);
                }
            }
        }
    }
}
