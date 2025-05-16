<%-- ユーザ一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ユーザ管理</h2>
			<div class="my-2 text-end px-4">
				<a href="UserCreate.action">新規登録</a>
			</div>
			<form method="get">
				<div class="row mx-3 mb-3 py-2 align-items-center" id="filter">
					<div class="col-2">区分</div>
					<div class="col-5 form-check text-center">
						<select class="form-select" id="student-f2-select" name="f">
						<option value="0" ${param.f == 0 ? 'selected' : '' }>すべて</option>
						<option value="1" ${param.f == 1 ? 'selected' : '' }>管理アカウントのみ</option>
						<option value="2" ${param.f == 2 ? 'selected' : '' }>一般アカウントのみ</option>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">絞込み</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>
			<hr>

			<c:choose>
				<c:when test="${users.size()>0 }">
					<div>検索結果：${users.size() }名</div>
					<table class="table table-hover">
						<tr>
							<th>ユーザID</th>
							<th>ユーザ氏名</th>
							<th>担当校</th>
							<th>権限</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="user" items="${users }">
							<tr>
								<td>${user.id }</td>
								<td>${user.name }</td>
								<td>${user.school.cd }</td>
								<td>
								<c:choose>
									<c:when test="${user.manager}">
										管理者
									</c:when>
									<c:otherwise>
										一般
									</c:otherwise>
								</c:choose>
								</td>
								<td><a href="UserUpdate.action?id=${user.id }">変更</a></td>
								<td><a href="UserDelete.action?id=${user.id }">削除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>学生情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>