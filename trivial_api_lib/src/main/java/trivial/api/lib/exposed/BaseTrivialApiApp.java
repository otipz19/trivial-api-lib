package trivial.api.lib.exposed;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import trivial.api.lib.exposed.annotations.*;
import trivial.api.lib.exposed.exceptions.MisconfiguredControllerException;
import trivial.api.lib.internal.routing.HttpMethodType;
import trivial.api.lib.internal.routing.RoutersCollection;

import java.lang.reflect.Method;

public abstract class BaseTrivialApiApp implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ClassGraph classGraph = new ClassGraph()
                .enableAllInfo()
                .enableClassInfo()
                .enableAnnotationInfo()
                .enableMethodInfo();

        RoutersCollection routersCollection = new RoutersCollection();

        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfoList controllerClasses = scanResult.getClassesWithAnnotation(Controller.class);

            for (ClassInfo controllerClassInfo : controllerClasses) {
                Class<?> clazz = controllerClassInfo.getClass();

                if (!BaseController.class.isAssignableFrom(clazz)) {
                    throw new MisconfiguredControllerException("Class annotated with @Controller must extend BaseController");
                }

                Class<? extends BaseController> controller = (Class<? extends BaseController>) clazz;

                for (Method method : controller.getMethods()) {
                    if (method.isAnnotationPresent(HttpGet.class)) {
                        HttpGet annotation = method.getAnnotationsByType(HttpGet.class)[0];
                        String route = annotation.value();
                        routersCollection.addRoute(HttpMethodType.GET, route, controller, method);
                    }

                    if (method.isAnnotationPresent(HttpPost.class)) {
                        HttpPost annotation = method.getAnnotationsByType(HttpPost.class)[0];
                        String route = annotation.value();
                        routersCollection.addRoute(HttpMethodType.POST, route, controller, method);
                    }

                    if (method.isAnnotationPresent(HttpPut.class)) {
                        HttpPut annotation = method.getAnnotationsByType(HttpPut.class)[0];
                        String route = annotation.value();
                        routersCollection.addRoute(HttpMethodType.PUT, route, controller, method);
                    }

                    if (method.isAnnotationPresent(HttpDelete.class)) {
                        HttpDelete annotation = method.getAnnotationsByType(HttpDelete.class)[0];
                        String route = annotation.value();
                        routersCollection.addRoute(HttpMethodType.DELETE, route, controller, method);
                    }
                }

                sce.getServletContext().setAttribute(RoutersCollection.SERVLET_CONTEXT_KEY, routersCollection);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
