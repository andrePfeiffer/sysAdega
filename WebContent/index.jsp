<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>
	
	<div class="container">

		<div class="panel panel-success">
		    <div class="panel-heading"><strong>Administrador</strong></div>
		    <div class="panel-body">
	    		<a href="gerenciarVinhos.jsp" data-toggle="popover" data-trigger="hover" data-content="Permite o cadastro de novo vinho, consulta, edição e remoção de vinhos.">Gerenciar Vinhos</a>
	    	</div>
	    	<div class="panel-body">
	    		<a href="gerenciarClientes.jsp" data-toggle="popover" data-trigger="hover" data-content="Permite o cadastro de novo cliente, consulta, edição e remoção de clientes.">Gerenciar Clientes</a>
	    	</div>
	    </div>
	    
	    <div class="panel panel-info">
		    <div class="panel-heading"><strong>Pedidos</strong></div>
	    	<div class="panel-body">
	    		<a href="criarPedido.jsp" data-toggle="popover" data-trigger="hover" data-content="Permite a criação e processamento de um pedido.">Criar pedido</a>
	    	</div>
	    	<div class="panel-body">
	    		<a href="consultarPedidoPorEstado.jsp" data-toggle="popover" data-trigger="hover" data-content="Exibe os pedidos por status.">Consultar pedidos por status</a>
	    	</div>
	    </div>
	    
	    <div class="panel panel-info">
		    <div class="panel-heading"><strong>Geral</strong></div>
	    	<div class="panel-body">
	    		<a href="painelGerencial.jsp" data-toggle="popover" data-trigger="hover" data-content="Exibe principais indicadores do negócio.">Painel Gerencial <span class="badge badge-secondary">Novo</span></a>
	    	</div>
	    </div>
	    
	    
	</div>
	<!-- fim .container da pagina -->
</body>
</html>
