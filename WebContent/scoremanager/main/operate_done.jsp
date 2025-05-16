<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="content">
  <style>
	.link-container {
    	display: inline-block;
    	margin: 0 20px;
	}
  </style>

    <section>
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">${title}</h2>
      <div class="bg-success bg-opacity-25 py-2 px-3">${message}</div>
      <div class="mt-3">
        <c:forEach var="linkItem" items="${links}">
          <a class="link-container" href="${linkItem.url}">${linkItem.text}</a>
        </c:forEach>
      </div>
    </section>
  </c:param>
</c:import>
