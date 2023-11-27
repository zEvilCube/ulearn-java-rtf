package edu.project.models;

import java.util.Map;

public enum Experience {
    NO_EXPERIENCE,
    BETWEEN_1_AND_3,
    BETWEEN_3_AND_6,
    MORE_THAN_6;

    private static final Map<String, Experience> PARSING_MAP = Map.of(
        "noExperience", NO_EXPERIENCE,
        "between1And3", BETWEEN_1_AND_3,
        "between3And6", BETWEEN_3_AND_6,
        "moreThan6", MORE_THAN_6
    );

    public static Experience parseExperience(String string) {
        return PARSING_MAP.get(string);
    }
}
