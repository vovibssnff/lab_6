package cl.io;

import cl.managment.ProgramState;
import cmn.OutputEngine;
import cmn.data.Color;
import cmn.data.Difficulty;
import cmn.data.LabWork;
import cmn.data.Person;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ElemInputService {
    //private Validator validator = new Validator();
    /**
     * Скрипт для ввода и валидации значений полей новых элементов основной коллекции
     * @param ID - id, используется в команде update
     * @return elem - новый элемент коллекции
     */

    public static LabWork setElemScript(Long ID) {
        Scanner sc = ProgramState.getScanner();

        LabWork elem = new LabWork();
        String name = null;
        Double coordinatesX = null;
        Long coordinatesY = null;
        Double minimalPoint = null;
        String difficultyStr;
        Difficulty difficulty = null;
        String authorName = null;
        String colorStr;
        Color eyeColor=null;
        Float locationX = null;
        Double locationY = null;
        Float locationZ = null;
        Person author = new Person();

        switch (ProgramState.getMode()) {
            case ADD -> elem.setId();
            case UPDATE -> elem.setId(ID);
        }
        System.out.println(OutputEngine.insertName());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                name = sc.nextLine().trim();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while(!Validator.checkName(name));
        elem.setName(name);

        System.out.println(OutputEngine.insertCoordinatesX());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                if (sc.hasNextDouble()) {
                    coordinatesX = sc.nextDouble();
                    sc.nextLine();
                    if (Validator.checkCoordinatesX(coordinatesX)) {
                        break;
                    } else {
                        System.out.println(OutputEngine.incorrectCoordinatesX());
                    }
                } else {
                    System.out.println(OutputEngine.incorrectCoordinatesX());
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        System.out.println(OutputEngine.insertCoordinatesY());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                if (sc.hasNextLong()) {
                    coordinatesY = sc.nextLong();
                    sc.nextLine();
                    if (Validator.checkCoordinatesY(coordinatesY)) {
                        break;
                    } else {
                        System.out.println(OutputEngine.incorrectCoordinatesX());
                    }
                } else {
                    System.out.println(OutputEngine.incorrectCoordinatesY());
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        elem.setCoordinates(coordinatesX, coordinatesY);

        elem.setCreationDate();

        System.out.println(OutputEngine.insertMinimalPoint());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                if (sc.hasNextDouble()) {
                    minimalPoint = sc.nextDouble();
                    sc.nextLine();
                    if (Validator.checkMinimalPoint(minimalPoint)) {
                        break;
                    } else {
                        System.out.println(OutputEngine.incorrectMinimalPoint());
                    }
                } else {
                    System.out.println(OutputEngine.incorrectMinimalPoint());
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        } while (true);
        elem.setMinimalPoint(minimalPoint);

        System.out.println(OutputEngine.insertDifficulty());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                difficultyStr = sc.nextLine().trim();
                difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!Validator.checkDifficulty(difficulty));
        elem.setDifficulty(difficulty);

        System.out.println(OutputEngine.insertAuthorName());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                authorName = sc.nextLine().trim();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while(!Validator.checkAuthorName(authorName));
        author.setName(authorName);

        author.setPassportID();

        System.out.println(OutputEngine.insertColor());
        do {
            try{
                System.out.print(OutputEngine.prompt());
                colorStr = sc.nextLine().trim();
                eyeColor = Color.valueOf(colorStr.toUpperCase());
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while(!Validator.checkColor(eyeColor));
        author.setEyeColor(eyeColor);

        System.out.println(OutputEngine.insertLocationX());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                if (sc.hasNextFloat()) {
                    locationX = sc.nextFloat();
                    sc.nextLine();
                    if (Validator.checkLocationX(locationX)) {
                        break;
                    } else {
                        System.out.println(OutputEngine.incorrectLocationX());
                    }
                } else {
                    System.out.println(OutputEngine.incorrectLocationX());
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        } while (true);
        author.getLocation().setX(locationX);

        System.out.println(OutputEngine.insertLocationY());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                if (sc.hasNextDouble()) {
                    locationY = sc.nextDouble();
                    sc.nextLine();
                    if (Validator.checkLocationY(locationY)) {
                        break;
                    } else {
                        System.out.println(OutputEngine.incorrectLocationY());
                    }
                } else {
                    System.out.println(OutputEngine.incorrectLocationY());
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        } while (true);
        author.getLocation().setY(locationY);

        System.out.println(OutputEngine.insertLocationZ());
        do {
            try {
                System.out.print(OutputEngine.prompt());
                if (sc.hasNextFloat()) {
                    locationZ = sc.nextFloat();
                    sc.nextLine();
                    if (Validator.checkLocationZ(locationZ)) {
                        break;
                    } else {
                        System.out.println(OutputEngine.incorrectLocationZ());
                    }
                } else {
                    System.out.println(OutputEngine.incorrectLocationZ());
                    sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        } while (true);
        author.getLocation().setZ(locationZ);
        elem.setAuthor(author);
        return elem;
    }
}
