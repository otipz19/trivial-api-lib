package trivial.api.lib.exposed;

public record ControllerResult<T>(T resultObject, int statusCode) {
}
