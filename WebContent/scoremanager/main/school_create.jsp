<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学校情報登録</h2>
      <form action="SchoolCreateExecute.action" method="post">
        <div>
          <label for="subject_cd">学校コード</label>
          <input class="form-control" type="text" id="school_cd" name="school_cd"
                 value="${cd}" maxlength="10" placeholder="学校コードを入力してください" required />
        </div>
        <div class="mt-2 text-warning">${errors.cd}</div>

        <div>
          <label for="subject_name">学校名</label>
          <input class="form-control" type="text" id="school_name" name="school_name"
                 value="${name}" maxlength="30" placeholder="学校名を入力してください" required />
        </div>
        <div class="mt-2 text-warning">${errors.name}</div>

        <div class="mx-auto py-2">
          <button class="btn btn-primary" type="submit">登録</button>
        </div>
      </form>
      <a href="SchoolList.action">戻る</a>
    </section>
  </c:param>
</c:import>
