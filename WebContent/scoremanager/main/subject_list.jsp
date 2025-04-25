<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		科目管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="subject-f1-select">入学年度</label>
						<select class="form-select" id="subject-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="subject-f2-select">クラス</label>
						<select class="form-select" id="subject-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 form-check text-center">
						<label class="form-check-label" for="subject-f3-check">在学中
							<%-- パラメーターf3が存在している場合checkedを追記 --%>
							<input class="form-check-input" type="checkbox"
							id="subject-f3-check" name="f3" value="t"
							<c:if test="${!empty f3 }">checked</c:if> />
						</label>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">絞込み</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>


			<c:choose>
				<c:when test="${subject.size()>0 }">
					<div>検索結果：${subjects.size() }件</div>
					<table class="table table-hover">
						<tr>
							<th>科目コード</th>
						    <th>科目名</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="student" items="${subjects }">
							<tr>
								<td>${subject.entYear }</td>
								<td>${subject.no }</td>
								<td>${subject.name }</td>
								<td>${subject.classNum }</td>
								<td class="text-center">

								</td>
								<td><a href="SubjectUpdate.action?no=${subject.no }">変更</a></td>
								<td><a href="SubjectDelete.action?no=${subject.no }">削除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>科目情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>