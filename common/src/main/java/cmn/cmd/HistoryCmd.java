package cmn.cmd;

import cmn.service.ReceiverInterface;

public class HistoryCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.history();
    }
    public static String getName() {return "history";}
}
