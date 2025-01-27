package trivial.api.lib.internal.controller.method.parameters.adapters;

import trivial.api.lib.exposed.annotations.RequiredQueryParam;
import trivial.api.lib.internal.controller.method.parameters.adapters.base.AbstractQueryParameterAdapter;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

import static trivial.api.lib.internal.TypeUtils.parsePrimitives;

public class RequiredQueryParameterAdapter extends AbstractQueryParameterAdapter<RequiredQueryParam> {
    public RequiredQueryParameterAdapter(Parameter parameter, RequiredQueryParam annotation) {
        super(parameter, annotation, annotation.value());
    }

    @Override
    public Object mapValue(HttpServletRequest request) {
        String requestParamValue = request.getParameterMap().get(parameterName)[0];
        return parsePrimitives(parameter.getType(), requestParamValue);
    }
}
