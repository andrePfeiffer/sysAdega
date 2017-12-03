<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
	
	<script type="text/javascript">								
		var vinhoObj = [];
		var vinhoObjAux = [];
		var idVinho = 1;
	</script>	
	
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	<script type="text/javascript">
	<%
		// Caso tenha ocorrido erro na criação do pedido, recuperar os valores para que o formulário já venha preenchido
		String[] vinhosPrev = (String[])request.getAttribute("vinhos");	
		String[] qtdVinhosPrev = (String[])request.getAttribute("qtdVinhos");
		Integer idClientePrev = (Integer)request.getAttribute("idCliente");
	
		if(vinhosPrev != null){
			
			int i = 1;
		
			for( String vinho : vinhosPrev ) {	
				
				int idVinho = Integer.parseInt(vinho);
				
				if ( idVinho != 0){
				
				Vinho wine = VinhoManager.consultarVinhoPorId(Integer.parseInt(vinho));
				String nomeVinho = wine.getNomeVinho();
				double precoVinho = wine.getPrecoVinho();
				int qtd = Integer.parseInt(qtdVinhosPrev[i]);
				
			%>			
						
				 console.log("<% out.print(nomeVinho); %>");
				 console.log("<% out.print(precoVinho); %>");
				 console.log("<% out.print(qtd); %>");
				 
				 $( document ).ready(function() {
					 
					vinhoObjAux.forEach(function(elem, i) {
					    if (elem['id'] == <% out.print(vinho); %>) {
					        vinhoObjAux.splice(i,1);
					    }
					});
			 
					  $("#vinhos").append("<div class='row' id='"+ <% out.print(i); %> +"'>"+							
								"<div class='col-md-5'>"+	
								"<br><select class='form-control' id='vinho" + idVinho + "' name='vinho' required>"+
								"<option value='<% out.print(vinho); %>'><% out.print(nomeVinho); %></option>"+
								"</select></div><div class='col-md-2'>"+		
								"<br><input type='number' min='1' class='form-control' id='qtdVinho' value='" + <% out.print(qtd); %> + "' name='qtdVinho' required/>"+
								"</div><div class='col-md-2'><label for='qtdVinho'></label>"+		
								"<input type='hidden' id='idhidden' value='<% out.print(vinho); %>'/><input type='hidden' value='<% out.print(nomeVinho); %>' id='nomehidden'/>"+
						  		"<input type='number' min='0' class='form-control' value='" + <% out.print(qtd*precoVinho); %> + "' id='precoVinho' name='precoVinho' readonly='readonly'/>"+
								"</div><div class='col-md-2'>"+
					  			"<span><i class='fa fa-plus fa-2x addWine' aria-hidden='true' onclick='addWineLine()'></i></span>&nbsp;&nbsp;&nbsp;"+
					  			"<span><i class='fa fa-trash fa-2x removeWine' aria-hidden='true' onclick='removeWineLine("+<% out.print(i); %>+",this)'></i></span>&nbsp;"+
								"</div></div>");
					 });
				 
				 <%
				}
				
				i++;
				%>
				idVinho++;
				<%
				}
		}
	%>
	</script>
	
	<%
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
										
										String selected = "";
										
										// Caso a página tenha dado erro anteriormente
										if(idClientePrev != null && cliente.getIdCliente() == idClientePrev) {
											selected = "selected";
										}										
										
										selectClientes += "<option value=" + cliente.getIdCliente() + " title='CPF: " + cliente.getCpf() + "' " + selected + ">" + cliente.getNomeCliente() + "</option>";								
								 
									} 
									
									out.print(selectClientes);
								%>
								
					  		</select>
					  	</div>						
					</div>
					<br>			
					<div class="form-group">
						<div id="vinhos">
						<div class="row" id="0">							
							<div class="col-md-5">
				        		<label for="vinho">Vinho</label>		
								<select class="form-control" id="vinho0" name="vinho" required>
									<option value='0' price='0' selected>Selecione ... </option>
									<script type="text/javascript">
									
									<% 
										for( Vinho vinho : vinhos ) {
									%>
										vinhoObj.push({id: <% out.print(vinho.getIdVinho()); %>, preco: <% out.print(vinho.getPrecoVinho()); %>, nome: "<% out.print(vinho.getNomeVinho()); %>"});
										vinhoObjAux.push({id: <% out.print(vinho.getIdVinho()); %>, preco: <% out.print(vinho.getPrecoVinho()); %>, nome: "<% out.print(vinho.getNomeVinho()); %>"});
									<%		
										}
									%>																		
									$.each(vinhoObj, function() {
										
										var option = "<option value=";
										
										    $.each(this, function(index, value) {
										
										        if(index == "id"){
										        	option += value;
										        }else if(index == "preco"){
										        	option += " price="+ value + ">"
										        }else{
										        	option += value + "</option>";
										        }
										
										    });
										    
										    $("select[id^='vinho']").append(option);
										
										});

									
									</script>
					  			</select>
					  		</div>
					  		<div class="col-md-2">	
					  			<label for="qtdVinho">Quantidade</label>		
								<input type="number" min="1" class="form-control" id="qtdVinho" name="qtdVinho" value="0" required/>
							</div>	
							<div class="col-md-2">	
					  			<label for="precoVinho">Preço</label>
					  			<input type="hidden" id="idhidden"/>
					  			<input type="hidden" id="nomehidden"/>	
								<input type="number" class="form-control" id="precoVinho" name="precoVinho" readonly="readonly"/>
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
	
	var valorTotalPedido = parseFloat(<% out.print(precoPrimeiroVinho); %>);
	
	
	$(document).on('click', '.addWine', function() {
		
		valorTotalPedido += parseFloat(<% out.print(precoPrimeiroVinho); %>);
		
		  $("#vinhos").append("<div class='row' id='"+ idVinho +"'>"+							
			"<div class='col-md-5'>"+	
			"<br><select class='form-control' id='vinho" + idVinho + "' name='vinho' required>"+
			"<option value='0' >Selecione ...</option>"+
			"</select></div><div class='col-md-2'>"+		
			"<br><input type='number' min='1' class='form-control' id='qtdVinho' value='0' name='qtdVinho' required/>"+
			"</div><div class='col-md-2'><label for='qtdVinho'></label>"+		
			"<input type='hidden' id='idhidden'/><input type='hidden' id='nomehidden'/>"+
	  		"<input type='number' min='0' class='form-control' id='precoVinho' name='precoVinho' readonly='readonly'/>"+
			"</div><div class='col-md-2'>"+
  			"<span><i class='fa fa-plus fa-2x addWine' aria-hidden='true' onclick='addWineLine()'></i></span>&nbsp;&nbsp;&nbsp;"+
  			"<span><i class='fa fa-trash fa-2x removeWine' aria-hidden='true' onclick='removeWineLine("+idVinho+",this)'></i></span>&nbsp;"+
			"</div></div>");
		  
		  $("#txtTotal").html("Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));
		  
		 
		  $.each(vinhoObjAux, function() {
			  				
				var option = "";
				
				    $.each(this, function(index, value) {
				
				        if(index == "id" && value != 0){
				        	option += "<option value= " + value;
				        }else if(index == "preco" && value != 0){
				        	option += " price="+ value + ">"
				        }else if (index == "nome" && value != ""){
				        	option += value + "</option>";
				        }
				
				    });
				    
				  var vinho = "#vinho" + idVinho;
				    
					    $(vinho).append(option);
				
				});

		idVinho++;
			
		});
	
 	$(document).on('mouseenter', "select[id^='vinho']", function() {
 		
		console.log($(this).parent().parent());
		
		if($(this).find(':selected').val() != '' ){
			var option = "";
			
			console.log($(this).find(':selected').val());
			console.log($(this).find(':selected').html());
			console.log($(this).find(':selected').attr('price'));
			
			option += "<option value=" +  $(this).find(':selected').val() + " price=" + $(this).find(':selected').attr('price') + " selected>" + $(this).find(':selected').html() + "</option>"; 
			
			$.each(vinhoObjAux, function() {
					
					    $.each(this, function(index, value) {
					
					        if(index == "id" && value != ''){
					        	option += "<option value= " + value;
					        }else if(index == "preco" && value != ''){
					        	option += " price="+ value + ">"
					        }else if(index == "nome" && value != "") {
					        	option += value + "</option>";
					        }
					
					    });				    
					  
					});
			
		    $(this).empty();
		    $(this).html(option);
		}
 	});
	
	function removeWineLine(id,objeto){
		
		var vinho = "#vinho" + id;		
		
		if ($(objeto).parent().parent().parent().find(vinho).val() != 0){
		
			vinhoObjAux.push({id: parseInt($(objeto).parent().parent().parent().find(vinho).val()), preco: parseFloat($(objeto).parent().parent().parent().find('#precoVinho').val()), nome: $(objeto).parent().parent().parent().find(vinho + " option:selected").text()});
			
		}
		
		valorTotalPedido -= parseFloat($(objeto).parent().parent().parent().find('#precoVinho').val());
		$("#"+id).empty();
		
		valorTotalPedido = 0.00;
		 $.each( $(".form-control"), function() {
				if( $(this).attr('readonly')=="readonly" ){
					valorTotalPedido += $(this).val() > 0 ? parseFloat($(this).val()) : 0;
					}
			});	
		 $("#txtTotal").html(" Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));	
		
	}	

	
	$(document).on('change', "select[id^='vinho']", function() {
		
		var selectedValue = parseInt($(this).val());
		
		if($(this).find('option:selected').val() != 0 && $(this).find('option:selected').val() != null){
			
			vinhoObjAux.push({id: $(this).parent().parent().find("#idhidden").val(), preco: $(this).parent().parent().find("#precoVinho").val(), nome: $(this).parent().parent().find("#nomehidden").val()});
			
		}
			
			vinhoObjAux.forEach(function(elem, i) {
			    if (elem['id'] == selectedValue) {
			        vinhoObjAux.splice(i,1);
			    }
			});	
		
	
		valorTotalPedido -= parseFloat($(this).parent().parent().find("#precoVinho").val()).toFixed(2);
		valorTotalPedido += parseFloat($(this).find('option:selected').attr("price")).toFixed(2);
		$(this).parent().parent().find("#qtdVinho").val("1");
		$(this).parent().parent().find("#idhidden").val($(this).val());
		$(this).parent().parent().find("#nomehidden").val($(this).find('option:selected').text());
		$(this).parent().parent().find("#precoVinho").val(parseFloat($(this).find('option:selected').attr("price")).toFixed(2));
		valorTotalPedido += parseFloat($(this).find('option:selected').attr("price")).toFixed(2);
		
		valorTotalPedido = 0.00;
		 $.each( $(".form-control"), function() {
				if( $(this).attr('readonly')=="readonly" ){
					valorTotalPedido += $(this).val() > 0 ? parseFloat($(this).val()) : 0;
					}
			});	
		 $("#txtTotal").html(" Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));	
		
	});
	
	$(document).on('change', '#qtdVinho', function() {
		
		var precoVinho = $(this).parent().siblings().find("select[id^='vinho'] option:selected").attr("price");
		
		if ($(this).val() < 1){
			alert("A quantidade de vinhos precisa ser positiva. Alterando para 1 ...");
			$(this).val("1");
			$(this).parent().siblings().next().next().find("#precoVinho").val($(this).parent().siblings().find("#vinho option:selected").attr("price"));
		}
		
		valorTotalPedido = 0.00;
		
		$(this).parent().siblings().next().next().find("#precoVinho").val(($(this).val() * precoVinho).toFixed(2));
		
		 $.each( $(".form-control"), function() {
			if( $(this).attr('readonly')=="readonly" ){
				valorTotalPedido += $(this).val() > 0 ? parseFloat($(this).val()) : 0;
				}
		});	
				
		$("#txtTotal").html(" Valor total do pedido: R$ " + parseFloat(valorTotalPedido).toFixed(2));
	});
	</script>
</body>
</html>
