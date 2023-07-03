var OnlyAcceptNumber = function(e){
    var key_codes = [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 0, 8];

    if (!($.inArray(e.which, key_codes) >= 0)) {
         e.preventDefault();
    }
};