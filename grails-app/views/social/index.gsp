<html>
	<head>
	</head>
	<body>
	<h1>Social Net</h1>
	</body>
	<g:form controller="Social" action="login">
		User Name: <g:textField name="userNM" maxlength="255"></g:textField>
		Password: <g:passwordField name="passWD" maxlength="255"></g:passwordField>
		<g:submitButton id="btn" name="loginButton" value="Login"/>
	</g:form>
	<hr></hr>
	<h3>Create an Account</h3>
	<g:form controller="Social" action="createAccount">
		User Name: <g:textField name="userNm" maxlength="255"></g:textField>
		Password:  <g:passwordField type="password" name="passWd" maxlength="255"></g:passwordField>
		Nick Name: <g:textField name="nickname" maxlength="255"></g:textField>
		<g:submitButton id="btn1" name="createAccount" value="Create Account"/>
	</g:form>
</html>