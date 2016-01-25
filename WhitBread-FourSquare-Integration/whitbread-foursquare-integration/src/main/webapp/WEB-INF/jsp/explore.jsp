<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		var ajaxify = function() {
			$("#ajaxForm").submit(function() {
				var data = $(this).serialize();
				$.ajax({
					type : $(this).attr("method"),
					url : $(this).attr("action"), 
					data : data, 
					success : function(data) {
						var id = $(data).attr("id");
						$("#" + id).replaceWith(data);
						ajaxify();
					}
				});
				return false;  
			});

		}
		ajaxify();
	});
</script>
</head>
<body>
	<div class="jumbotron"><h1>Welcome to Venue Search</h1></div>

	<div id="container">
		<form:form class="form-horizontal" method="post" modelAttribute="explore" action="/submit" id="ajaxForm">


			<spring:bind path="location">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Location</label>
					<div class="col-sm-5">
						<form:input path="location" type="text" class="form-control "
							id="location" placeholder="location" />
						<form:errors path="location" class="control-label" />
					</div>
					<div class="col-sm-5"></div>
				</div>
			</spring:bind>


			<spring:bind path="radius">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Radius</label>
					<div class="col-sm-5">
						<form:select path="radius" items="${radiusValues}"
							class="form-control" />
						<form:errors path="radius" class="control-label" />
					</div>
					<div class="col-sm-5"></div>
				</div>
			</spring:bind>

			<spring:bind path="limit">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-2 control-label">Limit</label>
					<div class="col-sm-5">
						<form:select path="limit" items="${limitValues}"
							class="form-control" />
						<form:errors path="limit" class="control-label" />
					</div>
					<div class="col-sm-5"></div>
				</div>
			</spring:bind>


			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-5">

					<button type="submit" class="btn-lg btn-primary">Submit</button>

				</div>
				<div class="col-sm-5"></div>

			</div>
		</form:form>
		<jsp:include page="success.jsp"/>
		


	</div>

</body>
</html>