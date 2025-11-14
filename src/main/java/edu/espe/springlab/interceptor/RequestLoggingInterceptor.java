package edu.espe.springlab.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // Marca de tiempo para medir la latencia total
        request.setAttribute("t0", System.currentTimeMillis());

        // Log básico de método + URI
        System.out.println("preHandle -> " + request.getMethod() + " " + request.getRequestURI());

        // (Opcional) Leer claims del filtro JWT si existen
        Object jwtObj = request.getAttribute("jwt");
        if (jwtObj instanceof com.auth0.jwt.interfaces.DecodedJWT jwt) {
            String sub = jwt.getSubject();
            String scope = jwt.getClaim("scope").asString();
            System.out.println("caller=" + sub + " scope=" + scope);

            // (Opcional) Reglas por scope (si quisieras bloquear aquí)
            // if (request.getRequestURI().startsWith("/api/students")
            //         && (scope == null || !scope.contains("students:read"))) {
            //     response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //     return false;
            // }
        }

        return true; // continuar con el flujo
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        Long t0 = (Long) request.getAttribute("t0");
        long elapsed = (t0 == null ? 0 : System.currentTimeMillis() - t0);
        System.out.println("afterCompletion -> status = " + response.getStatus()
                + " tiempo = " + elapsed + " ms");
    }
}
