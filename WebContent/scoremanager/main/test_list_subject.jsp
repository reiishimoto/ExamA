<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">

    <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>

    <jsp:include page="test_list_form.jsp"/>

    <c:if test="${not empty testList}">
      <div class="mt-3">科目：${subject.name}</div>
      <table class="table table-bordered mt-2">
        <thead>
          <tr>
            <th>入学年度</th>
            <th>クラス</th>
            <th>学生番号</th>
            <th>氏名</th>
            <th>1回</th>
            <th>2回</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="score" items="${testList}">
            <tr>
              <td>${score.entYear}</td>
              <td>${score.classNum}</td>
              <td>${score.studentNo}</td>
              <td>${score.studentName}</td>
              <td><c:out value="${score.getPoint(1)}" default="-" /></td>
              <td><c:out value="${score.getPoint(2)}" default="-" /></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:if>

    <!-- 成績が0件のとき -->
    <c:if test="${empty testList}">
      <div class="alert alert-warning mt-3">科目情報が見つかりませんでした。</div>
    </c:if>

    <c:if test="${not empty message}">
      <div class="text-danger mt-3">${message}</div>
    </c:if>

  </c:param>
</c:import>
