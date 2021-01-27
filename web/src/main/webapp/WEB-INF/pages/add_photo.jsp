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
<div role="dialog" tabindex="-1" class="modal fade" id="modalChangePhoto">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                    <fmt:message key="modal.title.changePhoto"/>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/do"
                      enctype="multipart/form-data"
                      method="post">
                    <input type="hidden" name="command" value="CHANGE_USER_PHOTO"/>
                    <input type="hidden" id="eventId" name="userId"/>
                    <div class="file-loading">
                        <input id="file-0c" required class="file" type="file" data-theme="fas" name="userPhoto"
                               formenctype="multipart/form-data">
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
</div>>
</body>
<script src="${pageContext.request.contextPath}/themes/fas/theme.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/themes/explorer-fas/theme.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/add_photo.js"></script>
</html>