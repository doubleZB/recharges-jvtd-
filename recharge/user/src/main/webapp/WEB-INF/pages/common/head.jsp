<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<%-- 公用变量,用${}来获取 --%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${path}/" />

<link href="${path}/static/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
<link href="${path}/static/css/common.css" rel="stylesheet">
<link rel="stylesheet" href="${path}/static/css/amazeui.min.css">


<link href="${path}/static/css/z-home.css" rel="stylesheet">
<link href="${path}/static/css/z-liuliangchongzhi.css" rel="stylesheet">
<link rel="stylesheet" href="${path}/static/css/z-dingdanjilu.css">


<script src="${path}/static/js/echarts.common.min.js"></script>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js "></script>

<script src="${path}/static/js/index.js"></script>
<script src="${path}/static/js/z-huafei.js"></script>
<script src="${path}/static/js/z-huafei.js"></script>
<script src="${path}/static/js/z-mimaguanli.js"></script>
<script src="${path}/static/js/myBrowser.js"></script>

<script src="${path}/static/js/layer/layer.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/js/vue-resource.js"></script>