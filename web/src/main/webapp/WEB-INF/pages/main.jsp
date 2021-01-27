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
</head>
<body>
<c:import url="common/header_admin.jsp"/>
<!-- MAIN -->
<main class="main">
    <div class="container">
        <div class="row" style="text-align:center;">
            <h2 style="text-align:center;"><fmt:message key="main.h2.theatre"/></h2>
            <hr>
            <div class="row" style="text-align:center;">
                <c:forEach items="${listTheatre}" var="event">
                    <div class="col col-md-3 col-lg-4">
                        <div class="card mb-4 box-shadow">
                            <div class="card-header">
                                <h4 class="my-0 font-weight-normal">${event.getName()}</h4>
                            </div>
                            <c:choose>
                                <c:when test="${not empty event.getMainPicture().getName()}">
                                    <div>
                                        <img width="200px"
                                             src="data:image/png;base64,${event.getMainPicture().getName()}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="photoBorder">
                                        <img width=
                                                     src="${pageContext.request.contextPath}/images/1.png"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <ul class="list-unstyled mt-3 mb-4">
                                    <p>${event.getShortDescription()}</p>
                                </ul>
                                <form action="${pageContext.request.contextPath}/do" method="post">
                                    <input type="hidden" name="command" value="event_view">
                                    <button type="submit" class="button"
                                            name="eventId"
                                            value="${event.getEventId()}"><fmt:message key="button.view"/></button>
                                </form>
                                </br>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <a class="nav-link" href="${pageContext.request.contextPath}/do/events">
                <fmt:message key="navbar.theatre.page"/></a>
        </div>
        <div class="row" style="text-align:center;">
            <h2 style="text-align:center;"><fmt:message key="main.h2.quest"/></h2>
            <hr>
            <div class="row" style="text-align:center;">
                <c:forEach items="${listQuest}" var="event">
                    <div class="col col-md-3 col-lg-4">
                        <div class="card mb-4 box-shadow">
                            <div class="card-header">
                                <h4 class="my-0 font-weight-normal">${event.getName()}</h4>
                            </div>
                            <c:choose>
                                <c:when test="${not empty event.getMainPicture().getName()}">
                                    <div>
                                        <img width="200px"
                                             src="data:image/png;base64,${event.getMainPicture().getName()}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="photoBorder">
                                        <img width=
                                                     src="${pageContext.request.contextPath}/images/1.png"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!--   <img class="card-img-top" src="images/${event.getMainPicture().getName()}" alt="Card image cap">
       -->
                            <div class="card-body">
                                <ul class="list-unstyled mt-3 mb-4">
                                    <p>${event.getShortDescription()}</p>
                                </ul>
                                <form action="${pageContext.request.contextPath}/do" method="post">
                                    <input type="hidden" name="command" value="event_view">
                                    <button type="submit" class="button"
                                            name="eventId"
                                            value="${event.getEventId()}"><fmt:message key="button.view"/></button>
                                </form>
                                </br>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <a class="nav-link" href="${pageContext.request.contextPath}/do/events">
                <fmt:message key="navbar.quest.page"/></a>
        </div>
    </div>
</main>
<c:import url="common/footer.jsp"/>
</body>
</html>
