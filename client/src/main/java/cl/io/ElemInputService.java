package cl.io;

import cl.managment.ProgramState;
import cmn.OutputEngine;
import cmn.data.Color;
import cmn.data.Difficulty;
import cmn.data.LabWork;
import cmn.data.Person;
import cmn.service.LabWorkBuilder;

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
        LabWorkBuilder labWorkBuilder = new LabWorkBuilder();
        String coordinatesX = null;
        String coordinatesY = null;
        String minimalPoint = null;
        String difficultyStr;
        String authorName = null;
        String colorStr;
        String locationX = null;
        String locationY = null;
        String locationZ = null;



        //Ввод имени
        System.out.println(OutputEngine.insertName());
        while (true) {
            System.out.print(OutputEngine.prompt());
            String inputName = sc.nextLine();
            try {
                labWorkBuilder.setLabName(inputName);
            } catch (RuntimeException flag) {
                continue;
            }
            break;
        }
        // elem.setName(name);

        //Ввод координат Х
        System.out.println(OutputEngine.insertCoordinatesX());
        while (true) {
            System.out.print(OutputEngine.prompt());
            coordinatesX = sc.nextLine();
            try {
                labWorkBuilder.setCoordsX(coordinatesX);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesX());
                continue;
            }
            break;
        }

        //Ввод координат Y
        System.out.println(OutputEngine.insertCoordinatesY());
        while (true) {
            System.out.print(OutputEngine.prompt());
            coordinatesY = sc.nextLine();
            try {
                labWorkBuilder.setCoordsY(coordinatesY);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }

        labWorkBuilder.setCreationDate();

        System.out.println(OutputEngine.insertMinimalPoint());
        while (true) {
            System.out.print(OutputEngine.prompt());
            minimalPoint = sc.nextLine();
            try {
                labWorkBuilder.setMinimalPoint(minimalPoint);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }

        System.out.println(OutputEngine.insertDifficulty());
        while (true) {
            System.out.print(OutputEngine.prompt());
            difficultyStr = sc.nextLine();
            try {
                labWorkBuilder.setDifficulty(difficultyStr);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }

        System.out.println(OutputEngine.insertAuthorName());
        while (true) {
            System.out.print(OutputEngine.prompt());
            authorName = sc.nextLine();
            try {
                labWorkBuilder.setAuthorName(authorName);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }

        labWorkBuilder.setPassportID();

        System.out.println(OutputEngine.insertColor());
        while (true) {
            System.out.print(OutputEngine.prompt());
            colorStr = sc.nextLine();
            try {
                labWorkBuilder.setColor(colorStr);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }

        System.out.println(OutputEngine.insertLocationX());
        while (true) {
            System.out.print(OutputEngine.prompt());
            locationX = sc.nextLine();
            try {
                labWorkBuilder.setLocationX(locationX);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }

        System.out.println(OutputEngine.insertLocationY());
        while (true) {
            System.out.print(OutputEngine.prompt());
            locationY = sc.nextLine();
            try {
                labWorkBuilder.setLocationY(locationY);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }

        System.out.println(OutputEngine.insertLocationZ());
        while (true) {
            System.out.print(OutputEngine.prompt());
            locationZ = sc.nextLine();
            try {
                labWorkBuilder.setLocationZ(locationZ);
            } catch (RuntimeException flag) {
                //System.out.println(OutputEngine.incorrectCoordinatesY());
                continue;
            }
            break;
        }
        elem = labWorkBuilder.getLabWork();
        if (ID!=null) {
            elem.setId(ID);
        }
        return elem;
    }
}
