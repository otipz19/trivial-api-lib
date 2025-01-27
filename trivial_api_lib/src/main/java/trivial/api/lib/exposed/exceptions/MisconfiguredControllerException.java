package trivial.api.lib.exposed.exceptions;

public class MisconfiguredControllerException extends RuntimeException {
    public MisconfiguredControllerException(String msg) {
        super(msg);
    }
}
