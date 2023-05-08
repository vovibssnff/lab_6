package wing.cmd;

import wing.ReceiverInterface;
import wing.UsrInputInterface;
import wing.data.LabWork;

public class AddCmd implements Command {
    private UsrInputInterface usrInputReceiver;
    private ReceiverInterface labWorkService;
    private LabWork elem;
    @Override
    public void setUsrInputReceiver(UsrInputInterface usrInputReceiver) {
        this.usrInputReceiver=usrInputReceiver;
    }
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void setArg(String arg) {
        LabWork elem = this.usrInputReceiver.add();
        if (elem!=null) {
            this.elem=elem;
        }
    }
    @Override
    public void execute() {
        if (this.elem!=null) {
            this.labWorkService.addElem(this.elem);
        }
    }
    public static String getName() {
        return "add";
    }
}
