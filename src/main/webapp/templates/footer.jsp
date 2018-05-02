<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container">
	<span class="text-muted">
		©2018 DVT HDH | Academi Gymraeg
	</span>

	<c:if test="${user != null}">
		<span class="float-right text-muted">
			<c:choose>
				<c:when test="${user.role == 'student'}">Student:</c:when>
				<c:when test="${user.role == 'instructor'}">Instructor:</c:when>
				<c:when test="${user.role == 'administrator'}">Administrator:</c:when>
			</c:choose>
			${user.username}
		</span>
	</c:if>
</div>
