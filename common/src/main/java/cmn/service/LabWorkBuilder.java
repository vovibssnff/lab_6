package cmn.service;

import cmn.data.Color;
import cmn.data.Difficulty;
import cmn.data.LabWork;

public class LabWorkBuilder {
    private final LabWork labWork = new LabWork();

    public LabWorkBuilder setLabName(String name) throws RuntimeException{
        if (!Validator.checkName(name)) throw new RuntimeException();
        labWork.setName(name);
        return this;
    }

    public LabWorkBuilder setCoordsX(String x) throws RuntimeException{
        if (!Validator.checkCoordinatesX(Double.parseDouble(x))) throw new RuntimeException();
        labWork.getCoordinates().setX(Double.parseDouble(x));
        return this;
    }

    public LabWorkBuilder setCoordsY(String y) throws RuntimeException{
        if (!Validator.checkCoordinatesY(Long.parseLong(y))) throw new RuntimeException();
        labWork.getCoordinates().setY(Long.parseLong(y));
        return this;
    }

    public LabWorkBuilder setCreationDate() throws RuntimeException {
        labWork.setCreationDate();
        return this;
    }

    public LabWorkBuilder setMinimalPoint(String minimalPoint) throws RuntimeException {
        if (!Validator.checkMinimalPoint(Double.parseDouble(minimalPoint))) throw new RuntimeException();
        labWork.setMinimalPoint(Double.parseDouble(minimalPoint));
        return this;
    }

    public LabWorkBuilder setDifficulty(String difficulty) throws RuntimeException {
        if (!Validator.checkDifficulty(Difficulty.valueOf(difficulty.toUpperCase()))) throw new RuntimeException();
        labWork.setDifficulty(Difficulty.valueOf(difficulty.toUpperCase()));
        return this;
    }

    public LabWorkBuilder setAuthorName(String name) throws RuntimeException {
        if (!Validator.checkAuthorName(name)) throw new RuntimeException();
        labWork.getAuthor().setName(name);
        return this;
    }

    public LabWorkBuilder setPassportID() {
        labWork.getAuthor().setPassportID();
        return this;
    }

    public LabWorkBuilder setColor(String color) throws RuntimeException {
        if (!Validator.checkColor(Color.valueOf(color.toUpperCase()))) throw new RuntimeException();
        labWork.getAuthor().setEyeColor(Color.valueOf(color.toUpperCase()));
        return this;
    }

    public LabWorkBuilder setLocationX(String locationX) throws RuntimeException {
        if (!Validator.checkLocationX(Float.parseFloat(locationX))) throw new RuntimeException();
        labWork.getAuthor().getLocation().setX(Float.parseFloat(locationX));
        return this;
    }

    public LabWorkBuilder setLocationY(String locationY) throws RuntimeException {
        if (!Validator.checkLocationY(Double.parseDouble(locationY))) throw new RuntimeException();
        labWork.getAuthor().getLocation().setY(Double.parseDouble(locationY));
        return this;
    }

    public LabWorkBuilder setLocationZ(String locationZ) throws RuntimeException {
        if (!Validator.checkLocationZ(Float.parseFloat(locationZ))) throw new RuntimeException();
        labWork.getAuthor().getLocation().setZ(Float.parseFloat(locationZ));
        return this;
    }



    //Билд нового элемента коллекции
    public void setArgs(String... args) {
        this.setLabName(args[0]).setCoordsX(args[1]).setCoordsY(args[2]).setMinimalPoint(args[3])
                .setDifficulty(args[4]).setAuthorName(args[5]).setPassportID().setColor(args[6])
                .setLocationX(args[7]).setLocationY(args[8]).setLocationZ(args[9]).setCreationDate();
    }
    public LabWork getLabWork() {
        return labWork;
    }
}