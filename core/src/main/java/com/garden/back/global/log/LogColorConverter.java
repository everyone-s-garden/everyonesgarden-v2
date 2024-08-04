package com.garden.back.global.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class LogColorConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        return switch (level.toInt()) {
            case Level.ERROR_INT -> ANSIConstants.BOLD + ANSIConstants.RED_FG;
            case Level.WARN_INT -> ANSIConstants.YELLOW_FG;
            case Level.INFO_INT -> ANSIConstants.GREEN_FG;
            case Level.DEBUG_INT -> ANSIConstants.BLUE_FG;
            case Level.TRACE_INT -> ANSIConstants.BOLD + ANSIConstants.BLUE_FG;
            default -> ANSIConstants.DEFAULT_FG;
        };
    }
}
