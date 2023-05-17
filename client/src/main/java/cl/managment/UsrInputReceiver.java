package cl.managment;

import cmn.service.UsrInputInterface;
import cl.io.*;
import cmn.OutputEngine;
import cmn.data.LabWork;

import java.lang.reflect.InvocationTargetException;

/**
 * Класс, выполняющий команды
 */
public class UsrInputReceiver implements UsrInputInterface {
    public int iterations = 0;
    @Override
    public void exit() {
        System.exit(0);
    }
    @Override
    public LabWork add() {
        ProgramState.setCommandType(CommandType.ADD);
        return ElemInputService.setElemScript(null);
    }
    @Override
    public LabWork update(Long arg) {
        ProgramState.setCommandType(CommandType.UPDATE);
        return ElemInputService.setElemScript(arg);
    }
    @Override
    public <T> boolean typeValidator(String arg, Class<T> clazz) {
        if (arg!=null && !arg.equals("")) {
            try {
                clazz.getConstructor(String.class).newInstance(arg);
                return true;
            } catch (Exception e) {
                System.out.println(OutputEngine.incorrectArg()+clazz.getName());
            }
        }
        return false;
    }
    @Override
    public <T> T setArg(String arg, Class<T> clazz) {
        try {
            return clazz.getConstructor(String.class).newInstance(arg);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
