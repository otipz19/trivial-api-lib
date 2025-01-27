package trivial.api.lib.internal.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import trivial.api.lib.exposed.ControllerResult;
import trivial.api.lib.exposed.exceptions.ControllerMethodParameterMappingException;
import trivial.api.lib.internal.controller.method.adapters.ControllerMethodAdapter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trivial.api.lib.internal.exceptions.RouteNotMatchedException;
import trivial.api.lib.internal.routing.HttpMethodType;
import trivial.api.lib.internal.routing.Router;
import trivial.api.lib.internal.routing.RoutersCollection;

import java.io.IOException;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    private RoutersCollection routersCollection;

    @Override
    public void init() {
        routersCollection = (RoutersCollection) getServletContext().getAttribute(RoutersCollection.SERVLET_CONTEXT_KEY);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(routersCollection.getRouter(HttpMethodType.GET), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(routersCollection.getRouter(HttpMethodType.POST), req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(routersCollection.getRouter(HttpMethodType.PUT), req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(routersCollection.getRouter(HttpMethodType.DELETE), req, resp);
    }

    private void processRequest(Router router, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String route = req.getPathInfo();
            ControllerMethodAdapter chosenMethodAdapter = router.matchRoute(route).chooseMethodAdapter(req);

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
        } catch (RouteNotMatchedException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}