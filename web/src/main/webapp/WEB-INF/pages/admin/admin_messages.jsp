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
                <fmt:message key="event.name"/>
            </th>
            <th>
                <fmt:message key="user.fullName"/>
            </th>
            <th>
                <fmt:message key="message.text"/>
            </th>
            <th>
                <fmt:message key="message.date"/>
            </th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${listMessages}" var="message">
            <tr>
                <td><c:out value="${message.getEvent().getName()}"/></td>
                <td><c:out value="${message.getUser().fullName()}"/></td>
                <td><c:out value="${message.getText()}"/></td>
                <td><c:out value="${message.getModifyDate()}"/></td>
                <td>
                    <form action="${pageContext.request.contextPath}/do/admin_message_approve" method="post">
                        <button type="submit" class="button" name="messageId"
                                value="${message.getMessageId()}">
                            <fmt:message key="button.approve"/>
                        </button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/do/admin_message_cancel" method="post">
                        <button type="submit" class="button" name="messageId"
                                value="${message.getMessageId()}">
                            <fmt:message key="button.cancel"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    </br>
    <div>
        <form action="${pageContext.request.contextPath}/do" method="get">
            <input type="hidden" name="commandNext" value="admin_messages">
            <ctg:pagination/>
        </form>
    </div>
</div>
</body>
</html>