<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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
		List<Vinho> vinhos = VinhoManager.consultarTodosVinhos();
		int qtdVinhosVendidos = VinhoManager.consultarQtdeTotalVinhosVendidos();	
		// Lista de clientes
		List<Cliente> clientes = ClienteManager.consultarTodosClientes();
		// Lista de pedidos
		List<Pedido> pedidos = PedidoManager.consultarPedidoPorEstado("todos");

	%>	
	
	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>
	
	<div class="container">
		
		<div class="row">
			<div class="col-sm-3">
	    		<div class="panel panel-default">
		    		<div class="panel-heading"><h1><%=vinhos.size()%>   <span class="glyphicon glyphicon-barcode"></span></h1>Rótulos à Venda</div>        
	    		</div>
	    	</div>
	    	<div class="col-sm-3">
	    		<div class="panel panel-default">
		    		<div class="panel-heading"><h1><%=clientes.size()%>   <span class="glyphicon glyphicon-user"></span></h1>Clientes Cadastrados</div>        
	    		</div>
	    	</div>
	    	<div class="col-sm-3">
	    		<div class="panel panel-default">
		    		<div class="panel-heading"><h1><%=pedidos.size()%>   </h1>Pedidos Registrados</div>        
	    		</div>
	    	</div>
	    	<div class="col-sm-3">
	    		<div class="panel panel-default">
		    		<div class="panel-heading"><h1><%=qtdVinhosVendidos %>   </h1>Vinhos Vendidos</div>        
	    		</div>
	    	</div>
	    </div>
	    	
		<div class="row">
	    	
	    	<div class="col-sm-6">
	    		<div class="panel panel-default">
		    		<div class="panel-heading"><strong>Status Pedidos - Aberto x Em andamento</strong></div>
		    		<div class="panel-body"><canvas id="statusPedidos1"></canvas></div>             
	    		</div>
	    	</div>
	    	
	    	<div class="col-sm-6">
	    		<div class="panel panel-default">
		    		<div class="panel-heading"><strong>Status Pedidos - Encerrado x Cancelado</strong></div>
		    		<div class="panel-body"><canvas id="statusPedidos2"></canvas></div>             
	    		</div>
	    	</div>	
		</div>
		<div class="row">		
			<div class="col-sm-12">
				<div class="panel panel-default">
		    		<div class="panel-heading"><strong>Estoque atual</strong></div>
		    		<div class="panel-body"><canvas id="estoque"></canvas></div>               
	    		</div>
	    	</div>
	    </div>	  
	
	</div>
	<!-- fim .container da pagina -->
	
	<script>
		
	// Status Pedidos 1	 
	var ctx = document.getElementById("statusPedidos1").getContext('2d');
	var statusPedidos1 = new Chart(ctx, {
	    type: 'doughnut',
	    data: {
	        labels: ["Aberto","Em andamento"],
	        datasets: [{	            
	            data: ["<%=PedidoManager.consultarPedidoPorEstado("Aberto").size()%>","<%=PedidoManager.consultarPedidoPorEstado("Em andamento").size()%>"],
	            backgroundColor: ["#f39c12","#7ccc63"],
	            borderColor: ["#f39c12","#7ccc63"],
	            borderWidth: 1
	        }]
	    },
	    options: {
	    	legend: {
	    		display: true,
	    		position: 'bottom'
	    	},
	    }
	});
	
	// Status Pedidos 2	 
	var ctx = document.getElementById("statusPedidos2").getContext('2d');
	var statusPedidos2 = new Chart(ctx, {
	    type: 'doughnut',
	    data: {
	        labels: ["Encerrado","Cancelado"],
	        datasets: [{	            
	            data: ["<%=PedidoManager.consultarPedidoPorEstado("Encerrado").size()%>","<%=PedidoManager.consultarPedidoPorEstado("Cancelado").size()%>"],
	            backgroundColor: ["#7ccc63","#e74c3c"],
	            borderColor: ["#7ccc63","#e74c3c"],
	            borderWidth: 1
	        }]
	    },
	    options: {
	    	legend: {
	    		display: true,
	    		position: 'bottom'
	    	},
	    }
	});
	
	// Estoque	
	<%    	
	String labelsEstoque = "";
	String dataEstoque = "";
	String backgroundColorEstoque = "";
	String borderColorEstoque = "";
	for( Vinho vinho : vinhos ) {
		labelsEstoque += "\"" + vinho.getNomeVinho() + "\",";
		dataEstoque += vinho.getQtdEstoque() + ", ";
		backgroundColorEstoque += "'rgba(255, 99, 132, 0.2)',";
		borderColorEstoque += "'rgba(255,99,132,1)',";
	}
	%>
	var ctx = document.getElementById("estoque").getContext('2d');
	var estoque = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: [<%=labelsEstoque%>],
	        datasets: [{
	            
	            data: [<%=dataEstoque%>],
	            backgroundColor: [<%=backgroundColorEstoque%>],
	            borderColor: [<%=borderColorEstoque%>],
	            borderWidth: 1
	        }]
	    },
	    options: {
	    	legend: {
	    		display: false
	    	},
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero:true
	                }
	            }]
	        }
	    }
	});
	
	</script>
</body>
</html>
