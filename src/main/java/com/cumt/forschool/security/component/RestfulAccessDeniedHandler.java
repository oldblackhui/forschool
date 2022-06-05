package com.cumt.forschool.security.component;

import com.cumt.forschool.enums.ResultVOEnum;
import com.cumt.forschool.utils.JacksonUtil;
import com.cumt.forschool.vo.ResultVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
/**
 * @Author: ahui
 * @date: 2022/2/5 - 21:50
 */

/**
 * <p>
 * 权限不足异常处理类
 * </p>
 * 当用户尝试访问需要权限才能的REST资源而权限不足的时候，
 * 将调用此方法发送403响应以及错误信息
 *
 * @author 和耳朵
 * @since 2020-06-30
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(JacksonUtil.toJsonString(ResultVO.fail(ResultVOEnum.PERMISSION_FAIL)));
        response.getWriter().flush();
    }
}

