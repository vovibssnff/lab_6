package cmn.cmd;

import cmn.service.ReceiverInterface;

public class PrintFieldDescendingMinimalPointCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.printFieldDescendingMinimalPoint();
    }
    public static String getName() {return "pfdmp";}
}
