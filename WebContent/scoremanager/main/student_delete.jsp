<%-- 学生情報変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報表示</h2>
			<form action="StudentDeleteExecute.action" method="get">

				<div>
				<label class="mx-auto py-2">入学年度</label><br>
				&nbsp;&nbsp; ${ent_year}<br>
				</div>
				<a></a>

				<div>
				<label class="mx-auto py-2">学生番号</label><br>
				&nbsp;&nbsp; ${no}<br>
				</div>
				<a></a>

				<div>
				<label class="mx-auto py-2">氏名</label><br>
				&nbsp;&nbsp; ${name}<br>
				</div>
				<a></a>

				<div>
				<label class="mx-auto py-2">クラス</label><br>
				&nbsp;&nbsp; ${my_class}<br>
				</div>
				<a></a>

				<div class="d-flex align-items-center py-2">
					<label class="form-check-label me-2" for="isAttend">在学状況</label>
					<c:choose>
						<c:when test="${isAttend == true}">
							<span>在学中</span>
						</c:when>
						<c:otherwise>
							<span>不在</span>
						</c:otherwise>
					</c:choose>
				</div>





			    <div class="border border-danger bg-light p-2 fw-bold text-center">
			    <h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
				<h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
				    本当に削除しますか？
				<h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
				<h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
				</div>

				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" name="login" value="削除">
				</div>
			</form>
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>