package wing.managment;

import common.*;
import common.data.Color;
import common.data.Difficulty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для валидации значений полей
 */
public class Validator {
    public static boolean checkId(long id) {
        if (Collections.containsId(id)) {
            System.out.println(OutputEngine.incorrectId());
            return false;
        } else {
            System.out.println(OutputEngine.correctId());
            Collections.addId(id);
            return true;
        }
    }
    public static boolean checkName(String name) {
        if ("".equals(name)||name==null) {
            System.out.println(OutputEngine.incorrectName());
            return false;
        } else {
            System.out.println(OutputEngine.correctName());
            return true;
        }
    }
    public static boolean checkCoordinatesX(Double x) {
        if ((x>=775)||x==null) {
            System.out.println(OutputEngine.incorrectCoordinatesX());
            return false;
        } else {
            System.out.println(OutputEngine.correctCoordinatesX());
            return true;
        }
    }
    public static boolean checkCoordinatesY(Long y) {
        if ((y>=775)||y==null) {
            System.out.println(OutputEngine.incorrectCoordinatesY());
            return false;
        } else {
            System.out.println(OutputEngine.correctCoordinatesY());
            return true;
        }
    }
    public static boolean checkMinimalPoint(Double minimalPoint) {
        if ((minimalPoint<=0)||(minimalPoint == null)) {
            System.out.println(OutputEngine.incorrectMinimalPoint());
            return false;
        } else {
            System.out.println(OutputEngine.correctMinimalPoint());
            return true;
        }
    }
    public static boolean checkCreationDate(String creationDate) {;
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(creationDate);
        if (matcher.matches()) {
            System.out.println(OutputEngine.correctCreationDate());
            return true;
        } else {
            System.out.println(OutputEngine.incorrectCreationDate());
            return false;
        }
    }
    public static boolean checkDifficulty(Difficulty difficulty) {
        if (Difficulty.VERY_HARD.equals(difficulty)||Difficulty.VERY_EASY.equals(difficulty)||
                Difficulty.IMPOSSIBLE.equals(difficulty)) {
            System.out.println(OutputEngine.correctDifficulty());
            return true;
        } else {
            System.out.println(OutputEngine.incorrectDifficulty());
            return false;
        }
    }
    public static boolean checkAuthorName(String name) {
        if ("".equals(name)||name==null) {
            System.out.println(OutputEngine.incorrectAuthorName());
            return false;
        } else {
            System.out.println(OutputEngine.correctAuthorName());
            return true;
        }
    }
    public static boolean checkPassportId(String passportID) {
        if (Collections.containsPassportId(passportID)) {
            System.out.println(OutputEngine.incorrectPassportId());
            return false;
        } else {
            System.out.println(OutputEngine.correctPassportId());
            Collections.addPassportId(passportID);
            return true;
        }
    }
    public static boolean checkColor(Color eyeColor) {
        if (Color.GREEN.equals(eyeColor)||Color.BROWN.equals(eyeColor)||Color.BLACK.equals(eyeColor)) {
            System.out.println(OutputEngine.correctEyeColor());
            return true;
        } else {
            System.out.println(OutputEngine.incorrectEyeColor());
            return false;
        }
    }
    public static boolean checkLocationX(Float x) {
        if (x==null) {
            System.out.println(OutputEngine.incorrectLocationX());
            return false;
        } else {
            System.out.println(OutputEngine.correctLocationX());
            return true;
        }
    }
    public static boolean checkLocationY(Double y) {
        if (y==null) {
            System.out.println(OutputEngine.incorrectLocationY());
            return false;
        } else {
            System.out.println(OutputEngine.correctLocationY());
            return true;
        }
    }
    public static boolean checkLocationZ(Float z) {
        if (z==null) {
            System.out.println(OutputEngine.incorrectLocationZ());
            return false;
        } else {
            System.out.println(OutputEngine.correctLocationZ());
            return true;
        }
    }
}
