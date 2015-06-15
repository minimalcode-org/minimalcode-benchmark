package org.minimalcode.benchmark.convert;

import org.minimalcode.convert.SimpleConversionManager;
import org.minimalcode.convert.SimplePropertyConverter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Locale;

@State(Scope.Benchmark)
public class SimpleConversion implements ConvertExperiment<Locale> {

    private final String tag = "en";
    private GenericConversionService springConversionService;
    private SimpleConversionManager minimalcodeConversionManager;

    @Setup
    public void before() {
        springConversionService = new GenericConversionService();
        springConversionService.addConverter(new Converter<String, Locale>() {
            @Override
            public Locale convert(String source) {
                return new Locale(source);
            }
        });

        minimalcodeConversionManager = new SimpleConversionManager();
        minimalcodeConversionManager.addConverter(new SimplePropertyConverter<String, Locale>() {
            @Override
            public Locale convert(String source) {
                return new Locale(source);
            }
        });
    }

    @Override
    @Benchmark
    public Locale minimalcodeConversionManager() {
        if(minimalcodeConversionManager.canConvert(String.class, Locale.class)) {
            return minimalcodeConversionManager.convert(tag, Locale.class);
        }

        throw new IllegalStateException();
    }

    @Override
    @Benchmark
    public Locale springConversionService() {
        if(springConversionService.canConvert(String.class, Locale.class)) {
            return springConversionService.convert(tag, Locale.class);
        }

        throw new IllegalStateException();
    }
}
