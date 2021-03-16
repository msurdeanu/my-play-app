package ro.mihaisurdeanu.play.mappers;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.function.Function0;

@RequiredArgsConstructor
public class Mapper0<T, R> {

    private final Map<T, Function0<R>> mapping;

    private final Function0<R> unmapped;

    public static <T, R> Builder<T, R> builder(Map<T, Function0<R>> mapping) {
        return new Builder<>(mapping);
    }

    public R map(T type) {
        return mapping.getOrDefault(type, unmapped).apply();
    }

    @RequiredArgsConstructor
    public static class Builder<T, R> {

        private final Map<T, Function0<R>> mapping;

        private Function0<R> unmapped = () -> null;

        public Mapper0<T, R> build() {
            return new Mapper0<>(mapping, unmapped);
        }

        public Builder<T, R> map(T type, Function0<R> function) {
            mapping.put(type, function);
            return this;
        }

        public Builder<T, R> unmapped(Function0<R> unmapped) {
            this.unmapped = unmapped;
            return this;
        }

    }

}
