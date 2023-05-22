package cmn.service;

import cmn.data.LabWork;

public class Transmitter {
    private String command;
    private Long id;
    private Double minimalPoint;
    private LabWork elem;
    private String message;
    public Transmitter(String command, Long id, Double minimalPoint, LabWork elem, String message) {
        this.command = command;
        this.id = id;
        this.minimalPoint = minimalPoint;
        this.elem = elem;
        this.message = message;
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
    public String getMessage() {return message;}
}
