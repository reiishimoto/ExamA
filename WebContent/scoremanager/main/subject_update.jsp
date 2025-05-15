<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">
  	得点管理システム
  </c:param>
  <c:param name="content">

    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

      	<c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
      	</c:if>

      	<form action="SubjectUpdateExecute.action" method="post">
        <div>
          <label>科目コード</label><br>
          <input type="hidden" name="subject_cd" value="${subject.cd}" />
          ${subject.cd}
        </div>

        <div class="mt-2">
          <label for="subject_name">科目名</label><br>
          <input class="form-control" type="text" id="subject_name" name="subject_name"
                 value="${subject.name}" maxlength="20" placeholder="科目名を入力してください" required />
        </div>

        <div class="mt-3">
          <input class="btn btn-primary" type="submit" value="変更" />
        </div>
      </form>
      <div class="mt-2">
        <a href="SubjectList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
