<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
      <form action="SubjectCreateExecute.action" method="post">
        <div>
          <label for="subject_cd">科目コード</label>
          <input class="form-control" type="text" id="subject_cd" name="subject_cd"
                 value="${subject_cd}" maxlength="10" placeholder="科目コードを入力してください" required />
        </div>
        <div class="mt-2 text-warning">${errors.subject_cd}</div>

        <div>
          <label for="subject_name">科目名</label>
          <input class="form-control" type="text" id="subject_name" name="subject_name"
                 value="${subject_name}" maxlength="20" placeholder="科目名を入力してください" required />
        </div>
        <div class="mt-2 text-warning">${errors.subject_name}</div>

        <div class="mx-auto py-2">
          <button class="btn btn-primary" type="submit">登録</button>
        </div>
      </form>
      <a href="SubjectList.action">戻る</a>
    </section>
  </c:param>
</c:import>
