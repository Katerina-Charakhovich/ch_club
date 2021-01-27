<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <title><fmt:message key="main.title"/></title>
</head>
<style>
    <%@include file="../style.css" %>
</style>
<body>

<header style="padding-top:5px;" class="header">
    <div class="container-fluid">
        <div class="col-md-12 col-xl-12 logo">
            <div id="logo" style="float:left">
                <img src="${pageContext.request.contextPath}/images/logo.png">
            </div>
            <div id="contacts" style="float:right;">
                <i class="fa fa-mobile fa-lg"></i> +375-29-111-11-11 |
                <i class="fa fa-envelope fa-lg"></i> info@testmail.ru
                <i class="fa fa-facebook-square fa-2x" style="margin-left:10px; color: blue;"></i>
                <i>
                    <a href=${pageContext.request.contextPath}/do?command=CHANGE_LOCALE&locale=en><img
                            src="${pageContext.request.contextPath}/images/us.gif"
                            alt= <fmt:message key="label.enqlish"/>></a></li>
                </i>
                <i>
                    <a href=${pageContext.request.contextPath}/do?command=CHANGE_LOCALE&locale=ru><img
                            src="${pageContext.request.contextPath}/images/ru.gif"
                            alt="<fmt:message key="label.russian"/>"></a>
                </i>
            </div>
            <div class="clearfix"></div>
        </div>
        <ctg:user-info/>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01"
                    aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/do/main_page_view">
                    <fmt:message key="navbar.top.main.page"/></a>
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/do/events?eventType=theatre">
                            <fmt:message key="navbar.theatre.page"/></a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/do/events?eventType=quest">
                            <fmt:message key="navbar.quest.page"/></a>
                    </li>
                </ul>
                <c:choose>

                <c:when test="${not empty authUser}">
                <i>
                    <form action="${pageContext.request.contextPath}/do/private_cabinet" method="post">
                        <button type="submit" class="button">
                            <fmt:message key="main.button.profile"/>
                        </button>
                        <input type="hidden" name="command" value="private_cabinet">
                    </form>
                </i>
                <i>
                    <form action="${pageContext.request.contextPath}/do/logout" method="post">
                        <button type="submit" class="button">
                            <fmt:message key="main.button.logout"/>
                        </button>
                    </form>
                </i>
            </div>
            </c:when>
            <c:otherwise>
                <i>
                    <form class="form" action="${pageContext.request.contextPath}/do"
                          method="post">
                        <input type="hidden" name="command" value="login"/>
                        <div class="form-group">
                            <button type="button" class="button" data-toggle="modal" data-target="#modal_login"
                                    onclick="">
                                <fmt:message key="main.button.sign.in"/>
                            </button>
                        </div>
                    </form>
                </i>
                <i>
                    <form action="${pageContext.request.contextPath}/do" method="post">
                        <button type="submit" class="button">
                            <fmt:message key="main.button.registration"/>
                        </button>
                        <input type="hidden" name="command" value="registry_page">
                    </form>
                </i>
            </c:otherwise>
            </c:choose>
            </br>
        </nav>
    </div>

    <c:if test="${authUser.getRole().toString()!='ADMIN'}">
        <div id="header-bottom" style="position:relative;">
            <img src="${pageContext.request.contextPath}/images/banner.jpg" style="width:100%;">
            <div id="bannertext" style="position:absolute; top:10%; width:100%; text-align:center; color:white;">
                <div class="hidden-xs">
                    <h1>Мы предлагаем<br/>
                        <small style="color: white;">проведение праздников для вас и ваших детей</small>
                    </h1>
                </div>
            </div>
        </div>
    </c:if>


    </div>
</header>
<main>
    <c:if test="${authUser.getRole().toString()=='ADMIN'}">
        <div id="menu" style="background-color:#f1f1f1;height:calc(100vh - 40px);width:200px;float:left;">
            <c:import url="/WEB-INF/pages/common/navigationLeft.jsp"/>
        </div>
    </c:if>
</main>
<input type="hidden" id="isInvalidUser" value="${isInvalidUser}"/>
<input type="hidden" id="isBlockedUser" value="${isBlockedUser}"/>
<input type="hidden" id="isUnConfirmedUser" value="${isUnConfirmedUser}"/>
<div class="modal fade" id="modal_login" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="modal_login">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"> <fmt:message key="modal.login.title"/></h5>
                <button type="button" class="close"
                        data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form class="form" action="${pageContext.request.contextPath}/do/login" method="post">
                <div class="modal-body">
                    <div data-parsley-check-children="2" data-parsley-validate-if-empty="">
                        <div class="first">
                            <label><fmt:message key="main.login"/></label>
                            <div class="required col-xs-10">
                                <input type="email" class="form-control" id="login" name="login"
                                       pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                                       data-parsley-length="[8, 15]"
                                       data-parsley-group="block-1"
                                       required
                                       type="text" value=""/>
                                <div class="text-error" id="messageInvalidLoginPassword" hidden style="color: darkred">
                                    <fmt:message key="message.InvalidLoginPassword"/>
                                </div>
                                <div class="text-error" id="messageBlockedUser" hidden style="color: darkred">
                                    <fmt:message key="message.BlockedUser"/>
                                </div>
                                <div class="text-error" id="messageUnConfirmedUser" hidden style="color: darkred">
                                    <fmt:message key="message.UnConfirmedUser"/>
                                </div>
                                </br>
                                <label><fmt:message key="main.password"/></label>
                                <input type="password" class="form-control" id="password" name="password"

                                       data-parsley-group="block-1"
                                       required
                                       type="text" value=""/>
                                <!--   pattern= pattern="[а-яА-Яa-zA-Z0-9]{1,3}"-->
                                <div class="text-error" id="messageInvalidPasswordLogin" hidden style="color: darkred">
                                    <fmt:message key="message.InvalidLoginPassword"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="button">
                        <fmt:message key="button.send"/>
                    </button>
                </div>
            </form>
    </div>
</div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>
