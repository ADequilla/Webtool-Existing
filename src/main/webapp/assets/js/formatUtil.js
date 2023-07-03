// ------------------------------------------------------------------	
// To use addSeparatorsNF, you need to pass it the following arguments: 
// nStr: The number to be formatted, as a string or number. No validation is done, so don't input a formatted number. If inD is something other than a period, then nStr must be passed in as a string. 
// inD: The decimal character for the input, such as '.' for the number 100.2 
// outD: The decimal character for the output, such as ',' for the number 100,2 
// sep: The separator character for the output, such as ',' for the number 1,000.2
// Author: Keith Jenci
// WWW: http://www.mredkj.com/javascript/nfbasic.html	
// ------------------------------------------------------------------
function addSeparatorsNF(nStr, inD, outD, sep) {
	nStr += '';
	var dpos = nStr.indexOf(inD);
	var nStrEnd = '';
	if (dpos != -1) {
		nStrEnd = outD + nStr.substring(dpos + 1, nStr.length);
		nStr = nStr.substring(0, dpos);
	}
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(nStr)) {
		nStr = nStr.replace(rgx, '$1' + sep + '$2');
	}
	return nStr + nStrEnd;
}

// format a number into indonesian format 
function formatNumberAsPrice(str) {
	return addSeparatorsNF(str, ".", ",", ".");
}

//get price in indonesian format, convert into a number
function formatPriceAsNumber(str) {
	str += '';
	var rgx = /\./;
	while (rgx.test(str)) {
		str = str.replace(rgx, '');
	}
	return str + '';
}

//filter input key to number only
function filterKeyToNumberOnly() {
	var key = event.keyCode;
	return (key >= 48 && key <= 57);
}

//filter input key to decimal number only
function filterKeyToDecimalOnly() {
	var key = event.keyCode;
	return ((key >= 48 && key <= 57) || key == 46);
}
