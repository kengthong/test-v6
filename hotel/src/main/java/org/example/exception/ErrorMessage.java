package org.example.exception;

import java.text.MessageFormat;

public enum ErrorMessage {
    
    Room_InvalidRoomNumber("Invalid room number: ''{0}''."),
    Room_CannotBeCleaned("Room ''{0}'' is not currently vacant and cannot be cleaned at the moment."),
    Room_NotUnderRepair("Room ''{0}'' is not currently under repair."),
    Customer_NotFound("Could not find customer with name: ''{0}''."),
    Customer_NotCheckedIn("Customer of name ''{0}'' is currently not checked in.");
    
    private String messagePattern;
    ErrorMessage(String messagePattern) {
        this.messagePattern = messagePattern;
    }

    /**
     * Renders this message as a string.
     *
     * @param args sequence of args for the message placeholders
     * @return the rendered message
     */
    public String render(Object... args) {
        if (args == null) {
            return MessageFormat.format(messagePattern, "null");
        }
        return MessageFormat.format(messagePattern, args);
    }

    /**
     * Throw an InvalidRequestException for this message
     *
     * @param args any parameters to the message
     */
    public void raise(Object... args) {
        throw new InvalidRequestException(render(args));
    }

    /**
     * Ensures the given condition is valid, or else throws a InvalidRequestException
     * with the given message rendered with the given arguments.
     *
     * @param condition condition to check
     * @param message the message to present in case of failure
     * @param args any parameters to the message
     */
    public static void ensure(boolean condition, ErrorMessage message, Object... args) {
        if (!condition) {
            message.raise(args);
        }
    }
}
