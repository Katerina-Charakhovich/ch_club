<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>

<html>
<head>
    <title>Title</title>
</head>
<style>
    <%@include file="../style.css" %>
</style>
<body>
<c:import url="../common/header_admin.jsp"/>
<div class="container">
    <table style="with: 50%" border="1">
        <tr>
            <th>
                <fmt:message key="user.Lastname"/>
            </th>
            <th>
                <fmt:message key="user.Firstname"/>
            </th>
            <th>
                <fmt:message key="user.Login"/>
            </th>
            <th>
                <fmt:message key="user.Phone"/>
            </th>
            <th>
                <fmt:message key="user.balance"/>
            </th>
            <th>
                <fmt:message key="user.state"/>
            </th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${listUsers}" var="user">
            <tr>
                <td>${user.getLastName()}</td>
                <td>${user.getFirstName()}</td>
                <td>${user.getLogin()}</td>
                <td>${user.getPhone()}</td>
                <td>${user.getBalance()}</td>
                <td>${user.getState()}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.getState()=='ACTUAL'||user.getState()=='UNCONFIRMED'}">
                            <form action="${pageContext.request.contextPath}/do/admin_user_block" method="post">
                                <button type="submit" class="button" name="userId"
                                        value="${user.getUserId()}">
                                    <fmt:message key="button.blocked"/>
                                </button>
                            </form>
                        </c:when>
                        <c:when test="${user.getState()=='BLOCKED'}">
                            <form action="${pageContext.request.contextPath}/do/admin_user_unblock" method="post">
                                <button type="submit" class="button" name="userId"
                                        value="${user.getUserId()}">
                                    <fmt:message key="button.unblocked"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${user.getState()}"/>
                        </c:otherwise>
                    </c:choose>

                </td>
                <td>
                    <button type="submit" class="button" name="userId"
                            value="${user.getUserId()}"
                            data-toggle="modal" data-target="#modalAddBalance"
                            onclick="addUserBalance(${user.getUserId()})">
                        <fmt:message key="button.increase.account"/>
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
  <!--  <button type="submit" class="button" name="userId"
            value="${user.getUserId()}">
        <fmt:message key="button.create"/>
    </button>-->
    </form>
    </br>
    <div>
        <form action="${pageContext.request.contextPath}/do" method="get">
            <input type="hidden" name="commandNext" value="admin_users">
            <ctg:pagination/>
        </form>
    </div>
</div>

<input type="hidden" id="isUserAddBalance" value="${isUserAddBalance}">
<input type="hidden" id="isAmountValid" value="${isAmountValid}">
<div role="dialog" tabindex="-1" class="modal fade" id="modalAddBalance">
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
                    <input type="hidden" name="command" value="admin_add_balance"/>
                    <input type="hidden" id="userAddBalanceId" name="userId"/>
                    <div class="form-group">
                        <label for="from-amount">
                            <fmt:message key="addBalance.amount"/>
                        </label>
                        <input class="form-control"
                               type="number"
                               id="from-amount"
                               name="amount"
                               min="0.5"
                               max="100.00"
                               step="0.5"
                               value=""
                               required>
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

<div role="dialog" tabindex="-1" class="modal fade" id="modalSuccessAddBalance">
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
                <div class="form-group">
                    <p>
                        <fmt:message key="adminUsers.successAddBalance"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<div role="dialog" tabindex="-1" class="modal fade" id="modalUnSuccessAddBalance">
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
                <div class="form-group">
                    <p>
                        <fmt:message key="adminUsers.unSuccessAddBalance"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<div role="dialog" tabindex="-1" class="modal fade" id="modalAmountInvalid">
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
                <div class="form-group">
                    <p>
                        <fmt:message key="adminUsers.amountInvalid"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/add_balance.js"></script>
</body>
</html>