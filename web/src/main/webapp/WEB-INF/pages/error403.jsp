<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
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
</head>
<body>
<div id="appearance-error">
    <div class="">
        <h1 id="error-title">
            <br><fmt:message key="error403.title"/></h1>
        <p id="error-name">
            <br><fmt:message key="error403.name"/><br>
            <br>
        </p>
        <p>
            <a class="btn btn-primary text-uppercase" role="button"
               href="${pageContext.request.contextPath}/do/main_page_view">
                <fmt:message key="error.back"/>
            </a>
        </p>
    </div>
</div>
</body>

</html>