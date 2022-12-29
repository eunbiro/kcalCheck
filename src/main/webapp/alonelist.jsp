<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원: ${m_no} 음식목록</title>
<link rel="stylesheet" href="./css/style.css" />
</head>
<body>
	<%@ include file="header.jsp" %>
	<section>
	<div class="wrap">
		<div class="title">회원: ${mname.mname} 음식목록</div>
		<div>
			<table class="list-tbl">
				<tr>
					<th>날짜</th>
					<th>식사시간</th>
					<th>음식</th>
					<th>칼로리</th>
					<th class="del-btn"></th>
				</tr>
				<c:forEach var="m" varStatus="i" items="${list}">
				<tr class="tr-list">
					<td><a href="edit?food_no=${m.food_no}">${m.eat_date}</a></td>
					<td>${m.time}</td>
					<td>${m.food}</td>
					<td>${m.kcal}</td>
					<td class="del-btn"><a onclick="chkDelete(${m.food_no}, ${m.member_no}); return false;">삭 제</a></td>
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
	<script type="text/javascript" src="script.js"></script>
</body>
</html>