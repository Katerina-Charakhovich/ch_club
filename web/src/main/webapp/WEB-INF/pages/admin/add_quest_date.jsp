<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <style>
        <%@include file="../style.css" %>
    </style>
</head>
<body>
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
                      action="${pageContext.request.contextPath}/do">
                    <input type="hidden" name="command" value="admin_add_quest_date"/>
                    <input type="hidden" id="questId" name="questId"/>
                    <div class="input-group" style="margin-bottom: 10px">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fa fa-pencil">*</i>
                            </span>
                        </div>
                        <input type="date" id="date" name="date"
                               min="2021-12-14" max="2030-12-31"
                               value=""
                               placeholder="<fmt:message key="quest.date"/>">
                    </div>
                    <div class="input-group" style="margin-bottom: 10px">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fa fa-pencil">*</i>
                            </span>
                        </div>
                        <input type="time" id="questStartTime" name="questStartTime" id="questStartTime"
                               min="09:00" max="20:00"
                               value="" pattern="HH:MM"/>"
                        placeholder="<fmt:message key="quest.start.time"/>">
                    </div>
                    <div class="input-group" style="margin-bottom: 10px">
                        <div class="input-group" style="margin-bottom: 10px">
                            <div class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fa fa-pencil">*</i>
                            </span>
                            </div>
                            <input type="time" id="questEndTime" name="questEndTime" id="questEndTime"
                                   min="09:00" max="20:00"
                                   value=""
                                   placeholder="<fmt:message key="quest.end.time"/>">
                        </div>
                    </div>
                    <div class="input-group" style="margin-bottom: 10px">
                        <div class="input-group" style="margin-bottom: 10px">
                            <div class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fa fa-pencil">*</i>
                            </span>
                            </div>
                            <input class="form-control" id="duration" name="duration"
                                   type="number"
                                   name="duration" required="" data-provide="datepicker"
                                   min="1"
                                   max="2"
                                   value=""
                                   placeholder="<fmt:message key="quest.duration"/>">
                        </div>
                    </div>
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
            </div>
        </div>
    </div>
</div>
</body>
