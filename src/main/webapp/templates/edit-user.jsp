<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${user != null}">
	<div class="row">
		<div class="col-md-8">
			<h4 class="mb-3">Edit user <i>${user.username}</i></h4>

			<form class="needs-validation" action="${basePath}/edit-user" method="post">
				<div class="form-row">
					<div class="col-md-6 form-group">
						<label for="username">Username</label>
						<input type="text" class="form-control" id="username" name="username"
							   placeholder="Enter username" value="${user.username}" required>
					</div>

					<div class="col-md-6 form-group">
						<label for="password">Password</label>
						<input type="password" class="form-control" id="password" name="password"
							   placeholder="Enter password">
					</div>
				</div>

				<div class="form-row">
					<div class="col-md-6 form-group">
						<label for="role">Role</label>
						<select class="form-control" id="role" name="role" required>
							<option value="student"${user.role == 'student' ? ' selected' : ''}>
								student
							</option>
							<option value="instructor"${user.role == 'instructor' ? ' selected' : ''}>
								instructor
							</option>
							<option value="administrator"${user.role == 'administrator' ? ' selected' : ''}>
								administrator
							</option>
						</select>
					</div>
				</div>

				<hr class="mb-4">

				<input type="hidden" name="userId" value="${user.id}">
				<button class="btn btn-success btn-lg btn-block" type="submit" value="submit" name="submit">
					Save
				</button>
			</form>
		</div>
	</div>
</c:if>
