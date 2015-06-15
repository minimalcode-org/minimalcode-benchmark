package org.minimalcode.benchmark.beans.set;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused")
public interface SetExperiment {

    void minimalcodeObjectWrapper();

    void apachePropertyUtils() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void springBeanWrapper();

    void azekoskyFieldUtils();

    void joddBeanUtil();
}
