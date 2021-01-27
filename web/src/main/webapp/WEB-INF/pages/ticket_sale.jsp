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
    <div class="container text-center">
        <input type="hidden" name="command" value="ticket_sale">
        <h1 class="h3 mt-5 mb-1"><c:out value="${eventView.getName()}"/></h1>
        <h2 class="lead mt-0 mb-5"><c:out value="${eventView.getShortDescription()}"/></h2>
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
                        <p style="overflow:auto; width: 500px; height:300px;"><c:out
                                value="${eventView.getDescription()}"/>
                        </p>
                        <br/>
                    </div>
                </div>
                <br/>
                <c:if test="${eventView.getEventType().toString()=='THEATRE'}">
                    <table style="with: 50%" border="1">
                        <tr>
                            <th>
                                <fmt:message key="event.date.time"/>
                            </th>
                            <th>
                                <fmt:message key="event.ticket.cost"/>
                            </th>
                            <th>
                                <fmt:message key="event.ticket.free.count"/>
                            </th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach items="${listEventDates}" var="eventDate">
                            <tr>
                                <td><fmt:parseDate value="${eventDate.getEventDateTime()}" pattern="yyyy-MM-dd'T'HH:mm"
                                                   var="parsedDateTime" type="both"/>
                                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"
                                                    var="datetime"/>
                                        ${datetime}</td>
                                <td>${eventDate.getTicketCost()}</td>
                                <td>${eventDate.getFreeTicketCount()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${eventDate.getFreeTicketCount()>0}">
                                            <select id="countTicketBuy">
                                                <c:forEach var="i" begin='1' end="${eventDate.getFreeTicketCount()}">
                                                    <option value=${i}> ${i} </option>
                                                </c:forEach>
                                            </select>

                                        </c:when>
                                        <c:otherwise>
                                            <label class="control-label col-xs-3"><fmt:message
                                                    key="label.no_ickets_available"/>></label>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${eventDate.getFreeTicketCount()>0 && not empty authUser}">
                                        <button type="submit" class="button"
                                                name="eventId"
                                                value="${event.getEventId()}">
                                            <fmt:message key="button.buy.booked"/>
                                        </button>
                                        </c:when>
                                        <c:when test="${eventDate.getFreeTicketCount()>0 && empty authUser}">
                                            <button type="submit" class="button"
                                                    name="eventId"
                                                    value="${event.getEventId()}">
                                                <fmt:message key="button.booked"/>
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="control-label col-xs-3"><fmt:message
                                                    key="label.no_tickets_available"/>></label>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    </br>
                </c:if>
                <c:if test="${eventView.getEventType().toString()=='QUEST'}">
                    <c:forEach items="${questSchedule}" var="entry">
                        <div class="row">
                            <div class="date">${entry.key}
                            <div class="slots">
                                <c:forEach items="${entry.value}" var="eventDate">
                                    <fmt:parseDate value="${eventDate.getEventDateTime()}" pattern="yyyy-MM-dd'T'HH:mm"
                                                   var="parsedDateTime" type="both"/>
                                    <fmt:formatDate pattern="dd.MM.yyyy" value="${parsedDateTime}"
                                                    var="date"/>

                                    <fmt:parseDate value="${eventDate.getEventDateTime()}"
                                                   pattern="yyyy-MM-dd'T'HH:mm"
                                                   var="parsedDateTime1" type="both"/>
                                    <fmt:formatDate pattern="HH:mm" value="${parsedDateTime1}"
                                                    var="time"/>
                                    <c:if test="${not empty authUser && authUser.getRole().toString()=='USER' && eventDate.getFreeTicketCount()>0}">
                                        <button type="button"
                                                id="buttonBuy"
                                                data-original-title="Is free"
                                                data-toggle="modal" data-target="#modalQuestSale"
                                                onclick="changeText(${eventDate.getEventDateId()})"
                                                class="item time_table_label free price-2-of-4" >
                                                ${time}
                                        </button>
                                    </c:if>
                                    <c:if test="${eventDate.getFreeTicketCount()>0 && empty authUser}">
                                        <button type="button"
                                                data-original-title="Is free"
                                                data-toggle="modal" data-target="#modalUserMustRegister"
                                                class="item time_table_label free price-2-of-4" >
                                                ${time}
                                        </button>
                                    </c:if>
                                    <c:if test="${eventDate.getFreeTicketCount()<1}">
                                        <button type="button" data-original-title=
                                            <fmt:message key="quest.schedule.sales"/>
                                                data-toggle="tooltip"
                                                class="item time_table_label closed">
                                                ${time}
                                        </button>

                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                    </br>
                    <div>
                        <form action="${pageContext.request.contextPath}/do" method="get">
                            <input type="hidden" name="commandNext" value="admin_event_dates">
                            <ctg:pagination/>
                        </form>
                    </div>
                    </br>
                </c:if>
            </div>
        </div>
    </div>
</main>
<div role="dialog" tabindex="-1" class="modal fade" id="modalUserMustRegister">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                    <i class="fa fa-edit"></i>
                    <fmt:message key="modal.message"/>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <div class="form-group" >
                    <p>
                        <fmt:message key="user.mustRegister"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="common/footer.jsp"/>
<c:import url="modal_ticket_sale.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ticket_sale.js"></script>
</body>
<script>
    function changeText(q) {
        document.getElementById('questName').setAttribute('value', '${eventView.getName()}');
        document.getElementById('timeModal').setAttribute('value', '${time}');
        document.getElementById('dateModal').setAttribute('value', '${date}');
        document.getElementById('eventDateId').setAttribute('value', q);
        document.getElementById('eventId').setAttribute('value', '${eventView.getEventId()}');
        document.getElementById('eventType').setAttribute('value', '${eventView.getEventType()}');

    }
    $("#pay").click(function () {
        $("#commentAddBlock").prop('hidden', true);
        $("#ticketState").val("PAID");
        $("#addComment").prop('hidden', false);
    });
    $("#booked").click(function () {
        $("#commentAddBlock").prop('hidden', true);
        $("#ticketState").val("BOOKED");
        $("#addComment").prop('hidden', false);
    });
    </script>
</html>