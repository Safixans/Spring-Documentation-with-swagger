package SpringDoc.uz.pdp.docs.handler;

import SpringDoc.uz.pdp.docs.dto.AppErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public MyAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        accessDeniedException.printStackTrace();
        String errorPath=request.getRequestURI();
        String errorMessage=accessDeniedException.getMessage();
        int errorCode=403;
        response.setStatus(errorCode);
        AppErrorDto appErrorDto = new AppErrorDto(errorPath,errorMessage,errorCode);
        OutputStream outputStream=response.getOutputStream();
        objectMapper.writeValue(outputStream,appErrorDto);

    }
}
