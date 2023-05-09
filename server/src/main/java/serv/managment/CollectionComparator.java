package serv.managment;

import cmn.data.LabWork;

import java.util.Comparator;

public class CollectionComparator implements Comparator<LabWork> {
    @Override
    public int compare(LabWork labwork1, LabWork labwork2) {
        return Long.compare(labwork1.getId(), labwork2.getId());
    }
}
