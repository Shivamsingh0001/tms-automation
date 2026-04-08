package org.delhivery.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final Logger log = LoggerFactory.getLogger(LogUtils.class);

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BOLD = "\u001B[1m";

    private LogUtils() {}

    public static void debug(String format, Object... args) { log.debug(format, args); }
    public static void info(String format, Object... args) { log.info(format, args); }
    public static void warn(String format, Object... args) { log.warn(format, args); }
    public static void error(String format, Object... args) { log.error(format, args); }
    public static void error(String message, Throwable t) { log.error(message, t); }

    public static void success(String message) {
        log.info("{}\u2705 {}{}", GREEN, message, RESET);
    }

    public static void step(String stepDescription) {
        log.info("{}\u27A4 {}{}{}", CYAN, BOLD, stepDescription, RESET);
    }

    public static void section(String title) {
        String border = "\u2550".repeat(Math.max(50, title.length() + 4));
        log.info("\n{}\u2554{}\u2557{}", PURPLE, border, RESET);
        log.info("{}\u2551  {}  {}\u2551{}", PURPLE, title, " ".repeat(border.length() - title.length() - 4), RESET);
        log.info("{}\u255A{}\u255D{}", PURPLE, border, RESET);
    }

    public static void testStart(String testName) {
        log.info("\n\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
        log.info("\u2502 {}TEST: {}{}", BOLD, testName, RESET);
        log.info("\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
    }

    public static void testPass(String testName) {
        log.info("{}\u2705 PASSED: {}{}\n", GREEN, testName, RESET);
    }

    public static void testFail(String testName, String reason) {
        log.error("\u274C FAILED: {}", testName);
        log.error("   Reason: {}\n", reason);
    }

    public static void testSkip(String testName, String reason) {
        log.warn("{}\u23ED\uFE0F  SKIPPED: {}{}", YELLOW, testName, RESET);
        log.warn("{}   Reason: {}{}\n", YELLOW, reason, RESET);
    }

    public static void separator() {
        log.info("\u2500".repeat(60));
    }
}
