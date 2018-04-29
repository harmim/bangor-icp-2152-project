<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty vocabulary}">
	<div class="row">
		<div class="col-md-12">
			<h4 class="mb-3">Vocabulary</h4>
			<a href="${basePath}/add-word" class="btn btn-success mb-3">Add word</a>

			<table class="table data-table table-striped table-hover table-bordered"
				   data-order="[[0, &quot;asc&quot;]]">
				<thead>
					<tr>
						<th>ID</th>
						<th>English</th>
						<th>Welsh</th>
						<th>Gender</th>
						<th></th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${vocabulary}" var="word">
						<tr>
							<td>${word.id}</td>
							<td>${word.english}</td>
							<td>${word.welsh}</td>
							<td>${word.welsh_gender}</td>
							<td>
								<a href="${basePath}/edit-word?wordId=${word.id}" class="btn btn-primary btn-sm">
									Edit
								</a>
								<a href="${basePath}/delete-word?wordId=${word.id}" class="btn btn-danger btn-sm">
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
