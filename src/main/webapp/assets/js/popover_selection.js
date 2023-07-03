var tmp = $.fn.popover.Constructor.prototype.show;
$.fn.popover.Constructor.prototype.show = function() {
    tmp.call(this); if (this.options.callback) {
        this.options.callback();
    }
};

(function( $ ) {
    function PopoverSelection() {
        var jqThis = undefined;
        var parent = undefined;
        var opts;
        var callback;
        var dataTables;
        var tableId;
        var txtSearchId;
        var btnSelectId;
        var btnSearchId;
        var btnClearId;
        var btnCloseId;
        var htmlForm;
        var aoColumns = [];
        var prefix_id = "klik_popup_";
        
        this.init = function init(comp, opts_param, callbackParam) {
            jqThis 		= comp;
            opts 		= opts_param;
            callback 	= callbackParam;
            parent 		= jqThis.parent();
            tableId 	= prefix_id + "datatable_" + opts.modal_id;
            txtSearchId	= prefix_id + "txtSearchPopup_" + opts.modal_id;
            btnSelectId	= prefix_id + "btnSelectPopup_" + opts.modal_id;
            btnSearchId = prefix_id + "btnSearchPopup_" + opts.modal_id;
            btnClearId 	= prefix_id + "btnClearPopup_" + opts.modal_id;
            btnCloseId 	= prefix_id + "btnClosePopup_" + opts.modal_id;
        };

        this.getDataTables = function getDataTables() {
            return dataTables;
        };

        this.getHtmlForm = function getHtmlForm() {
            return htmlForm;
        };

        this.createForm = function createForm() {
            htmlForm = '';
            htmlForm += '<div class="popover-content" id="' + opts.modal_id + '">';
            htmlForm += '<div class="header">';
            htmlForm += '<span class="col-md-11 title">';
            
            if(opts.modal_title == "Institution List" || opts.modal_title == "Branch List" || opts.modal_title == "Unit List" || opts.modal_title == "Center List" || opts.modal_title == "Structure List")
            	htmlForm += '<i class="fa fa-sitemap"></i> ';
            else if(opts.modal_title == "User List")
            	htmlForm += '<i class="fa fa-user"></i> ';
            
            htmlForm += opts.modal_title + '</h3>';
            htmlForm += '</span>';
            htmlForm += '<div class="controls text-right">';
            htmlForm += '<button id="' + btnCloseId + '" class="btn btn-danger btn-circle" type="button"><i class="fa fa-close"></i></button>';
            htmlForm += '</div>';
            htmlForm += '</div>';
            htmlForm += '<div class="body">';
            if(!_.isUndefined(opts.fieldsSearch)) {
            	htmlForm += '<table>';
            	htmlForm += '<tr>';
            	htmlForm += '<td width="60%">';
            	htmlForm += '<input id="' + txtSearchId + '" name="' + opts.fieldsSearch.name + '" type="text" class="form-control" placeholder="' + opts.fieldsSearch.label +'"/>';
            	htmlForm += '</td>';
            	htmlForm += '<td width="20%" class="text-right">';
                htmlForm += '<button id="' + btnSearchId + '" class="btn btn-custom-primary"><i class="fa fa-search"></i> Search</button>';
                htmlForm += '<td width="20%" class="text-right">';
                htmlForm += '<button id="' + btnClearId + '" class="btn btn-default"><i class="fa fa-refresh"></i> Reset</button>';
            	htmlForm += '</td>';
            	htmlForm += '</tr>';
            	htmlForm += '</table>';
            }
            htmlForm += '<table id="' + tableId + '" class="table table-striped table-hover table-bordered datatable">';
            htmlForm += '<thead>';
            htmlForm += '<tr>';
            htmlForm += '<th>#</th>';
            for(i=0; i<opts.fieldsTable.length; i++) {
                htmlForm += '<th>' + opts.fieldsTable[i].fieldLabel + '</th>';
            }
            htmlForm += '</tr>';
            htmlForm += '</thead>';
            htmlForm += '</table>';
            htmlForm += '</div>';
            htmlForm += '</div">';
        };

        this.createDataTables = function createDataTables() {
            aoColumns = [];
            if ( $.fn.DataTable.isDataTable( "#" + tableId ) ) {
                $('#' + tableId).dataTable().fnDestroy();
            }

            aoColumns.push({ "mDataProp": fnBlank, "bSortable": false, "sWidth": "5%" });
            for(var i=0; i<opts.fieldsTable.length; i++) {
            	aoColumns.push({ "mDataProp": opts.fieldsTable[i].fieldId});
            }

            dataTables = $("#" + tableId).dataTable({
                "sAjaxSource"	: opts.ajax_url,
                "sServerMethod"	: "GET",
                "aoColumns"		: aoColumns,
                "iDisplayLength": 5,
                "fnServerData"	: function (sSource, aoData, fnCallback) {

                    if(_.isFunction(opts.func_ajax_url)){
                        sSource = opts.func_ajax_url();
                    }

                    for(var i=0; i<opts.ajax_data.length; i++) {
                    	var value;
                        if(_.isFunction(opts.ajax_data[i].fieldValue)) {
                            value = opts.ajax_data[i].fieldValue();
                        } else {
                            value = opts.ajax_data[i].fieldValue;
                        }
                        aoData.push({ "name": opts.ajax_data[i].fieldVar, "value": value });
                    }

                    if(!_.isUndefined(opts.fieldsSearch)) {
                    	value = $("#" + txtSearchId).val();
                        aoData.push({ "name": opts.fieldsSearch.name, "value": value });
                    }
  
                    jQuery.ajax({
                        "dataType"	: 'json',
                        "type"		: "GET",
                        "url"		: sSource,
                        "data"		: aoData,
                        "success"	: fnCallback
                    }).done(function (msg) {
                        var hiddendVal = parseInt($("#" + opts.hiddenIdContainer).val());
                        if(!_.isUndefined(hiddendVal)){
                            if(!_.isUndefined(msg)){
                                var data = msg.aaData;
                                for(i=0; i< data.length; i++) {
                                    if(data[i].id == hiddendVal){
                                        jqThis.val(data[i][opts.fieldsValueLabel]);
                                        break;
                                    }
                                }
                            }
                        }

                    }).fail(function (jqXHR, textStatus) {
                        console.log(jqXHR.responseText);
                        console.log("AJAX Failed : " + textStatus);
                    });
                },
                "aoColumnDefs" : [
                    {
                    	className: "text-center",
                    	"mRender": function(data, type, row){
                            return '<div class="btn-group btn-group-xs"><button type="button" class="btn btn-warning btn-check ' + btnSelectId + '"' +
                                ' pop-value-id="' + row.id + '" pop-value-name="' + row[opts.fieldsValueLabel] + '"><i class="fa fa-check"></i></button></div>';
                        },
                        "aTargets": [0]
                    }
                ]
            });

            if(!_.isUndefined(dataTables)){
                $(document).on("click", '.' + btnSelectId, function () {
                    var value_id 	= $(this).attr("pop-value-id");
                    var value 		= $(this).attr("pop-value-name");

                    jqThis.val(value);
                    $("#" + opts.hiddenIdContainer).val(value_id);

                    callback(value_id);

                    jqThis.popover('hide');
                });

                $(document).on("click", '#' + btnSearchId, function (e) {
                    e.preventDefault();
                    dataTables.fnClearTable(this);
                    dataTables.fnDraw();
                });

                $(document).on("click", '#' + btnClearId, function (e) {
                    e.preventDefault();
                    jqThis.val("");
                    $("#" + opts.hiddenIdContainer).val("");
                    
                    callback("");

                    jqThis.popover('hide');
                });
                
                $(document).on("click", '#' + btnCloseId, function () {
                    jqThis.popover('hide');
                });
            }
        };
    }

    $.fn.popover_selection = function( options, callback ) {
        var opts 	= $.extend( {}, $.fn.popover_selection.defaults, options );
        var jqThis 	= $(this);
        var object 	= new PopoverSelection();
        object.init(jqThis, opts, callback);

        jqThis.focus(function() {
            object.createForm();

            jqThis.popover({
                html: true,
                container: opts.containerPopover,
                placement: "bottom",
                content: function () {
                    return object.getHtmlForm();
                },
                callback: function() {
                    object.createDataTables();
                    object.getDataTables().fnDraw();
                }
            });

        });

        return this;
    };

}( jQuery ));

$.fn.popover_selection.defaults = {
    containerPopover	: 'body',
    container			: undefined,
    modal_id			: "modalPKSelect",
    modal_title			: "Selection",
    ajax_url			: "",
    func_ajax_url		: undefined,
    ajax_data			: [],
    fieldsTable			: [],
    fieldsSearch		: [],
    hiddenIdContainer	: "",
    fieldsValueLabel	: ""
};

var popoverFunction = {
		
    guid: function guid() {
    	
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
        }
        
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
    },
    
    getStucturePopup : function getStucturePopup(args) {
		var param = {
			modal_id 			: this.guid(),
			ajax_url 			: args.url + "/getStructure",
			func_ajax_url 		: args.func_ajax_url,
			ajax_data 			: args.ajax_data,
			modal_title 		: args.modalTitle,
			hiddenIdContainer 	: args.hiddenId,
			fieldsValueLabel	: args.varValue,
			fieldsTable 	: [
				            	   {
										fieldId 	: "code",
										fieldLabel 	: "Code"
									}, 
									{
										fieldId 	: "description",
										fieldLabel 	: "Description"
									}
								],
			fieldsSearch 	: {
									label 	: "Description",
									name 	: "description"
					     	   }
		};

		$("." + args.classMain).popover_selection(param, function(event) {
			if (!_.isUndefined(args.callback)) {
				args.callback(event);
			}
		});
	},
	getStuctureMappingPopup : function getStuctureMappingPopup(args) {
		var param = {
			modal_id 			: this.guid(),
			ajax_url 			: args.url + "/getStructure",
			func_ajax_url 		: args.func_ajax_url,
			ajax_data 			: args.ajax_data,
			modal_title 		: args.modalTitle,
			hiddenIdContainer 	: args.hiddenId,
			fieldsValueLabel	: args.varValue,
			fieldsTable 	: [
				            	   {
										fieldId 	: "unit.code",
										fieldLabel 	: "Code"
									}, 
									{
										fieldId 	: "unit.description",
										fieldLabel 	: "Description"
									}
								],
			fieldsSearch 	: {
									label 	: "Description",
									name 	: "description"
					     	   }
		};

		$("." + args.classMain).popover_selection(param, function(event) {
			if (!_.isUndefined(args.callback)) {
				args.callback(event);
			}
		});
	},
	getUserPopup : function getUserPopup(args) {
		var param = {
			modal_id 			: this.guid(),
			ajax_url 			: args.url + "/getUser",
			func_ajax_url 		: args.func_ajax_url,
			ajax_data 			: args.ajax_data,
			modal_title 		: args.modalTitle,
			hiddenIdContainer 	: args.hiddenId,
			fieldsValueLabel 	: args.varValue,
			fieldsTable 		: [
			            		   		{
											fieldId 	: "login",
											fieldLabel 	: "Login Name"
										}, 
										{
											fieldId 	: "name",
											fieldLabel 	: "Name"
										}
									],
			fieldsSearch 		: {
										label 	: "User Name",
										name	: "userName"
									} 
			
		};

		$("." + args.classMain).popover_selection(param, function(event) {
			if (!_.isUndefined(args.callback)) {
				args.callback(event);
			}
		});
	}
};
