<%@ page import="com.basejava.webapp.model.ContactType" %>
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
            <h3 class="uk-card-title uk-float-left">Список резюме</h3>
            <a id="createButton" class="uk-button uk-button-primary uk-align-right" href="?action=create">Создать резюме</a>
        </div>

        <script>
            $(document).ready(function() {
                $('#createButton').on('click', function() {
                    UIkit.modal.confirm('Созать резюме?', {
                        labels: {
                            cancel: 'Отмена',
                            ok: 'Создать'
                        }
                    }).then(function () {
                        window.location.href="?action=create";
                        //console.log('Confirmed.')
                    }, function () {
                        //console.log('Rejected.')
                    });
                  return false;
                });
            });
        </script>

        <div class="uk-card-body">
            <table class="uk-table uk-table-striped uk-table-hover uk-table-small">
                <thead>
                <tr>
                    <th>Имя</th>
                    <th>Email</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
                    <tr>
                        <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                        <td>
                            <a href="mailto:${resume.getContact(ContactType.EMAIL)}">${resume.getContact(ContactType.EMAIL)}</a>
                        </td>
                        <td style="text-align: right">
                            <a href="resume?uuid=${resume.uuid}&action=edit" style="color: black" class="uk-icon-link"
                               uk-icon="icon: pencil"></a>&nbsp;&nbsp;
                            <a href="resume?uuid=${resume.uuid}&action=delete" style="color: red" class="uk-icon-link"
                               uk-icon="icon: trash"></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>


</section>
<jsp:include page="../../fragments/footer.jsp"/>
</body>
</html>