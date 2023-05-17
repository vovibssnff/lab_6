package cmn.cmd;

import cmn.service.ReceiverInterface;

public class SoutCollectionCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.soutCollection();
    }
    public static String getName() {return "show";}
}
