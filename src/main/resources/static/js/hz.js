//返回yyyy-MM-dd格式的日期字符串
function defaultDateFormatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
//读取yyyy-MM-dd格式的日期字符串，返回对应的日期对象
function defaultDateParser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
//返回yyyy-MM-dd HH:mm:ss格式的时间字符串
function defaultDateTimeFormatter(date){
	var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var h = date.getHours();
    var min = date.getMinutes();
    var s = date.getSeconds();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+" "+(h<10?('0'+h):h)+":"+(min<10?('0'+min):min)+":"+(s<10?('0'+s):s);
}

//返回yyyy-MM-dd格式的时间字符串
function simpleDateTimeFormatter(date){
	var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var h = date.getHours();
    var min = date.getMinutes();
    var s = date.getSeconds();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

//序列化表单元素
var sy = $.extend({}, sy);/*定义一个全局变量*/
sy.serializeObject = function (form) { /*将form表单内的元素序列化为对象，扩展Jquery的一个方法*/
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

function handleActionResult(result,handler){
	var json = $.parseJSON(result); 
	if(json.actionErrors && json.actionErrors.length>0){
		var error = "";
		$.each(json.actionErrors,function(index,data){  
			error +=(data+"<br/>");  
        });
		$.messager.alert('操作失败提示',error,'error');
//		$.messager.show({
//			 title: '操作失败提示',
//			 showType:'fade',
//			 style:{
//				 right:'',
//				 bottom:''
//			 },
//			 msg: error});
	}else if(json.fieldErrors && !isEmpty(json.fieldErrors)){
		var error = "";
		$.each(json.fieldErrors,function(index,value) {//index就是field的name,value就是该filed对应的错误列表，这里取第一个 
			error +=(value[0]+"<br/>");  
        });
		$.messager.alert('操作失败提示',error,'error');
	}else{
		handler.onSuccess();
	}
}
//判断对象是否为空(处理Object obj = {}这种情况认为isEmpty=true)  
function isEmpty(obj){
    for(var p in obj){  
        return false;  
    }  
    return true;  
} 

//cookie操作
function setCookie(c_name,value,exdays){
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	document.cookie=c_name + "=" + c_value;
}
function getCookie(c_name){
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++){
	  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
	  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
	  x=x.replace(/^\s+|\s+$/g,"");
	  if (x==c_name){
	    return unescape(y);
	  }
	}
}

//对象内部属性信息--便于调试
function display(obj){
	var result = "";
	for(var p in obj){
		result = result + p +"="+obj[p]+"\n";
	}
	alert(result);
}