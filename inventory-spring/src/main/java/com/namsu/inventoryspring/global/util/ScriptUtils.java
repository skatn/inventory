package com.namsu.inventoryspring.global.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ScriptUtils {

    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=euc-kr");
        response.setCharacterEncoding("euc-kr");
    }

    public static void alert(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter writer = response.getWriter();
        writer.println("<script>alert('" + alertText + "');</script> ");
        writer.flush();
    }

    public static void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage)
            throws IOException {
        init(response);
        PrintWriter writer = response.getWriter();
        writer.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
        writer.flush();
    }

    public static void alertAndBackPage(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter writer = response.getWriter();
        writer.println("<script>alert('" + alertText + "'); history.go(-1);</script>");
        writer.flush();
    }
}
