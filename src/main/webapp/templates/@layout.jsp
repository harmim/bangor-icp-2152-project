<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="head.jsp" %>
</head>
<body>
	<header>
		<%@ include file="header.jsp" %>
	</header>

	<main class="container">
		<c:if test="${not empty messages}">
			<c:forEach items="${messages}" var="message">
				<div class="alert alert-dismissable alert-${message.key}">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					${message.value}
				</div>
			</c:forEach>
		</c:if>

		<jsp:include page="${templateName}" flush="true"/>
	</main>

	<footer class="footer">
		<%@ include file="footer.jsp" %>
	</footer>

	<%@ include file="scripts.jsp" %>
</body>
</html>
