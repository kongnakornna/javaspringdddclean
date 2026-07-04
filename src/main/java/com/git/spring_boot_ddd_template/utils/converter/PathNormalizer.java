package com.git.spring_boot_ddd_template.utils.converter;


public class PathNormalizer {
    public static String normalizePath(String input) {
        if (input == null || input.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();

        char first = input.charAt(0);
        if (first == '/') {
            sb.append('/');
        } else if (Character.isLetter(first)) {
            sb.append('/');
            sb.append(first);
        } else {
            sb.append('/');
        }

        for (int i = 1; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (isAllowed(ch)) {
                sb.append(ch);
            } else {
                sb.append('/');
            }
        }

        if (sb.length() > 1) {
            char last = sb.charAt(sb.length() - 1);
            if (!isAlnumOrValidEnding(last)) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        return sb.toString();
    }

    private static boolean isAllowed(char ch) {
        return Character.isLetterOrDigit(ch)
                || ch == '/'
                || ch == '-'
                || ch == '_';
    }

    private static boolean isAlnumOrValidEnding(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '-' || ch == '_';
    }
}
