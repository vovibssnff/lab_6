package cmn.cmd;

import cmn.service.ReceiverInterface;

public class HelpCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.help();
    }

    public static String getName() {return "help";}
}
