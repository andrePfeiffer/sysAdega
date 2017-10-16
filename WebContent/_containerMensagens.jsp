	<!--  CONTAINER DE MENSAGENS -->
	<%		
		String mensagem = (String)request.getAttribute("mensagem"); // Recupera a mensagem vinda do Servlet, se houver
		if( mensagem == null ) {
			mensagem = "";
		} else {		
	%>	
		<div class="container"> 
			<div class="alert alert-warning">		
				<strong><%=mensagem %></strong>
			</div>
		</div>
	<%
		}
	%>	