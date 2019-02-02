/* GET 파라미터 Encode */
function encodeURIParam(data) {
	var count = 0;
	var param = "";
	var keys = Object.keys(data);
	for(var i = 0; i < keys.length; i++){
		var key = keys[i];
		if((data[key] || data[key] === 0) && data[key] != undefined && data[key].length != 0){
			count++;
			if(count == 1){ param += "?"}
			else{ param += "&"}
			
			var value = data[key];
			if(Array.isArray(data[key])){
				value = data[key][0];
				for(var j = 1; j < data[key].length; j++){
					value += "," + data[key][j].trim();
				}
			}
			param += key + "=" + value;
		}
	}
	
	return encodeURI(param);
}  



Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};
 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

$.fn.datebox.defaults.formatter = function(date){
	 var y = date.getFullYear();
     var m = date.getMonth() + 1;
     var d = date.getDate();
     
		// date format dd/mm/yyyy
     var r = y + "-" +(m < 10 ? ('0' + m) : m) + "-" + (d < 10 ? ('0' + d) : d);
     return r;
}
$.fn.datebox.defaults.parser = function(s){
    if (!s) {
        return new Date();
    }
	// date format dd/mm/yyyy
    var ss = (s.split('-'));
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    var d = parseInt(ss[2], 10);
    
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
        return new Date(y, m - 1, d);
    } else {
        return new Date();
    }
}

