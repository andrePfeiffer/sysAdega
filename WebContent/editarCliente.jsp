<%@include file="_import.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="_header.jsp"%>
</head>
<body>
	<%@include file="_cabecalho.jsp"%>
	
	<%
		// Cliente a editar
		String idClienteStr = request.getParameter("idCliente"); // se tiver vindo da lista de clientes
		if( idClienteStr == null ) { // se tiver vindo do servlet de edi��o
			idClienteStr = (String)request.getAttribute("idCliente");
		}
		Cliente clienteEd = ClienteManager.consultarClientePorId(Integer.parseInt(idClienteStr));
			
		// Caso tenha ocorrido erro na edi��o, recuperar os valores preenchidos

		String nomeCliente = (String)request.getAttribute("nomeCliente"); // campo obrigat�rio		
		String cpf = (String)request.getAttribute("cpf"); // campo obrigat�rio		
		String cep = (String)request.getAttribute("cep"); // campo obrigat�rio		
		String endereco = (String)request.getAttribute("endereco"); // campo obrigat�rio
		String numero = (String)request.getAttribute("numero"); // campo obrigat�rio
		String complemento = (String)request.getAttribute("complemento"); // campo obrigat�rio
		String bairro = (String)request.getAttribute("bairro"); // campo obrigat�rio
		String cidade = (String)request.getAttribute("cidade"); // campo obrigat�rio
		String estado = (String)request.getAttribute("estado"); // campo obrigat�rio
		String pais = (String)request.getAttribute("pais"); // campo obrigat�rio
	%>
	<!--  CONTAINER DE MENSAGENS -->
	<%@include file="_containerMensagens.jsp"%>

		
	 <!-- EDI��O DE PRODUTO -->
	<div class="container">		
		<form action="EditaCliente.do" method="post">
			
			<fieldset>
				<legend>Edi��o de Cliente</legend>
				<input type="hidden" name="idCliente" value="<%=clienteEd.getIdCliente()%>"/>
				
				<div class="row">
					<div class="col-md-7">	
		        		<label for="nomeCliente">Nome</label>					
						<input type="text" class="form-control" id="nomeCliente" name="nomeCliente" maxlength="150" required value="<%if(clienteEd.getNomeCliente()!=null){out.println(clienteEd.getNomeCliente());}%>"/>						
					</div>
					<div class="col-md-5">
						<label for="cpf">Cpf</label>					
						<input type="text" class="form-control" id="cpf" name="cpf" maxlength="150" onblur="validaCPF(this)" required value="<%if(clienteEd.getCpf()!=null){out.println(clienteEd.getCpf());}%>"/>
					</div>
				</div>
				<br><br>
				<div class="row">
					<div class="col-md-2">
						<label for="cep">Cep</label>					
						<input type="text" class="form-control" id="cep" name="cep" maxlength="150" required value="<%if(clienteEd.getCep()!=null){out.println(clienteEd.getCep());}%>"/>
					</div>
					<div class="col-md-4">
						<label for="endereco">Endere�o</label>					
						<input type="text" class="form-control" id="endereco" name="endereco" maxlength="150" required value="<%if(clienteEd.getEndereco()!=null){out.println(clienteEd.getEndereco());}%>"/>
					</div>
					<div class="col-md-1">
						<label for="numero">N�mero</label>					
						<input type="text" class="form-control" id="numero" name="numero" maxlength="150" required value="<%if(clienteEd.getNumero()!=null){out.println(clienteEd.getNumero());}%>"/>
					</div>
					<div class="col-md-2">
						<label for="complemento">Complemento</label>					
						<input type="text" class="form-control" id="complemento" name="complemento" maxlength="150" required value="<%if(clienteEd.getComplemento()!=null){out.println(clienteEd.getComplemento());}%>"/>
					</div>
					<div class="col-md-3">
						<label for="bairro">Bairro</label>					
						<input type="text" class="form-control" id="bairro" name="bairro" maxlength="150" required value="<%if(clienteEd.getBairro()!=null){out.println(clienteEd.getBairro());}%>"/>
					</div>
				</div>
				<br><br>
				<div class="row">
					<div class="col-md-6">
						<label for="cidade">Cidade</label>					
						<input type="text" class="form-control" id="cidade" name="cidade" maxlength="150" required value="<%if(clienteEd.getCidade()!=null){out.println(clienteEd.getCidade());}%>"/>
					</div>
					<div class="col-md-1">
						<label for="estado">Estado</label>					
						<input type="text" class="form-control" id="estado" name="estado" maxlength="150" required value="<%if(clienteEd.getEstado()!=null){out.println(clienteEd.getEstado());}%>"/>
					</div>
					<div class="col-md-5">
						<label for="pais">Pa�s</label>					
						<input type="text" class="form-control" id="pais" name="pais" maxlength="150" required value="<%if(clienteEd.getPais()!=null){out.println(clienteEd.getPais());}%>"/>
					</div>
				</div>
				<br><br>					
			</fieldset>
			<button type="submit" class="btn btn-primary">Editar Cliente</button>
		</form>		
	</div>
	<!-- fim .container da pagina -->
</body>
<script type="javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {
        
    //Quando o campo cep perde o foco.
    $("#cep").blur(function() {

        //Nova vari�vel "cep" somente com d�gitos.
        var cep = $(this).val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {

            //Express�o regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if(validacep.test(cep)) {

                //Consulta o webservice viacep.com.br/
                $.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#endereco").val(dados.logradouro);
                        $("#bairro").val(dados.bairro);
                        $("#cidade").val(dados.localidade);
                        $("#estado").val(dados.uf);
                        $("#pais").val("Brasil");
                    } //end if.
                    else {
                        //CEP pesquisado n�o foi encontrado.
                        limpa_formul�rio_cep();
                        alert("CEP n�o encontrado.");
                    }
                });
            } //end if.
            else {
                //cep � inv�lido.
                limpa_formul�rio_cep();
                alert("Formato de CEP inv�lido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formul�rio.
            limpa_formul�rio_cep();
        }
    });
});

$( "#cpf" ).click(function() {
	$('#cpf').mask('999.999.999-99', {reverse: true});
	});
	
$("#cep").mask("99.999-999");

	
function coreCPFValidador(cpf) {  
    cpf = cpf.replace(/[^\d]+/g,'');    
    if(cpf == '') return false; 
        
    if (cpf.length != 11 || 
        cpf == "00000000000" || 
        cpf == "11111111111" || 
        cpf == "22222222222" || 
        cpf == "33333333333" || 
        cpf == "44444444444" || 
        cpf == "55555555555" || 
        cpf == "66666666666" || 
        cpf == "77777777777" || 
        cpf == "88888888888" || 
        cpf == "99999999999")
            return false;       
     
    add = 0;    
    for (i=0; i < 9; i ++)       
        add += parseInt(cpf.charAt(i)) * (10 - i);  
        rev = 11 - (add % 11);  
        if (rev == 10 || rev == 11)     
            rev = 0;    
        if (rev != parseInt(cpf.charAt(9)))     
            return false;       
     
    add = 0;    
    for (i = 0; i < 10; i ++)        
        add += parseInt(cpf.charAt(i)) * (11 - i);  
    rev = 11 - (add % 11);  
    if (rev == 10 || rev == 11) 
        rev = 0;    
    if (rev != parseInt(cpf.charAt(10)))
        return false;       
    return true;   
}
	
function validaCPF(value){
	if (!coreCPFValidador(value.value)){
		alert("Por favor, digite um CPF v�lido.");
		$('.cpf').html("");
	}
}

</script>
</html>
