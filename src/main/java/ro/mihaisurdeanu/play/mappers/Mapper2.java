package ro.mihaisurdeanu.play.mappers;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.function.Function2;

@RequiredArgsConstructor
public class Mapper2<T, I1, I2, R> {

    private final Map<T, Function2<I1, I2, R>> mapping;

    private final Function2<I1, I2, R> unmapped;

    public static <T, I1, I2, R> Mapper2.Builder<T, I1, I2, R> builder(Map<T, Function2<I1, I2, R>> mapping) {
        return new Mapper2.Builder<>(mapping);
    }

    public R map(T type, I1 input1, I2 input2) {
        return mapping.getOrDefault(type, unmapped).apply(input1, input2);
    }

    @RequiredArgsConstructor
    public static class Builder<T, I1, I2, R> {

        private final Map<T, Function2<I1, I2, R>> mapping;

        private Function2<I1, I2, R> unmapped = (i1, i2) -> null;

        public Mapper2<T, I1, I2, R> build() {
            return new Mapper2<>(mapping, unmapped);
        }

        public Mapper2.Builder<T, I1, I2, R> map(T type, Function2<I1, I2, R> function) {
            mapping.put(type, function);
            return this;
        }

        public Mapper2.Builder<T, I1, I2, R> unmapped(Function2<I1, I2, R> unmapped) {
            this.unmapped = unmapped;
            return this;
        }

    }
}