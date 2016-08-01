<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>

<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="utf-8">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script>
	function display() {
		//document.getElementById("demo").innerHTML = Date();
		document.getElementById("demo").innerHTML = document
				.getElementById("name").value
				+ document.getElementById("password").value;
				
	}
</script>
</head>

<body>
	This is my JSP page.
	<br>
	<p id="demo">这是一个段落</p>


	<table>
		<tr>
			<th align="left">Name:</th>
			<th><input id=name type="text" name="name" value="">
			</th>
		</tr>

		<tr>
			<th align="left">PassWord:</th>
			<th><input id=password type="password" name="pwd">
			</th>
		</tr>
		<tr>
			<th align="center"><button type="button" onclick="display()" >login</button>
			</th>
		</tr>

	</table>
</body>

</html>
