package com.example.tripdivine.constants;

import java.util.stream.Stream;

public class Constant {

    public enum BASE_LOCATIONS {
        kalupurMandir("શ્રી સ્વામિનારાયણ મંદિર કાલુપુર"),
        bhujMandir("શ્રી સ્વામિનારાયણ મંદિર ભુજ"),
        vadtalMandir("શ્રી સ્વામિનારાયણ મંદિર, વડતાલ");

        private final String value;

        BASE_LOCATIONS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Stream<BASE_LOCATIONS> stream() {
            return Stream.of(BASE_LOCATIONS.values());
        }
    }
}
