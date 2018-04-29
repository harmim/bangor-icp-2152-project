<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row">
	<c:if test="${user != null}">
		<c:if test="${user.role == 'instructor'}">
			<div class="col-md-6">
				<a href="${basePath}/students-list" class="btn btn-primary btn-lg btn-block" role="button">Students</a>
			</div>
		</c:if>

		<c:if test="${user.role == 'administrator'}">
			<div class="col-md-6">
				<a href="${basePath}/users-list" class="btn btn-primary btn-lg btn-block" role="button">Users</a>
			</div>
		</c:if>

		<c:if test="${user.role == 'instructor' or user.role == 'administrator'}">
			<div class="col-md-6">
				<a href="${basePath}/vocabulary-list" class="btn btn-primary btn-lg btn-block" role="button">
					Vocabulary
				</a>
			</div>
		</c:if>

		<c:if test="${user.role == 'student'}">
			<div class="col-md-6">
				<a href="${basePath}/test" class="btn btn-primary btn-lg btn-block" role="button">Take test</a>
			</div>

			<div class="col-md-6">
				<a href="${basePath}/grade-history" class="btn btn-primary btn-lg btn-block" role="button">
					Grade history
				</a>
			</div>
		</c:if>
	</c:if>
</div>
