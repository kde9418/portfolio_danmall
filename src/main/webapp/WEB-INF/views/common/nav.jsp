<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-expand-md navbar-light fixed-top" style="background-color: #ffde4d;">
<%--<nav class="navbar navbar-expand-md // navbar-dark bg-dark // fixed-top">--%>
  <a class="navbar-brand" href="/"><b>DANMALL</b></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  
  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      <!--  
      <li class="nav-item">
        <a class="nav-link <c:out value="${navActive == 'home'? 'active' : '' }" />" href="/">Home</a>
      </li>-->
      <!-- 인증 전 표시 -->
      <c:if test="${sessionScope.loginStatus == null }">
      <li class="nav-item">
        <a class="nav-link <c:out value="${navActive == 'login'? 'active' : '' }" />" href="/member/login">로그인</a>
      </li>
      <li class="nav-item">
        <a class="nav-link <c:out value="${navActive == 'join'? 'active' : '' }" />" href="/member/join">회원가입</a>
      </li>
      </c:if>
      <!-- 인증 후 표시 -->
      <c:if test="${sessionScope.loginStatus != null }">
      <li class="nav-item">
        <a class="nav-link" style="cursor: default;"><b>${sessionScope.loginStatus.mem_name }</b><span style="font-size: small;">님</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link <c:out value="${navActive == 'login'? 'active' : '' }" />" href="/member/logout">로그아웃</a>
      </li>
      <li class="nav-item">
        <a class="nav-link <c:out value="${navActive == 'mypage' || myActive == 'modify' ? 'active' : '' }" />" href="/member/mypage">마이페이지</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/order/order_list">주문내역</a>
      </li>
      <li class="nav-item">
        <a class="nav-link <c:out value="${navActive == 'cartlist'? 'active' : '' }" />" href="/cart/cart_list">장바구니</a>
      </li>
      </c:if>
    </ul>
  </div>
</nav>

<script>
  $(document).ready(function(){
    $('.nav-link').click(function () {

      // .nav-link 클릭시 이전의 active 값 해제 후, 
      $('.nav-link').removeClass('active');

      // 클릭한 위치 active 적용 
      $(this).addClass('active');
    });
  });
</script>


