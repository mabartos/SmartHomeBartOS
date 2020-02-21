package mqtt.exceptions;

public class MqttDeviceConflictException extends RuntimeException {

    public MqttDeviceConflictException() {
        super();
    }

    public MqttDeviceConflictException(String message) {
        super(message);
    }

    public MqttDeviceConflictException(String message, Throwable thr) {
        super(message, thr);
    }
}
