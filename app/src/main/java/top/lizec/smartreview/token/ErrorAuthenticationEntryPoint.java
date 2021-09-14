package top.lizec.smartreview.token;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.lizec.smartreview.response.Result;

@Component
public class ErrorAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        // 设置 Json 格式返回
        response.setContentType("application/json;charset=UTF-8");
        // 按照HTTP规范应该返回401, 但会导致前端处理更加复杂, 所以还是返回200
        response.setStatus(HttpServletResponse.SC_OK);
        // PrintWriter 输出 Response 返回信息
        PrintWriter writer = response.getWriter();

        writer.write(Result.failure("非授权访问").toJSON());
    }
}
