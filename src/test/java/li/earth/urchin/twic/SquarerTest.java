package li.earth.urchin.twic;

import org.junit.ClassRule;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@Fork(1)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SquarerTest {

    private static final int NUM_CALLS = 1_000_000;

    @ClassRule
    public static JMHRule jmh = new JMHRule();

    @Test
    public void squaringInJavaIsFast() throws Exception {
        assertThat(nanosPerCall("squareInJava") - nanosPerCall("noOp"),
                   lessThan(1.0));
    }

    @Test
    public void squaringNativelyIsABitSlower() throws Exception {
        assertThat(nanosPerCall("squareNatively") - nanosPerCall("squareInJava"),
                   allOf(greaterThan(5.0), lessThan(10.0)));
    }

    private double nanosPerCall(String squareInJava) {
        return jmh.getAggregatedResult(squareInJava).getScore() / NUM_CALLS;
    }

    @Benchmark
    public void noOp(Blackhole blackhole) {
        for (int i = 0; i < NUM_CALLS; ++i) {
            blackhole.consume(23);
        }
    }

    @Benchmark
    public void squareInJava(Blackhole blackhole) {
        for (int i = 0; i < NUM_CALLS; ++i) {
            blackhole.consume(Squarer.squareInJava(i));
        }
    }

    @Benchmark
    public void squareNatively(Blackhole blackhole) {
        for (int i = 0; i < NUM_CALLS; ++i) {
            blackhole.consume(Squarer.squareNatively(i));
        }
    }

}
