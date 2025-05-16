<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
        <h5 class="text-danger">消すよ？</h5>
        <p>${user.name}(${user.id})</p>
        <div class="mt-3">
          <a class="btn btn-danger" href="UserDeleteExecute.action">削除</a>
        </div>
      <div class="mt-3">
        <a href="UserList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
