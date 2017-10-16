<%@include file="_import.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<%			
		// Lista de vinhos
		List<Vinho> vinhos = VinhoManager.consultarTodosVinhos();
		
		if( vinhos.isEmpty() ) {
	%>	
		<div class="container"> 
			<div class="alert alert-danger">		
				<strong>Não há vinhos cadastrados.</strong>
				<br></br><a href="cadastrarVinho.jsp" class="btn btn-success">Cadastrar Novo</a>
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
		<a href="cadastrarVinho.jsp" class="btn btn-success">Cadastrar Novo</a>
		<a href="confirmacao.jsp?operacao=limparBD" class="btn btn-success">Limpar Banco de Dados</a>
		<br><br/>
		
		<div class="row">
	        <div class="panel panel-primary filterable">
	            <div class="panel-heading">
	                <h3 class="panel-title">Vinhos</h3>
	                <div class="pull-right">
	                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filtro</button>
	                </div>
	            </div>
	            <table class="table">
	                <thead>
	                    <tr class="filters">
	                        <th width="5%"></th>
	                        <th width="5%"><input type="text" class="form-control" placeholder="Id" disabled></th>
	                        <th><input type="text" class="form-control" placeholder="Nome" disabled></th>
	                        <th><input type="text" class="form-control" placeholder="Cor" disabled></th>
	                        <th><input type="text" class="form-control" placeholder="Ano" disabled></th>
	                        <th><input type="text" class="form-control" placeholder="Preço" disabled></th>
	                        <th><input type="text" class="form-control" placeholder="Qtd Estoque" disabled></th>
	                    </tr>
	                </thead>
	                <% for( Vinho vinho : vinhos ) { %>
				      <tr>
				      	<td>
				      		<a href="editarVinho.jsp?idVinho=<%=vinho.getIdVinho()%>" data-toggle="popover" data-trigger="hover" data-content="Editar"><img src="images/lapis-icon.png" alt="Editar" title="Editar" style="width:16px;height:16px;border:0;"></img></a>
				      		<a href="confirmacao.jsp?idVinho=<%=vinho.getIdVinho()%>&nomeVinho=<%=vinho.getNomeVinho()%>&operacao=excluirVinho" data-toggle="popover" data-trigger="hover" data-content="Excluir"><img src="images/delete-icon.png" alt="Excluir" title="Excluir" style="width:16px;height:16px;border:0;"></img></a>
				      	</td>				      	
				      	<td><strong><%=vinho.getIdVinho()%></strong></td>
				      	<td><%=vinho.getNomeVinho()%></td>				      	
				      	<td><%=vinho.getCorVinho()%></td>
				      	<td><%=vinho.getAnoVinho()%></td>
				      	<td><%=Utils.strDoubleParaMoeda(vinho.getPrecoVinho())%></td>
				      	<td><%=vinho.getQtdEstoque()%></td>
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
