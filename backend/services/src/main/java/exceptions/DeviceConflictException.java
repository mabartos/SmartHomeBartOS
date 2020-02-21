package exceptions;

public class DeviceConflictException extends RuntimeException {

    public DeviceConflictException() {
        super();
    }

    public DeviceConflictException(String message) {
        super(message);
    }

    public DeviceConflictException(String message, Throwable thr) {
        super(message, thr);
    }
}
