<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<div id="footer">
    <div class="session" style="float:left"><span ><ctg:user-info/></span>
    </div>
    <div class="clock" style="float:right">
        <span class="footer-clock"></span>
    </div>
    </form>
</div>
<script src="${pageContext.request.contextPath}/js/clock.js"></script>
</body>
</html>

