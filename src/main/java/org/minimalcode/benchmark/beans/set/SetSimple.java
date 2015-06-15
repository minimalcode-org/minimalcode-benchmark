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


@SuppressWarnings("unused")
@State(Scope.Benchmark)
public class SetSimple implements SetExperiment {

    private final String PROPERTY_NAME = "stringProperty";
    private final String PROPERTY_VALUE = "updated-value";

    private GenericBean input;
    private BeanWrapper springBeanWrapper;
    private FieldUtils azekoskyFieldUtils;
    private ObjectWrapper minimalcodeObjectWrapper;

    @Setup
    public void before() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        input = new GenericBean();

        minimalcodeObjectWrapper = new ObjectWrapper(input);
        springBeanWrapper = new BeanWrapperImpl(input);
        azekoskyFieldUtils = FieldUtils.getInstance();
    }

    @Override
    @Benchmark()
    public void apachePropertyUtils() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PropertyUtils.setSimpleProperty(input, PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void azekoskyFieldUtils() {
        azekoskyFieldUtils.setFieldValue(input, PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void minimalcodeObjectWrapper() {
        minimalcodeObjectWrapper.setSimpleValue(PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void joddBeanUtil() {
        BeanUtil.setProperty(input, PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void springBeanWrapper() {
        springBeanWrapper.setPropertyValue(PROPERTY_NAME, PROPERTY_VALUE);
    }
}
