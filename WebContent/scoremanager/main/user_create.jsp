<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">
  	得点管理システム
  </c:param>
  <c:param name="content">

    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">ユーザ情報登録</h2>

      	<c:if test="${not empty error}">
        <p class="text-danger">${error}</p>
      	</c:if>

      	<form action="UserCreateExecute.action" method="post">
        <div class="mt-2">
          <label for="user_name">ユーザID</label><br>
          <input class="form-control" type="text" id="user_id" name="user_id"
                 value="${user_id }" maxlength="30" placeholder="ユーザIDを入力してください" required />
        </div>
        <div class="mt-2 text-warning">
          ${errors['id']}
        </div>

        <div class="mt-2">
          <label for="user_name">ユーザ名</label><br>
          <input class="form-control" type="text" id="user_name" name="user_name"
                 value="${user_name }" maxlength="30" placeholder="ユーザ名を入力してください" required />
        </div>

        <div class="mt-2">
          <label for="user_name">パスワード</label><br>
          <input class="form-control" type="text" id="user_pw" name="user_pw"
                 value="${user_pw }" maxlength="30" placeholder="パスワードを入力してください" required />
        </div>

		<div class="mx-auto py-2">
			<label for="school_cd">担当校</label>
			<select class="form-select" id="school_cd" name="school_cd">
				<c:forEach var="school" items="${schools }">
					<option value="${school.cd }" ${school.cd == school_cd ? 'selected' : '' }>${school.name }</option>
				</c:forEach>
			</select>
		</div>

        <div class="mt-2">
          <label for="roll">管理権限</label><br>
          <input type="checkbox" id="roll" name="roll"
                 value="manager" ${roll == 'manager' ? 'checked' : '' }/>
        </div>

        <div class="mt-3">
          <input class="btn btn-primary" type="submit" value="登録" />
        </div>
      </form>
      <div class="mt-2">
        <a href="UserList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
