package ro.mihaisurdeanu.play.mappers;

import java.util.EnumMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ro.mihaisurdeanu.play.exceptions.PlayException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Mapper1Test {

    private static final Mapper1<Type, String, Integer> CASE_MAPPING = createMapper();

    private static Mapper1<Type, String, Integer> createMapper() {
        return Mapper1.<Type, String, Integer>builder(new EnumMap<>(Type.class))
                .map(Type.STRING_LENGTH, input -> Optional.ofNullable(input).map(String::length).orElse(-1))
                .map(Type.STRING_STATIC_VALUE, input -> 1)
                .unmapped((input) -> {
                    throw new PlayException("Unsupported type for given input string: " + input);
                }).build();
    }

    public static Integer getValue(Type type, String input) {
        return CASE_MAPPING.map(type, input);
    }

    @Test
    public void testMapperWithExistingCases() {
        assertThat(getValue(Type.STRING_LENGTH, "myString")).isEqualTo(8);
        assertThat(getValue(Type.STRING_STATIC_VALUE, "something")).isEqualTo(1);
    }

    @Test
    public void testMapperWithUnmappedCases() {
        assertThrows(PlayException.class, () -> getValue(Type.STRING_NOT_MAPPED, "something"));
    }

    public enum Type {
        STRING_LENGTH, STRING_STATIC_VALUE, STRING_NOT_MAPPED
    }

}
