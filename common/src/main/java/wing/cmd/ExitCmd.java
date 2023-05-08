package wing.cmd;

public class ExitCmd implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }
    public static String getName() {return "exit";}
}
