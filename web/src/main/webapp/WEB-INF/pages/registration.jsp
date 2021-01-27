<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="main.title"/></title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link href="http://bootstrapformhelpers.com/assets/css/bootstrap-formhelpers.min.css" rel="stylesheet"
          media="screen">
    <script src="http://bootstrapformhelpers.com/assets/js/bootstrap-formhelpers.min.js"></script>
    <style>
        <%@include file="style.css" %>
    </style>
</head>
<body>
<c:import url="common/header_admin.jsp"/>
<!-- MAIN -->
<main class="main">
    <div class="container" id="registration">
        <div class="row register-form">
            <div class="col-md-8 offset-md-2" id="registrationForm">
                <form class="custom-form" name="homePage" method="POST"
                      action="${pageContext.request.contextPath}/do">
                    <input type="hidden" name="command" value="registration"/>
                    <h1>
                        <fmt:message key="registration.title"/>
                    </h1>

                    <div class="form-row form-group">
                        <div class="col-sm-4 label-column">
                            <label class="col-form-label">
                                <fmt:message key="user.Lastname"/>
                            </label>
                        </div>
                        <div class="col-sm-6 input-column">
                            <input class="form-control" type="text" required="" name="lastname"
                                   pattern="[а-яА-Яa-zA-Z]{2,45}"
                                   onchange="this.setCustomValidity('')"
                                   value="${mapPageParams.get('lastname')}">
                            <div class="warnMessage">
                                <c:if test="${not empty mapPageParams && mapPageParams.get('lastname').equals('error')}">
                                    <fmt:message key="registration.warnMessage"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="form-row form-group">
                        <div class="col-sm-4 label-column">
                            <label class="col-form-label">
                                <fmt:message key="user.Firstname"/>
                            </label>
                        </div>
                        <div class="col-sm-6 input-column">
                            <input class="form-control" type="text" required="" name="firstname"
                                   pattern="[а-яА-Яa-zA-Z]{2,20}"
                                   class=" col-sm-4 label-column"
                                   onchange="this.setCustomValidity('')"
                                   value="${mapPageParams.get('firstname')}">
                            <div class="warnMessage">
                                <c:if test="${not empty mapPageParams && mapPageParams.get('firstname').equals('error')}">
                                    <fmt:message key="registration.warnMessage"/>
                                </c:if>
                            </div>
                        </div>
                    </div> <div class="form-row form-group">
                    <div class="col-sm-4 label-column">
                        <label class="col-form-label">
                            <fmt:message key="user.Phone"/>
                        </label>
                    </div>
                    <div class="col-sm-6 input-column">
                        <input class="form-control" type="text" required="" name="phone"
                               pattern="[0-9]{12}"
                               class=" col-sm-4 label-column"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="registration.warnMessage"/>')"
                               onchange="this.setCustomValidity('')"
                               value="${mapPageParams.get('phone')}">
                        <div class="warnMessage">
                            <c:if test="${not empty mapPageParams && mapPageParams.get('phone').equals('error')}">
                                <fmt:message key="registration.warnMessage"/>
                            </c:if>
                        </div>
                    </div>
                </div>
                    <div class="form-row form-group">
                        <div class="col-sm-4 label-column">
                            <label class="col-form-label">
                                <fmt:message key="user.Login"/>
                            </label>
                        </div>
                        <div class="col-sm-6 input-column">
                            <input class="form-control" type="email" required="" name="email"
                                   oninvalid="this.setCustomValidity('<fmt:message
                                           key="registration.warnMessage"/>')"
                                   pattern="^[\\w-\\.]+@([\\w-]+\\.)+[\\w]{2,4}$"
                                   onchange="this.setCustomValidity('')"
                                   value="${mapPageParams.get('email')}">
                            <div class="warnMessage">
                                <c:if test="${not empty mapPageParams && mapPageParams.get('email').equals('error')}">
                                    <fmt:message key="registration.warnMessage"/>
                                </c:if>
                                <c:if test="${not empty isDuplicateUser}">
                                    <fmt:message key="registration.DuplicateUser"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="form-row form-group">
                        <div class="col-sm-4 label-column">
                            <label class="col-form-label">
                                <fmt:message key="user.password"/>
                            </label>
                        </div>
                        <div class="col-sm-6 input-column">
                            <input class="form-control" type="password" required="" name="password"
                                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                   oninvalid="this.setCustomValidity('<fmt:message
                                           key="registration.warnMessage"/>')"
                                   onchange="this.setCustomValidity('')"
                                   value="${mapPageParams.get('password')}">
                            <div class="field-description">
                                <fmt:message key="registration.passwordDescription"/>
                            </div>
                            <div class="warnMessage">
                                <c:if test="${not empty userParameters and empty userParameters.get('password')}">
                                    <fmt:message key="registration.warnMessage"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="form-row form-group">
                        <div class="col-sm-4 label-column">
                            <label class="col-form-label">
                                <fmt:message key="user.repeatedPassword"/>
                            </label>
                        </div>
                        <div class="col-sm-6 input-column">
                            <input class="form-control" type="password" required="" name="repeatedPassword"
                                   oninvalid="this.setCustomValidity('<fmt:message
                                           key="registration.warnMessage"/>')"
                                   onchange="this.setCustomValidity('')"

                                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}$"
                                   value="${userParameters.get('repeatedPassword')}">
                            <div class="warnMessage">
                                <c:if test="${not empty userParameters and empty userParameters.get('repeatedPassword')}">
                                    <fmt:message key="registration.warnMessage"/>
                                </c:if>
                                <c:if test="${not empty isNonequivalent}">
                                    <fmt:message key="registration.nonequivalent.repeated.password"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <button class="button" type="submit" id="register">
                        <fmt:message key="button.registration"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
    <c:import url="common/footer.jsp"/>
</body>
</html>