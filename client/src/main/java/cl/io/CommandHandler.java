package cl.io;

import cl.managment.CollectionsEngine;
import cl.managment.ProgramState;
import cmn.OutputEngine;
import cmn.service.UsrInputInterface;
import cmn.cmd.Command;

import java.lang.reflect.InvocationTargetException;

public class CommandHandler {
    public static Command castCommand(String input) {
        String[] tokens = input.split(" ");
        Class<? extends Command> command = CollectionsEngine.searchCommand(tokens[0]);
        //    command.setArg(tokens[1]);
        Command currentCommand;
        if (tokens.length<2) {
            try {
                currentCommand =  command.getConstructor(UsrInputInterface.class).newInstance(ProgramState.getUsrInputReceiver());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                try {
                    currentCommand =  command.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e1) {
                    System.out.println(OutputEngine.incorrectArg());
                    throw new RuntimeException(e1);
                }
            }
        } else {
            try {
                currentCommand = command.getConstructor(UsrInputInterface.class, String.class).newInstance(ProgramState.getUsrInputReceiver(), tokens[1]);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println(OutputEngine.incorrectArg());
                throw new RuntimeException(e);
            }
        }
        return currentCommand;
    }

    public static void launchInvoke(Command command) {
        command.setLabWorkService(ProgramState.getLabWorkService());
        command.execute();
    }
}
