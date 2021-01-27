<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="main.title"/></title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link href="http://bootstrapformhelpers.com/assets/css/bootstrap-formhelpers.min.css" rel="stylesheet"
          media="screen">
    <script src="http://bootstrapformhelpers.com/assets/js/bootstrap-formhelpers.min.js"></script>
    <style>
        <%@include file="style.css" %>
    </style>
</head>
<body>
<c:import url="common/header_admin.jsp"/>
<main class="main">
    <div class="container" id="registration">
        <c:if test="${isRegistry=='true'}">
            <fmt:message key="registration.success"/>
        </c:if>
        <c:if test="${isRegistry=='false'}">
            <fmt:message key="registration.unsuccess"/>
        </c:if>
        <c:if test="${isPreRegistration=='true'}">
            <fmt:message key="pre-registration.success"/>
        </c:if>
        <c:if test="${isPreRegistration=='false'}">
            <fmt:message key="pre-registration.unsuccess"/>
        </c:if>
        <p>
            <a href="${pageContext.request.contextPath}/do/main_page_view">
                <fmt:message key="back.main.page"/>
            </a>
        </p>
    </div>
</main>
</body>

</html>