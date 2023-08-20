
   $(document).ready(function() {
    $('#loader').hide();
    $("#submit").on("click", function() {
    	var company_name = $("#company_name").val();
        var file = $("#image").val(); 
        var com_address1 = $("#com_address1").val();
         var com_address2 = $("#com_address2").val();
        var com_offAddress = $("#com_offAddress").val();
        var number = $("#number").val();
        var mail_id = $("#mail_id").val();
        var ac = $("#ac").val();
        var ac_holder = $("#ac_holder").val();
        var bank = $("#bank").val();
        var ifsc = $("#ifsc").val();

        var form = $("#form").serialize();
    	var data = new FormData($("#form")[0]);
    	data.append('company_name', company_name);
    	data.append('com_address1', com_address1);
    	data.append('com_address2', com_address2);
    	data.append('com_offAddress', com_offAddress);
        data.append('number', number);
        data.append('mail_id', mail_id);
        data.append('ac', ac);
        data.append('ac_holder', ac_holder);
        data.append('bank', bank);
        data.append('ifsc', ifsc);
    	//alert(data);
        $('#loader').show();
        if (company_name === "" || file === "" || com_address1 === "" || number === "" || ac === "" || bank === "" || ifsc === "" ) {
        	$("#submit").prop("disabled", false);
            $('#loader').hide();
            $("#company_name").css("border-color", "red");
            $("#image").css("border-color", "red");
            $("#com_address1").css("border-color", "red");
            $("#number").css("border-color", "red");
            $("#ac").css("border-color", "red");
            $("#bank").css("border-color", "red");
             $("#ifsc").css("border-color", "red");
            $("#error_company").html("Please fill the required field.");
            $("#error_com_address1").html("Please fill the required field.");
            $("#error_number").html("Please fill the required field.");
            $("#error_file1").html("Please fill the required field.");
            $("#error_ac").html("Please fill the required field.");
            $("#error_bank").html("Please fill the required field.");
            $("#error_ifsc").html("Please fill the required field.");
        } else {
            $("#company_name").css("border-color", "");
            $("#image").css("border-color", "red");
            $("#com_address1").css("border-color", "");
            $("#number").css("border-color", "");
            $("#ac").css("border-color", "");
            $("#bank").css("border-color", "");
            $("#ifsc").css("border-color", "");
             $('#error_company').css('opacity', 0);
                        $('#error_com_address1').css('opacity', 0);
                        $('#error_number').css('opacity', 0);
                        $('#error_file1').css('opacity', 0);
                        $('#error_ac').css('opacity', 0);
                        $('#error_bank').css('opacity', 0);
                        $('#error_ifsc').css('opacity', 0);

                    $.ajax({
                        type: 'POST',
                        enctype: 'multipart/form-data',
                        data: data,
                        url: "/company/saveCompanyDetails",
                        processData: false,
                        contentType: false,
                        cache: false,
                        success: function(data, statusText, xhr) {
                        console.log(xhr.status);
                        if(xhr.status == "200") {
                        	$('#loader').hide(); 
                        	$("#form")[0].reset();
                        	$('#success').css('display','block');
                            $("#error").text("");
                            $("#success").html("Company data save Successfully.");
                            $('#success').delay(2000).fadeOut('slow');
                         }	   
                        },
                        error: function(e) {
                        	$('#loader').hide();
                        	$('#error').css('display','block');
                            $("#error").html(e.responseText);
                            $('#error').delay(5000).fadeOut('slow');
                                                   }
                    });
        }
            });
        });
