package cmn.cmd;


import cmn.service.ReceiverInterface;

public class HeadCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.head();
    }
    public static String getName() {return "head";}
}
