/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery(document).ready(function($) {

		$("#search-form").submit(function(event) {
			enableButtons(false);
			event.preventDefault();
			createCompany();
		});
                
                $("#edit-form").submit(function(event) {
			enableButtons(false);
			event.preventDefault();
			editCompany();
		});
                
                $("#del-one-form").submit(function() {
                        enableButtons(false);
			event.preventDefault();
			deleteOne();                    
                });
                
                $("#del-tree-form").submit(function() {
                        enableButtons(false);
			event.preventDefault();
			deleteTree();                    
                });

	});

    function createCompany() {

		var editData = {};
		editData["companyName"] = $("#companyName").val();
		editData["earnings"] = $("#earnings").val();
                editData["parentName"] = $("#parentName").val();

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${home}create",
			data : JSON.stringify(editData),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				display(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
//				alert(e);
			},
			done : function(e) {
				console.log("DONE");
				enableButtons(true);
			}
		});

	}

    function editCompany() {
        var editData = {};
	editData["companyName"] = $("#editCompanyName").val();
	editData["newName"] = $("#newName").val();
        editData["earnings"] = $("#newEarnings").val();
        editData["parentName"] = $("#newParentName").val();
        
        $.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}edit",
		data : JSON.stringify(editData),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
		console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
			enableButtons(true);
		}
	    });

	}
        
    function deleteOne (){
    var editData = {};
	editData["companyName"] = $("#delCompanyName").val();
	
        $.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}delete",
		data : JSON.stringify(editData),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
		console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
			enableButtons(true);
		}
	    });
    }
    
    function deleteTree (){
    var editData = {};
	editData["companyName"] = $("#delCompanyName").val();
	
        $.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}delete/tree",
		data : JSON.stringify(editData),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
		console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
			enableButtons(true);
		}
	    });
    }
    
    	function display(data) {
		var json = "<h4>Enabled trees</h4><pre>"
                            +data.msg
//				+ JSON.stringify(data, null, 4) 
                                + "</pre>";
                        
                var errorcode = "<h3>"+data.code+"</h3>";
		$('#feedback').html(json);
                $("#errorview").html(errorcode);
	}
    
	function enableButtons(flag) {
		$("#createbtn").prop("disabled", flag);
                $("#edit").prop("disabled", flag);
                $("#delete").prop("disabled", flag);
                $("#delTree").prop("disabled", flag);
	}

