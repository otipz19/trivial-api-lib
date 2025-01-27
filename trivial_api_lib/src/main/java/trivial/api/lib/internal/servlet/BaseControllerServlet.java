package trivial.api.lib.internal.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import trivial.api.lib.exposed.BaseController;
import trivial.api.lib.exposed.ControllerResult;
import trivial.api.lib.exposed.annotations.HttpDelete;
import trivial.api.lib.exposed.annotations.HttpGet;
import trivial.api.lib.exposed.annotations.HttpPost;
import trivial.api.lib.exposed.annotations.HttpPut;
import trivial.api.lib.exposed.exceptions.ControllerMethodParameterMappingException;
import trivial.api.lib.internal.controller.method.adapters.ControllerMethodAdapter;
import trivial.api.lib.internal.controller.method.adapters.ControllerMethodAdaptersContainer;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseControllerServlet extends HttpServlet {
    private final Class<? extends BaseController> controllerClass = getControllerClass();

    private ControllerMethodAdaptersContainer httpGetMethodAdapters;
    private ControllerMethodAdaptersContainer httpPostMethodAdapters;
    private ControllerMethodAdaptersContainer httpPutMethodAdapters;
    private ControllerMethodAdaptersContainer httpDeleteMethodAdapters;

    protected abstract Class<? extends BaseController> getControllerClass();

    @Override
    public void init() {
        httpGetMethodAdapters = ControllerMethodAdaptersContainer.createFromController(controllerClass, HttpGet.class);
        httpPostMethodAdapters = ControllerMethodAdaptersContainer.createFromController(controllerClass, HttpPost.class);
        httpPutMethodAdapters = ControllerMethodAdaptersContainer.createFromController(controllerClass, HttpPut.class);
        httpDeleteMethodAdapters = ControllerMethodAdaptersContainer.createFromController(controllerClass, HttpDelete.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(this.httpGetMethodAdapters, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(this.httpPostMethodAdapters, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(this.httpPutMethodAdapters, req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(this.httpDeleteMethodAdapters, req, resp);
    }

    private void processRequest(ControllerMethodAdaptersContainer methodAdapters, HttpServletRequest req, HttpServletResponse resp) {
        try {
            ControllerMethodAdapter chosenMethodAdapter = methodAdapters.chooseMethodAdapter(req);

            if (chosenMethodAdapter == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            ControllerResult<?> controllerResult = chosenMethodAdapter.invoke(req);

            resp.setStatus(controllerResult.statusCode());
            if (controllerResult.resultObject() != null) {
                String json = new ObjectMapper().writeValueAsString(controllerResult.resultObject());
                resp.getWriter().write(json);
            }
        } catch (ControllerMethodParameterMappingException | JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}