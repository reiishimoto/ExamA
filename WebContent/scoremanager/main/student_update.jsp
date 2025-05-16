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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form action="StudentUpdateExecute.action" method="get">

				<div>
				<label class="mx-auto py-2">入学年度</label><br>
				&nbsp;&nbsp; ${ent_year}<br>
				</div>

				       			 <div>
				<label class="mx-auto py-2">学生番号</label><br>
				&nbsp;&nbsp; ${no}<br>
				</div>


				 <p></p>

				<div>
					<label for="name">氏名</label><br>
					<input class="form-control" type="text" id="name" name="name" value="${name}" required maxlength="30" placeholder="氏名を入力してください" />
				</div>

				<div class="mx-auto py-2">
					<label for="class_num">クラス</label>
					<select class="form-select" id="class_num" name="class_num">
						<c:forEach var="num" items="${class_num_set }">
							<option value="${num }" <c:if test="${num==my_class }">selected</c:if>>${num }</option>
						</c:forEach>
					</select>
				</div>

				<div class="d-flex align-items-center py-2">
					<label class="form-check-label me-2" for="isAttend">在学中</label>
					<input type="checkbox" id="isAttend" name="isAttend" value="true"
    					<c:if test="${isAttend }">checked</c:if>>
					<div id="extraForm" style="display: none;">
						<label for="extraInfo">&nbsp;&nbsp;&nbsp;&nbsp;追加情報</label>
						<label>
							<input type="radio" name="reason" value="卒業" ${reason == '卒業' ? 'checked' : '' }> 卒業
						</label>
						<label>
							<input type="radio" name="reason" value="退学" ${reason == '退学' ? 'checked' : '' }> 退学
						</label>
					</div>
				</div>
				<script>
				function updateFormVisibility() {
				    let checkbox = document.getElementById("isAttend");
				    let extraForm = document.getElementById("extraForm");
				    extraForm.style.display = checkbox.checked ? "none" : "block";
				}

				// 初期状態を適用
				document.addEventListener("DOMContentLoaded", updateFormVisibility);

				// チェックの変更を監視
				document.getElementById("isAttend").addEventListener("change", updateFormVisibility);
				</script>

				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" name="login" value="変更">
				</div>
			</form>
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>


