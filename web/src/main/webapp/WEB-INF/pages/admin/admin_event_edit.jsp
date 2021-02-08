<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>

</head>
<style>
    <%@include file="../style.css" %>
</style>
<body>
<c:import url="../common/header_admin.jsp"/>
<main class="main">
    <div class="container">
        <h1 class="h3 mt-5 mb-1"><c:out value="${eventView.getName()}" /></h1>
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
                    <div class="col-md-6 col-lg-6 item">
                        <form class="custom-form" method="POST" id="updateEventInfo"
                              action="${pageContext.request.contextPath}/do/update_event_info">
                            <input type="hidden" id="eventUpdateId" name="eventUpdateId"
                                   value="${eventView.getEventId()}"/>
                            <div class="form-group">
                                <label for="from-eventname">
                                    <fmt:message key="event.label.name"/>
                                </label>
                                <input class="form-control" type="text" id="from-eventname"
                                       name="eventName"
                                       required
                                       onchange="this.setCustomValidity('')"
                                       previousValue="${eventView.getName()}"
                                       value="${eventView.getName()}" disabled>
                            </div>
                            <div class="warnMessage" style="color: #8b0000">
                                <c:if test="${not empty mapPageParams && mapPageParams.get('eventName').equals('error')}">
                                    <fmt:message key="eventEdit.warnMessage"/>
                                </c:if>
                            </div>

                            <div class="form-group">
                                <label for="from-eventshortdesc">
                                    <fmt:message key="event.short.description"/>
                                </label>
                                <input class="form-control"
                                       type="text"
                                       required
                                       id="from-eventshortdesc"
                                       name="eventShortDescription"
                                       previousValue="${eventView.getShortDescription()}"
                                       value="${eventView.getShortDescription()}" disabled>
                            </div>
                            <div class="warnMessage" style="color: #8b0000">
                                <c:if test="${not empty mapPageParams && mapPageParams.get('eventShortDescription').equals('error')}">
                                    <fmt:message key="eventEdit.warnMessage"/>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="from-eventdesc">
                                    <fmt:message key="event.description"/>
                                </label>
                                <textarea formenctype="text/plain;charset= UTF-8"
                                          rows="8"
                                          required
                                          class="form-control"
                                          id="from-eventdesc" name="eventDescription"
                                          previousValue=${eventView.getDescription()}
                                                  disabled>
                                    ${eventView.getDescription()}
                                </textarea>
                            </div>
                            <div class="warnMessage" style="color: #8b0000">
                                <c:if test="${not empty mapPageParams && mapPageParams.get('eventDescription').equals('error')}">
                                    <fmt:message key="eventEdit.warnMessage"/>
                                </c:if>
                            </div>
                            <button id="saveChange" class="button" type="submit" hidden>
                                <fmt:message key="button.save"/>
                            </button>
                            <button id="cancel" class="button" type="button" hidden>
                                <fmt:message key="button.cancel"/>
                            </button>
                        </form>
                        <button id="editEvent" type="submit" class="button" name="edit"
                                value="${eventView.getEventId()}"><fmt:message key="button.edit"/>
                        </button>

                    </div>
                </div>
                </br>
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
                            <!--     <th></th>-->
                        </tr>
                        <c:forEach items="${listEventDates}" var="eventDate">
                            <tr>
                                <td><fmt:parseDate value="${eventDate.getEventDateTime()}"
                                                   pattern="yyyy-MM-dd'T'HH:mm"
                                                   var="parsedDateTime" type="both"/>
                                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"
                                                    var="time"/>
                                        ${time}</td>
                                <td>${eventDate.getTicketCost()}</td>
                                <td>${eventDate.getFreeTicketCount()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${eventDate.getFreeTicketCount()>0}">
                                            <select id="countTicketBuy">
                                                <c:forEach var="i" begin='1'
                                                           end="${eventDate.getFreeTicketCount()}">
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
                                <!--<td>
                                    <c:choose>
                                        <c:when test="${eventDate.getFreeTicketCount()>0 }">
                                            <button type="submit" class="button"
                                                    name="eventId"
                                                    value="${event.getEventId()}">
                                                <fmt:message key="button.buy"/>
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="control-label col-xs-3"><fmt:message
                                                    key="label.no_ickets_available"/>></label>
                                        </c:otherwise>
                                    </c:choose>
                                </td> -->
                            </tr>
                        </c:forEach>
                    </table>
                    </br>
                    </hr>
                    </br>
                    <button type="submit" class="button" data-toggle="modal" data-target="#modalEventDate"
                            id="eventAddDate">
                            <!--onclick="javascript:document.getElementById('eventAddDateId').setAttribute('value', '${eventView.getEventId()}')">-->

                        <fmt:message key="button.newEventDate"/>
                    </button>
                </c:if>
                <c:if test="${eventView.getEventType().toString()=='QUEST'}">
                    <c:forEach items="${questdates}" var="entry">
                        <div class="row">
                            <div class="date">${entry.key}</div>
                            <div class="slots">
                                <c:forEach items="${entry.value}" var="eventDate">
                                    <c:if test="${eventDate.getState().toString()=='ACTUAL'}">
                                        <button type="button" data-original-title="Is free"
                                                data-toggle="tooltip"
                                                class="item time_table_label free price-1-of-4">
                                            <fmt:parseDate value="${eventDate.getEventDateTime()}"
                                                           pattern="yyyy-MM-dd'T'HH:mm"
                                                           var="parsedDateTime1" type="both"/>
                                            <fmt:formatDate pattern="HH:mm" value="${parsedDateTime1}"
                                                            var="time1"/>
                                                ${time1}
                                        </button>
                                    </c:if>
                                    <c:if test="${eventDate.getState().toString()=='BOOKED'}">
                                        <button type="button" data-original-title="Бронь недоступна"
                                                data-toggle="tooltip"
                                                class="item time_table_label closed">
                                            <fmt:parseDate value="${eventDate.getEventDateTime()}"
                                                           pattern="yyyy-MM-dd'T'HH:mm"
                                                           var="parsedDateTime1" type="both"/>
                                            <fmt:formatDate pattern="HH:mm" value="${parsedDateTime1}"
                                                            var="time1"/>
                                                ${time1}
                                        </button>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                        </br>
                    </c:forEach>
                    </br>
                    <div>
                        <form action="${pageContext.request.contextPath}/do" method="get">
                            <input type="hidden" name="commandNext" value="admin_event_dates">
                            <ctg:pagination/>
                        </form>
                    </div>
                    </br>
                    <button type="submit" class="button" data-toggle="modal" data-target="#modalQuestDate"
                            id="questAddDate"
                            onclick="javascript:document.getElementById('questId').setAttribute('value', '${eventView.getEventId()}')">
                        <fmt:message key="button.newEventDate"/>
                    </button>

                </c:if>
            </div>

        </div>
    </div>
    </div>
</main>


<input id="isUpdateEvent" type="hidden" value="${isUpdateEvent}">

<div role="dialog" tabindex="-1" class="modal fade" id="modalSuccessUpdateEvent">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                    <i class="fa fa-edit"></i>
                    <fmt:message key="event.UpdateTitle"/>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <div class="form-group" align="justify">
                    <p>
                        <fmt:message key="event.wasSuccessUpdate"/>
                    </p>
                </div>
                <div class="form-group">
                    <button type="button" class="button" data-dismiss="modal">
                        <fmt:message key="button.close"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


<div role="dialog" tabindex="-1" class="modal fade" id="modalQuestDate">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                    <fmt:message key="modal.title.newQuestDate"/>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <form class="p-4" method="POST"
                      action="${pageContext.request.contextPath}/do/admin_add_quest_date">
                    <!--      <input type="hidden" name="command" value="admin_add_quest_date"/> -->
                    <input type="hidden" id="questId" name="questId" value="${eventView.getEventId()}"/>

                    <label><fmt:message key="quest.date"/></label>
                    <br class="required col-xs-10">
                    <input class="input-group"
                           type="date" id="date" name="date"
                           required
                           max="2090-12-31">
                    <div class="warnMessage" id="oldQuestDate" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.oldDate"/>
                    </div>
                    <div class="warnMessage" id="existQuestDate" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.existDate"/>
                    </div>
                    </br>

                    <label><fmt:message key="quest.start.time"/></label>
                    <br class="required col-xs-10">
                    <input class="input-group"
                           type="time" id="questStartTime"
                           name="questStartTime"
                           required
                           min="09:00" max="20:00"
                           value="" pattern="HH:MM"/>"

                    <div class="warnMessage" id="incorrectQuestStartTime" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.incorrectTime"/>
                    </div>
                    </br>

                    <label><fmt:message key="quest.end.time"/></label>
                    <br class="required col-xs-10">
                    <input class="input-group"
                           type="time" id="questEndTime"
                           name="questEndTime" id="questEndTime"
                           required
                           min="10:00" max="21:00"
                           value="" pattern="HH:MM"/>"

                    <div class="warnMessage" id="incorrectQuestEndTime" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.incorrectTime"/>
                    </div>
                    </br>

                    <label><fmt:message key="label.cost.ticket"/></label>
                    <div class="input-group" style="margin-bottom: 10px">
                        <input type="number"
                               name="costQuestTicket"
                               pattern="\d+,\d{2}"
                               value="" min="5.00"
                               max="50.00"
                               step="0.01"
                               required>
                    </div>
                    <div class="warnMessage" id="incorrectQuestCostTicket" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.incorrectCostTicket"/>
                    </div>
                    </br>
                    <div style="margin-left: 15%">
                        <button type="submit" class="button">
                            <fmt:message key="button.save"/>
                        </button>
                        <button style="margin-left: 10%" id="cancelPhoto" type="button"
                                class="button" data-dismiss="modal">
                            <fmt:message key="button.cancel"/>
                        </button>
                    </div>
                </form>
                <input id="isInvalidQuestData" type="hidden" value="${isInvalidQuestData}">
                <input id="isInvalidQuestDate" type="hidden" value="${isInvalidQuestDate}">
                <input id="isExistQuestDate" type="hidden" value="${isExistQuestDate}">
                <input id="isInvalidQuestCostTicket" type="hidden" value="${isInvalidQuestCostTicket}">
                <input id="isInvalidQuestTime" type="hidden" value="${isInvalidQuestTime}">
            </div>
        </div>
    </div>
</div>


<div role="dialog" tabindex="-1" class="modal fade" id="modalEventDate">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                    <fmt:message key="modal.title.date"/>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <form class="p-4" method="POST"
                      action="${pageContext.request.contextPath}/do/admin_add_event_date">
                    <input type="hidden" id="eventAddDateId" name="eventId"/>
                    <label><fmt:message key="label.date.time"/></label>
                    <br class="required col-xs-10">
                    <input class="input-group"
                           type="datetime-local" id="dateTime" name="dateTime"
                           required
                           max="2090-12-31T23:59">
                    <div class="warnMessage" id="invalidDate" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.oldDate"/>
                    </div>
                    <div class="warnMessage" id="invalidTime" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.incorrectTime"/>
                    </div>
                    <div class="warnMessage" id="existDate" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.existDate"/>
                    </div>
                    </br>
                    <label><fmt:message key="label.count.ticket"/></label>
                    <div class="input-group" style="margin-bottom: 10px">
                        <input type="number" name="countTickets" id="countTickets"
                               value=""
                               min="1"
                               max="30"
                               step="1"
                               required>
                    </div>
                    <div class="warnMessage" id="invalidCountTicket" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.incorrectCountTicket"/>
                    </div>
                    <label><fmt:message key="label.cost.ticket"/></label>
                    <div class="input-group" style="margin-bottom: 10px">
                        <input type="number"
                               name="costTicket"
                               pattern="\d+,\d{2}"
                               value="" min="5.00"
                               max="50.00"
                               step="0.01"
                               required>
                    </div>
                    <div class="warnMessage" id="invalidCostTicket" hidden style="color: #8b0000">
                        <fmt:message key="modal.eventDate.incorrectCostTicket"/>
                    </div>
                    <div style="margin-left: 15%">
                        <button class="button" type="submit" class="btn btn-primary">
                            <fmt:message key="button.save"/>
                        </button>
                        <button style="margin-left: 10%" id="cancelPassword" type="button"
                                class="button" data-dismiss="modal">
                            <fmt:message key="button.cancel"/>
                        </button>
                    </div>
                </form>
                <input id="isInvalidData" type="hidden" value="${isInvalidData}">
                <input id="isInvalidTime" type="hidden" value="${isInvalidTime}">
                <input id="isInvalidDate" type="hidden" value="${isInvalidDate}">
                <input id="isExistDate" type="hidden" value="${isExistDate}">
                <input id="isInvalidCountTicket" type="hidden" value="${isInvalidCountTicket}">
                <input id="isInvalidCostTicket" type="hidden" value="${isInvalidCostTicket}">

            </div>
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/edit_event.js"></script>
<script src="${pageContext.request.contextPath}/js/add_event_date.js"></script>
<script src="${pageContext.request.contextPath}/js/add_event_quest.js"></script>
</body>
</html>


