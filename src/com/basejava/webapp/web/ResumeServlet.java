package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageDb();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        action = action == null ? "list" : action;

        String jspFile;
        switch (action) {
            case "create":
                Resume r = new Resume("Новое Резюме");
                uuid = r.getUuid();
                storage.save(r);
                response.sendRedirect("resume?action=edit&uuid=" + uuid);
                return;
            case "delete":
                storage.delete(uuid);
                uuid = null;
            case "list":
                request.setAttribute("resumes", storage.getAllSorted());
                jspFile = "/WEB-INF/jsp/list.jsp";
                break;
            case "view":
                jspFile = "/WEB-INF/jsp/view.jsp";
                break;
            case "edit":
                jspFile = "/WEB-INF/jsp/edit.jsp";
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        if (uuid != null) {
            Resume r = storage.get(uuid);
            request.setAttribute("resume", r);
        }
        request.getRequestDispatcher(jspFile).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());

            if (value == null || value.trim().length() == 0) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(Arrays.stream(value.split("\\R", -1)).filter(e -> e.trim().length() > 0).toArray(String[]::new)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                }
            }
        }

        storage.update(r);
        response.sendRedirect("resume?action=edit&uuid=" + uuid);
    }
}
