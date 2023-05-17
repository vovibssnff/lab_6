package cmn.cmd;


import cmn.OutputEngine;
import cmn.service.ReceiverInterface;
import cmn.service.UsrInputInterface;

public class CountLessThanMinimalPointCmd implements Command {
    private UsrInputInterface usrInputReceiver;
    private ReceiverInterface labWorkService;
    private Double minimalPoint;
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
        if (this.usrInputReceiver.typeValidator(arg, Double.class)) {
            this.minimalPoint = this.usrInputReceiver.setArg(arg, Double.class);
        }
    }
    @Override
    public void execute() {
        if (this.minimalPoint!=null) {
            this.labWorkService.countLessThanMinimalPoint(this.minimalPoint);
        } else {
            System.out.println(OutputEngine.incorrectDoubleArg());
        }
    }
    public static String getName() {return "cltmp";}
}
