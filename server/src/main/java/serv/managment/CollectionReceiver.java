package serv.managment;
import cmn.service.Transmitter;
import cmn.OutputEngine;
import serv.load.CollectionLoader;

import java.lang.reflect.Method;


/**
 * Класс, выполняющий команды
 */
public class CollectionReceiver {
    public int iterations=0;

    public String processTransmitter(Transmitter transmitter) {

        String methodName = transmitter.getCommand();
        CollectionService.addCommand(methodName);
        //CollectionLoader.save(ServerState.getTmpFile());
        try {
            Class<?> clazz = CollectionReceiver.class;
            Method method = clazz.getDeclaredMethod(methodName, Transmitter.class);
            return (String) method.invoke(this, transmitter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String info(Transmitter transmitter) {
        return OutputEngine.collectionName() + " collection" + "\n" + OutputEngine.collectionType() + " " +
                CollectionService.getCollection().getClass().getSimpleName() + "\n" +
                OutputEngine.collectionSize() + " " + CollectionService.getCollection().size();
    }

    public String show(Transmitter transmitter) {
        return CollectionService.printCollection();
    }

    public String help(Transmitter transmitter) {return ("help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "add : добавить новый элемент в коллекцию\n" +
            "update {id} : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_by_id {id} : удалить элемент из коллекции по его id\n" +
            "clear : очистить коллекцию\n" +
            "execute_script {file_name} : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "head : вывести первый элемент коллекции\n" +
            "remove_lower {id} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
            "history : вывести последние 12 команд (без их аргументов)\n" +
            "cltmp {minimal_point}: вывести количество элементов, значение поля minimalPoint которых меньше заданного\n" +
            "print_unique_authors : вывести уникальные значения поля author всех элементов в коллекции\n" +
            "pfdmp : вывести значения поля minimalPoint всех элементов в порядке убывания");
    }

    public void add(Transmitter transmitter) {
        if (Validator.checkId(transmitter.getElem().getId())&&Validator.checkPassportId(transmitter.getElem().getAuthor().getPassportID())) {
            CollectionService.addElem(transmitter.getElem());
        } else {
            System.out.println(OutputEngine.incorrectId());
        }

    }

    public void update(Transmitter transmitter) {
        if (!Validator.checkId(transmitter.getId())) {
            CollectionService.update(transmitter.getId(), transmitter.getElem());
        }
    }

    public String remove_by_id(Transmitter transmitter) {
        if (Validator.checkId(transmitter.getId())) {
            return CollectionService.removeById(transmitter.getId());
        } else {
            return OutputEngine.incorrectId();
        }
    }

    public String clear(Transmitter transmitter) {
        if (!CollectionService.getCollection().isEmpty()) {
            return CollectionService.clearCollection();
        } else {
            return OutputEngine.collectionEmpty();
        }
    }

    public String head(Transmitter transmitter) {
        return CollectionService.printFirstElem();
    }
    public void remove_lower(Transmitter transmitter) {
        if (Validator.checkId(transmitter.getId())) {
            CollectionService.removeLower(transmitter.getId());
        }
    }

    public String history(Transmitter transmitter) {
        return CollectionService.printHistory();
    }

    public String cltmp(Transmitter transmitter) {
        return CollectionService.countLessThanMinimalPoint(transmitter.getMinimalPoint()).toString();
    }

    public String print_unique_authors(Transmitter transmitter) {
        return CollectionService.printUniqueAuthor();
    }

    public String pfdmp(Transmitter transmitter) {
        return CollectionService.printMinimalPoints();
    }
}
