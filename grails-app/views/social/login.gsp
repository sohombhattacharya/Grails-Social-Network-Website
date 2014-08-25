<html>
	<head>
	</head>
	<body>
	<h1>Social Net</h1>
	<g:form controller="Social" action="login">
		User Name: <g:textField name="userNM" maxlength="255"></g:textField>
		Password: <g:passwordField name="passWD" maxlength="255"></g:passwordField>
		<g:submitButton id="btn" name="loginButton" value="Login"/>
	</g:form>
	<g:if test="${flash.error}">
 		 <div class="alert alert-error" style="display: block">${flash.error}</div>
	</g:if>
	</body>
</html>