package cl.managment;

import cl.io.CommandHandler;
import cl.io.Mode;
import cmn.cmd.*;
import cmn.data.LabWork;
import cmn.data.Transmitter;
import cl.io.InputEngine;
import cmn.OutputEngine;
import cmn.ReceiverInterface;
import cl.ClientConnectionService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

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
        for (String line : lines) {
            Command command = CommandHandler.castCommand(line);
            if (command == null) {
                continue;
            }
            CommandHandler.launchInvoke(command);

        }
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
