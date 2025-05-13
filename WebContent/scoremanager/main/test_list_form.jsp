<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="me=4">
	<form method="get" action="TestList.action">
		<div class="row border mx-3 mb-3 py-2 rounded" id="filter">
		<div class="row align-items-center">
			<div class="col-2">
				科目情報
			</div>
			<div class="col-2">
				<label class="form-label" for="student-f1-select">入学年度</label>
				<select class="form-select" id="student-f1-select" name="ey">
					<option value="0">--------</option>
					<c:forEach var="year" items="${ent_year_set }">
						<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
						<option value="${year }" ${year == param.ey ? "selected" : "" }>${year }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-2">
				<label class="form-label" for="student-f2-select">クラス</label>
				<select class="form-select" id="student-f2-select" name="cn">
					<option value="0">--------</option>
					<c:forEach var="num" items="${class_num_set }">
						<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
						<option value="${num }" ${num == param.cn ? "selected" : "" }>${num }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-4">
				<label class="form-label" for="student-f2-select">科目</label>
				<select class="form-select" id="student-f2-select" name="sj">
					<option value="0">--------</option>
					<c:forEach var="sub" items="${subject_set }">
						<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
						<option value="${sub.cd }" ${sub.cd.equals(param.sj) ? "selected" : "" }>${sub.name }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-2 text-center">
				<button name="f" value="sj" class="btn btn-secondary" id="filter-button">検索</button>
			</div>
			<div class="mt-2 text-warning">${errors.get("sub") }</div>
			</div>
			<hr style="display: flex; margin: 10px; margin-left: 2%; width: 96%;">
			<!-- 🔹 2行目（ボックス内に収める） -->
			<div class="row align-items-center">
			<div class="col-2">
				学生情報
			</div>
			<div class="col-4">
				<label class="form-label" for="student-f2-select">学生番号</label>
				<input class="form-control" type="text" name="st" value="${param.st }" placeholder="学生番号を入力してください">
			</div>
			<div class="col-2 text-center">
				<button name="f" value="st" class="btn btn-secondary" id="filter-button">検索</button>
			</div>
			<div class="mt-2 text-warning">${errors.get("stu") }</div>
			</div>
		</div>
	</form>
</section>