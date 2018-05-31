<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<%-- 公用变量,用${}来获取 --%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${path}/" />
<link rel="stylesheet" href="${path}/static/css/base.css">
<link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="${path}/static/css/shanghuliebiao.css">
<link rel="stylesheet" href="${path}/static/css/shanghujiakuanshenhe.css">
<link rel="stylesheet" href="${path}/static/css/shanghuzhanghuyue.css"/>
<link rel="stylesheet" href="${path}/static/css/shanghujiakuanjilu.css">
<link rel="stylesheet" href="${path}/static/css/zhanghujiesuanguanli.css">
<link rel="stylesheet" href="${path}/static/css/caozuorizhi.css">
<link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css"/>
<link rel="stylesheet" href="${path}/static/css/xiadandingdanchaxun.css">
<link rel="stylesheet" href="${path}/static/css/jieguoweizhidingdan.css">
<link rel="stylesheet" href="${path}/static/css/shangpinguanli.css">
<link rel="stylesheet" href="${path}/static/css/authentication.css">
<link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">


<%--<script src="${path}/static/js/bootstrap.min.js "></script>--%>
<script src="${path}/static/laydate/laydate.js "></script>

<script src="${path}/static/js/jquery.min.js"></script>

<script src="${path}/static/assets/js/amazeui.min.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/js/layer/layer.js"></script>
<script src="${path}/static/js/vue-resource.js"></script>
<script src="${path}/static/js/jquery.validate.js" type="text/javascript"></script>
<script src="${path}/static/js/messages_zh.js" type="text/javascript"></script>
