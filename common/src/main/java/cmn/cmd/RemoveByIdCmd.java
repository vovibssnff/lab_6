package cmn.cmd;

import cmn.ReceiverInterface;
import cmn.UsrInputInterface;

public class RemoveByIdCmd implements Command {
    private UsrInputInterface usrInputReceiver;
    private ReceiverInterface labWorkService;
    private Long id;

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
        if (this.usrInputReceiver.typeValidator(arg, Long.class)) {
            this.id = this.usrInputReceiver.setArg(arg, Long.class);
        }
    }
    @Override
    public void execute() {
        this.labWorkService.removeById(this.id);
    }
    public static String getName() {return "remove_by_id";}
}
