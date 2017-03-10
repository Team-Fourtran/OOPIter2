package models.ctrlCommand;

public class CommandNotConfiguredException extends Exception {

    public CommandNotConfiguredException(String message) {
        super(message);
    }

    public CommandNotConfiguredException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
