<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="searchResults" class="table-responsive">
<c:if test="${not empty searchList}">
	<table  class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Contact Number</th>
				<th>Address</th>
				<th>Checkins</th>
			</tr>
		</thead>
		<tbody>
<%-- 		<tr>
		<td>${searchList}</td>
				<td>${searchList}</td>
				<td>${searchList}</td>
				<td>${searchList}</td>
		
		</tr> --%>
			<c:forEach var="exploreResponse" items="${searchList}">
				<tr>
					<td>${exploreResponse.name}</td>
					<td>${exploreResponse.contactNumber}</td>
					<td>${exploreResponse.address}</td>
					<td>${exploreResponse.checkins}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	</c:if>
</div>