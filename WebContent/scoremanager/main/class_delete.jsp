<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス削除</h2>
        <h5 class="text-danger">以下のクラスを完全に消去します</h5>
        <p>${classData.class_num}(${classData.school.name})</p>
        <br>
        <div class="mt-3">
		<p class="text-denger">※該当クラス(${classData.class_num })に所属する学生も全て消去されます。<a href="StudentList.action?f2=${classData.class_num }">確認をしてください</a></p>
        </div>
        <br>
        <div class="mt-3">
          <a class="btn btn-danger" href="ClassDeleteExecute.action">削除</a>
        </div>
      <div class="mt-3">
        <a href="ClassList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
