<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<body>
<c:import url="../common/header.jsp"/>
<!-- MAIN -->
<main class="main">
    <div class="container text-center">
        <h1 class="h3 mt-5 mb-1">${eventView.getName()}</h1>
        <h2 class="lead mt-0 mb-5">${eventView.getShortDescription()}</h2>
        <div class="event">
            <div class="container-sm">
                <div class="row">
                    <div class="col-7" id="main_pictures">
                        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                            <div class="carousel-inner">
                                <c:if test="${not empty eventAdditionalPictures && eventAdditionalPictures.size()>0}">
                                    <div class="carousel-item active">
                                        <img src="data:image/png;base64,${eventAdditionalPictures.get(0).getName()}"
                                             alt="12"/>
                                    </div>
                                    <c:if test="${eventAdditionalPictures.size()>1}">
                                        <c:forEach items="${eventAdditionalPictures}" begin="1"
                                                   end="${eventAdditionalPictures.size()}" var="picture">
                                            <div class="carousel-item">
                                                <img src="data:image/jpeg;base64,${picture.getName()}"
                                                     alt="Additional"/>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </div>
                            <a class="carousel-control-prev" href="#carouselExampleControls" role="button"
                               data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselExampleControls" role="button"
                               data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </div>
                    <div class="col-5" id="event_data">
                        <br/>
                        <p style="overflow:auto; width: 500px; height:300px;">${eventView.getDescription()}
                        </p>
                        <br/>
                        <footer>
                            <button type="submit" class="btn btn-lg btn-block btn-outline-primary" name="edit"
                                    value="${eventView.getEventId()}"><fmt:message key="button.edit"/></button>
                        </footer>
                    </div>
                </div>

            </div>
        </div>
        <table style="with: 50%" border="1">
            <tr>
                <th>
                    <fmt:message key="event.short.description"/>
                </th>
                <th>
                    <fmt:message key="event.date.time"/>
                </th>
                <th>
                    <fmt:message key="event.short.description"/>
                </th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${listEvent}" var="event">
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
                        <form action="${pageContext.request.contextPath}/do/admin_event_dates" method="post">
                            <button type="submit" class="btn btn-lg btn-block btn-outline-primary" name="eventId"
                                    value="${event.getEventId()}">
                                <!--<fmt:message key="button.delete"/>-->
                                <img src="${pageContext.request.contextPath}/images/calendar.png">
                            </button>
                        </form>
                    </td>
                    <td>
                        <button type="submit" class="btn btn-lg btn-block btn-outline-primary" name="eventId"
                                value="${event.getEventId()}">
                            <!-- <fmt:message key="button.shedule"/>
                              -->
                            <img src="${pageContext.request.contextPath}/images/delete-sign.png">
                            <!-- <img src="${pageContext.request.contextPath}/images/del_s.png">-->
                        </button>
                    </td>
                    <td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
<!-- FOOTER -->
<footer class="footer">
    <div class="container-fluid">
        FOOTER
    </div>
</footer>
</body>
</html>