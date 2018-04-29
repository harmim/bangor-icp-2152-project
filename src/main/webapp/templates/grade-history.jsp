<%--
	Author: DVT HDH
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty tests and student != null}">
	<div class="row">
		<div class="col-md-12">
			<h4 class="mb-3">Grade history of student <i>${student.username}</i></h4>

			<table class="table data-table table-striped table-hover table-bordered"
				   data-order="[[0, &quot;desc&quot;]]">
				<thead>
					<tr>
						<th>Date</th>
						<th>Mark</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${tests}" var="test">
						<tr>
							<fmt:formatDate value="${test.test_date}" type="date"
											pattern="yyyy-MM-dd" var="dataOrder"/>
							<td data-order="${dataOrder}">
								<fmt:formatDate value="${test.test_date}" type="date" pattern="d/M/yyyy"/>
							</td>
							<td>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="1">
									${test.mark}
								</fmt:formatNumber>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</c:if>
