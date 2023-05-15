package cmn;

import cmn.data.LabWork;

import java.io.File;

public interface ReceiverInterface {
    void help();
    void info();
    void soutCollection();
    void addElem(LabWork elem);
    void update(LabWork elem);
    void removeById(Long id);
    void clear();
    void executeScript(File file);
    void exit();
    void head();
    void removeLower(Long id);
    void history();
    void countLessThanMinimalPoint(Double minimalPoint);
    void printUniqueAuthor();
    void printFieldDescendingMinimalPoint();

}
