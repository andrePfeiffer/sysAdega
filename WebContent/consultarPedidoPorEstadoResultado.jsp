<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<%			
		// Lista de vinhos
		List<?> pedidos = (List<?>)request.getAttribute("listaPedidos");
		String estadoPedido = (String) request.getAttribute("estadoPedido");
		
		if( pedidos == null || pedidos.isEmpty() ) {
	%>	
		<div class="container"> 
			<div class="alert alert-danger">		
				<strong>Não há pedidos no estado <i><%=estadoPedido%></i>.</strong>
				<br/><a href="consultarPedidoPorEstado.jsp" class="btn btn-success">Nova consulta</a>
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
		<a href="consultarPedidoPorEstado.jsp" class="btn btn-success">Nova consulta</a>
		<br/>
		
		<div class="row">
	        <div class="panel panel-primary filterable">
	            <div class="panel-heading">
	                <h3 class="panel-title">Pedidos no estado <b><%=estadoPedido%></b></h3>
	                <div class="pull-right">
	                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span> Filtro</button>
	                </div>
	            </div>
	            <table class="table">
	                <thead>
	                    <tr class="filters">
	                        <th><input type="text" class="form-control" placeholder="Cliente" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Produtos" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Valor total" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Dt Pedido" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Dt Encerramento" disabled /></th>
	                        <th></th>
	                    </tr>
	                </thead>
	                <% for( Object pedidoObj : pedidos ) {%>
	                <% Pedido pedido = (Pedido) pedidoObj; %>
				      <tr>
				      	<td><%=pedido.getCliente().getNomeCliente()%></td>
				      	<td><table>
				      	<tr>
				      	<th>Vinho</th>
				      	<th>Qtd</th>
				      	</tr>
				      	<%
				      	List<ItemPedido> itens = pedido.getItensPedido();
				      	for(ItemPedido item : itens){
				      	%>	
				      	<tr>
				      		<td><%=item.getVinho().getNomeVinho() %></td>
				      		<td><%=item.getQtdVinho() %></td>
			      		</tr>
				      	<%
				      	}
				      	%>
				      	</table></td>
				      	<td><%=Utils.strDoubleParaMoeda(pedido.getValorTotal())%></td>
				      	<td><%=Utils.dateParaStr(pedido.getDtPedido())%></td>
				      	<td><%=Utils.dateParaStr(pedido.getDtEncerramento())%></td>
				      	<td>
				      	<%
				      		if( pedido.getEstadoAtualDoPedido().equals("Aberto")) {
				      	%>
				      			<a href="EncerraPedido.do?idPedido=<%=pedido.getIdPedido()%>" class="btn btn-warning">Encerrar</a>
				      	<%
				      		} else {
				      			out.println("<i>Encerrado</i>");
				      		}
				      	%></td>  	
				      	<td></td>
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
