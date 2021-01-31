<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <style>
        <%@include file="style.css" %>
    </style>
</head>
<body>
<div role="dialog" tabindex="-1" class="modal fade" id="modalSale">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <form class="p-4" method="POST"
                      action="${pageContext.request.contextPath}/do">
                    <input type="hidden" name="command" value="ticket_sale"/>
                    <input type="hidden" id="eventDateId" name="eventDateId"/>
                    <input type="hidden" id="eventId" name="eventId"/>
                    <input type="hidden" id="eventType" name="eventType"/>
                    <input type="hidden" id="ticketState" name="ticketState"/>
                    <input type="hidden" id="costTicket" name="costTicket"/>
                    <div style="margin-bottom: 10px">
                        <div class="input-group" style="margin-bottom: 10px">
                            <input class="form-control" type="text" disabled id="questName" name="questName"/>
                        </div>
                        <div class="input-group" style="margin-bottom: 10px">
                            <input class="form-control" type="text" disabled id="dateModal" name="dateModal"/>
                        </div>
                        <div class="input-group" style="margin-bottom: 10px">
                            <input class="form-control" type="text" disabled id="timeModal" name="timeModal"/>
                        </div>
                        <div class="input-group" style="margin-bottom: 10px">
                            <input class="form-control" type="text" disabled id="price" name="price"/>
                        </div>
                    </div>
                    <div style="margin-left: 10%">
                        <input
                                type="submit"
                                class="button"
                                name="PAID"
                                id="pay"
                                value=
                                <fmt:message key="button.pay"/>
                        >
                        <input
                                type="submit"
                                style="margin-left: 10%"
                                id="booked"
                                name="BOOKED"
                                type="submit"
                                class="button"
                                value=
                                <fmt:message key="button.booked"/>
                        >
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div role="dialog" type="hidden" tabindex="-1" class="modal fade" id="modalSuccessPaidTicket">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <p>
                    <fmt:message key="text.ticketSuccessPaid"/>
                </p>
            </div>
        </div>
    </div>
</div>
<div role="dialog" type="hidden" tabindex="-1" class="modal fade" id="modalSuccessBookTicket">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <p>
                    <fmt:message key="text.ticketSuccessBooked"/>
                </p>
            </div>
        </div>
    </div>
</div>
<div role="dialog" type="hidden" tabindex="-1" class="modal fade" id="modalUnSuccessSaleTicket">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <p>
                    <fmt:message key="text.ticketUnSuccessSale"/>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
