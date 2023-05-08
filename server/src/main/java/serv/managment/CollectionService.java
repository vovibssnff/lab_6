package serv.managment;

import cmn.cmd.Command;
import cmn.data.LabWork;
import cmn.data.Person;
import cmn.OutputEngine;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс, ответственный за работу со всеми коллекциями
 * @author mc_vovi
 */
public class CollectionService {
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

    public static void clearSets() {
        idSet.clear();
        passportIdSet.clear();
    }

    /**
     * Печать истории команд
     */
    public static String printHistory() {
        if (commandList.size()==1) {
            return commandList.get(0).getClass().getName();
        } else {
            return commandList.stream()
                    .map(cmd -> cmd + "\n").collect(Collectors.joining());
        }
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
    public static void addElem(LabWork elem) {
        collection.add(elem);
        sortCollection();
        //return OutputEngine.successAddElem();
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
            //System.out.println(OutputEngine.successAddElems());
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
            return collection.stream()
                    .map(elem -> "_________________________________" +
                    "\nid: " + elem.getId() +
                    "\nname: " + elem.getName() +
                    "\ncoordinates: " + elem.getCoordinates().getX() + " " + elem.getCoordinates().getY() +
                    "\ncreationDate: " + elem.getCreationDate() +
                    "\nminimalPoint: " + elem.getMinimalPoint() +
                    "\ndifficulty: " + elem.getDifficulty() +
                    "\nauthor: " + "\n=============" + elem.getAuthor().toString() + "\n=============" +
                    "\n#################################\n")
                    .collect(Collectors.joining());
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
     * Удаление старого элемента коллекции и добавление нового с тем же id
     * @param id - порядковый номер необходимого элемента
     * @param elem - обновленный объект
     */
    public static void update(Long id, LabWork elem) {
        CollectionService.removeById(collection.stream()
                .filter(entry -> Objects.equals(entry.getId(), id)).findFirst().get().getId());
        elem.setId(id);
        collection.add(elem);
        sortCollection();
    }

    /**
     * Сортировка коллекции при помощи кастомного компаратора
     */
    public static void sortCollection() {
        CollectionComparator comparator = new CollectionComparator();
        List<LabWork> collectionArray = collection.stream().sorted(comparator).toList();
        collection.clear();
        collection.addAll(collectionArray);

    }

    /**
     * Печать первого элемента коллекции
     */
    public static String printFirstElem() {
        if (!CollectionService.getCollection().isEmpty()) {
            return collection.stream().map(elem -> "_________________________________" +
                    "\nid: " + elem.getId() +
                    "\nname: " + elem.getName() +
                    "\ncoordinates: " + elem.getCoordinates().getX() + " " + elem.getCoordinates().getY() +
                    "\ncreationDate: " + elem.getCreationDate() +
                    "\nminimalPoint: " + elem.getMinimalPoint() +
                    "\ndifficulty: " + elem.getDifficulty() +
                    "\nauthor: " + "\n=============" + elem.getAuthor().toString() + "\n=============" +
                    "\n#################################").findFirst().get();
        } else {
            return (OutputEngine.collectionEmpty());
        }

    }

    /**
     * Печать значений minimalPoint элементов основной коллекции
     */
    public static String printMinimalPoints() {
        if (collection.isEmpty()) {
            return (OutputEngine.collectionEmpty());
        } else {
            return collection.stream().map(LabWork::getMinimalPoint).toList().toString();
        }
    }

    /**
     * Удаление элемента коллекции по id
     * @param id - id удаляемого элемента
     */
    public static String removeById(long id) {
        System.out.println("What a fuck");
        if (idSet.contains(id)) {
            LabWork elem = collection.stream().filter(entry -> entry.getId().equals(id)).findFirst().get();
            System.out.println(elem.getName() + elem.getColor().toString());
            idSet.remove(elem.getId());
            passportIdSet.remove(elem.getAuthor().getPassportID());
            collection.remove(elem);
            return null;
        } else {
            sortCollection();
            return (OutputEngine.idNotFoundError());
        }
    }

    /**
     * Счет количества элементов, имеющих значение minimalPoint меньше указанного
     * @param minimalPoint - minimalPoint
     * @return n - искомое количество
     */
    public static Long countLessThanMinimalPoint(double minimalPoint) {
        return collection.stream().filter(entry -> entry.getMinimalPoint()<minimalPoint).count();
    }

    /**
     * Печать уникальных объектов класса
     * @see Person
     * , принадлежащих элементам коллекции
     */
    public static String printUniqueAuthor() {
        return collection.stream()
                .map(entry -> "\n=============" + entry.getAuthor().toString()+"\n=============\n")
                .collect(Collectors.joining());
    }

    /**
     * Удаление всех элементов коллекции, id которых меньше заданного
     * @param id - заданный id
     */
    public static void removeLower(long id) {
        List<LabWork> newCollection = collection.stream().filter(entry -> entry.getId()>=id).toList();
        CollectionService.setCollection(new ArrayDeque<>(newCollection));
    }

    public static void setCollection(ArrayDeque<LabWork> c) {
        collection = c;
    }
}
