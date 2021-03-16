package ro.mihaisurdeanu.play.mappers;

import java.util.EnumMap;

import org.junit.jupiter.api.Test;

import ro.mihaisurdeanu.play.exceptions.PlayException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Mapper0Test {

    private static final Mapper0<Type, String> CASE_MAPPING = createMapper();

    private static Mapper0<Type, String> createMapper() {
        return Mapper0.<Type, String>builder(new EnumMap<>(Type.class))
                .map(Type.OPTION_1, () -> "Option1")
                .map(Type.OPTION_2, () -> "Option2")
                .unmapped(() -> {
                    throw new PlayException("Unsupported type given as input.");
                }).build();
    }

    public static String get(Type type) {
        return CASE_MAPPING.map(type);
    }

    @Test
    public void testMapperWithExistingCases() {
        assertThat(get(Type.OPTION_1)).isEqualTo("Option1");
        assertThat(get(Type.OPTION_2)).isEqualTo("Option2");
    }

    @Test
    public void testMapperWithUnmappedCases() {
        assertThrows(PlayException.class, () -> get(Type.OPTION_3));
    }

    public enum Type {
        OPTION_1, OPTION_2, OPTION_3
    }

}
