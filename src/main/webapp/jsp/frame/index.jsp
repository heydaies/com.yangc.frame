<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.include file="/jsp/frame/head.jspf" />
<script type="text/javascript" src="<%=js_custom%>frame/index.js"></script>
<script type="text/javascript">
var parentMenuId = 0;
var personName = "${sessionScope.LOGIN_USER.personName}";
</script>
</head>
<body>
</body>
</html>
