<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ユーザ情報削除</h2>
      <div class="bg-success bg-opacity-25 py-2 px-3">
        削除が完了しました
      </div>
      <div class="mt-3">
        <a href="UserList.action">ユーザ一覧</a>
      </div>
    </section>
  </c:param>
</c:import>
