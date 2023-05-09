package serv.managment;
import cmn.data.Transmitter;
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
        Collections.addCommand(methodName);
        CollectionLoader.save(ServerState.getTmpFile());
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
                Collections.getCollection().getClass().getSimpleName() + "\n" +
                OutputEngine.collectionSize() + " " + Collections.getCollection().size();
    }

    public String show(Transmitter transmitter) {
        return Collections.printCollection();
    }


    public String add(Transmitter transmitter) {
        return Collections.addElem(transmitter.getElem());
    }
    public void update(Transmitter transmitter) {
        Collections.update(Collections.searchInCollection(transmitter.getId()), transmitter.getElem());
    }
    public String remove_by_id(Transmitter transmitter) {
        return Collections.removeById(transmitter.getId());
    }
    public String clear(Transmitter transmitter) {
        if (!Collections.getCollection().isEmpty()) {
            return Collections.clearCollection();
        } else {
            return OutputEngine.collectionEmpty();
        }
    }
    //public void save() {
//        Serializer.save(Collections.getCollection());
//    }

    public String head(Transmitter transmitter) {
        return Collections.printFirstElem();
    }
    public void remove_lower(Transmitter transmitter) {
        Collections.removeLower(transmitter.getId());
    }
    public String history(Transmitter transmitter) {
        return Collections.printHistory();
    }
    public String cltmp(Transmitter transmitter) {
        return Collections.countLessThanMinimalPoint(transmitter.getMinimalPoint()).toString();
    }
    public String print_unique_authors(Transmitter transmitter) {
        return Collections.printUniqueAuthor();
    }
    public String pfdmp(Transmitter transmitter) {
        return Collections.printMinimalPoints();
    }
}
