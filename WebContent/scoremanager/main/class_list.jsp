<%-- 学校一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>

			<div class="my-2 text-end px-4">
				<a href="ClassCreate.action">追加</a>
			</div>

			<c:choose>
				<c:when test="${not empty classes}">
					<table class="table table-hover mx-4">
						<tr>
							<th>クラス名</th>
							<th>学校コード</th>
						</tr>
						<c:forEach var="classData" items="${classes}">
							<tr>
								<td>${classData.class_num}</td>
								<td>${classData.school.name}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div class="px-4">クラス情報が存在していません。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
