<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘먹은 음식추가</title>
<link rel="stylesheet" href="./css/style.css" />
</head>
<body>
	<%@ include file="header.jsp" %>
	<section>
		<div class="title">오늘먹은 음식추가</div>
		<div>
			<form name="frm" action="insert">
				<table class="ipt-tbl">
					<tr>
						<th>회원번호</th>
						<td>
							<input type="text" name="member_no" value="${list.member_no}" />
						</td>
					</tr>
					<tr>
						<th>날짜</th>
						<td>
							<input type="text" name=eat_date value="${list.eat_date}" />
						</td>
					</tr>
					<tr>
						<th>식사시간</th>
						<td>
							<select name="time">
								<option value="">식사시간을 선택해주세요</option>
								<option value="아침">아침</option>
								<option value="점심">점심</option>
								<option value="저녁">저녁</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>음식</th>
						<td>
							<select name="food">
								<option value="">음식을 선택해주세요</option>
							<c:forEach var="f" varStatus="i" items="${foodList}">
								<option value="${f.food}">${f.food}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn" type="submit" onclick="fn_Check(); return false;">등 록</button>
							<button class="btn" type="button" onclick="location='home';">취 소</button>
						</td>
					</tr>
				</table>
			</form>
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