package cmn.cmd;

import cmn.service.LabWorkBuilder;
import cmn.service.LabWorkStorage;
import cmn.service.ReceiverInterface;
import cmn.service.UsrInputInterface;
import cmn.data.LabWork;

public class AddCmd implements Command, LabWorkStorage {

    private UsrInputInterface usrInputReceiver;
    private ReceiverInterface labWorkService;
    private LabWork elem;
    public AddCmd(UsrInputInterface usrInput) {
        this.usrInputReceiver = usrInput;
//        LabWorkBuilder labWorkBuilder = new LabWorkBuilder();
//        LabWork elem =
//        if (elem!=null) {
//            this.elem=elem;
//        }
    }
    @Override
    public void setUsrInputReceiver(UsrInputInterface usrInputReceiver) {
        this.usrInputReceiver=usrInputReceiver;
    }
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        if (this.elem==null) {
            // добавление от пользователя
            elem = this.usrInputReceiver.add();
        }
        this.labWorkService.addElem(this.elem);
    }
    public static String getName() {
        return "add";
    }

    @Override
    public void setLab(LabWork lw) {
        elem = lw;
    }
}
