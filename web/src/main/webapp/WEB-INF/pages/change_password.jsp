<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <style>
        <%@include file="../../css/style.css" %>
    </style>
</head>
<body>
<div role="dialog" tabindex="-1" class="modal fade" id="modalChangePassword">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                    <fmt:message key="modal.title.changePassword"/>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <form class="p-4" method="POST"
                      action="${pageContext.request.contextPath}/do">
                    <input type="hidden" name="command" value="change_password"/>
                    <label><fmt:message key="user.oldPassword"/></label>
                    <div class="required col-xs-10">
                        <input class="form-control" type="password" id="oldPassword" name="oldPassword"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="registration.passwordDescription"/>')"
                               onchange="this.setCustomValidity('')"
                               required="">
                        <div class="warnMessage" id="incorrectOldPassword" hidden style="color: darkred">
                            <fmt:message key="modal.changePassword.incorrectOldPassword"/>
                        </div>
                    </div>
                    <label><fmt:message key="user.newPassword"/></label>
                    <div class="input-group" style="margin-bottom: 10px">
                        <input class="form-control" type="password" id="newPassword" name="newPassword"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="registration.passwordDescription"/>')"
                               onchange="this.setCustomValidity('')"
                               required="">
                    </div>
                    <label><fmt:message key="user.repeatedNewPassword"/></label>
                    <div class="input-group" style="margin-bottom: 10px">

                        <input class="form-control" type="password" id="repeatedNewPassword" name="repeatedNewPassword"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="registration.passwordDescription"/>')"
                               onchange="this.setCustomValidity('')"
                               required="">
                    </div>
                    <div class="warnMessage" id="nonequivalentRepeatedPassword" hidden style="color: darkred">
                        <fmt:message key="modal.changePassword.nonequivalentRepeatedPassword"/>
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
            </div>
        </div>
    </div>
</div>
<input id="isCorrectChangePassword" type="hidden" value="${isCorrectChangePassword}">
<input id="isIncorrectOldPassword" type="hidden" value="${isIncorrectOldPassword}">
<input id="isNonequivalent" type="hidden" value="${isNonequivalent}">

<div role="dialog" tabindex="-1" class="modal fade" id="modalCorrectPasswordChange">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-primary">
                    <i class="fa fa-edit"></i>
                    <fmt:message key="user.changePasswordTitle"/>
                </h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
            <div class="modal-body">
                <div class="form-group" align="justify">
                    <p>
                        <fmt:message key="user.wasChangedPassword"/>
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

<script src="${pageContext.request.contextPath}/js/change_password.js"></script>
</body>
