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
public class SetNested implements SetExperiment {

    private final String PROPERTY_PATTERN = "beanProperty.stringProperty";
    private final String PROPERTY_VALUE = "updated-value";

    private GenericBean input;
    private BeanWrapper springBeanWrapper;
    private FieldUtils azekoskyFieldUtils;
    private ObjectWrapper minimalcodeObjectWrapper;

    @Setup
    public void before() {
        input = new GenericBean();
        input.setBeanProperty(new GenericBean());

        springBeanWrapper = new BeanWrapperImpl(input);
        azekoskyFieldUtils = FieldUtils.getInstance();
        minimalcodeObjectWrapper = new ObjectWrapper(input);
    }

    @Override
    @Benchmark()
    public void apachePropertyUtils() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PropertyUtils.setNestedProperty(input, PROPERTY_PATTERN, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void azekoskyFieldUtils() {
        azekoskyFieldUtils.setFieldValue(input, PROPERTY_PATTERN, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void minimalcodeObjectWrapper() {
        minimalcodeObjectWrapper.setValue(PROPERTY_PATTERN, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void joddBeanUtil() {
        BeanUtil.setProperty(input, PROPERTY_PATTERN, PROPERTY_VALUE);
    }

    @Override
    @Benchmark()
    public void springBeanWrapper() {
        springBeanWrapper.setPropertyValue(PROPERTY_PATTERN, PROPERTY_VALUE);
    }

}
