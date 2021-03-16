package ro.mihaisurdeanu.play.mappers;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.function.Function3;

@RequiredArgsConstructor
public class Mapper3<T, I1, I2, I3, R> {

    private final Map<T, Function3<I1, I2, I3, R>> mapping;

    private final Function3<I1, I2, I3, R> unmapped;

    public static <T, I1, I2, I3, R> Mapper3.Builder<T, I1, I2, I3, R> builder(Map<T, Function3<I1, I2, I3, R>> mapping) {
        return new Mapper3.Builder<>(mapping);
    }

    public R map(T type, I1 input1, I2 input2, I3 input3) {
        return mapping.getOrDefault(type, unmapped).apply(input1, input2, input3);
    }

    @RequiredArgsConstructor
    public static class Builder<T, I1, I2, I3, R> {

        private final Map<T, Function3<I1, I2, I3, R>> mapping;

        private Function3<I1, I2, I3, R> unmapped = (i1, i2, i3) -> null;

        public Mapper3<T, I1, I2, I3, R> build() {
            return new Mapper3<>(mapping, unmapped);
        }

        public Mapper3.Builder<T, I1, I2, I3, R> map(T type, Function3<I1, I2, I3, R> function) {
            mapping.put(type, function);
            return this;
        }

        public Mapper3.Builder<T, I1, I2, I3, R> unmapped(Function3<I1, I2, I3, R> unmapped) {
            this.unmapped = unmapped;
            return this;
        }

    }
}