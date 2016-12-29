<%@ page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/login/img"/>
<%
String auto = request.getParameter("auto");
if (auto != null && auto.equals("true") ){
%>
<html>
<head>
<script >
function doAutoLogin() {
	var service="<%=request.getParameter("service") %>";
	if(service!="null"){
		document.forms[0].action+="?service="+service;
	}
	document.forms[0].submit();
}

</script>
</head>
<body onload="doAutoLogin();" style="background-color: white;">
<div style="text-align: center;">
<img alt="" src="${ctxStatic}/images/login/wait.gif"><br>
<span>正在登录，请稍后...</span>
</div>
<form id="credentials" method="POST" action="<%=request.getContextPath() %>/login">
<input type="hidden" name="lt" value="${loginTicket}"/>
<input type="hidden" name="execution"value="${flowExecutionKey}" />
<input type="hidden"name="_eventId" value="submit" />
<input type="hidden" name="autoType" value="<%=request.getParameter("autoType")==null?request.getParameter("autotype"):request.getParameter("autoType") %>" />
<input type="hidden" name="username" value="<%=request.getParameter("username")%>" />
<input type="hidden" name="password" value="<%=request.getParameter("password")%>" />
<input type="submit" style="visibility: hidden;" />
</form>
</body>
</html>

<%
} else {
%>
<jsp:directive.include file="includes/top.jsp" />
<div class="container"><header class="login-header login-mar-t50">
  <h3><a href=""><img src="${ctxImg }/logo.png"></a></h3>
  <a class="login-header-infor" href="">数据挖掘分析系统</a>
  <div class="tishi-area" id="warn_div" style="display: none;">
      	<div class="pointer-box" onclick="showDiv();"> <!--  --> 
            <i class="waring-red"></i>
            <span>您当前浏览器对本系统兼容性不佳。建议更换，点此查看</span>
            <i class="pointer-r"></i>
        </div>
        <div class="browser-area" style="min-width:500px;z-index:2">
        	<div class="browser-box">
            	<i class="arrow-t"></i>
                <p class="browser-head">兼容的浏览器</p>
                <ul class="list-unstyled">
                	<li>
                    	<i class="brs-icon"></i>
                        <span>谷歌：</span>
                        <a href="http://www.google.cn/chrome/browser/desktop">http://www.google.cn/chrome/browser/desktop</a>
                    </li>
                    <li>
                    	<i class="brs-icon ff"></i>
                        <span>火狐：</span>
                        <a href="http://www.firefox.com.cn">http://www.firefox.com.cn</a>
                    </li>
                    <li>
                    	<i class="brs-icon jisu"></i>
                        <span>360极速模式：</span>
                        <a href="http://se.360.cn">http://se.360.cn</a>
                    </li>
                </ul>
             <div onclick="hideDiv();" style="position: absolute;right: 10px;top: 10px;cursor: pointer;">X</div> 
            </div>
        </div>
      </div>
  
  
  </header></div>
	<section class="login-bgcolor">
  <form:form method="post" commandName="${commandName}" htmlEscape="true" class="login-bg">

		
	<div class="login-windows">	
	
  
  <%--   <h2><spring:message code="screen.welcome.instructions" /></h2> --%>
  
    <section>
      <%-- <label for="username"><spring:message code="screen.welcome.label.netid" /></label> --%>
      <c:choose>
        <c:when test="${not empty sessionScope.openIdLocalId}">
          <strong>${sessionScope.openIdLocalId}</strong>
          <input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
        </c:when>
        <c:otherwise>
          <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
          <form:input cssClass="required login-zhanghao" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="off" htmlEscape="true" class="login-zhanghao" placeholder="账号"/>
        </c:otherwise>
      </c:choose>
    </section>
    
     <section >
    <%--   <label for="password"><spring:message code="screen.welcome.label.password" /></label> --%>
      <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
    <form:password cssClass="required login-mima" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" class="login-mima"  laceholder="密码"/>
    </section> 
    
<%--     <section class="row check">
      <input id="warn" name="warn" value="true" tabindex="3" accesskey="<spring:message code="screen.welcome.label.warn.accesskey" />" type="checkbox" style="width:auto" />
            转向其他站点前提示我
      <label for="warn"><spring:message code="screen.welcome.label.warn" /></label>
    </section> --%>
    <div id="" style="text-align:center;" class="error">${errormsg }</div>
    <%--  <form:errors path="*" class="error" style="text-align:center;" element="div" htmlEscape="false" /> --%>
    <section class=" btn-row">
      <input type="hidden" name="lt" value="${loginTicket}" />
      <input type="hidden" name="execution" value="${flowExecutionKey}" />
      <input type="hidden" name="_eventId" value="submit" />

      <button class="login-button" name="submit" accesskey="l" tabindex="4" type="submit" id="casFrom"> 登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
     <%--  <input class="btn-reset" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="reset" /> --%>
    </section>
    </div>
  </form:form>
  </section>

<%-- <jsp:directive.include file="includes/bottom.jsp" /> --%>
<div class="login-footer">Copyright © 2010-2015 Zhoukou Normal University</div>
<%
}
%>
<script type="text/javascript">
//判断浏览器
function myBrowser(){
  var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
  var isOpera = userAgent.indexOf("Opera") > -1;
  if (isOpera) {
      return "Opera"
  }; //判断是否Opera浏览器
  if (userAgent.indexOf("Firefox") > -1) {
      return "FF";
  } //判断是否Firefox浏览器
  if (userAgent.indexOf("Chrome") > -1){
return "Chrome";
}
  if (userAgent.indexOf("Safari") > -1) {
      return "Safari";
  } //判断是否Safari浏览器
  if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
      return "IE";
  }; //判断是否IE浏览器
  if(window.navigator.appName.indexOf("Microsoft") != -1&&(window.navigator.userProfile+'')=='null'){  
  	return "360";
  } 
}
var showDiv=function(){
	$(".browser-area").css("visibility","visible");
}
var hideDiv=function(){
	$(".browser-area").css("visibility","hidden");
}


//以下是调用上面的函数
$(function(){
	var mb = myBrowser();
	if(mb!='FF'&&mb!='Chrome'){
		$("#warn_div").css("display","inline-block");
	};
	
});
</script>