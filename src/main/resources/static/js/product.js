
   $(document).ready(function() {
    $('#loader').hide();
    $("#submit").on("click", function() {
        var file = $("#image").val(); 
        var price = $("#price").val();
        var description = $("#description").val();
        var customer_name = $("#customer_name").val();
        var package_charge = $("#package_charge").val();
        var qty = $("#qty").val();
         var order_date = $("#order_date").val();
        var form = $("#form").serialize();
    	var data = new FormData($("#form")[0]);
    	data.append('price', price);
    	data.append('description', description);
    	data.append('customer_name', customer_name);
        data.append('package_charge', package_charge);
        data.append('qty', qty);
        data.append('order_date', order_date);
    	//alert(data);
        $('#loader').show();
        if ( file === "" || price === "" || description === "" || customer_name === "" || qty === "" || order_date === "" ) {
        	$("#submit").prop("disabled", false);
            $('#loader').hide();
            $("#image").css("border-color", "red");
            $("#price").css("border-color", "red");
            $("#description").css("border-color", "red");
            $("#customer_name").css("border-color", "red");
            $("#qty").css("border-color", "red");
             $("#order_date").css("border-color", "red");
            $("#error_customer").html("Please fill the required field.");
            $("#error_qty").html("Please fill the required field.");
            $("#error_file").html("Please fill the required field.");
            $("#error_price").html("Please fill the required field.");
            $("#error_description").html("Please fill the required field.");
            $("#error_orderDate").html("Please fill the required field.");
        } else {
            $("#customer_name").css("border-color", "");
            $("#qty").css("border-color", "");
            $("#image").css("border-color", "");
            $("#price").css("border-color", "");
            $("#description").css("border-color", "");
            $("#order_date").css("border-color", "");
            $('#error_customer').css('opacity', 0);
            $('#error_qty').css('opacity', 0);
            $('#error_file').css('opacity', 0);
            $('#error_price').css('opacity', 0);
            $('#error_description').css('opacity', 0);
            $('#error_orderDate').css('opacity', 0);
                    $.ajax({
                        type: 'POST',
                        enctype: 'multipart/form-data',
                        data: data,
                        url: "/image/saveImageDetails", 
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
                            $("#success").html("Product Inserted Succsessfully.");
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
