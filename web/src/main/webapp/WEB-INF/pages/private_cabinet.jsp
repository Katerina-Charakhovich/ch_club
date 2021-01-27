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
    <div class="container">
        <div>
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-lg-6 item">
                        <div>
                            <c:choose>
                                <c:when test="${not empty sessionScope.authUser.getPhoto()}">
                                    <div>
                                        <img width="220px" src="data:image/jpeg;base64,${authUser.getPhoto()}"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="photoBorder">
                                        <img width="200px"
                                             src="${pageContext.request.contextPath}/images/1.png"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            </br>
                            <button id="addPhoto" class="button" type="button" data-toggle="modal"
                                    data-target="#modalChangePhoto"
                                    onclick="javascript:document.getElementById('userId').setAttribute('value', '${authUser.getUserId()}')">
                                <fmt:message key="button.change.photo"/>
                            </button>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-6 item">
                        <div  class="warnMessage" id="successUpdateUser" hidden>
                            <fmt:message key="updateUser.successUpdateUser"/>
                        </div>
                        <div  class="warnMessage" id="unsuccessUpdateUser" hidden>
                            <fmt:message key="updateUser.unsuccessUpdateUser"/>
                        </div>
                        <form class="custom-form" method="POST" id="updateUserInfo"
                              action="${pageContext.request.contextPath}/do/update_user_info">
                            <input type="hidden" name="command" value="update_user_info"/>
                            <div class="form-group">
                                <label for="from-email">
                                    <fmt:message key="user.Login"/>
                                </label>
                                <input class="form-control" type="email" id="from-email"
                                       name="email" required="" value="${sessionScope.authUser.getLogin()}"
                                       disabled/>
                            </div>
                            <div class="form-group">
                                <label for="from-lastname">
                                    <fmt:message key="user.Lastname"/>
                                </label>
                                <input class="form-control" type="text" id="from-lastname"
                                       pattern="[а-яА-Яa-zA-Z]{2,45}" required=""
                                       previousValue="${sessionScope.authUser.getLastName()}"
                                       oninvalid="this.setCustomValidity('<fmt:message
                                               key="registration.warnMessage"/>')"
                                       onchange="this.setCustomValidity('')"
                                       name="lastname" value="${sessionScope.authUser.getLastName()}" disabled
                                       placeholder=<fmt:message key="user.Lastname"/>>
                                <div class="warnMessage">
                                    <c:if test="${not empty mapPageParams && mapPageParams.get('lastname').equals('error')}">
                                        <fmt:message key="registration.warnMessage"/>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="from-name">
                                    <fmt:message key="user.Firstname"/>
                                </label>
                                <input class="form-control" type="text" id="from-name"
                                       name="firstname"
                                       pattern="[а-яА-Яa-zA-Z]{2,20}" required=""
                                       oninvalid="this.setCustomValidity('<fmt:message
                                               key="registration.warnMessage"/>')"
                                       onchange="this.setCustomValidity('')"
                                       previousValue="${sessionScope.authUser.getFirstName()}"
                                       value="${sessionScope.authUser.getFirstName()}" disabled
                                       placeholder=<fmt:message key="user.Firstname"/>></div>
                            <div class="warnMessage">
                                <c:if test="${not empty mapPageParams && mapPageParams.get('firstname').equals('error')}">
                                    <fmt:message key="registration.warnMessage"/>
                                </c:if>
                            </div>
                            <div class="row">
                                <div class="col-12 col-sm-6 col-md-12 col-lg-6">
                                    <div class="form-group">
                                        <label for="phone">
                                            <fmt:message key="user.Phone"/>
                                        </label>
                                        <input class="form-control" id="phone" name="phone"
                                               previousValue="${sessionScope.authUser.getPhone()}"
                                               pattern="[0-9]{12}"
                                               required="" value="${sessionScope.authUser.getPhone()}" disabled
                                               placeholder=<fmt:message key="user.Phone"/>></div>
                                    <div class="warnMessage">
                                        <c:if test="${not empty mapPageParams && mapPageParams.get('phone').equals('error')}">
                                            <fmt:message key="registration.warnMessage"/>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <button id="saveChange" class="button" type="submit" hidden>
                                <fmt:message key="button.save"/>
                            </button>
                            <button id="cancel" class="button" type="button" hidden>
                                <fmt:message key="button.cancel"/>
                            </button>
                            <hr class="d-flex d-md-none">
                            <input type="hidden" id="editInfo" value="${editInfo}" previousValue="${editInfo}">
                        </form>
                        <br/>
                        <button class="button" type="button" id="profileSettings"
                                onclick="enableFields()">
                            <fmt:message key="button.ProfileSettings"/>
                        </button>
                        <button class="button" type="button" data-toggle="modal"
                                id="changePassword"
                                data-target="#modalChangePassword">
                            <fmt:message key="button.ChangePassword"/>
                        </button>
                    </div>
                </div>

            </div>
            <c:if test="${sessionScope.user.role=='USER'}">

            </c:if>
            <c:if test="${sessionScope.user.role!='ADMIN'}">
            </c:if>
        </div>
    </div>
    </div>
</main>
<input type="hidden" id="isUpdateUser" value="${isUpdateUser}"/>
<c:import url="add_photo.jsp"/>
<c:import url="change_password.jsp"/>
<c:import url="common/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/profile_settings.js"></script>
</body>
</html>