package edu.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTest {
    @Test
    @DisplayName("Сложение числа с другим числом")
    void testAdd() {
        // given
        final int VALUE_TO_ADD = 1;
        int number = 0;

        // when
        number += VALUE_TO_ADD;

        // then
        final int EXPECTED_NUMBER = 1;
        assertThat(number).isEqualTo(EXPECTED_NUMBER);
    }
}
