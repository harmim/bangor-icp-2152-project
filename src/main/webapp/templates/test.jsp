<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty words}">
	<div class="row">
		<div class="col-md-8">
			<h4 class="mb-3">Test</h4>

			<form class="needs-validation" action="${basePath}/finished-test" method="post">
				<c:set var="i">${0}</c:set>
				<c:forEach items="${words}" var="word">
					<div class="form-group">
						<c:set var="i">${i + 1}</c:set>
						<c:set var="rand"><%= Math.random() %></c:set>
						<c:choose>
							<%-- cca 33% chance to Welsh noun gender question --%>
							<c:when test="${rand <= 0.33}">
								<p>
									<strong>${i}.</strong>
									What is the gender of the Welsh noun <strong>${word.welsh}</strong>?
								</p>

								<div class="custom-control custom-radio custom-control-inline">
									<input type="radio" id="q${i}GenderMasculine" name="q${i}Gender${word.id}"
										   value="masculine" class="custom-control-input" required>
									<label for="q${i}GenderMasculine" class="custom-control-label">masculine</label>
								</div>

								<div class="custom-control custom-radio custom-control-inline">
									<input type="radio" id="q${i}GenderFeminine" name="q${i}Gender${word.id}"
										   value="feminine" class="custom-control-input" required>
									<label for="q${i}GenderFeminine" class="custom-control-label">feminine</label>
								</div>
							</c:when>

							<%-- cca 33% chance to English meaning of Welsh noun question --%>
							<c:when test="${rand <= 0.66}">
								<p>
									<strong>${i}.</strong>
									What is the meaning of the Welsh noun <strong>${word.welsh}</strong>?
								</p>

								<c:set var="rand"><%= (int) (Math.random() * 4) % 4 + 1 %></c:set>
								<c:forEach begin="1" end="4" var="j" varStatus="loop">
									<div class="custom-control custom-radio custom-control-inline">
										<c:set var="value">
											${
												j != rand
												? randomWords[(i * rand * 10 + j) % numberOfWords].english
												: word.english
											}
										</c:set>
										<input type="radio" id="q${i}English${j}" name="q${i}English${word.id}"
											   value="${value}" class="custom-control-input" required>
										<label for="q${i}English${j}" class="custom-control-label">
											${value}
										</label>
									</div>
								</c:forEach>
							</c:when>

							<%-- cca 33% chance to Welsh noun for English noun question --%>
							<c:otherwise>
								<p>
									<strong>${i}.</strong>
									What is the Welsh noun for the English word for <strong>${word.english}</strong>?
								</p>

								<c:set var="rand"><%= (int) (Math.random() * 4) % 4 + 1 %></c:set>
								<c:forEach begin="1" end="4" var="j" varStatus="loop">
									<div class="custom-control custom-radio custom-control-inline">
										<c:set var="value">
											${
												j != rand
												? randomWords[(i * rand * 10 + j) % numberOfWords].welsh
												: word.welsh
											}
										</c:set>
										<input type="radio" id="q${i}Welsh${j}" name="q${i}Welsh${word.id}"
											   value="${value}" class="custom-control-input" required>
										<label for="q${i}Welsh${j}" class="custom-control-label">
											${value}
										</label>
									</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>

				<hr class="mb-4">

				<input type="hidden" name="time"
					   value="<%= new java.sql.Timestamp(new java.util.Date().getTime()).getTime() %>">
				<button class="btn btn-primary btn-lg btn-block" type="submit" value="submit" name="submit">
					Submit
				</button>
			</form>
		</div>
	</div>
</c:if>
