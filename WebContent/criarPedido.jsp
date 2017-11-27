<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<%
		// TODO: Caso tenha ocorrido erro na criação do pedido, recuperar os valores para que o formulário já venha preenchido
	
		List<Vinho> vinhos = VinhoManager.consultarTodosVinhos();
		List<Cliente> clientes = ClienteManager.consultarTodosClientes();
		
		String selectVinhos = "";
		String selectClientes = "";
	
		if( vinhos == null || vinhos.isEmpty() ) {
	%>	
		<div class="container"> 
			<div class="alert alert-danger">		
				<strong>Não há vinhos cadastrados.</strong>
			</div>
		</div>		
	<%
		} 
		else if( clientes == null || clientes.isEmpty() ) {
			%>	
				<div class="container"> 
					<div class="alert alert-danger">		
						<strong>Não há clientes cadastrados.</strong>
					</div>
				</div>		
			<%
		}
		else {
	%>

	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>

		
	 <!-- CADASTRO DE NOVO PEDIDO -->
	<div class="container">
	
		<!-- Botões -->	
		
		<form action="CriaPedido.do" method="post">
			<fieldset>
				<legend>Novo Pedido</legend>
				
				<!-- TODO: substituir o campo nomeCliente por uma combobox que traga os nomes dos clientes cadastrados -->					
					<div class="row">
						<div class="col-md-5">	
			        		<label for="nomeCliente">Cliente</label>					
							<select class="form-control" id="cliente" name="cliente" required>
								<% 
								
									for( Cliente cliente : clientes ) { 
								
										selectClientes += "<option value=" + cliente.getIdCliente() + " title='CPF: " + cliente.getCpf() + "'>" + cliente.getNomeCliente() + "</option>";
								 
									} 
									
									out.print(selectClientes);
								%>
					  		</select>
					  	</div>						
					</div>
					<br>			
					<div class="form-group">
						<div id="vinhos">
						<div class="row">							
							<div class="col-md-5">
				        		<label for="vinho">Vinho</label>		
								<select class="form-control" id="vinho" name="vinho" required>
									<% 
									
										for( Vinho vinho : vinhos ) { 
									
											selectVinhos += "<option value=" + vinho.getIdVinho() + " price=" + vinho.getPrecoVinho() + ">" + vinho.getNomeVinho() + "</option>";
									 
										} 
										
										out.print(selectVinhos);
									%>
					  			</select>
					  		</div>
					  		<div class="col-md-2">	
					  			<label for="qtdVinho">Quantidade</label>		
								<input type="number" min="0" class="form-control" id="qtdVinho" name="qtdVinho" required/>
							</div>	
							<div class="col-md-2">	
					  			<label for="precoVinho">Preço</label>		
								<input type="number" min="0" class="form-control" id="precoVinho" name="precoVinho" readonly="readonly" value="0.00"/>
							</div>	
							<div class="col-md-2">
					  			<span><i class="fa fa-plus fa-2x addWine" aria-hidden="true"></i></span>&nbsp;&nbsp;&nbsp;
					  			
							</div>	
						</div>
						</div>	
						<br><br>																	
						<hr style="border-top-color:#000000"> 
						<div id="total">
							<div class="row">
								<div class="col-md-12" id="txtTotal">
									Valor total do pedido: R$ 0,00
								</div>
							</div>
						</div>				
					</div>
					
					
					<!-- TODO: permitir a seleção de mais de um vinho por pedido, cada um com sua quantidade -->
				
							
			</fieldset>
			<br>
			<button type="submit" class="btn btn-primary">Criar Pedido</button>
			<br><br>
		</form>		
	</div>
	<!-- fim .container da pagina -->
	<%
		}
	%>
	<script type="text/javascript">
	
	var idVinho = 0;
	var valorTotalPedido = 0;
	
	$(document).on('click', '.addWine', function() {
		  $("#vinhos").append("<div class='row' id='"+ idVinho +"'>"+							
			"<div class='col-md-5'>"+	
			"<br><select class='form-control' id='vinho' name='vinho' required>"+
			<% out.print('"'+ selectVinhos + '"'); %> + 
			"</select></div><div class='col-md-2'>"+		
			"<br><input type='number' min='0' class='form-control' id='qtdVinho' name='qtdVinho' required/>"+
			"</div><div class='col-md-2'><label for='qtdVinho'></label>"+		
			"<input type='number' min='0' class='form-control' id='precoVinho' name='precoVinho' readonly='readonly'/>"+
			"</div><div class='col-md-2'>"+
  			"<span><i class='fa fa-plus fa-2x addWine' aria-hidden='true' onclick='addWineLine()'></i></span>&nbsp;&nbsp;&nbsp;"+
  			"<span><i class='fa fa-trash fa-2x removeWine' aria-hidden='true' onclick='removeWineLine("+idVinho+",this)'></i></span>&nbsp;"+
			"</div></div>");
		  
		  idVinho++;
		});
	
	function removeWineLine(id,objeto){
		valorTotalPedido -= $(objeto).parent().parent().parent().find('#precoVinho').val();
		$("#"+id).empty();
		$("#txtTotal").html("Valor total do pedido: R$ " + valorTotalPedido.toFixed(2));
	}
	
	$(document).on('change', '#qtdVinho', function() {
		var precoVinho = $(this).parent().siblings().find("#vinho option:selected").attr("price"); 
		$(this).parent().siblings().next().next().find("#precoVinho").val(($(this).val() * precoVinho).toFixed(2));		
	});	
	
	$(document).on('change', '#vinho', function() {
		$(this).parent().parent().find("#qtdVinho").val("");
		$(this).parent().parent().find("#precoVinho").val("");
	});
	
	$(document).on('change', '#qtdVinho', function() {
		valorTotalPedido = 0;
		$.each( $(".form-control"), function() {
			if( $(this).attr('readonly')=="readonly" ){
				valorTotalPedido += parseFloat($(this).val());
				} 			
		});
		$("#txtTotal").html("Valor total do pedido: R$ " + valorTotalPedido.toFixed(2));
	});
	
	
	
	
	</script>
</body>
</html>
