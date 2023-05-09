package serv.managment;

import cmn.cmd.Command;
import cmn.data.LabWork;
import cmn.data.Person;
import cmn.OutputEngine;

import java.util.*;

/**
 * Класс, ответственный за работу со всеми коллекциями
 * @author mc_vovi
 */
public class Collections {
    private static final ArrayList<String> commandList = new ArrayList<>(); //журнал истории команд
    private static final Map<String, Command> commandMap = new HashMap<>(); //коллекция для идентификации введенных команд
    private static final HashSet<Long> idSet = new HashSet<Long>(); //множество значений id класса LabWork
    private static final HashSet<String> passportIdSet = new HashSet<>(); //множество значений passportId класса Person
    private static ArrayDeque<LabWork> collection = new ArrayDeque<LabWork>(); //основная коллекция объектов LabWork

    /**
     * Добавление в журнал истории команд очередной команды
     * @param command - выполненная команда
     */
    public static void addCommand(String command) {
        commandList.add(command);
    }

    /**
     * Печать истории команд
     */
    //TODO переделать на возврат строки вместо принта
    public static String printHistory() {
        String string1 = "";
        String string2 = "";
        String stringRes = "";
        if (commandList.size()==1) {
            return commandList.get(0).getClass().getName();
        } else {
            int i;
            for (i=1; i<=commandList.size(); i++) {
                string1 = commandList.get(commandList.size()-i)+"\n";
                stringRes = string1 + string2;
                string2 = stringRes;
            }
            return stringRes;
        }
    }
    public static List<String> getHistory() {
        return commandList;
    }

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
     * Проверка, были ли вызваны команды, изменяющие коллекцию, после вызова команды save
     */
    public static boolean saveCheck() {
        int a=-1;
        for (int i=0; i<commandList.size(); i++) {
            if (commandList.get(i).equals(commandMap.get("save"))) {
                a=i;
            }
        }
        for (int i=a; i<commandList.size(); i++) {
            if (commandList.stream().anyMatch(cmd ->
                    cmd.equals(commandMap.get("add"))
                    || cmd.equals(commandMap.get("update"))
                    || cmd.equals(commandMap.get("delete"))
                    || cmd.equals(commandMap.get("clear"))
                    || cmd.equals(commandMap.get("remove_by_id"))
                    || cmd.equals(commandMap.get("remove_lower")))) {
                return false;
            }
        }
        return true;
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
     * Добавление в HashSet нового уникального значения id
     * @param i - новый id
     */
    public static void addId(long i) {
        idSet.add(i);
    }

    /**
     * Проверка, есть ли в HashSet указанное значение id
     * @param i - id
     */
    public static boolean containsId(long i) {
        return idSet.contains(i);
    }
    /**
     * Добавление в HashSet нового уникального значения passportId
     * @param i - новый id
     */
    public static void addPassportId(String i) {passportIdSet.add(i);}
    /**
     * Проверка, есть ли в HashSet указанное значение passportId
     * @param i - id
     */
    public static boolean containsPassportId(String i) {return passportIdSet.contains(i);}

    /**
     * Добавление нового объекта класса
     * @see LabWork
     * в коллекцию
     * @param elem - новый объект
     */
    public static String addElem(LabWork elem) {
        collection.add(elem);
        sortCollection();
        return OutputEngine.successAddElem();
    }

    /**
     * Добавление в коллекцию элементов из списка ArrayList
     * @param labWorkArrayList - список объектов
     * @see LabWork
     * @throws NullPointerException - бросает исключение при пустом списке
     */
    public static void addElemsFromList(ArrayList<LabWork> labWorkArrayList) throws NullPointerException {
        try {
            collection.addAll(labWorkArrayList);
            System.out.println(OutputEngine.successAddElems());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    /**
     * Возврат основной коллекции
     */
    public static ArrayDeque<LabWork> getCollection() {
        return collection;
    }

    /**
     * Печать основной коллекции
     */
    public static String printCollection() {
        String string1 = "";
        String string2 = "";
        String stringRes = "";
        Iterator<LabWork> iter = collection.iterator();
        if (collection.isEmpty()) {
            return (OutputEngine.collectionEmpty());
        } else {
            while (iter.hasNext()) {
                LabWork elem = iter.next();
                string1 = "_________________________________" +
                        "\nid: " + elem.getId() +
                        "\nname: " + elem.getName() +
                        "\ncoordinates: " + elem.getCoordinates().getX() + " " + elem.getCoordinates().getY() +
                        "\ncreationDate: " + elem.getCreationDate() +
                        "\nminimalPoint: " + elem.getMinimalPoint() +
                        "\ndifficulty: " + elem.getDifficulty() +
                        "\nauthor: " + "\n=============" + elem.getAuthor().toString() + "\n=============" +
                        "\n#################################";
                stringRes = string1+string2;
                string2 = stringRes;
            }
            return stringRes;
        }
    }

    /**
     * Удаление всех элементов основной коллекции
     */
    public static String clearCollection() {
        collection.clear();
        return (OutputEngine.successClear());
    }

    /**
     * Поиск элемента коллекции по id
     * @param id - id искомого элемента
     * @return index - порядковый номер искомого элемента
     */
    public static int searchInCollection(long id) {
        for (LabWork labWork : collection) {
            int index=0;
            if (labWork.getId()==id) {
                return index;
            }
            index++;
        }
        //System.out.println(OutputEngine.idNotFoundError());
        return -1;
    }

    /**
     * Удаление старого элемента коллекции и создание нового с тем же id
     * @param index - порядковый номер необходимого элемента
     * @param elem - обновленный объект
     */
    public static void update(int index, LabWork elem) {
        Iterator<LabWork> iterator = collection.iterator();
        for (int i=0; i<=index && iterator.hasNext(); i++) {
            if (i==index) {
                iterator.next();
                iterator.remove();
                collection.add(elem);
            } else {
                iterator.next();
            }
        }
        sortCollection();
    }

    /**
     * Сортировка коллекции при помощи кастомного компаратора
     */
    public static void sortCollection() {
        CollectionComparator comparator = new CollectionComparator();
        LabWork[] collectionArray = collection.toArray(new LabWork[0]);
        Arrays.sort(collectionArray, comparator);
        collection.clear();
        for (LabWork labWork : collectionArray) {
            collection.add(labWork);
        }
    }

    /**
     * Печать первого элемента коллекции
     */
    public static String printFirstElem() {
        if (!Collections.getCollection().isEmpty()) {
            LabWork elem = collection.getFirst();
            return ("_________________________________" +
                    "\nid: " + elem.getId() +
                    "\nname: " + elem.getName() +
                    "\ncoordinates: " + elem.getCoordinates().getX() + " " + elem.getCoordinates().getY() +
                    "\ncreationDate: " + elem.getCreationDate() +
                    "\nminimalPoint: " + elem.getMinimalPoint() +
                    "\ndifficulty: " + elem.getDifficulty() +
                    "\nauthor: " + "\n=============" + elem.getAuthor().toString() + "\n=============" +
                    "\n#################################");
        } else {
            return (OutputEngine.collectionEmpty());
        }

    }

    /**
     * Печать значений minimalPoint элементов основной коллекции
     */
    public static String printMinimalPoints() {
        Iterator<LabWork> iter = collection.iterator();
        ArrayList<Double> mas = new ArrayList<>();
        if (collection.isEmpty()) {
            return (OutputEngine.collectionEmpty());
        } else {
            while (iter.hasNext()) {
                LabWork elem = iter.next();
                mas.add(elem.getMinimalPoint());
            }
            for (int i=0; i<mas.size()-1; i++) {
                for (int j=i+1; j<mas.size(); j++) {
                    if (mas.get(i)<mas.get(j)) {
                        double a = mas.get(i);
                        mas.set(i, mas.get(j));
                        mas.set(j, a);
                    }
                }
            }
            return mas.toString();
        }
    }

    /**
     * Удаление элемента коллекции по id
     * @param id - id удаляемого элемента
     */
    public static String removeById(long id) {
        Iterator<LabWork> iter = collection.iterator();
        boolean found = false;
        while (iter.hasNext()) {
            if (iter.next().getId()==id) {
                found = true;
                iter.remove();
                iter.next();
            } else {
                iter.next();
            }
        }
        if (!found) {
            return (OutputEngine.idNotFoundError());
        }
        sortCollection();
        return null;
    }

    /**
     * Счет количества элементов, имеющих значение minimalPoint меньше указанного
     * @param minimalPoint - minimalPoint
     * @return n - искомое количество
     */
    public static Integer countLessThanMinimalPoint(double minimalPoint) {
        Iterator<LabWork> iter = collection.iterator();
        int n = 0;
        while (iter.hasNext()) {
            if (iter.next().getMinimalPoint()<minimalPoint) {
                n++;
            }
            iter.next();
        }
        return n;
    }

    /**
     * Печать уникальных объектов класса
     * @see Person
     * , принадлежащих элементам коллекции
     */
    public static String printUniqueAuthor() {
        String string = "";
        String stringRes = "";
        Iterator<LabWork> iter = collection.iterator();
        Set<Person> uniqueAuthors = new HashSet<>();
        while (iter.hasNext()) {
            uniqueAuthors.add(iter.next().getAuthor());
        }
        for (Person author : uniqueAuthors) {
            string = ("\n=============" + author.toString()+"\n=============");
            stringRes = stringRes+string;
        }
        stringRes = stringRes+"\n";
        return stringRes;
    }

    /**
     * Удаление всех элементов коллекции, id которых меньше заданного
     * @param id - заданный id
     */
    public static void removeLower(long id) {
        for (LabWork elem: collection) {
            if (elem.getId()==id) {
                break;
            } else {
                collection.remove(elem);
            }
        }
    }
    public static void setCollection(ArrayDeque<LabWork> c) {
        collection = c;
    }
}
