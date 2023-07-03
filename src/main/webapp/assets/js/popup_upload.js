/**
 * Created by joker on 8/14/15.
 */

(function( $ ) {
    function PopupUpload() {
        var jqThis = undefined;
        var parent = undefined;
        var opts;
        var modal;

        this.init = function init(comp, opts_param) {
            jqThis = comp;
            opts = opts_param;

            if(_.isUndefined(opts.container)) {
                parent = jqThis.parent();
            } else {
                parent = $("#" + opts.container);
            }
        };

        this.createModal = function createModal() {
        	var html =	'';
        	html += '<form method="post" enctype="multipart/form-data" action="' + opts.url + '">';
        	html += '<div class="modal fade" id="' + opts.modal_id + '" tabindex="-1" role="dialog" aria-labelledby="' + opts.modal_id + '">';
        	html += '<div class="modal-dialog" role="document">';
        	html += '<div class="modal-content">';
        	html += '<div class="modal-header text-left">';
        	html += '<h4 class="modal-title">' + opts.modal_title + '</h4>';
        	html += '</div>';
        	html += '<div class="modal-body text-left">';
        	html += 'Browse File <span class="required">*</span>';
        	html += '<input type="file" class="form-control required" name="files" placeholder="Upload Files" />';
        	html += '</div>';
        	html += '<div class="modal-footer text-right">';
        	html += '<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i>Cancel</button>';
        	html += '<button type="submit" id="saveLine" class="btn btn-custom-primary"><i class="fa fa-upload"></i> Upload</button>';
        	html += '</div>';
        	html += '</div>';
        	html += '</div>';
        	html += '</div>';
        	html += '</form>';

            parent.append(html);

            modal = $('#' + opts.modal_id);
        };
    }

    $.fn.popup_upload = function( options, callback ) {
        var opts = $.extend( {}, $.fn.popup_upload.defaults, options );
        var jqThis = $(this);

        var object = new PopupUpload();
        object.init(jqThis, opts);

        if(jqThis.is("a")){
            object.createModal();

            jqThis.click(function() {
                $('#' + opts.modal_id).modal('show');
            });
        }

        return this;
    };

}( jQuery ));

$.fn.popup_upload.defaults = {
    container: undefined,
    modal_id: "modalPKUpload",
    modal_title: "Upload",
    url: "",
    ajax_data: []
};
