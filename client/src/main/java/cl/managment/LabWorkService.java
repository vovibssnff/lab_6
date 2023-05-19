package cl.managment;

import cl.io.CommandHandler;
import cmn.OutputEngine;
import cmn.service.LabWorkBuilder;
import cmn.service.LabWorkStorage;
import cmn.cmd.*;
import cmn.data.LabWork;
import cmn.service.Transmitter;
import cmn.service.ReceiverInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LabWorkService implements ReceiverInterface {
    public int iterations=0;
    @Override
    public void help() {RequestService.sendRequest(new Transmitter(HelpCmd.getName(), null, null, null));}
    @Override
    public void info() {
        RequestService.sendRequest(new Transmitter(InfoCmd.getName(), null, null, null));
    }
    @Override
    public void soutCollection() {
        RequestService.sendRequest(new Transmitter(SoutCollectionCmd.getName(), null, null, null));
    }
    @Override
    public void addElem(LabWork elem) {
        RequestService.sendRequest(new Transmitter(AddCmd.getName(), null, null, elem));
    }
    @Override
    public void update(LabWork elem) {
        RequestService.sendRequest(new Transmitter(UpdateCmd.getName(), elem.getId(), null, elem));
    }
    @Override
    public void removeById(Long id) {
        RequestService.sendRequest(new Transmitter(RemoveByIdCmd.getName(), id, null, null));
    }
    @Override
    public void clear() {
        RequestService.sendRequest(new Transmitter(ClearCmd.getName(), null, null, null));
    }
    @Override
    public void executeScript(File file) {
        String filename = file.getName();
        Path path = Paths.get(filename);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Iterator<String> iterator = lines.iterator();
        Command command = null;
        while (iterator.hasNext()) {
            String line = iterator.next();
            try {
                command = CommandHandler.castCommand(line);
            } catch (RuntimeException e) {
                System.out.println(OutputEngine.incorrectArg());
            }

            if (command == null) {
                continue;
            }

            if (command instanceof LabWorkStorage) {
                LabWorkBuilder lb = new LabWorkBuilder();

                // Read the next 10 lines and store them in an array
                List<String> argsList = new ArrayList<>();
                for (int i = 0; i < 10 && iterator.hasNext(); i++) {
                    argsList.add(iterator.next());
                }

                // Convert the list of arguments to an array
                String[] args = argsList.toArray(new String[0]);

                // Skip the next 10 lines by advancing the iterator
//                for (int i = 0; i < 11 && iterator.hasNext(); i++) {
//                    iterator.next();
//                }

                // Pass the arguments to the command
                lb.setArgs(args);
                ((LabWorkStorage) command).setLab(lb.getLabWork());
                //RequestService.sendRequest(new Transmitter(AddCmd.getName(), null, null, lb.getLabWork()));
            }

            CommandHandler.launchInvoke(command);
        }

//        for (String line : lines) {
//            Command command = CommandHandler.castCommand(line);
//            if (command == null) {
//                continue;
//            }
//            if (command instanceof LabWorkStorage) {
//
//                // Тут берёт Н строк
//                // var lab = parseMovie(String[] args)
//                LabWorkBuilder lb = new LabWorkBuilder();
//                // lb.putArgs(String[])
//                // if lab != null -> command.setLab(lab)
//            }
//            CommandHandler.launchInvoke(command);
//
//        }
        // For line in file
        // Command a = CommandFactory.createCommnad(line)
        // a.execute() или что-то типа

    }
    @Override
    public void exit() {
        System.exit(0);
    }
    @Override
    public void head() {
        RequestService.sendRequest(new Transmitter(HeadCmd.getName(), null, null, null));
    }
    @Override
    public void removeLower(Long id) {
        RequestService.sendRequest(new Transmitter(RemoveLowerCmd.getName(), id, null, null));
    }
    @Override
    public void history() {
        RequestService.sendRequest(new Transmitter(HistoryCmd.getName(), null, null, null));
    }
    @Override
    public void countLessThanMinimalPoint(Double minimalPoint) {
        RequestService.sendRequest(new Transmitter(CountLessThanMinimalPointCmd.getName(), null, minimalPoint, null));
    }
    @Override
    public void printUniqueAuthor() {
        RequestService.sendRequest(new Transmitter(PrintUniqueAuthorCmd.getName(), null, null, null));
    }
    @Override
    public void printFieldDescendingMinimalPoint() {
        RequestService.sendRequest(new Transmitter(PrintFieldDescendingMinimalPointCmd.getName(), null, null, null));
    }
}
