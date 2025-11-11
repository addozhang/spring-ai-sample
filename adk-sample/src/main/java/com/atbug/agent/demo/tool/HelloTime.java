package com.atbug.agent.demo.tool;

import com.google.adk.tools.Annotations.Schema;

import java.util.Map;

public class HelloTime {

    @Schema(description = "Get the current local date and time as a string")
    public static Map<String, String> getCurrentDateAndTime() {
        return Map.of(
                "currentTime", java.time.LocalTime.now().toString(),
                    "currentDate", java.time.LocalDate.now().toString()
                );
    }
}
