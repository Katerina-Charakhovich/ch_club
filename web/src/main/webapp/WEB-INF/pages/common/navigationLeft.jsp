<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<form action="${pageContext.request.contextPath}/do" method="post">
    <ul id="navbarV">
        <li><a class="nav-link" href="${pageContext.request.contextPath}/do/admin_users"> <fmt:message key="navigation.users"/></a></li>
        <li> <a class="nav-link" href="${pageContext.request.contextPath}/do/admin_events"><fmt:message key="navigation.events"/></a></li>
        <li> <a class="nav-link" href="${pageContext.request.contextPath}/do/admin_messages"><fmt:message key="navigation.messages"/></a></li>
    </ul>
</form>