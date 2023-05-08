package wing.managment;
import wing.data.LabWork;
import wing.load.Serializer;
import wing.OutputEngine;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import wing.io.ElemInputService.setElemScript;

/**
 * Класс, выполняющий команды
 */
public class CollectionReceiver {
    public int iterations=0;

    public void help() {
        System.out.println("help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "add : добавить новый элемент в коллекцию\n" +
            "update {id} : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_by_id {id} : удалить элемент из коллекции по его id\n" +
            "clear : очистить коллекцию\n" +
            "save : сохранить коллекцию в файл\n" +
            "execute_script {file_name} : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "head : вывести первый элемент коллекции\n" +
            "remove_lower {id} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
            "history : вывести последние 12 команд (без их аргументов)\n" +
            "cltmp {minimal_point}: вывести количество элементов, значение поля minimalPoint которых меньше заданного\n" +
            "print_unique_authors : вывести уникальные значения поля author всех элементов в коллекции\n" +
            "pfdmp : вывести значения поля minimalPoint всех элементов в порядке убывания");
    }
    public void info() {
        System.out.println(OutputEngine.collectionName() + " collection");
        System.out.println(OutputEngine.collectionType() + " " + Collections.getCollection().getClass().getSimpleName());
        System.out.println(OutputEngine.collectionSize() + " " + Collections.getCollection().size());
    }

    public void soutCollection() {
        Collections.printCollection();
    }


    public void addElem() {
        Collections.addElem(new LabWork());
    }
    public void update(String arg, Scanner scanner, Mode mode) {
        Collections.update(Collections.searchInCollection(Long.parseLong(arg)), setElemScript(Long.parseLong(arg)));
    }
    public void removeById(Long id) {
        Collections.removeById(id);
    }
    public void clear() {
        if (!Collections.getCollection().isEmpty()) {
            Collections.clearCollection();
        } else {
            System.out.println(OutputEngine.collectionEmpty());
        }
    }
    public void save() {
        Serializer.save(Collections.getCollection());
    }
    public void executeScript(String filename) {
        iterations++;
        if (iterations>499) {
            System.out.println(OutputEngine.stackOverflowError());
            return;
        }
        //ServerConnector.launcher(null, Mode.FILE, null, filename);
    }
    public void exit() {
        if (Collections.saveCheck()) {
            System.exit(0);
        } else {
            Scanner scanner = new Scanner(System.in);
            String resp=null;
            Pattern pattern = Pattern.compile("^[yn]$");
            do {
                try {
                    System.out.println("В коллекции есть несохраненные изменения. Вы точно хотите выйти (y/n)");
                    System.out.print(OutputEngine.prompt());
                    resp = scanner.nextLine().trim();

                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            } while (!pattern.matcher(resp).matches());
            if (resp.equals("y")) {
                this.save();
                System.exit(0);
            } else {
                System.exit(0);
            }
        }
    }
    public void head() {
        Collections.printFirstElem();
    }
    public void removeLower(Long id) {
        Collections.removeLower(id);
    }
    public void history() {
        Collections.printHistory();
    }
    public void countLessThanMinimalPoint(Double minimal_point) {
        Collections.countLessThanMinimalPoint(minimal_point);
    }
    public void printUniqueAuthor() {
        Collections.printUniqueAuthor();
    }
    public void printFieldDescendingMinimalPoint() {
        Collections.printMinimalPoints();
    }
}
