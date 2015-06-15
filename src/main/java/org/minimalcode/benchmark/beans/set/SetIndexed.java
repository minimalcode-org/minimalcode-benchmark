package org.minimalcode.benchmark.beans.set;

import jodd.bean.BeanUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.azeckoski.reflectutils.FieldUtils;
import org.minimalcode.beans.ObjectWrapper;
import org.minimalcode.reflect.util.GenericBean;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


@State(Scope.Benchmark)
public class SetIndexed implements SetExperiment{

    private final int PROPERTY_INDEX = 1;
    private final String PROPERTY_NAME = "listProperty";
    private final String PROPERTY_VALUE = "updated-value";

    private GenericBean input;
    private BeanWrapper springBeanWrapper;
    private FieldUtils azekoskyFieldUtils;
    private ObjectWrapper minimalcodeObjectWrapper;

    @Setup
    public void before() {
        input = new GenericBean();
        input.setListProperty(new ArrayList<String>());
        input.getListProperty().add("not-me");
        input.getListProperty().add(PROPERTY_VALUE);

        springBeanWrapper = new BeanWrapperImpl(input);
        azekoskyFieldUtils = FieldUtils.getInstance();
        minimalcodeObjectWrapper = new ObjectWrapper(input);
    }

    @Override
    @Benchmark()
    public void apachePropertyUtils() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PropertyUtils.setIndexedProperty(input, PROPERTY_NAME, PROPERTY_INDEX, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void azekoskyFieldUtils() {
        azekoskyFieldUtils.setFieldValue(input, PROPERTY_NAME + "[" + PROPERTY_INDEX + "]", PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void minimalcodeObjectWrapper() {
        minimalcodeObjectWrapper.setIndexedValue(PROPERTY_NAME, PROPERTY_INDEX, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void joddBeanUtil() {
        BeanUtil.setProperty(input, PROPERTY_NAME + "[" + PROPERTY_INDEX + "]", PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void springBeanWrapper() {
        springBeanWrapper.setPropertyValue(PROPERTY_NAME + "[" + PROPERTY_INDEX + "]", PROPERTY_VALUE);
    }

}
