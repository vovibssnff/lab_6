package cmn.cmd;

import cmn.ReceiverInterface;
import cmn.UsrInputInterface;

public class ExecuteScriptCmd implements Command {
    private UsrInputInterface usrInputReceiver;
    private ReceiverInterface labWorkService;
    private String filename;
    @Override
    public void setUsrInputReceiver(UsrInputInterface usrInputReceiver) {
        this.usrInputReceiver=usrInputReceiver;
    }
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService = labWorkService;
    }

    @Override
    public void setArg(String arg) {
        if (arg!=null) {
            this.filename = arg;
        }
    }
    @Override
    public void execute() {
        if (!filename.equals("")&&!filename.equals(null)) {
            this.labWorkService.executeScript(this.filename);
        }
    }
    public static String getName() {return "execute_script";}
}
