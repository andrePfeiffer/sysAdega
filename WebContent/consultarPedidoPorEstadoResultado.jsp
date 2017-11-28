<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
	
	<style>
		.modal .modal-header {
		  border-bottom: none;
		  position: relative;
		}
		.modal .modal-header .btn {
		  position: absolute;
		  top: 0;
		  right: 0;
		  margin-top: 0;
		  border-top-left-radius: 0;
		  border-bottom-right-radius: 0;
		}
		.modal .modal-footer {
		  border-top: none;
		  padding: 0;
		}
		.modal .modal-footer .btn-group > .btn:first-child {
		  border-bottom-left-radius: 0;
		}
		.modal .modal-footer .btn-group > .btn:last-child {
		  border-top-right-radius: 0;
		}
	</style>
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
	                        <th style="width: 150px;"><input type="text" class="form-control" placeholder="Cliente" disabled /></th>
	                        <th style="width: 200px;"><input type="text" class="form-control" placeholder="Vinhos | Qtd" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Valor total" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Dt Pedido" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Dt Encerramento" disabled /></th>
	                        <th><input type="text" class="form-control" placeholder="Estado" disabled /></th>
	                        <th style="width: 200px;"><input type="text" class="form-control" placeholder="Ações" disabled /></th>
	                    </tr>
	                </thead>
	                <% for( Object pedidoObj : pedidos ) {%>
	                <% Pedido pedido = (Pedido) pedidoObj; %>
				      <tr>
				      	<td><%=pedido.getCliente().getNomeCliente()%></td>
				      	<td><table>
				      	<%
				      	List<ItemPedido> itens = pedido.getItensPedido();
				      	for(ItemPedido item : itens){
				      	%>	
				      	<tr>
				      		<%=item.getVinho().getNomeVinho() %> | <%=item.getQtdVinho() %><br>
			      		</tr>
				      	<%
				      	}
				      	%>
				      	</table></td>
				      	<td><%=Utils.strDoubleParaMoeda(pedido.getValorTotal())%></td>
				      	<td><%=Utils.dateParaStr(pedido.getDtPedido())%></td>
				      	<td><%=Utils.dateParaStr(pedido.getDtEncerramento())%></td>
				      	<td><%=pedido.getEstadoPedido()%></td>
				      	<td>
				      	<%
				      		if( pedido.getEstadoPedido().equals("Aberto")) {
				      	%>
				      	<a data-toggle="modal" href="#myModal" local="PreparaPedido.do?idPedido=<%=pedido.getIdPedido()%>" class="btn btn-primary"><i class='fa fa-rocket fa-lg' aria-hidden='true' title='Preparar'></i></a>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-power-off fa-lg' aria-hidden='true' title='Encerrar'></i></a>
				      	<a data-toggle="modal" href="#myModal" local="CancelaPedido.do?idPedido=<%=pedido.getIdPedido()%>" class="btn btn-danger"><i class='fa fa-ban fa-lg' aria-hidden='true' title='Cancelar'></i></a>

				      	<%
				      		} else if( pedido.getEstadoPedido().equals("Em andamento")) {

				      	%>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-rocket fa-lg' aria-hidden='true' title='Preparar'></i></a>
				      	<a data-toggle="modal" href="#myModal" local="EncerraPedido.do?idPedido=<%=pedido.getIdPedido()%>" class="btn btn-warning"><i class='fa fa-power-off fa-lg' aria-hidden='true' title='Encerrar'></i></a>
				      	<a data-toggle="modal" href="#myModal" local="CancelaPedido.do?idPedido=<%=pedido.getIdPedido()%>" class="btn btn-danger"><i class='fa fa-ban fa-lg' aria-hidden='true' title='Cancelar'></i></a>
				      	<%
				      		} else if( pedido.getEstadoPedido().equals("Encerrado")) {
				      	%>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-rocket fa-lg' aria-hidden='true' title='Preparar'></i></a>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-power-off fa-lg' aria-hidden='true' title='Encerrar'></i></a>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-ban fa-lg' aria-hidden='true' title='Cancelar'></i></a>
				      	<%
				      		} else if ( pedido.getEstadoPedido().equals("Cancelado")) {				      	
				      	%>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-rocket fa-lg' aria-hidden='true' title='Preparar'></i></a>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-power-off fa-lg' aria-hidden='true' title='Encerrar'></i></a>
				      	<a data-toggle="modal" href="#" class="btn disabled"><i class='fa fa-ban fa-lg' aria-hidden='true' title='Cancelar'></i></a>
				      	<%
				      		}
				      	%>				      			
				      	</td> 
				      </tr>
				    <% } %>
	            </table>
	        </div>
    	</div>  

	<div id="myModal" class="modal fade in">
        <div class="modal-dialog">
            <div class="modal-content">
 
                <div class="modal-header">
                    <a class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span></a>
                    <center><h3 class="modal-title">Confirmar Ação</h3></center>
                </div>
                <div class="modal-body">
                    <center>
	                    <h4>Deseja realmente prosseguir?</h4>
	                    <br>
	                    <p>Esta ação não poderá ser desfeita, clique em cancelar para retornar ou em confirmar para prosseguir com a ação.</p>
	                </center>
                </div>
                <div class="modal-footer">
                    <div class="btn-group">
                        <button class="btn btn-danger" data-dismiss="modal" id="cancelar"><span class="glyphicon glyphicon-remove"></span> Cancelar</button>
                        <button class="btn btn-primary" id="confirmar"><span class="glyphicon glyphicon-check"></span> Confirmar</button>
                    </div>
                </div>
 
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dalog -->
    </div><!-- /.modal -->
	</div>
	<!-- fim .container da pagina -->
	<%
		}
	%>
</body>
<script language="JavaScript">
	var urlAtual = "";
	$(document).on('click', 'a', function() {
		urlAtual = $(this).attr("local");
	});
	$(document).on('click', '#confirmar', function() {
		window.location.href = urlAtual;
	});
	$(document).on('click', '#cancelar', function() {
		urlAtual = "";
	});
 </script>
</html>
