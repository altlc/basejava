package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    private final Storage storage;

    public ResumeServlet() {
        storage = Config.get().getStorageDb();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuilder result = new StringBuilder();

        result.append("<table border=0 cellspacing=0 style=\"border: 1px solid black\">\n<thead>\n<tr>\n<th>UUID</th><th>Full name</th>\n<tr>\n<thead>\n<tbody>\n");
        int row = 0;
        for (Resume resume: storage.getAllSorted()) {
            String rowStyle = (row % 2 == 0) ? "#dedfe0" : "white";
            row++;
            result.append("<tr style='background-color: ").append(rowStyle).append("'>\n<td >").
                    append(resume.getUuid()).
                    append("</td><td>").
                    append(resume.getFullName()).
                    append("</td>\n<tr>\n");
        }

        result.append("</tbody>\n</table>\n");

        out.write(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
