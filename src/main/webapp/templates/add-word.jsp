<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row">
	<div class="col-md-8">
		<h4 class="mb-3">Add word</h4>

		<form class="needs-validation" action="${basePath}/add-word" method="post">
			<div class="form-row">
				<div class="col-md-6 form-group">
					<label for="english">English</label>
					<input type="text" class="form-control" id="english" name="english"
						   placeholder="Enter english word" required>
				</div>

				<div class="col-md-6 form-group">
					<label for="welsh">Welsh</label>
					<input type="text" class="form-control" id="welsh" name="welsh"
						   placeholder="Enter welsh word" required>
				</div>
			</div>

			<div class="form-row">
				<div class="col-md-6 form-group">
					<label for="gender">Gender</label>
					<select class="form-control" id="gender" name="gender" required>
						<option value="masculine">masculine</option>
						<option value="feminine">feminine</option>
					</select>
				</div>
			</div>

			<hr class="mb-4">

			<button class="btn btn-success btn-lg btn-block" type="submit" value="submit" name="submit">Add</button>
		</form>
	</div>
</div>
