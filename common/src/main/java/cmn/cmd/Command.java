package cmn.cmd;


import cmn.ReceiverInterface;
import cmn.UsrInputInterface;


public interface Command {

    void execute();
    default void setArg(String arg) {}
    default void setUsrInputReceiver(UsrInputInterface usrInputReceiver) {}
    default void setLabWorkService(ReceiverInterface labWorkService) {}
}
