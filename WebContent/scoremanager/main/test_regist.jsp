<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
<c:param name="title">成績登録システム</c:param>
<c:param name="scripts">
<style>
    .filter-section, .result-section { margin-bottom: 20px; padding: 15px; border: 1px solid #ccc; }
    table { border-collapse: collapse; width: 100%; margin-top: 15px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #f2f2f2; }
    .error { color: red; margin-top: 5px; display: block; }
    .hidden { display: none; }
    td .error { margin-top: 0; }

    .filter-item-wrapper {
        margin-bottom: 0.5rem;
    }
    .filter-item-wrapper .form-label {
        margin-bottom: 0;
    }
</style>
</c:param>

<c:param name="content">
<section>
<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

    <c:if test="${not empty errors.general}">
<p class="error"><c:out value="${errors.general}" /></p>
</c:if>
<c:if test="${not empty errors.filter}">
<p class="error"><c:out value="${errors.filter}" /></p>
</c:if>
<c:if test="${not empty errors.data}">
<p class="error"><c:out value="${errors.data}" /></p>
</c:if>
<c:if test="${not empty errors.numberFormat}">
<p class="error"><c:out value="${errors.numberFormat}" /></p>
</c:if>

    <div class="filter-section">
<form action="TestRegist.action" method="get">
<div class="d-flex flex-wrap align-items-center">
    <div class="filter-item-wrapper me-3">
<label for="f1" class="form-label me-1">入学年度:</label>
<select name="f1" id="f1" required class="form-select form-select-sm w-auto d-inline-block">
<option value="">--------</option>
<c:forEach var="year" items="${ent_year_set}">
<option value="${year}" ${year == f1 ? 'selected' : ''}>${year}</option>
</c:forEach>
</select>
</div>

    <div class="filter-item-wrapper me-3">
<label for="f2" class="form-label me-1">クラス:</label>
<select name="f2" id="f2" required class="form-select form-select-sm w-auto d-inline-block">
<option value="">--------</option>
<c:forEach var="classNum" items="${class_num_set}">
<option value="${classNum}" ${classNum == f2 ? 'selected' : ''}>${classNum}</option>
</c:forEach>
</select>
</div>

    <div class="filter-item-wrapper me-3">
<label for="f3" class="form-label me-1">科目:</label>
<select name="f3" id="f3" required class="form-select form-select-sm w-auto d-inline-block">
<option value="">--------</option>
<c:forEach var="subject" items="${subject_set}">
<option value="${subject.cd}" ${subject.cd == f3 ? 'selected' : ''}>${subject.name}</option>
</c:forEach>
</select>
</div>

    <div class="filter-item-wrapper me-3">
<label for="f4" class="form-label me-1">回数:</label>
<select name="f4" id="f4" required class="form-select form-select-sm w-auto d-inline-block">
<option value="">--------</option>
<c:forEach var="num" items="${test_no_set}">
<option value="${num}" ${num == f4 ? 'selected' : ''}>${num}</option>
</c:forEach>
</select>
</div>

    <div class="filter-item-wrapper">
<button type="submit" class="btn btn-secondary btn-sm">検索</button>
</div>
</div>
</form>
</div>
    <c:if test="${empty errors and not empty f1 and not empty f2 and not empty f3 and not empty f4 and not empty students}">
<div class="result-section">
<h3>科目: <c:out value="${subject_name}"/>（<c:out value="${f4}"/>回）</h3>

        <form action="TestRegistExecute.action" method="post">
<input type="hidden" name="f1" value="${f1}">
<input type="hidden" name="f2" value="${f2}">
<input type="hidden" name="f3" value="${f3}">
<input type="hidden" name="f4" value="${f4}">

            <table class="table table-striped table-bordered">
<thead>
<tr>
<th>入学年度</th>
<th>クラス</th>
<th>学生番号</th>
<th>氏名</th>
<th>点数</th>
</tr>
</thead>
<tbody>
<c:forEach var="student" items="${students}">
<tr>
<td><c:out value="${student.entYear}" /></td>
<td><c:out value="${student.classNum}" /></td>
<td><c:out value="${student.no}" /></td>
<td><c:out value="${student.name}" /></td>
<td>
<input type="number"
       name="point_${student.no}"
       value="${scoresMap[student.no]}"
       size="5"
       min="0"
       max="100"
       required
       class="form-control form-control-sm w-auto d-inline-block">

<c:if test="${not empty errors[student.no]}">
<span class="error"><c:out value="${errors[student.no]}" /></span>
</c:if>
</td>
</tr>
</c:forEach>
</tbody>
</table>
<button type="submit" class="btn btn-primary">登録して終了</button>
</form>
</div>
</c:if>

    <c:if test="${empty errors and not empty f1 and not empty f2 and not empty f3 and not empty f4 and empty students}">
<p>指定された条件に一致する学生情報が見つかりませんでした。</p>
</c:if>
</section>
</c:param>
</c:import>