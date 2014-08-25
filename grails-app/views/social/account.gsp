<html>
	<head>
	</head>
	<body>
	<h1>Social Net</h1>
		<h2>What's up ${nickName}?</h2>
		<hr></hr>
		<g:if test="${friendsWhoRequested}">
			<g:each in="${friendsWhoRequested}">
				<p>${it} wants to be your friend!</p>			
				<g:form controller="Social" action="accept" params="[nickName: "${nickName}", friend: "${it}", accountID:"${accountID}"]">
					<g:submitButton id="btn" name="accept" value="Accept"/>
				</g:form>
				<g:form controller="Social" action="deny" params="[nickName: "${nickName}", friend: "${it}", accountID:"${accountID}"]">
					<g:submitButton id="btn" name="deny" value="Deny"/>
				</g:form>
			</g:each>
		</g:if>
		<g:else>
			<p>No new friend requests!</p>
		</g:else>
		<hr></hr>
		<g:form controller="Social" action="getFriends" params="[accountID:"${accountID}"]">
				<g:submitButton id="btn" name="friends" value="View Friends"/>
		</g:form>
		<g:form controller="Social" action="addFriend" params="[nickName: "${nickName}", accountID:"${accountID}"]">
			<g:textField name="friendNM" maxlength="255" size="100"></g:textField>
			<g:submitButton id="btn" name="addfriend" value="Add Friend"/>
		</g:form>
		<g:if test="${responseMessage}">
 		 <div class="alert alert-error" style="display: block">${responseMessage}</div>
		</g:if>
		<hr></hr>
		<g:form controller="Social" action="addStatus" params="[accountID:"${accountID}"]">
			<g:textField name="status" maxlength="10000" size="100"></g:textField>
			<g:submitButton id="btn" name="create" value="Update Status"/>
		</g:form>
		<div style="height:500px;width:850px;font:16p... Georgia, Garamond, Serif;overflow:scroll;"> 

			<g:if test="${statusUpdates}">
				<g:each in="${statusUpdates}">
				<p><b>${it.friendNM}</b></p>
				<p>${it.status}</p>
				</g:each>
			</g:if>
			<g:else>
			<p>No updates, put up a status or add some friends!</p>
			</g:else>
		</div>
	</body>
</html>