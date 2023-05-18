package cmn.cmd;

import cmn.service.ReceiverInterface;
import cmn.service.UsrInputInterface;
import cmn.data.LabWork;

public class UpdateCmd implements Command {
    private UsrInputInterface usrInputReceiver;
    private ReceiverInterface labWorkService;
    private Long id;
    private LabWork elem;

    public UpdateCmd(UsrInputInterface usrInput, String arg) {
        this.usrInputReceiver = usrInput;
        this.id = Long.parseLong(arg);
    }

    public UpdateCmd(String arg) {
        if (this.usrInputReceiver.typeValidator(arg, Long.class)) {
            this.id = this.usrInputReceiver.setArg(arg, Long.class);
            this.elem = this.usrInputReceiver.update(this.id);
        }
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
    public void setArg(String arg) {

    }
    @Override
    public void execute() {
        if (this.elem==null) {
            elem = this.usrInputReceiver.update(this.id);
        }
        this.labWorkService.update(this.elem);
    }
    public static String getName() {return "update";}
}
