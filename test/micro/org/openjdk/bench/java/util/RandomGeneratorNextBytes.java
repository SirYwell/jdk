package org.openjdk.bench.java.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(3)
public class RandomGeneratorNextBytes {

    @Param({"15", "1023", "1024"})
    private static int size;

    @Param({"random", "default"})
    private static String generatorName;

    private RandomGenerator generator;

    private byte[] bytes;

    @Setup
    public void setup() {
        this.bytes = new byte[size];
        this.generator = switch (generatorName) {
            case "random" -> new Random(0);
            case "default" -> RandomGeneratorFactory.getDefault().create(0);
            default -> throw new AssertionError("Unexpected value: " + generatorName);
        };
    }

    @Benchmark
    public byte[] nextBytes() {
        generator.nextBytes(bytes);
        return bytes;
    }
}
