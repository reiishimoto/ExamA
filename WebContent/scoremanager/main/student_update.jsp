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
				<input type="hidden" id="ent_year" name="ent_year" value="${ent_year}"> &nbsp;&nbsp; ${ent_year}<br>
				</div>
				<a></a>

				       			 <div>
				<label class="mx-auto py-2">学生番号</label><br>
				<input type="hidden" id="no" name="no" value="${no}"> &nbsp;&nbsp; ${no}<br>
				</div>
				<a></a>


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
					<input type="checkbox" name="isAttend" value="true"
    					<c:if test="${isAttend == true}">checked</c:if>>
				</div>

				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" name="login" value="変更">
				</div>
			</form>
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>


