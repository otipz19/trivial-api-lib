package trivial.api.lib.internal.controller.method.parameters.adapters;

import trivial.api.lib.internal.controller.method.parameters.adapters.base.ControllerMethodParameterAdapter;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

import static trivial.api.lib.internal.TypeUtils.getDefaultValue;

public class NotAnnotatedParameterAdapter extends ControllerMethodParameterAdapter<Annotation> {
    public NotAnnotatedParameterAdapter(Parameter parameter) {
        super(parameter, null);
    }

    @Override
    public Object mapValue(HttpServletRequest request) {
        return getDefaultValue(parameter.getType());
    }
}
