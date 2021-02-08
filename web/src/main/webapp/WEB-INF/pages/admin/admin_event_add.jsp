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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <form class="load_pic" action="${pageContext.request.contextPath}/do" enctype="multipart/form-data"
              method="POST">
            <input type="hidden" name="command" value="EVENT_ADD"/>
            </br>
            <h2><fmt:message key="title.event.add"/></h2>
            <br>
            <label for="from-mainImage" class="control-label col-xs-3">
                <fmt:message key="event_add.h4.chooseMainPicture"/>
            </label>
            <c:if test="${empty mainPicture}">
                <div class="file-loading">
                    <input id="file-0c" required class="file" type="file" data-theme="fas" name="main"
                           id="from-mainImage"
                           formenctype="multipart/form-data">
                </div>
                </c:if>
            </h4>
            <hr>
            <h3><fmt:message key="event_add.h4.chooseAddPictures"/></h3>
            <div class="form-group">
                <div class="file-loading">
                    <input id="file-4" required name="additional" type="file" class="file" multiple
                           data-min-file-count="3" data-upload-url="#" data-theme="fas"
                           formenctype="multipart/form-data">
                </div>
            </div>
            <hr>
            <c:if test="${not empty mainPicture}">
                <div class="card-header">
                    <input type="hidden" name="mainPicture" value="${mainPicture}">
                    <img class="card-img-top" src="${mainPicture}" alt="Card image cap">
                </div>
            </c:if>
            </br>
            <div class="form-group">
                <h3>
                    <label for="from-eventname" class="control-label col-xs-3">
                        <fmt:message key="event.label.name"/>
                    </label>
                </h3>
                <div class="col-xs-10">
                    <c:choose>
                        <c:when test="${not empty mapPageParams && mapPageParams['eventName'].equals('error')}">
                            <input type="text"
                                   required
                                   class="form-control"
                                   id="from-eventname"
                                   name="eventName"
                                   value="">
                            </br>
                            <div class="warnMessage" style="color: #8b0000">
                                <fmt:message key="eventEdit.warnMessage"/>
                            </div>
                        </c:when>
                        <c:when test="${not empty mapPageParams && !mapPageParams['eventName'].equals('error')}">
                            <input type="text"
                                   required
                                   class="form-control"
                                   id="from-eventname"
                                   name="eventName"
                                   value=${mapPageParams['eventName']}>
                            </br>
                        </c:when>
                        <c:otherwise>
                            <input type="text"
                                   required
                                   class="form-control"
                                   id="from-eventname"
                                   name="eventName"
                                   placeholder=
                                       <fmt:message key="event.placeholder.name"/>
                                           value="">
                            </br>

                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
            </br>
            <div class="form-group">
                <label class="control-label col-xs-3" name="typeEvent" id="typeEvent"><fmt:message
                        key="event.typeEvent"/></label>
            </div>
            <div class="form-check form-check-inline">
                <label class="radio-inline">
                    <input formenctype="text/plain;charset= UTF-8" type="radio" checked name="eventType"
                           value="theatre"> <fmt:message
                        key="event.typeEvent.theatre"/>
                </label>
            </div>

            <div class="form-check form-check-inline">
                <label class="radio-inline">
                    <input type="radio" name="eventType" value="quest"> <fmt:message key="event.typeEvent.quest"/>
                </label>
            </div>
            </br>
            <div class="form-group">
                <label class="control-label col-xs-3"
                       for="from-eventshortdesc">
                    <fmt:message key="event.short.description"/>
                </label>
                <div class="col-xs-10">
                    <c:choose>
                        <c:when test="${not empty mapPageParams && mapPageParams['eventShortDescription'].equals('error')}">
                            <input type="text"
                                   required
                                   class="form-control"
                                   id="from-eventshortdesc"
                                   name="eventShortDescription"
                                   value="">
                            </br>
                            <div class="warnMessage" style="color: #8b0000">
                                <fmt:message key="eventEdit.warnMessage"/>
                            </div>
                        </c:when>
                        <c:when test="${not empty mapPageParams && !mapPageParams['eventShortDescription'].equals('error')}">
                            <input type="text"
                                   required
                                   class="form-control"
                                   id="from-eventshortdesc"
                                   name="eventShortDescription"
                                   value=${mapPageParams['eventShortDescription']}>
                            </br>
                        </c:when>
                        <c:otherwise>
                            <input type="text"
                                   required
                                   class="form-control"
                                   id="from-eventshortdesc"
                                   name="eventShortDescription"
                                   value="">
                            </br>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                </br>
                <label class="control-label col-xs-3" for="from-eventdesc">
                    <fmt:message key="event.description"/>
                </label>
                <div class="col-xs-10">
                    <c:choose>
                        <c:when test="${not empty mapPageParams && mapPageParams['eventDescription'].equals('error')}">
                                <textarea formenctype="text/plain;charset= UTF-8"
                                          rows="8"
                                          required
                                          class="form-control"
                                          id="from-eventdesc"
                                          name="eventDescription"
                                          value="">
                        </textarea>
                            </br>
                            <div class="warnMessage" style="color: #8b0000">
                                <fmt:message key="eventEdit.warnMessage"/>
                            </div>
                        </c:when>
                        <c:when test="${not empty mapPageParams && !mapPageParams['eventDescription'].equals('error')}">
                            <textarea formenctype="text/plain;charset= UTF-8"
                                      rows="8"
                                      required
                                      class="form-control"
                                      id="from-eventdesc"
                                      name="eventDescription">
                                    ${mapPageParams['eventDescription']}
                            </textarea>
                        </c:when>
                        <c:otherwise>
                           <textarea formenctype="text/plain;charset= UTF-8"
                                     rows="8"
                                     required
                                     class="form-control"
                                     id="from-eventdesc"
                                     name="eventDescription"
                                     value="">
                        </textarea>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                </br>
                <label class="control-label col-xs-3" for="from-duration">
                    <fmt:message key="event.duration"/>
                </label>
                <c:choose>
                    <c:when test="${not empty mapPageParams && mapPageParams['duration'].equals('error')}">
                        <div class="col-xs-9">
                            <input type="number"
                                   name="duration"
                                   id="from-duration"
                                   pattern="\d+,\d{1}"
                                   min="0.5"
                                   max="3.00"
                                   step="0.5"
                                   value=""
                                   required>
                        </div>
                        <div class="warnMessage" id="incorrectDurationEvent" hidden style="color: #8b0000">
                            <fmt:message key="event_add.incorrectDurationEvent"/>
                        </div>
                    </c:when>
                    <c:when test="${not empty mapPageParams && !mapPageParams['duration'].equals('error')}">
                        <div class="col-xs-9">
                            <input type="number"
                                   name="duration"
                                   id="from-duration"
                                   pattern="\d+,\d{1}"
                                   value=${mapPageParams['duration']}
                                           min="0.5"
                                   max="3.00"
                                   step="0.5"
                                   required>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-xs-9">
                            <input type="number"
                                   name="duration"
                                   id="from-duration"
                                   pattern="\d+,\d{1}"
                                   min="0.5"
                                   max="3.00"
                                   step="0.5"
                                   value=""
                                   required>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            </br>
            <div class="form-group">
                <div class="col-xs-offset-3 col-xs-9">
                    <button id="saveChange" class="button" type="submit">
                        <fmt:message key="button.save"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
    </div>
</main>
</body>
<script src="${pageContext.request.contextPath}/themes/fas/theme.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/themes/explorer-fas/theme.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
</html>
