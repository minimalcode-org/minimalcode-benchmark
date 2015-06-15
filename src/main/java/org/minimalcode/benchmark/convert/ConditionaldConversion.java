package org.minimalcode.benchmark.convert;

import org.minimalcode.convert.PropertyConverter;
import org.minimalcode.convert.SimpleConversionManager;
import org.minimalcode.reflect.Bean;
import org.minimalcode.reflect.Property;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Locale;
import java.util.Set;

@State(Scope.Benchmark)
public class ConditionaldConversion implements ConvertExperiment<Locale> {

    private GenericConversionService springConversionService;
    private SimpleConversionManager minimalcodeConversionManager;

    static class GenericBean {
        private final String tag = "en";
        private Locale locale;

        public String getTag() {
            return tag;
        }

        public Locale getLocale() {
            return locale;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }
    }

    private static GenericBean beanObject;
    private static final Bean<?> BEAN = Bean.forClass(GenericBean.class);

    @Setup
    public void before() {
        beanObject = new GenericBean();

        springConversionService = new GenericConversionService();
        springConversionService.addConverter(new ConditionalGenericConverter() {

            @Override
            public Set<ConvertiblePair> getConvertibleTypes() {
                return null;// all
            }

            @Override
            public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
                return new Locale((String)source);
            }

            @Override
            public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
                return sourceType != null && sourceType.getType() == String.class
                    && targetType != null && targetType.getType() == Locale.class;
            }
        });

        minimalcodeConversionManager = new SimpleConversionManager();
        minimalcodeConversionManager.addConverter(String.class, Locale.class, new PropertyConverter<String, Locale>() {
            @Override
            public boolean canConvert(Property sourceProperty, Property targetProperty) {
                return sourceProperty != null && sourceProperty.getType() == String.class
                    && targetProperty != null && targetProperty.getType() == Locale.class;
            }

            @Override
            public Locale convert(String source, Property sourceProperty, Property targetProperty) {
                return new Locale(source);
            }
        });
    }

    @Override
    @Benchmark
    public Locale minimalcodeConversionManager() {
        Property tag = BEAN.getProperty("tag");
        Property locale = BEAN.getProperty("locale");

        if(minimalcodeConversionManager.canConvert(String.class, Locale.class, tag, locale)) {
            return minimalcodeConversionManager.convert(beanObject.getTag(), Locale.class, tag, locale);
        }

        throw new IllegalStateException();
    }

    @Override
    @Benchmark
    public Locale springConversionService() {
        TypeDescriptor tag = TypeDescriptor.valueOf(String.class);
        TypeDescriptor locale = TypeDescriptor.valueOf(Locale.class);

        if(springConversionService.canConvert(tag, locale)) {
            return (Locale) springConversionService.convert(beanObject.getTag(), tag, locale);
        }

        throw new IllegalStateException();
    }
}
