package wing.managment;
import wing.data.LabWork;
import wing.data.Transmitter;
import wing.load.Serializer;
import wing.OutputEngine;

import java.lang.reflect.Method;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Класс, выполняющий команды
 */
public class CollectionReceiver {
    public int iterations=0;

    public String processTransmitter(Transmitter transmitter) {
        String methodName = transmitter.getCommand();

        try {
            Class<?> clazz = CollectionReceiver.class;
            Method method = clazz.getDeclaredMethod(methodName, Transmitter.class);
            return (String) method.invoke(this, transmitter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void info(Transmitter transmitter) {
        System.out.println(OutputEngine.collectionName() + " collection");
        System.out.println(OutputEngine.collectionType() + " " + Collections.getCollection().getClass().getSimpleName());
        System.out.println(OutputEngine.collectionSize() + " " + Collections.getCollection().size());
    }

    public String show(Transmitter transmitter) {
        return Collections.printCollection();
    }


    public void add(Transmitter transmitter) {
        Collections.addElem(transmitter.getElem());
    }
    public void update(Transmitter transmitter) {
        Collections.update(Collections.searchInCollection(transmitter.getId()), transmitter.getElem());
    }
    public void remove_by_id(Transmitter transmitter) {
        Collections.removeById(transmitter.getId());
    }
    public void clear(Transmitter transmitter) {
        if (!Collections.getCollection().isEmpty()) {
            Collections.clearCollection();
        } else {
            System.out.println(OutputEngine.collectionEmpty());
        }
    }
    //public void save() {
//        Serializer.save(Collections.getCollection());
//    }
    public void execute_script(Transmitter transmitter) {
        iterations++;
        if (iterations>499) {
            System.out.println(OutputEngine.stackOverflowError());
            return;
        }
        //ServerConnector.launcher(null, Mode.FILE, null, filename);
    }

    public String head(Transmitter transmitter) {
        return Collections.printFirstElem();
    }
    public void remove_lower(Transmitter transmitter) {
        Collections.removeLower(transmitter.getId());
    }
    public void history(Transmitter transmitter) {
        Collections.printHistory();
    }
    public void cltmp(Transmitter transmitter) {
        Collections.countLessThanMinimalPoint(transmitter.getMinimalPoint());
    }
    public void print_unique_authors(Transmitter transmitter) {
        Collections.printUniqueAuthor();
    }
    public void pfdmp(Transmitter transmitter) {
        Collections.printMinimalPoints();
    }
}
