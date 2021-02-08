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
<main class="main">
    <div class="container text-center">
        <h1 class="h3 mt-5 mb-1"><c:out value="${eventView.getName()}"/></h1>
        <h2 class="lead mt-0 mb-5"><c:out value="${eventView.getShortDescription()}"/></h2>
        <br class="container-sm">
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
                <p style="overflow:auto; width: 500px; height:300px;"><c:out
                        value="${eventView.getDescription()}"/>
                </p>
                <br/>
                <form action="${pageContext.request.contextPath}/do" method="post">
                    <input type="hidden" name="command" value="EVENT_TICKET_SALE">
                    <input type="hidden" name="eventId" value="${eventView.getEventId()}">
                    <footer>
                        <button type="submit" class="button" name="edit"
                                value="${eventView.getEventId()}"><fmt:message
                                key="button.viewDates"/></button>
                    </footer>
                    <c:if test="${empty authUser}">
                        <footer>
                            <label style="color:#0000FF" size="4"><fmt:message
                                    key="label.must.registry.buy.ticket"/></label>
                        </footer>
                    </c:if>

                </form>
            </div>
        </div>
        </br>
        <div class="row">
            <c:if test="${empty authUser}">
                <label style="color:#0000FF" size="4">
                    <fmt:message key="label.must.registry.add.message"/>
                </label>
            </c:if>
            <c:if test="${not empty authUser && authUser.getRole().toString()=='USER'}">
            <div class="form-group" id="commentAddBlock" hidden>
                <form action="${pageContext.request.contextPath}/do/add_message" method="post">
                    <label for="from-eventMessage">
                        <fmt:message key="label.opinionAboutEvent"/>
                    </label>
                    <input type="hidden" name="eventId" value="${eventView.getEventId()}">
                    <input type="hidden" name="numberPage" value="${numberPage}">
                    <textarea formenctype="text/plain;charset= UTF-8"
                              rows=
                                      id="from-eventdesc"
                              class="form-control"
                              id="from-eventMessage" name="eventMessage"
                              value="">
                        </textarea>
                    </br>
                    <button id="saveComment" class="button" type="submit">
                        <fmt:message key="button.save"/>
                    </button>
                    <button id="cancelSaveComment" class="button" type="button">
                        <fmt:message key="button.cancel"/>
                    </button>
                    <!--
                        <button type="submit" class=button" name="add" value="${eventView.getEventId()}">
                            <fmt:message key="button.add"/></button> -->
                </form>
                <!--
                    <button type="button" class=button" id="Cancel">
                        <fmt:message key="button.cancel"/></button>
                        -->
            </div>
            <div class="form-group"></div>
            <c:if test="${authUser.getRole()=='USER'}">
                <button type="submit" class=button" id="addComment">
                    <fmt:message key="button.comment"/></button>
            </c:if>
        </div>
        </c:if>
    </div>
    <c:forEach items="${listMessages}" var="message">
        <div class="container_mes">
            <c:choose>
                <c:when test="${not empty message.getUser().getPhoto()}">
                    <div>
                        <img width="220px" src="data:image/jpeg;base64,${message.getUser().getPhoto()}"
                             alt="Avatar"
                             style="width:90px">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="photoBorder">
                        <img src="${pageContext.request.contextPath}/images/1.png" alt="Avatar"
                             style="width:90px">
                    </div>
                </c:otherwise>
            </c:choose>
            <p><span><c:out value="${message.getUser().fullName()}"/></span></p>
            <p><c:out value="${message.getText()}"/></p>
        </div>
    </c:forEach>
    <div>
        <form action="${pageContext.request.contextPath}/do" method="get">
            <input type="hidden" name="command" value="edit_view">
            <ctg:pagination/>
        </form>
    </div>
    </br>
    </div>
</main>
<c:import url="common/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/event_view.js"></script>
</body>
</html>