<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원리스트</title>
<link rel="stylesheet" href="./css/style.css" />
</head>
<body>
	<%@ include file="header.jsp" %>
	<section>
	<div class="wrap">
		<div class="title">회원리스트</div>
		<div>
			<table class="list-tbl">
				<tr>
					<th>회원번호</th>
					<th>이름</th>
					<th>키</th>
					<th>하루필요열량</th>
				</tr>
				<c:forEach var="m" varStatus="i" items="${list}">
				<tr class="tr-list">
					<td><a href="alonelist?member_no=${m.member_no}">${m.member_no}</a></td>
					<td>${m.mname}</td>
					<td>${m.height}</td>
					<td>${m.needKcal}</td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan="4">
					<a> </a>
					</td>
				</tr>
				<tr>
					<td class="tr-list" colspan="4" style="text-align: center;">
						<a href="add">회원추가</a>
					</td>
				</tr>
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