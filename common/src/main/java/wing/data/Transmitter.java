package wing.data;

import wing.cmd.Command;

public class Transmitter {
    private String command;
    private Long id;
    private Double minimalPoint;
    private LabWork elem;
    public Transmitter(String command, Long id, Double minimalPoint, LabWork elem) {
        this.command = command;
        this.id = id;
        this.minimalPoint = minimalPoint;
        this.elem = elem;
    }

    public String getCommand() {
        return command;
    }

    public Long getId() {
        return id;
    }

    public Double getMinimalPoint() {
        return minimalPoint;
    }

    public LabWork getElem() {
        return elem;
    }
}
