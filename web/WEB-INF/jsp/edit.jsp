<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.Section" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.OrganizationSection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="../../fragments/header.jsp"/>
<body>
<section>
    <div class="uk-card uk-card-default uk-card-small uk-width-2-3 uk-align-center"
         style="margin-top: 10px">
        <form class="uk-form-horizontal" method="post" action="resume" enctype="application/x-www-form-urlencoded">
            <div class="uk-card-header">
                <h3 class="uk-card-title uk-float-left">${resume.fullName}</h3>
                <a class="uk-button uk-button-primary uk-align-right" href="?action=list">Список</a>
            </div>

            <div class="uk-card-body">


                <input type="hidden" name="uuid" value="${resume.uuid}">

                <div class="uk-margin">
                    <label class="uk-form-label" for="fullName">Имя: </label>
                    <div class="uk-form-controls">
                        <input class="uk-input uk-form-width-large" type="text" id="fullName" name="fullName"
                               value="${resume.fullName}">
                    </div>
                </div>


                <h4>Контакты</h4>
                <hr>
                <c:forEach var="type" items="<%=ContactType.values()%>">
                    <div class="uk-margin">
                        <label class="uk-form-label" for="${type.name()}">${type.title}: </label>
                        <div class="uk-form-controls">
                            <input class="uk-input uk-form-width-medium" type="text" name="${type.name()}"
                                   id="${type.name()}"
                                   value="${resume.getContact(type)}">
                        </div>
                    </div>
                </c:forEach>
                <c:forEach var="type" items="<%=SectionType.values()%>">
                    <jsp:useBean id="type" type="com.basejava.webapp.model.SectionType"/>
                    <h4>${type.title}</h4>
                    <hr>
                    <c:choose>
                        <c:when test="${type=='OBJECTIVE'}">
                            <div class="uk-margin">
                                <label class="uk-form-label" for="${type.name()}">${type.title}: </label>
                                <div class="uk-form-controls">
                                    <input class="uk-input uk-form-width-large" name='${type.name()}'
                                           id="${type.name()}"
                                           value='${resume.getSection(type).getContent()}'>
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${type=='PERSONAL'}">
                            <div class="uk-margin">
                                <label class="uk-form-label" for="${type.name()}">${type.title}: </label>
                                <div class="uk-form-controls">
                            <textarea class="uk-textarea uk-form-width-large" rows="5"
                                      name='${type.name()}'
                                      id='${type.name()}'>${resume.getSection(type).getContent()}</textarea>
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                            <%
                                Section section = resume.getSection(type);
                                List<String> sectionContent = section != null ? ((ListSection) section).getContentList() : new ArrayList<String>();
                                String content = (String.join("\n", sectionContent));
                            %>
                            <div class="uk-margin">
                                <label class="uk-form-label" for="${type.name()}">${type.title}: </label>
                                <div class="uk-form-controls">
                                <textarea class="uk-textarea uk-form-width-large" rows="5"
                                          name='${type.name()}'
                                          id='${type.name()}'><%=content%></textarea>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </div>

            <div class="uk-card-footer">
                <button class="uk-button uk-button-danger" type="submit">Сохранить</button>&nbsp;
                <button class="uk-button uk-button-default" type="reset">Отменить</button>&nbsp;
                <a class="uk-button uk-button-primary" href="?action=list">Список</a>
            </div>
        </form>
    </div>
</section>
<jsp:include page="../../fragments/footer.jsp"/>
</body>
</html>