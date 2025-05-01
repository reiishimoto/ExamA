<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">

   	<c:param name="title">
		成績登録システム1
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
            td .error { margin-top: 0; }
        </style>
    </c:param>

    <c:param name="content">
        <section>
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2> <%-- 見出しのスタイルを例に合わせて調整 --%>

            <%-- Display General Errors (from request attribute 'errors') --%>
            <c:if test="${not empty errors.get('general')}">
                <p class="error"><c:out value="${errors.get('general')}" /></p>
            </c:if>
            <c:if test="${not empty errors.get('filter')}">
                <p class="error"><c:out value="${errors.get('filter')}" /></p>
            </c:if>
<div class="filter-section">
                <form action="TestRegist.action" method="get">
                    <%-- 各フィルター項目をdivで囲み、縦に並べる --%>
                    <div class="mb-3"> <%-- 下にマージンを追加 --%>
					  <label for="mx-auto py-2" class="form-label">入学年度:</label> <%-- form-labelクラスを追加 --%>
					  <select name="f1" id="f1" required class="form-select w-auto"> <%-- d-inline-blockを削除 --%>
					   <option value="">--------</option>
					   <%-- items属性の値を ${ent_year} に変更 --%>
					   <c:forEach var="year" items="${ent_year}">
					    <option value="${year}" ${year == f1 ? 'selected' : ''}>${year}</option>
					   </c:forEach>
					  </select>
					 </div>

                    <div class="mb-3">
                        <label for="f2" class="form-label">クラス:</label>
                        <select name="f2" id="f2" required class="form-select w-auto"> <%-- d-inline-blockを削除 --%>
                            <option value="">--------</option>
                             <c:forEach var="classNum" items="${class_num_set}">
                                <option value="${classNum}" ${classNum == f2 ? 'selected' : ''}>${classNum}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="f3" class="form-label">科目:</label>
                        <select name="f3" id="f3" required class="form-select w-auto"> <%-- d-inline-blockを削除 --%>
                            <option value="">--------</option>
                             <c:forEach var="subject" items="${subject_set}">
                                <option value="${subject.cd}" ${subject.cd == f3 ? 'selected' : ''}>${subject.name}</option>
                             </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="f4" class="form-label">回数:</label>
                        <select name="f4" id="f4" required class="form-select w-auto"> <%-- d-inline-blockを削除 --%>
                            <option value="">--------</option>
                             <c:forEach var="num" items="${test_num_set}">
                                <option value="${num}" ${num == f4 ? 'selected' : ''}>${num}</option>
                             </c:forEach>
                        </select>
                    </div>

                    <%-- 検索ボタンは最後に追加 --%>
                    <button type="submit" class="btn btn-secondary btn-sm">検索</button>
                </form>
            </div>
            <%-- Results Section (Displayed after search) --%>
            <c:if test="${not empty students}">
                <div class="result-section">
                    <h3>科目: <c:out value="${subject_name}"/> (<c:out value="${f4}"/>回)</h3>

                     <c:if test="${not empty errors.get('score')}">
                         <p class="error"><c:out value="${errors.get('score')}" /></p>
                     </c:if>

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
                                            <input type="text" name="point_${student.no}" value="${scoresMap[student.no]}" size="5" class="form-control form-control-sm d-inline-block w-auto">
                                             <c:if test="${not empty errors.get(student.no)}">
                                                 <span class="error"><c:out value="${errors.get(student.no)}" /></span>
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
             <c:if test="${not empty students and empty errors.search}">
                <p>指定された条件に一致する学生情報が見つかりませんでした。</p>
             </c:if>

        </section>
    </c:param>

</c:import>