<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주중 회원별 칼로리 조회</title>
<link rel="stylesheet" href="./css/style.css" />
</head>
<body>
	<%@ include file="header.jsp" %>
	<section>
	<div class="wrap">
		<div class="title">주중 회원별 칼로리 조회</div>
		<div>
			<table class="list-tbl">
				<tr>
					<th>회원이름</th>
					<th>칼로리</th>
				</tr>
				<c:forEach var="f" varStatus="i" items="${list}">
				<tr class="tr-list">
					<td>${f.mname}</td>
					<td>${f.kcal}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	</section>
	<script type="text/javascript">
		<c:if test="${param.error != null}">
			alert("${param.error}");
		</c:if>
		<c:if test="${error != null}">
			alert("${error}");
		</c:if>
	</script>
</body>
</html>