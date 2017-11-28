<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>

		
	 <!-- CONSULTA DE PEDIDO -->
	<div class="container">
	
		<!-- Botões -->	
		
		<form action="ConsultaPedidoPorEstado.do" method="post">
			<fieldset>
				<legend>Consulta de Pedidos</legend>				
					
					<div class="form-group">
		        		<label for="estadoPedido">Estado</label>		
						<select class="form-control" id="estadoPedido" name="estadoPedido" required>
							<option value="Aberto">Abertos</option>
							<option value="Em andamento">Em andamento</option>
							<option value="Encerrado">Encerrados</option>
							<option value="Cancelado">Cancelados</option>
							<option value="todos">Todos</option>
						</select>			  				
					</div>
						
			</fieldset>
			<button type="submit" class="btn btn-primary">Consultar</button>
		</form>		
	</div>
	<!-- fim .container da pagina -->
</body>
</html>
