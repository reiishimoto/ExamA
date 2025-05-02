<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">

    <div class="container mt-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>

      <jsp:include page="test_list_form.jsp">
        <jsp:param name="action" value="TestListStudentExecute.action" />
      </jsp:include>

      <c:if test="${not empty student}">
        <div class="mt-3 mb-2 fw-bold">氏名：${student.name}（${student.no}）</div>

        <table class="table table-bordered table-striped">
          <thead class="table-light">
            <tr>
              <th>科目名</th>
              <th>科目コード</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="score" items="${testList}">
              <tr>
                <td>${score.subjectName}</td>
                <td>${score.subjectCd}</td>
                <td>${score.num}</td>
                <td><c:out value="${score.point}" default="-" /></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

		    <!-- ここを追加：成績が0件のとき -->
        <c:if test="${empty testList}">
          <div class="alert alert-warning mt-3">成績情報が見つかりませんでした。</div>
        </c:if>

      <c:if test="${not empty message}">
        <div class="text-danger mt-3">${message}</div>
      </c:if>
    </div>

  </c:param>
</c:import>
