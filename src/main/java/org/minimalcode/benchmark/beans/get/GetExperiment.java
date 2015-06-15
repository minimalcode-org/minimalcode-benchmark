package org.minimalcode.benchmark.beans.get;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused")
public interface GetExperiment {

    String minimalcodeObjectWrapper();

    String apachePropertyUtils() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    String springBeanWrapper();

    String azekoskyFieldUtils();

    String joddBeanUtil();
}
