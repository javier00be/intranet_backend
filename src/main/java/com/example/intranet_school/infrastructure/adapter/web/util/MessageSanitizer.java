package com.example.intranet_school.infrastructure.adapter.web.util;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class MessageSanitizer {
    private static final int MAX_LENGTH = 1000;

    public String sanitize(String input) {
        if (input == null) return "";
        String trimmed = input.strip();
        if (trimmed.length() > MAX_LENGTH) {
            trimmed = trimmed.substring(0, MAX_LENGTH);
        }
        return HtmlUtils.htmlEscape(trimmed);
    }

    public boolean isValid(String input) {
        return input != null && !input.isBlank();
    }
}
