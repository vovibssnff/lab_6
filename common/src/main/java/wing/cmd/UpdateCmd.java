package wing.cmd;

import wing.ReceiverInterface;
import wing.UsrInputInterface;
import wing.data.LabWork;

public class UpdateCmd implements Command {
    private UsrInputInterface usrInputReceiver;
    private ReceiverInterface labWorkService;
    private Long id;
    private LabWork labWork;
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
        if (this.usrInputReceiver.typeValidator(arg, Long.class)) {
            this.id = this.usrInputReceiver.setArg(arg, Long.class);
            this.labWork = this.usrInputReceiver.update(this.id);
        }
    }
    @Override
    public void execute() {
        this.labWorkService.update(this.labWork);
    }
    public static String getName() {return "update";}
}
