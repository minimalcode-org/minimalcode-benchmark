package org.minimalcode.benchmark;

import org.minimalcode.benchmark.beans.get.GetIndexed;
import org.minimalcode.benchmark.beans.get.GetMapped;
import org.minimalcode.benchmark.beans.get.GetNested;
import org.minimalcode.benchmark.beans.get.GetSimple;
import org.minimalcode.benchmark.beans.set.SetIndexed;
import org.minimalcode.benchmark.beans.set.SetMapped;
import org.minimalcode.benchmark.beans.set.SetNested;
import org.minimalcode.benchmark.beans.set.SetSimple;
import org.minimalcode.benchmark.convert.ConditionaldConversion;
import org.minimalcode.benchmark.convert.SimpleConversion;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

    @SuppressWarnings("FieldCanBeLocal")
    private static boolean IS_DEBUG = false;

    public static void main(String[] args) throws RunnerException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        Options opt = new OptionsBuilder()
                .include(SimpleConversion.class.getSimpleName())
                .include(ConditionaldConversion.class.getSimpleName())

                .include(GetSimple.class.getSimpleName())
                .include(GetNested.class.getSimpleName())
                .include(GetIndexed.class.getSimpleName())
                .include(GetMapped.class.getSimpleName())

                .include(SetSimple.class.getSimpleName())
                .include(SetNested.class.getSimpleName())
                .include(SetIndexed.class.getSimpleName())
                .include(SetMapped.class.getSimpleName())

                // Options
                .mode(Mode.AverageTime)
                .warmupIterations(IS_DEBUG ? 3 : 5)
                .measurementIterations(IS_DEBUG ? 5 : 20)
                .timeUnit(TimeUnit.NANOSECONDS)
                .shouldFailOnError(IS_DEBUG)
                .resultFormat(ResultFormatType.CSV)
                .result((IS_DEBUG ? "debug_" : "") + dateFormat.format(new Date()) + "-result.csv")
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
