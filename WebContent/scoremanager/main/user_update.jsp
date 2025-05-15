<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">
  	得点管理システム
  </c:param>
  <c:param name="content">

    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ユーザ情報変更</h2>

      	<c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
      	</c:if>

      	<form action="UserUpdateExecute.action" method="post">
        <div>
          <label>ユーザid</label><br>
          ${user.id}
        </div>

        <div class="mt-2">
          <label for="user_name">ユーザ名</label><br>
          <input class="form-control" type="text" id="user_name" name="user_name"
                 value="${user.name}" maxlength="10" placeholder="ユーザ名を入力してください" required />
        </div>

		<div class="mx-auto py-2">
			<label for="school_cd">担当校</label>
			<select class="form-select" id="school_cd" name="school_cd">
				<c:forEach var="school" items="${schools }">
					<option value="${school.cd }" ${school.cd==user.school.cd ? 'selected' : ''}>${school.cd }</option>
				</c:forEach>
			</select>
		</div>

        <div class="mt-2">
          管理権限<br>
          ${user.manager ? '管理者' : '一般' }
        </div>

        <div class="mt-3">
          <input class="btn btn-primary" type="submit" value="変更" />
        </div>
      </form>
      <div class="mt-2">
        <a href="UserList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
