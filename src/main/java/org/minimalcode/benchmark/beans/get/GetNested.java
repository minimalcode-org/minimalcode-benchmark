package org.minimalcode.benchmark.beans.get;

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
public class GetNested implements GetExperiment {

    public final String PROPERTY_PATTERN = "beanProperty.stringProperty";
    public final String PROPERTY_VALUE = "get-me";

    private GenericBean input;
    private BeanWrapper springBeanWrapper;
    private FieldUtils azekoskyFieldUtils;
    private ObjectWrapper minimalcodeObjectWrapper;

    @Setup
    public void before() {
        input = new GenericBean();
        input.setBeanProperty(new GenericBean());
        input.getBeanProperty().setStringProperty(PROPERTY_VALUE);

        springBeanWrapper = new BeanWrapperImpl(input);
        azekoskyFieldUtils = FieldUtils.getInstance();
        minimalcodeObjectWrapper = new ObjectWrapper(input);
    }

    @Override
    @Benchmark()
    public String apachePropertyUtils() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return (String) PropertyUtils.getNestedProperty(input, PROPERTY_PATTERN);
    }

    @Override
    @Benchmark()
    public String azekoskyFieldUtils() {
        return (String) azekoskyFieldUtils.getFieldValue(input, PROPERTY_PATTERN);
    }

    @Override
    @Benchmark()
    public String minimalcodeObjectWrapper() {
        return (String) minimalcodeObjectWrapper.getValue(PROPERTY_PATTERN);
    }

    @Override
    @Benchmark()
    public String joddBeanUtil() {
        return (String) BeanUtil.getProperty(input, PROPERTY_PATTERN);
    }

    @Override
    @Benchmark()
    public String springBeanWrapper() {
        return (String) springBeanWrapper.getPropertyValue(PROPERTY_PATTERN);
    }
}
