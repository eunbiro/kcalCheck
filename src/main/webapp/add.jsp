<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원추가</title>
<link rel="stylesheet" href="./css/style.css" />
</head>
<body>
	<%@ include file="header.jsp" %>
	<section>
	<div class="wrap">
		<div class="title">회원추가</div>
		<div>
			<form name="frm" action="addInsert">
				<table class="ipt-tbl">
					<tr>
						<th>회원번호</th>
						<td>
							<input type="text" name="member_no" value="${member.member_no }" readonly />
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input type="text" name=mname />
						</td>
					</tr>
					<tr>
						<th>키</th>
						<td>
							<input type="text" name=height />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn" type="submit" onclick="fn_addCheck(); return false;">등 록</button>
							<button class="btn" type="button" onclick="location='list';">취 소</button>
						</td>
					</tr>
				</table>
			</form>
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