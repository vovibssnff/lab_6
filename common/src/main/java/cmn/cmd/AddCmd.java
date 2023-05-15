package cmn.cmd;

import cmn.ReceiverInterface;
import cmn.UsrInputInterface;
import cmn.data.LabWork;

public class AddCmd implements Command {
    public AddCmd(UsrInputInterface usrInput) {
        LabWork elem = usrInput.add();
        if (elem!=null) {
            this.elem=elem;
        }
    }
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
