package cmn;

import cmn.data.LabWork;

public interface UsrInputInterface {
    void exit();
    LabWork add();
    LabWork update(Long arg);
    <T> boolean typeValidator(String arg, Class<T> clazz);
    <T> T setArg(String arg, Class<T> clazz);
}
