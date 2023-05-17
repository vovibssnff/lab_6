package cmn.cmd;

import cmn.OutputEngine;
import cmn.service.ReceiverInterface;
import cmn.service.UsrInputInterface;
import cmn.service.Transmitter;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class ExecuteScriptCmd implements Command {

    public ExecuteScriptCmd(UsrInputInterface usrInputReceiver, String filename) {
        this.usrInputReceiver = usrInputReceiver;
        this.filename = filename;
    }
    private UsrInputInterface usrInputReceiver;
    private static final ArrayDeque<File> fileMemory = new ArrayDeque<>();
    private final ArrayList<Transmitter> commands = new ArrayList<>();
    private ReceiverInterface labWorkService;
    private String filename;
    @Override
    public void setUsrInputReceiver(UsrInputInterface usrInputReceiver) {
        this.usrInputReceiver=usrInputReceiver;
    }
    @Override
    public void setLabWorkService(ReceiverInterface labWorkService) {
        this.labWorkService = labWorkService;
    }

    @Override
    public void setArg(String arg) {
        if (arg!=null) {
            this.filename = arg;
        }
    }
    @Override
    public void execute() {
        if (filename.equals("")) {
            return;
        }
        File file = new File(filename);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            System.out.println(OutputEngine.accessError());
            return;
        }
        if (fileMemory.contains(file)) {
            System.out.println("Recursion detected!");
            return;
        }


        this.labWorkService.executeScript(file);
//        if (filename.equals("")) {
//            return;
//        }
//        File file = new File(filename);
//        if (!file.exists() || !file.isFile() || !file.canRead()) {
//            System.out.println(OutputEngine.accessError());
//            return;
//        }
//        if (fileMemory.contains(file)) {
//            System.out.println("Recursion detected!");
//            return;
//        }
//        try {
//            Path path = Paths.get(filename);
//            List<String> lines = null;
//            try {
//                lines = Files.readAllLines(path);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            for (String line : lines) {
//                Command command = CommandHadler;
//                if (executable == null) {
//                    continue;
//                }
//                executable.execute();
//                fileHistory.remove(this);
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading script file: " + fileName);
//        }


    }

    public static void removeFile(File file) {
        fileMemory.remove(file);
    }
    public static String getName() {return "execute_script";}
}
