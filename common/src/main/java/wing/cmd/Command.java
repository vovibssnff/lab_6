package wing.cmd;


import wing.ReceiverInterface;
import wing.UsrInputInterface;


public interface Command {

    void execute();
    default void setArg(String arg) {}
    default void setUsrInputReceiver(UsrInputInterface usrInputReceiver) {}
    default void setLabWorkService(ReceiverInterface labWorkService) {}
}
