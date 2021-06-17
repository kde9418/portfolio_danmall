<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul style="padding-left: 10px; min-width: 150px;">
<c:forEach items="${mainCateList}" var="cateVO">
	<li class="nav-item mainCategory" style="list-style: none">
	  <a class="nav-link" href="#" style="color: black" data-code="${cateVO.cg_code }">${cateVO.cg_name }</a>

     <!-- 1차카테고리 선택에 의한 2차카테고리 출력위치 -->
     <ul class="subCategory" style="list-style: none"></ul>
	</li>
</c:forEach>
</ul>
			
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script id="subCGListTemplate" type="text/x-handlebars-template">
  {{#each .}}
    <li><a style="color: black" href="/product/product_list?cg_code={{cg_code}}">{{cg_name}}</a></li>
  {{/each}}
</script>

<script>
  $(document).ready(function(){

    $(".mainCategory").on("click", function(){

      var mainCategory = $(this); // ajax안에서 $(this)가 직접 먹지 않는다. 변수로 먼저 받아 쓰면 된다.
      var cg_code = $(this).find("a").attr("data-code");
      var url = "/product/subCategoryList/" + cg_code;

      // alert(url);
      // 1차카테고리를 참조하는  2차카테고리 정보
      $.getJSON(url, function(data){
        subCGList(data, mainCategory, $("#subCGListTemplate"));
      });
    });
  });

  var subCGList = function(subCGData, targetSubCategory, templateObject){

    var template = Handlebars.compile(templateObject.html());
    var subCGLi = template(subCGData);

    // targetSubCategory.find(".subCategory").empty();

    $(".mainCategory .subCategory").empty();

    targetSubCategory.find(".subCategory").append(subCGLi);
  }

</script>