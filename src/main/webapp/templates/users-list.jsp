<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty users}">
	<div class="row">
		<div class="col-md-12">
			<h4 class="mb-3">Users</h4>
			<a href="${basePath}/add-user" class="btn btn-success mb-3">Add user</a>

			<table class="table data-table table-striped table-hover table-bordered"
				   data-order="[[0, &quot;asc&quot;]]">
				<thead>
					<tr>
						<th>ID</th>
						<th>Username</th>
						<th>Role</th>
						<th></th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td>${user.id}</td>
							<td>${user.username}</td>
							<td>${user.role}</td>
							<td>
								<c:if test="${user.role == 'student'}">
									<a href="${basePath}/grade-history?userId=${user.id}"
									   class="btn btn-primary btn-sm">
										Grade history
									</a>
								</c:if>
								<a href="${basePath}/edit-user?userId=${user.id}" class="btn btn-primary btn-sm">
									Edit
								</a>
								<a href="${basePath}/delete-user?userId=${user.id}" class="btn btn-danger btn-sm">
									Delete
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</c:if>
