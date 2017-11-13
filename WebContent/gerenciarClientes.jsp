<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<%			
		// Lista de clientes
		List<Cliente> clientes = ClienteManager.consultarTodosClientes();
		
		if( clientes.isEmpty() ) {
	%>	
		<div class="container"> 
			<div class="alert alert-danger">		
				<strong>Não há clientes cadastrados.</strong>
				<br/><a href="cadastrarCliente.jsp" class="btn btn-success">Cadastrar Novo</a>
				<a href="confirmacao.jsp?operacao=popularBD" class="btn btn-success">Popular Banco de Dados</a>
			</div>
		</div>		
	<%
		} else {
	%>
	
	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>
	
	<!--  CONTAINER PRINCIPAL -->
	<div class="container">		
		<!-- Botões -->	
		<a href="cadastrarCliente.jsp" class="btn btn-success">Cadastrar Novo</a>
		<a href="confirmacao.jsp?operacao=limparBD" class="btn btn-success">Limpar Banco de Dados</a>
		<br/>
		
		<div class="row">
	        <div class="panel panel-primary filterable">
	            <div class="panel-heading">
	                <h3 class="panel-title">Clientes</h3>
	                <div class="pull-right">
	                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filtro</button>
	                </div>
	            </div>
	            <table class="table">
	                <thead>
	                    <tr class="filters">
	                        <th width="5%"></th>
	                        <th width="5%"><input type="text" class="form-control" placeholder="Id" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Nome" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Cpf" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Cep" disabled /></th>
	                    </tr>
	                </thead>
	                <% for( Cliente cliente : clientes ) { %>
				      <tr>
				      	<td>
				      		<a href="editarCliente.jsp?idCliente=<%=cliente.getIdCliente()%>" data-toggle="popover" data-trigger="hover" data-content="Editar"><img src="images/lapis-icon.png" alt="Editar" title="Editar" style="width:16px;height:16px;border:0;"></img></a>
				      		<a href="confirmacao.jsp?idCliente=<%=cliente.getIdCliente()%>&nomeVinho=<%=cliente.getNomeCliente()%>&operacao=excluirCliente" data-toggle="popover" data-trigger="hover" data-content="Excluir"><img src="images/delete-icon.png" alt="Excluir" title="Excluir" style="width:16px;height:16px;border:0;"></img></a>
				      	</td>				      	
				      	<td><strong><%=cliente.getIdCliente() %></strong></td>
				      	<td><%=cliente.getNomeCliente() %></td>				      	
				      	<td><%=cliente.getCpf() %></td>
				      	<td><%=cliente.getCep() %></td>
				      </tr>
				    <% } %>
	            </table>
	        </div>
    	</div>  

		
	</div>
	<!-- fim .container da pagina -->
	<%
		}
	%>
</body>
</html>
