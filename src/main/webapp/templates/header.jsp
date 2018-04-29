<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	<a class="navbar-brand" href="${basePath}/homepage">Home</a>

	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
			aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<c:if test="${user != null}">
				<c:if test="${user.role == 'instructor'}">
					<li class="nav-item${servletName == 'StudentsListServlet' ? ' active' : ''}">
						<a class="nav-link" href="${basePath}/students-list">Students</a>
					</li>
				</c:if>

				<c:if test="${user.role == 'administrator'}">
					<li class="nav-item${servletName == 'UsersListServlet' ? ' active' : ''}">
						<a class="nav-link" href="${basePath}/users-list">Users</a>
					</li>
				</c:if>

				<c:if test="${user.role == 'instructor' or user.role == 'administrator'}">
					<li class="nav-item${servletName == 'VocabularyListServlet' ? ' active' : ''}">
						<a class="nav-link" href="${basePath}/vocabulary-list">Vocabulary</a>
					</li>
				</c:if>

				<c:if test="${user.role == 'student'}">
					<li class="nav-item${servletName == 'TestServlet' ? ' active' : ''}">
						<a class="nav-link" href="${basePath}/test">Take test</a>
					</li>

					<li class="nav-item${servletName == 'GradeHistoryServlet' ? ' active' : ''}">
						<a class="nav-link" href="${basePath}/grade-history">Grade history</a>
					</li>
				</c:if>
			</c:if>
		</ul>

		<ul class="navbar-nav float-lg-right">
			<c:choose>
				<c:when test="${servletName == 'LoginServlet'}">
					<li class="nav-item active">
						<a class="nav-link" href="${basePath}/login">Login
							<i class="fa fa-sign-in" aria-hidden="true"></i>
						</a>
					</li>
				</c:when>

				<c:otherwise>
					<li class="nav-item">
						<a class="nav-link" href="${basePath}/logout">Logout
							<i class="fa fa-sign-out" aria-hidden="true"></i>
						</a>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</nav>
