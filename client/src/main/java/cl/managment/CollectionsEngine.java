package cl.managment;

import cmn.cmd.Command;
import cmn.data.LabWork;

import java.util.*;

/**
 * Класс, ответственный за работу со всеми коллекциями
 * @author mc_vovi
 */
public class CollectionsEngine {
    private static final Map<Integer, byte[]> packetBuffer = new HashMap<>();
    private static final Map<Integer, Long> sentTimestamps = new HashMap<>();
    private static final Map<String, Command> commandMap = new HashMap<>(); //коллекция для идентификации введенных команд

    /**
     * Добавление в commandMap новой команды
     * @param key - ключ, имя команды
     * @param value - значение, объект команды
     */
    public static void addElemToCommandMap(String key, Command value) {
        commandMap.put(key, value);
    }
    public static String printCommandMap() {
        return commandMap.toString();
    }
    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }

    /**
     * Поиск в HashMap объект необходимой команды по её названию
     * @param command - искомая команда
     * @return command
     */
    public static Command searchCommand(String command) {
        Command cmd = null;
        for (Map.Entry<String, Command> entry : commandMap.entrySet()) {
            String key = entry.getKey();
            if (key.equals(command)) {
                cmd = entry.getValue();
                return cmd;
            }
        }
        return cmd;
    }



    /**
     * Добавление в коллекцию элементов из списка ArrayList
     * @param labWorkArrayList - список объектов
     * @see LabWork
     * @throws NullPointerException - бросает исключение при пустом списке
     */
//    public static void addElemsFromList(ArrayList<LabWork> labWorkArrayList) throws NullPointerException {
//        try {
//            collection.addAll(labWorkArrayList);
//            System.out.println(OutputEngine.successAddElems());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public static void setCollection(ArrayDeque<LabWork> c) {
//        collection = c;
//    }
}
