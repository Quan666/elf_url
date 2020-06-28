

//debug
var AJAXDEBUG=true
//返回 -1均为参数错误
//操作函数参数为json转换的对象，不能为空



//GET API 实现 传入完整get地址 , 带参操作函数 fnc
function AjaxGetLoadXML(url,fnc){
    var xmlhttp;
    if(url.length<=0){
        console.log("错误：URL 为空！！！")
        return -1;
    }
    if (window.XMLHttpRequest){
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // IE6, IE5 浏览器执行代码
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        AjaxDebug(xmlhttp)
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            //调用操作函数
            if (typeof xmlhttp.responseText !== 'undefined') {
                var json =JSON.parse(xmlhttp.responseText);
                fnc(json);
            }else {
                console.error("POST "+url+" 结果为空！")
            }
        }
    }
    xmlhttp.open("GET",url,true);
    xmlhttp.setRequestHeader("Content-type","application/json");
    xmlhttp.setRequestHeader("charset","utf-8");
    xmlhttp.send();
}



//POST API 实现 传入POST地址 ，对象 ，带参操作函数 fnc
function AjaxPostLoadXML(url,objct,fnc){
    var xmlhttp;
    if(url.length<=0||JSON.stringify(objct) === '{}'){
        console.log("错误：POST 参数错误！！！")
        return -1;
    }
    if (window.XMLHttpRequest){
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // IE6, IE5 浏览器执行代码
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        AjaxDebug(xmlhttp)
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            if (typeof xmlhttp.responseText !== 'undefined') {
                var json =JSON.parse(xmlhttp.responseText);
                fnc(json);
            }else {
                console.error("POST "+url+" 结果为空！")
            }
        }
    }
    xmlhttp.open("POST",url,true);
    xmlhttp.setRequestHeader("Content-type","application/json");
    xmlhttp.setRequestHeader("charset","utf-8");
    xmlhttp.send(JSON.stringify(objct));
}

//PUT API 实现 传入PUT地址 ，对象 ，带参操作函数 fnc
function AjaxPutLoadXML(url,objct,fnc){
    var xmlhttp;
    if(url.length<=0||JSON.stringify(objct) === '{}'){
        console.log("错误：POST 参数错误！！！")
        return -1;
    }
    if (window.XMLHttpRequest){
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // IE6, IE5 浏览器执行代码
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        AjaxDebug(xmlhttp)
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            if (typeof xmlhttp.responseText !== 'undefined') {
                var json =JSON.parse(xmlhttp.responseText);
                fnc(json);
            }else {
                console.error("PUT "+url+" 结果为空！")
            }
        }
    }
    xmlhttp.open("PUT",url,true);
    xmlhttp.setRequestHeader("Content-type","application/json");
    xmlhttp.setRequestHeader("charset","utf-8");
    xmlhttp.send(JSON.stringify(objct));
}

//DELETE API 实现 传入完整 DELETE 地址 , 带参操作函数 fnc
function AjaxDeleteLoadXML(url,fnc){
    var xmlhttp;
    if(url.length<=0){
        console.log("错误：URL 为空！！！")
        return -1;
    }
    if (window.XMLHttpRequest){
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // IE6, IE5 浏览器执行代码
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        AjaxDebug(xmlhttp)
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            //调用操作函数
            if (typeof xmlhttp.responseText !== 'undefined') {
                var json =JSON.parse(xmlhttp.responseText);
                fnc(json);
            }else {
                console.error("DELETE "+url+" 结果为空！")
            }
        }
    }
    xmlhttp.open("DELETE",url,true);
    xmlhttp.setRequestHeader("Content-type","application/json");
    xmlhttp.setRequestHeader("charset","utf-8");
    xmlhttp.send();
}



//DEBUG
function AjaxDebug(xmlhttp) {
    if (!AJAXDEBUG){
        return;
    }
    switch (xmlhttp.readyState) {
        case 0:console.log("0: (未初始化)还没有调用send()方法。");break;
        case 1:console.log("1: (载入)已经调用send()方法，正在派发请求。");break;
        case 2:console.log("2: (载入完成)send()已经执行完成，已经接收到全部的响应内容。");break;
        case 3:console.log("3: (交互)正在解析响应内容。");break;
        case 4:console.log("4: (完成)响应内容已经解析完成，用户可以调用。");
               console.log(xmlhttp.responseText);
               break;
    }
}