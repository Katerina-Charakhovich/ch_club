<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="main.title"/></title>
    <meta name="description" content="">
    <meta name="keywords" content="">
</head>
<body>
<c:import url="common/header_admin.jsp"/>
<main class="main">
    <div class="container">
        <h1> Hello? this page Events</h1>

            <div class="card-deck mb-3 text-center">
                <c:forEach items="${listEvent}" var="event">
                <div class="col col-md-3 col-lg-4">
                    <div class="card mb-4 box-shadow">
                        <div class="card-header">
                            <h4 class="my-0 font-weight-normal">${event.getName()}</h4>
                        </div>
                        <c:choose>
                            <c:when test="${not empty event.getMainPicture().getName()}">
                                <div>
                                    <img width="300px" src="data:image/png;base64,${event.getMainPicture().getName()}"/>
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
                                <p><c:out value="${event.getShortDescription()}"/></p>
                            </ul>
                            <form action="${pageContext.request.contextPath}/do" method="post">
                                <input type="hidden" name="command" value="event_view">
                                <button type="submit" class="button" name="eventId"
                                        value="${event.getEventId()}"><fmt:message key="button.view"/></button>
                            </form>
                            </br>
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>
        </br>
        <div>
            <form action="${pageContext.request.contextPath}/do" method="get">
                <input type="hidden" name="commandNext" value="events">
                <ctg:pagination/>
            </form>
        </div>
    </div>
</main>
<c:import url="common/footer.jsp"/>
</body>
</html>