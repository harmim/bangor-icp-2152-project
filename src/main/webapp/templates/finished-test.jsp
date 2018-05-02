<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty answers}">
	<div class="row">
		<div class="col-md-8">
			<h4 class="mb-3">Finished test</h4>
			<h5 class="mb-3">Final grade:
				<fmt:formatNumber maxFractionDigits="2" minFractionDigits="1">${grade}</fmt:formatNumber>
			</h5>

			<form>
				<c:set var="i">${0}</c:set>
				<c:forEach items="${answers}" var="answer">
					<div class="form-group">
						<c:set var="i">${i + 1}</c:set>
						<c:choose>
							<c:when test="${answer.questionType == 'Gender'}">
								<p>
									<strong>${i}.</strong>
									What is the gender of the Welsh noun <strong>${answer.word.welsh}</strong>?
								</p>

								<div class="custom-control custom-radio custom-control-inline">
									<input type="radio" id="q${i}Gender" class="custom-control-input"
										   required disabled checked>
									<label for="q${i}Gender" class="custom-control-label">${answer.value}</label>
								</div>
							</c:when>

							<c:when test="${answer.questionType == 'English'}">
								<p>
									<strong>${i}.</strong>
									What is the meaning of the Welsh noun <strong>${answer.word.welsh}</strong>?
								</p>

								<div class="custom-control custom-radio custom-control-inline">
									<input type="radio" id="q${i}English" class="custom-control-input"
										   required disabled checked>
									<label for="q${i}English" class="custom-control-label">${answer.value}</label>
								</div>
							</c:when>

							<c:otherwise>
								<p>
									<strong>${i}.</strong>
									What is the Welsh noun for the English word for
									<strong>${answer.word.english}</strong>?
								</p>

								<div class="custom-control custom-radio custom-control-inline">
									<input type="radio" id="q${i}Welsh" class="custom-control-input"
										   required disabled checked>
									<label for="q${i}Welsh" class="custom-control-label">${answer.value}</label>
								</div>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${answer.correct == true}">
								<small class="form-text text-success">
									<i class="fa fa-check" aria-hidden="true"></i> correct
								</small>
							</c:when>

							<c:otherwise>
								<small class="form-text text-danger">
									<i class="fa fa-times" aria-hidden="true"></i> incorrect
								</small>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>

				<hr class="mb-4">

				<a href="${basePath}/homepage" class="btn btn-primary btn-lg btn-block">Home</a>
			</form>
		</div>
	</div>
</c:if>
