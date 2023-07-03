/**
 * Created by usman on 7/5/15.
 */

(function( $ ) {
    function PopupSelection() {
        var jqThis = undefined;
        var parent = undefined;
        var opts;
        var dataTables;
        var modal;
        var tableName;
        var btnClassName;
        var btnSearchId;
        var btnClearId;
        var prefix_id = "klik_popup_";
        var callback;
        var dataSelectedByUser;

        var CONST_SEARCH_STRING = "string";
        var CONST_SEARCH_CHECK = "check";

        this.init = function init(comp, opts_param, callbackParam) {
            jqThis = comp;
            opts = opts_param;
            callback = callbackParam;

            if(_.isUndefined(opts.container)) {
                parent = jqThis.parent();
            } else {
                parent = $("#" + opts.container);
            }

            tableName = prefix_id + opts.modal_id + "_datatable";
            btnClassName = prefix_id + "btnSelectPopup_" + opts.modal_id;

            btnSearchId = prefix_id + "btnSearchPopup_" + opts.modal_id;
            btnClearId = prefix_id + "btnClearPopup_" + opts.modal_id;
        };

        this.getDataTables = function getDataTables() {
            return dataTables;
        };

        this.createModal = function createModal() {
            var aoColumns = [];
            aoColumns.push({ "mDataProp": fnBlank, "bSortable": false });

            var html = '<div class="modal fade" id="' + opts.modal_id + '" tabindex="-1" role="dialog" aria-labelledby="' + opts.modal_id + '">';
            html += '<div class="modal-dialog modal-lg" role="document">';
            html += '<div class="modal-content">';
            html += '<div class="modal-header">';
            html += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
            html += '<h4 class="modal-title">' + opts.modal_title + '</h4>';
            html += '</div>';
            html += '<div class="modal-body">';

            if(!_.isUndefined(opts.fieldsSearch)) {
                html += '<div class="widget-content">';
                for(var i=0; i< opts.fieldsSearch.length; i++){
                    var obj = opts.fieldsSearch[i];
                    html += '<div class="controls form-inline">';
                    html += '<label class="col-md-2 control-label">' + obj.label +'</label>';
                    html += '</div>';

                    var type_input = "text";

                    if(obj.type == CONST_SEARCH_STRING){
                        type_input = "text";
                    } else if(obj.type == CONST_SEARCH_CHECK) {
                        type_input = "checkbox";
                    }

                    html += '<div class="col-md-10">';
                    html += '<input id="' + prefix_id + obj.id + '" name="' + obj.name + '" type="' + type_input + '" class="form-control" />';
                    html += '</div>';
                }

                html += '<div class="controls form-inline">';
                html += '<button id="' + btnSearchId + '" class="btn"><i class="icon-zoom-in"></i> Search</button>';
                html += '&nbsp;';
                html += '<button type="button" id="' + btnClearId + '" class="btn"><i class="icon-zoom-in"></i> Clear</button>';
                html += '</div>';
                html += '</div>';
            }

            html += '<table id="' + tableName + '" class="table table-striped table-bordered table-hover table-full-width">';
            html += '<thead>';
            html += '<tr>';
            html += '<th></th>';
            for(i=0; i<opts.fieldsTable.length; i++) {
                html += '<th>' + opts.fieldsTable[i].fieldLabel + '</th>';

                aoColumns.push({ "mDataProp": opts.fieldsTable[i].fieldId  });
            }
            html += '</tr>';
            html += '</thead>';
            html += '</table>';
            html += '</div>';
            html += '<div class="modal-footer">';
            html += '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';

            parent.append(html);

            dataTables = $("#" + tableName).dataTable({
                "sAjaxSource": opts.ajax_url,
                "sServerMethod": "GET",
                "aoColumns": aoColumns,
                "iDisplayLength": 5,
                "fnServerData": function (sSource, aoData, fnCallback) {
                    console.log(opts.ajax_data);

                    if(_.isFunction(opts.func_ajax_url)){
                        sSource = opts.func_ajax_url();
                    }

                    var value;
                    for(var i=0; i<opts.ajax_data.length; i++) {
                        if(_.isFunction(opts.ajax_data[i].fieldValue)) {
                            value = opts.ajax_data[i].fieldValue();
                        } else {
                            value = opts.ajax_data[i].fieldValue;
                        }
                        aoData.push({ "name": opts.ajax_data[i].fieldVar, "value": value });
                    }

                    // data search
                    for(i=0; i< opts.fieldsSearch.length; i++){
                        var obj = opts.fieldsSearch[i];

                        value = $("#" + prefix_id + obj.id).val();
                        aoData.push({ "name": obj.name, "value": value });
                    }

                    console.log(aoData);

                    jQuery.ajax({
                        "dataType": 'json',
                        "type": "GET",
                        "url": sSource,
                        "data": aoData,
                        "success": fnCallback
                    }).done(function (msg) {
                        console.log("AJAX Done : ");
                        console.log(msg);
                        console.log(sSource);

                        dataSelectedByUser = msg;

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
                        "mRender": function(data, type, row){
                            return '<button type="button" class="btn btn-primary ' + btnClassName + '"' +
                                ' pop-value-id="' + row.id + '" pop-value-name="' + row[opts.fieldsValueLabel] + '">Choose</button>';
                        },
                        "aTargets": [0]
                    }
                ]
            });

            modal = $('#' + opts.modal_id);

            $(document).on("click", '.' + btnClassName, function () {
                var value_id = $(this).attr("pop-value-id");
                var value = $(this).attr("pop-value-name");

                jqThis.val(value);
                $("#" + opts.hiddenIdContainer).val(value_id);

                callback(dataSelectedByUser);

                modal.modal('hide');
            });

            $(document).on("click", '#' + btnSearchId, function () {
                console.log(dataTables);
                dataTables.fnClearTable(this);
                dataTables.fnDraw();
            });

            $(document).on("click", '#' + btnClearId, function () {
                jqThis.val("");
                $("#" + opts.hiddenIdContainer).val("");

                modal.modal('hide');
            });

            //modal = $('#' + opts.modal_id);
        };
    }

    $.fn.popup_selection = function( options, callback ) {
        var opts = $.extend( {}, $.fn.popup_selection.defaults, options );
        var jqThis = $(this);

        var object = new PopupSelection();
        object.init(jqThis, opts, callback);

        if(jqThis.is("input")){
            if(jqThis.attr("type") == "text"){
                object.createModal();

                jqThis.focus(function() {
                    object.getDataTables().fnDraw();
                    $('#' + opts.modal_id).modal('show');
                });
            }
        }

        return this;
    };

}( jQuery ));

$.fn.popup_selection.defaults = {
    container: undefined,
    modal_id: "modalPKSelect",
    modal_title: "Selection",
    ajax_url: "",
    func_ajax_url: undefined,
    ajax_data: [],
    fieldsTable: [],
    fieldsSearch: [],
    hiddenIdContainer: "",
    fieldsValueLabel: ""
};

var popupFunction = {
    guid: function guid() {
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
            s4() + '-' + s4() + s4() + s4();
    },
    getBranchPopup: function getBranchPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getBranch",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "Branch ID"
                },
                {
                    fieldId: "branchName",
                    fieldLabel: "Branch Name"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchBranchName" + this.uuid,
                    label: "Branch Name",
                    name: "branchName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.uuid,
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getOutletPopup: function getOutletPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getOutlet",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "Outlet ID"
                },
                {
                    fieldId: "outletName",
                    fieldLabel: "Outlet Name"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchOutletName" + this.uuid,
                    label: "Outlet Name",
                    name: "outletName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.uuid,
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getCompanyPopup: function getCompanyPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getCompany",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "Company ID"
                },
                {
                    fieldId: "companyName",
                    fieldLabel: "Company Name"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchCompanyName" + this.guid(),
                    label: "Company Name",
                    name: "companyName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getUserOrgNamePopup: function getUserOrgNamePopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getUserOrgName",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "Organization ID"
                },
                {
                    fieldId: "name",
                    fieldLabel: "Organization Name"
                },
                {
                    fieldId: "status",
                    fieldLabel: "Status"
                },
                {
                    fieldId: "type",
                    fieldLabel: "Type"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchOrgName" + this.guid(),
                    label: "Org Name",
                    name: "orgName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getImeiPopup: function getImeiPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getImei",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "IMEI ID"
                },
                {
                    fieldId: "imei",
                    fieldLabel: "IMEI"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getAgentPopup: function getAgentPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getAgent",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "Agent ID"
                },
                {
                    fieldId: "agentName",
                    fieldLabel: "Agent Name"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchAgentName" + this.guid(),
                    label: "Agent Name",
                    name: "agentName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getUomPopup: function getUomPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getUom",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "UOM ID"
                },
                {
                    fieldId: "uomCode",
                    fieldLabel: "UOM Code"
                },
                {
                    fieldId: "uomName",
                    fieldLabel: "UOM Name"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchUomName" + this.guid(),
                    label: "UOM Name",
                    name: "uomName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getWarehousePopup: function getWarehousePopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getWarehouse",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "warehouseId",
                    fieldLabel: "Warehouse ID"
                },
                {
                    fieldId: "warehouseName",
                    fieldLabel: "Warehouse Name"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchWarehouseName" + this.guid(),
                    label: "Warehouse Name",
                    name: "warehouseName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getOrganizationPopup: function getOrganizationPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getUserOrgName",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "Organization ID"
                },
                {
                    fieldId: "name",
                    fieldLabel: "Organization Name"
                },
                {
                    fieldId: "status",
                    fieldLabel: "Status"
                },
                {
                    fieldId: "type",
                    fieldLabel: "Type"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchOrgName" + this.guid(),
                    label: "Org Name",
                    name: "orgName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    },
    getProductPopup: function getProductPopup(args) {
        $("." + args.classMain).popup_selection({
            ajax_url: args.url + "getProduct",
            func_ajax_url: args.func_ajax_url,
            ajax_data: args.ajax_data,
            fieldsTable: [
                {
                    fieldId: "id",
                    fieldLabel: "Product ID"
                },
                {
                    fieldId: "productName",
                    fieldLabel: "Product Name"
                },
                {
                    fieldId: "status",
                    fieldLabel: "Status"
                }
            ],
            fieldsSearch: [
                {
                    id: "searchProductName" + this.guid(),
                    label: "Product Name",
                    name: "productName",
                    type: "string"
                }
            ],
            modal_id: args.modalId + this.guid(),
            hiddenIdContainer: args.hiddenId,
            fieldsValueLabel: args.varValue
        }, function (event){
            if(!_.isUndefined(args.callback)) {
                args.callback(event);
            }
        });
    }
};