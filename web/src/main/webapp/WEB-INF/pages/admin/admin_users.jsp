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
                <fmt:message key="user.Lastname"/>
            </th>
            <th>
                <fmt:message key="user.Firstname"/>
            </th>
            <th>
                <fmt:message key="user.Login"/>
            </th>
            <th>
                <fmt:message key="user.Phone"/>
            </th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${listUsers}" var="user">
            <tr>
                <td>${user.getLastName()}</td>
                <td>${user.getFirstName()}</td>
                <td>${user.getLogin()}</td>
                <td>${user.getPhone()}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/do/admin_user_block" method="post">
                        <button type="submit" class="button" name="userId"
                                value="${user.getUserId()}">
                            <fmt:message key="button.blocked"/>
                            <!--<img src="${pageContext.request.contextPath}/images/block_25.png">-->
                        </button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/do/admin_user_view" method="post">
                        <button type="submit" class="button" name="userId"
                                value="${user.getUserId()}">
                            <fmt:message key="button.edit"/>
                          <!--  <img src="${pageContext.request.contextPath}/images/profile_25.png">-->
                        </button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/do/admin_user_increase_account" method="post">
                        <button type="submit" class="button" name="userId"
                                value="${user.getUserId()}">
                            <fmt:message key="button.increase.account"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="submit" class="button" name="userId"
            value="${user.getUserId()}">
        <fmt:message key="button.create"/>
    </button>
    </form>

    </br>
    <div>
        <form action="${pageContext.request.contextPath}/do" method="get">
            <input type="hidden" name="commandNext" value="admin_users">
            <ctg:pagination/>
        </form>
    </div>
</div>
</body>
</html>