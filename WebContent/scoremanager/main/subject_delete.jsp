<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
      <form action="SubjectDeleteExecute.action" method="get">
        <h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
        <p>「${subject.name}(${subject.cd})」を削除してもよろしいですか？</p>
	    <h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
        <p>「${subject.name}(${subject.cd})」を本当に削除しますか？</p>
        <h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
        <p>ほんとに！？消去するよ！しちゃうよ？いいんだね？</p>
		<h5 class="text-danger">⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告⚠️警告</h5>
        <input type="hidden" name="cd" value="${subject.cd}">
        <div class="mt-3">
          <input class="btn btn-danger" type="submit" value="削除">
        </div>
      </form>
      <div class="mt-3">
        <a href="SubjectList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
