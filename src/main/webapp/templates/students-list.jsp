<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty students}">
	<div class="row">
		<div class="col-md-12">
			<h4 class="mb-3">Students</h4>

			<table class="table data-table table-striped table-hover table-bordered"
				   data-order="[[0, &quot;asc&quot;]]">
				<thead>
					<tr>
						<th>ID</th>
						<th>Username</th>
						<th></th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${students}" var="student">
						<tr>
							<td>${student.id}</td>
							<td>${student.username}</td>
							<td>
								<a href="${basePath}/grade-history?userId=${student.id}"
								   class="btn btn-primary btn-sm">
									Grade history
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</c:if>
