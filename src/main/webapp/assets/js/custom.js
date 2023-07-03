/**
 * Created by usmanbinyahya on 9/13/15.
 */

var util = {
    generateJSON: function generateJSON(trLines, data) {
        var lines = [];

        for(var i=0 ; i < trLines.length; i++) {
            var trThis = $(trLines[i]);

            var list = trThis.find(".linesClass");

            var dataLines = {};
            for (var j = 0; j < list.length; j++) {
                var element = $(list[j]);

                try {

                    for(var k=0; k < data.length; k++) {
                        if (element.attr("name-data") == data[k].name) {
                            if(data[k].type == "string") {
                                dataLines[element.attr("name-data")] = element.val();
                            } else if(data[k].type == "int") {
                                dataLines[element.attr("name-data")] = parseInt(element.val());
                            } else if(data[k].type == "float") {
                                dataLines[element.attr("name-data")] = parseFloat(element.val());
                            }
                        }
                    }
                } catch (err) {
                    console.log(err);
                    dataLines[element.attr("name-data")] = element.val();
                }
            }

            lines.push(dataLines);
        }

        return lines;
    },
    getJSONFromLines: function getJSONFromLines(data) {
        var trLines = $(document).find('.trLines');

        return this.generateJSON(trLines, data);
    },
    getJSONFromLinesById: function getJSONFromLines(dataTableId, data) {
        var trLines = $("#" + dataTableId).find('.trLines');

        return this.generateJSON(trLines, data);
    },
    guid: function guid() {
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
            s4() + '-' + s4() + s4() + s4();
    }
};