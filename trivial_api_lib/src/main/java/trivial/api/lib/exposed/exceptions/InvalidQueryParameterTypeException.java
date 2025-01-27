package trivial.api.lib.exposed.exceptions;

public class InvalidQueryParameterTypeException extends MisconfiguredControllerException {
    public InvalidQueryParameterTypeException(String parameterName, String parameterType) {
        super("Parameter " + parameterName + " has unsupported type for query parameter: " + parameterType);
    }
}
