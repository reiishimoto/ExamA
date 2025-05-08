<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        成績登録システム
    </c:param>

    <c:param name="scripts">
        <style>
            .filter-section, .result-section { margin-bottom: 20px; padding: 15px; border: 1px solid #ccc; }
            .filter-section label, .filter-section select, .filter-section button { margin-right: 10px; }
            table { border-collapse: collapse; width: 100%; margin-top: 15px; }
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background-color: #f2f2f2; }
            .error { color: red; margin-top: 5px; display: block; }
            .hidden { display: none; }
            td .error { margin-top: 0; } /* テーブル内のエラーメッセージのマージン調整 */
        </style>
    </c:param>

    <c:param name="content">
        <section>
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            <%-- ★変更: errors Mapからエラーメッセージを表示 --%>
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
<div class="row align-items-end">
<div class="col-auto">
<label for="f1" class="form-label">入学年度:</label>
<select name="f1" id="f1" required class="form-select">
<option value="">--------</option>
<c:forEach var="year" items="${ent_year_set}">
<option value="${year}" ${year == f1 ? 'selected' : ''}>${year}</option>
</c:forEach>
</select>
</div>

            <div class="col-auto">
<label for="f2" class="form-label">クラス:</label>
<select name="f2" id="f2" required class="form-select">
<option value="">--------</option>
<c:forEach var="classNum" items="${class_num_set}">
<option value="${classNum}" ${classNum == f2 ? 'selected' : ''}>${classNum}</option>
</c:forEach>
</select>
</div>

            <div class="col-auto">
<label for="f3" class="form-label">科目:</label>
<select name="f3" id="f3" required class="form-select">
<option value="">--------</option>
<c:forEach var="subject" items="${subject_set}">
<option value="${subject.cd}" ${subject.cd == f3 ? 'selected' : ''}>${subject.name}</option>
</c:forEach>
</select>
</div>

            <div class="col-auto">
<label for="f4" class="form-label">回数:</label>
<select name="f4" id="f4" required class="form-select">
<option value="">--------</option>
<c:forEach var="num" items="${test_no_set}">
<option value="${num}" ${num == f4 ? 'selected' : ''}>${num}</option>
</c:forEach>
</select>
</div>

            <div class="col-auto">
<label class="form-label d-block">&nbsp;</label>
<button type="submit" class="btn btn-secondary">検索</button>
</div>
</div>
</form>
</div>
             <%-- ★変更: 検索が実行されたが結果がなかった場合のメッセージ表示 (errorsがないことが前提) --%>
             <c:if test="${empty errors and not empty f1 and not empty f2 and not empty f3 and not empty f4 and empty students}">
                 <p>指定された条件に一致する学生情報が見つかりませんでした。</p>
             </c:if>

        </section>
    </c:param>

</c:import>