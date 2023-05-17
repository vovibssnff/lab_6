package cmn.cmd;


import cmn.service.ReceiverInterface;

public class PrintUniqueAuthorCmd implements Command {
    private ReceiverInterface labWorkService;
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService=labWorkService;
    }
    @Override
    public void execute() {
        this.labWorkService.printUniqueAuthor();
    }
    public static String getName() {return "print_unique_authors";}
}
