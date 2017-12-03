<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SisAdega 2.0</title>
<meta name="viewport" content="width=device-width">

<link rel="stylesheet" href="css/custom.css">
<link rel="stylesheet" href="css/bootstrap.css">	
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="js/jquery.maskMoney.min.js" type="text/javascript"></script>
<script src="js/jquery.maskedinput.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js" type="text/javascript"></script>

<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip(); 
});

$(document).ready(function(){
    $('[data-toggle="popover"]').popover();
});


/* Tabela com filtro */
$(document).ready(function(){
    $('.filterable .btn-filter').click(function(){
        var $panel = $(this).parents('.filterable'),
        $filters = $panel.find('.filters input'),
        $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });

    $('.filterable .filters input').keyup(function(e){
        /* Ignore tab key */
        var code = e.keyCode || e.which;
        if (code == '9') return;
        /* Useful DOM data and selectors */
        var $input = $(this),
        inputContent = $input.val().toLowerCase(),
        $panel = $input.parents('.filterable'),
        column = $panel.find('.filters th').index($input.parents('th')),
        $table = $panel.find('.table'),
        $rows = $table.find('tbody tr');
        /* Dirtiest filter function ever ;) */
        var $filteredRows = $rows.filter(function(){
            var value = $(this).find('td').eq(column).text().toLowerCase();
            return value.indexOf(inputContent) === -1;
        });
        /* Clean previous no-result if exist */
        $table.find('tbody .no-result').remove();
        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
        $rows.show();
        $filteredRows.hide();
        /* Prepend no-result row if all rows are filtered */
        if ($filteredRows.length === $rows.length) {
            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
        }
    });
});


/* Máscara de moeda */
$(function($){
	// Aqui fazemos uso do plugin MASKED INPUT
	$("#dtPrevistaAssinatura").mask("99/99/9999");
	$("[id*='dtPrazo']").mask("99/99/9999"); // todos os campos com id contendo dtPrazo
	$("#cpf").mask("999.999.999-99");
	$("#telefone").mask("(99) 9999-9999");
	$("#celular").mask("(99)99999-9999");
	// Aqui fazemos uso do plugin MASK MONEY
	$("#precoVinho").maskMoney({prefix:'R$ ', thousands:'.',decimal:','});
// 	$("#percentMoedaEstrangeira").maskMoney({suffix:'%', thousands:'.',decimal:',',precision:2,allowZero:true,allowNegative:false});
});
 
</script>

