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
		
		double precoPrimeiroVinho = 0;
	
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
										int i = 0;
				        				
										for( Vinho vinho : vinhos ) { 
									
											selectVinhos += "<option value=" + vinho.getIdVinho() + " price=" + vinho.getPrecoVinho() + ">" + vinho.getNomeVinho() + "</option>";
											
											if (i == 0){
												precoPrimeiroVinho = vinho.getPrecoVinho();
											}
									 		
											i++;
										} 
										
										out.print(selectVinhos);
									%>
					  			</select>
					  		</div>
					  		<div class="col-md-2">	
					  			<label for="qtdVinho">Quantidade</label>		
								<input type="number" min="1" class="form-control" id="qtdVinho" name="qtdVinho" value="1" required/>
							</div>	
							<div class="col-md-2">	
					  			<label for="precoVinho">Preço</label>		
								<input type="number" class="form-control" id="precoVinho" name="precoVinho" readonly="readonly" value="<% out.print(precoPrimeiroVinho); %>"/>
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
									Valor total do pedido: R$ <% out.print(precoPrimeiroVinho); %>
								</div>
							</div>
						</div>				
					</div>
					
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
	var valorTotalPedido = parseFloat(<% out.print(precoPrimeiroVinho); %>);
	
	$(document).on('click', '.addWine', function() {
		valorTotalPedido += parseFloat(<% out.print(precoPrimeiroVinho); %>);
		
		  $("#vinhos").append("<div class='row' id='"+ idVinho +"'>"+							
			"<div class='col-md-5'>"+	
			"<br><select class='form-control' id='vinho' name='vinho' required>"+
			<% out.print('"'+ selectVinhos + '"'); %> + 
			"</select></div><div class='col-md-2'>"+		
			"<br><input type='number' min='1' class='form-control' id='qtdVinho' value='1' name='qtdVinho' required/>"+
			"</div><div class='col-md-2'><label for='qtdVinho'></label>"+		
			"<input type='number' min='0' class='form-control' id='precoVinho' name='precoVinho' value='<% out.print(precoPrimeiroVinho); %>' readonly='readonly'/>"+
			"</div><div class='col-md-2'>"+
  			"<span><i class='fa fa-plus fa-2x addWine' aria-hidden='true' onclick='addWineLine()'></i></span>&nbsp;&nbsp;&nbsp;"+
  			"<span><i class='fa fa-trash fa-2x removeWine' aria-hidden='true' onclick='removeWineLine("+idVinho+",this)'></i></span>&nbsp;"+
			"</div></div>");
		  
		  $("#txtTotal").html("Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));
		  
		  idVinho++;
		});
	
	function removeWineLine(id,objeto){
		valorTotalPedido -= parseFloat($(objeto).parent().parent().parent().find('#precoVinho').val());
		$("#"+id).empty();
		
		valorTotalPedido = 0.00;
		 $.each( $(".form-control"), function() {
				if( $(this).attr('readonly')=="readonly" ){
					valorTotalPedido += parseFloat($(this).val());
					}
			});	
		 $("#txtTotal").html(" Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));	
		
	}	
	
	$(document).on('change', '#vinho', function() {
		valorTotalPedido -= parseFloat($(this).parent().parent().find("#precoVinho").val()).toFixed(2);
		valorTotalPedido += parseFloat($(this).find('option:selected').attr("price")).toFixed(2);
		$(this).parent().parent().find("#qtdVinho").val("1");
		$(this).parent().parent().find("#precoVinho").val(parseFloat($(this).find('option:selected').attr("price")).toFixed(2));
		valorTotalPedido += parseFloat($(this).find('option:selected').attr("price")).toFixed(2);
		
		valorTotalPedido = 0.00;
		 $.each( $(".form-control"), function() {
				if( $(this).attr('readonly')=="readonly" ){
					valorTotalPedido += parseFloat($(this).val());
					}
			});	
		 $("#txtTotal").html(" Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));	
		
	});
	
	$(document).on('change', '#qtdVinho', function() {
		
		var precoVinho = $(this).parent().siblings().find("#vinho option:selected").attr("price");
		
		if ($(this).val() < 1){
			$(this).val("1");
			$(this).parent().siblings().next().next().find("#precoVinho").val($(this).parent().siblings().find("#vinho option:selected").attr("price"));
		}
		
		valorTotalPedido = 0.00;
		
		$(this).parent().siblings().next().next().find("#precoVinho").val(($(this).val() * precoVinho).toFixed(2));
		
		 $.each( $(".form-control"), function() {
			if( $(this).attr('readonly')=="readonly" ){
				valorTotalPedido += parseFloat($(this).val());
				}
		});	
				
		$("#txtTotal").html(" Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));
	});
	</script>
</body>
</html>
