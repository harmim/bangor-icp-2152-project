<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row">
	<div class="col-md-8">
		<h4 class="mb-3">Login</h4>

		<form class="needs-validation" action="${basePath}/login" method="post">
			<div class="form-row">
				<div class="col-md-6 form-group">
					<label for="username">Username</label>
					<input type="text" class="form-control" id="username" name="username"
						   placeholder="Enter username" required>
				</div>

				<div class="col-md-6 form-group">
					<label for="password">Password</label>
					<input type="password" class="form-control" id="password" name="password"
						   placeholder="Enter password" required>
				</div>
			</div>

			<hr class="mb-4">

			<button class="btn btn-primary btn-lg btn-block" type="submit" value="submit" name="submit">
				Login
			</button>
		</form>
	</div>
</div>
