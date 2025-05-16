<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス情報登録</h2>
      <form action="ClassCreateExecute.action" method="post">
        <div>
          <label for="subject_cd">クラス番号</label>
          <input class="form-control" type="text" id="classNum" name="classNum" maxlength="3"
                 value="${cd}" maxlength="10" placeholder="クラス番号を入力してください" required />
        </div>
        <div class="mt-2 text-warning">${errors.cd}</div>

        <p>
			学校情報: ${school.name }(${school.cd })
        </p>

        <div class="mx-auto py-2">
          <button class="btn btn-primary" type="submit">登録</button>
        </div>
      </form>
      <a href="ClassList.action">戻る</a>
    </section>
  </c:param>
</c:import>
