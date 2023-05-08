package wing.managment;

import wing.data.*;
import wing.io.InputEngine;
import wing.OutputEngine;
import wing.ReceiverInterface;

public class LabWorkService implements ReceiverInterface {
    public int iterations=0;
    @Override
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
    @Override
    public void info() {}
    @Override
    public void soutCollection() {
        //System.out.println(ClientConnectionService.sendRequest("show"));
    }
    @Override
    public void addElem(LabWork elem) {}
    @Override
    public void update(LabWork elem) {}
    @Override
    public void removeById(Long id) {}
    @Override
    public void clear() {}
    @Override
    public void executeScript(String filename) {
        iterations++;
        if (iterations>499) {
            System.out.println(OutputEngine.stackOverflowError());
            return;
        }
        InputEngine.modeSwitcher(null, filename);
    }
    @Override
    public void exit() {
        System.exit(0);
    }
    @Override
    public void head() {}
    @Override
    public void removeLower(Long id) {}
    @Override
    public void history() {}
    @Override
    public void countLessThanMinimalPoint(Double minimalPoint) {}
    @Override
    public void printUniqueAuthor() {}
    @Override
    public void printFieldDescendingMinimalPoint() {}
}
