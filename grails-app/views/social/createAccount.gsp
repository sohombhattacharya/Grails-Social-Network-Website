<html>
	<head>
	</head>
	<body>
	<h1>Social Net</h1>
	<g:form controller="Social" action="createAccount">
		User Name: <g:textField name="userNM" maxlength="255"></g:textField><br></br>
		Password: <g:textField name="passWD" maxlength="255"></g:textField><br></br>
		Nickname: <g:textField name="nickname" maxlength="255"></g:textField>
		<g:submitButton id="btn" name="create" value="Create Account "/>
	</g:form>
	<g:if test="${flash.error}">
 		 <div class="alert alert-error" style="display: block">${flash.error}</div>
	</g:if>
	</body>
</html>