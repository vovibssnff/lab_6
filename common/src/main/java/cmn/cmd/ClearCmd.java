package cmn.cmd;

import cmn.service.ReceiverInterface;

public class ClearCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.clear();
    }
    public static String getName() {return "clear";}
}
