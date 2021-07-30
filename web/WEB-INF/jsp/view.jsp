<%@ page import="com.basejava.webapp.util.HtmlUtil" %>
<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.OrganizationSection" %>
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

        <div class="uk-card-header">
            <h3 class="uk-card-title uk-float-left">${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"
                                                                 style="color: black" class="uk-icon-link"
                                                                 uk-icon="icon: pencil"></a>&nbsp;&nbsp;</h3>
            <a class="uk-button uk-button-primary uk-align-right" href="?action=list">Список</a>
        </div>

        <div class="uk-card-body">
            <h4>Контакты</h4>
            <hr>
            <table class="uk-table uk-table-small">
                <tbody>
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry"
                                 type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                    <tr>
                        <td width="20%">
                                ${contactEntry.getKey().getTitle()}
                        </td>
                        <td>
                            <a href="<%= HtmlUtil.contactToLink(contactEntry.getKey(),contactEntry.getValue()) %>">${contactEntry.getValue()}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="section" value="${sectionEntry.value}"/>
                <jsp:useBean id="section" type="com.basejava.webapp.model.Section"/>
                <h4>${type.title}</h4>
                <hr>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE'}">
                        <div>
                            <%=((TextSection) section).getContent()%>
                        </div>
                    </c:when>
                    <c:when test="${type=='PERSONAL'}">
                        <div>
                            <%=((TextSection) section).getContent()%>
                        </div>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                        <div>
                            <ul class="uk-list uk-list-disc">
                                <c:forEach var="item" items="<%=((ListSection) section).getContentList()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="organization"
                                   items="<%=((OrganizationSection) section).getOrganizationsList()%>">
                            <div>
                                <c:choose>
                                    <c:when test="${empty organization.homePage.url}">
                                        <h3>${organization.homePage.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${organization.homePage.url}">${organization.homePage.name}</a>
                                        </h3>
                                    </c:otherwise>
                                </c:choose>
                                <ul class="uk-list uk-list-disc">
                                <c:forEach var="stage" items="${organization.stages}">
                                    <jsp:useBean id="stage" type="com.basejava.webapp.model.Organization.Stage"/>
                                    <li>${HtmlUtil.formatDate(stage)} <b>${stage.jobTitle}</b><br>${stage.description}</li>
                                </c:forEach>
                                </ul>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>


        </div>


    </div>

</section>
<jsp:include page="../../fragments/footer.jsp"/>
</body>
</html>