package cmn.cmd;

import cmn.service.ReceiverInterface;

public class InfoCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.info();
    }
    public static String getName() {return "info";}
}
