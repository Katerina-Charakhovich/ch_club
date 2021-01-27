<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>

<html>
<head>
    <title>Title</title>
</head>
<style>
    <%@include file="../style.css" %>
</style>
<body>
<c:import url="../common/header_admin.jsp"/>
<div class="container">
    <table style="with: 50%" border="1">
        <tr>
            <th>
                <fmt:message key="event.typeEvent"/>
            </th>
            <th>
                <fmt:message key="event.label.name"/>
            </th>
            <th>
                <fmt:message key="event.short.description"/>
            </th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${listEvent}" var="event">
        <div>
            <tr>
                <td>${event.getName()}</td>
                <td>${event.getEventType().toString()}</td>
                <td>${event.getShortDescription()}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/do/admin_event_edit" method="post">
                        <button type="submit" class="btn btn-lg btn-block btn-outline-primary" name="eventId"
                                value="${event.getEventId()}">
                            <img src="${pageContext.request.contextPath}/images/edit.png">
                            <!-- <fmt:message key="button.edit"/>
                           --></button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/do/admin_event_edit" method="post">
                        <button type="submit" class="btn btn-lg btn-block btn-outline-primary" name="eventId"
                                value="${event.getEventId()}">
                            <img src="${pageContext.request.contextPath}/images/calendar.png">
                        </button>
                    </form>
                </td>
                <td>
                    <button type="submit" class="btn btn-lg btn-block btn-outline-primary" name="eventId"
                            value="${event.getEventId()}">
                        <img src="${pageContext.request.contextPath}/images/delete-sign.png">
                    </button>
                </td>
            </tr>
            <div>
        </c:forEach>
        <div>
            <form action="${pageContext.request.contextPath}/do/ADMIN_EVENT_ADD_PAGE" method="post">
                <button type="submit" class="button" name="userId"
                        value="${user.getUserId()}">
                    <fmt:message key="button.create"/>
                </button>
            </form>
        </div>
    </table>
    </form>
    </br>
    <div>
        <form action="${pageContext.request.contextPath}/do" method="get">
            <input type="hidden" name="commandNext" value="admin_events">
            <ctg:pagination/>
        </form>
    </div>

</div>
<script src="${pageContext.request.contextPath}/js/delete_cookie.js"></script>
</body>
</html>