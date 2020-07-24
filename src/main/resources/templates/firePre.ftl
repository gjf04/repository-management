<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <title>智慧用电管理首页</title>
    <link href="${urls.getForLookupPath('/css/bootstrap.min.css')}" rel="stylesheet"/>
    <link href="${urls.getForLookupPath('/css/animate.css')}" rel="stylesheet" />
     <script src="${urls.getForLookupPath('/js/jquery.min.js')}"></script>
    
    
    <script src="http://api.map.baidu.com/api?v=3.0&ak=RIHNZxuoyitMmOkFscbf4Iq0bl5HomDM"></script>
    <script src="${urls.getForLookupPath('/js/mapv.js')}"></script>

    <script src="${urls.getForLookupPath('/js/highcharts.js')}"></script>

    <link href="${urls.getForLookupPath('/css/ElecSumPage.css')}" rel="stylesheet" />
    

    <style type="text/css">
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        #map {
            width: 100%;
            height: 100%;
        }
		.foot{
    color: #f44336;
    font-size: 2.5rem;
    font-weight: bolder;
    line-height: 3rem;
    padding: 0 8px;
    text-align: center;
    width: 100%;
}
    </style>
  
</head>
<body>
    

<div class="bodyBox">

    <div class="MapBox">
		<div id="map"></div>
		<canvas id="canvas"></canvas>

    </div>
        <div class="HeadBox">
            <div class="webTitle">智慧用电管理平台</div>
            <div class="navBack">
                <div class="backImg" onclick="window.top.goMain();"></div>
                <div class="backTitle">返回首页</div>
            </div>
        </div>

    <div class="leftBox">
        <div class="numBox" id="ElecAlarmSum">
            <div class="numTitle">设备总数</div>
            <div class="numContent" id="Alarm00">
                <div class="numCell">0</div>
                <div class="numCell">0</div>
                <div class="numCell">0</div>
                <div class="numCell">0</div>
                <div class="numCell">0</div>
                <div class="numCell">0</div>
            </div>
            <div class="numItem bg1" data-sign="01" onclick="openDepartment();">
                报警<div id="Alarm01" class="valueCell">0/0</div>
            </div>
            <div class="numItem bg2"  data-sign="02" >
                预警<div id="Alarm02" class="valueCell">0/0</div>
            </div>
            <div class="numItem bg3"  data-sign="03" >
                在线<div id="Alarm03" class="valueCell">0/0</div>
            </div>
            <div class="numItem bg4" data-sign="04">
                未激活<div id="Alarm04" class="valueCell">0/0</div>
            </div>
        </div>

        
    </div>
	
	
	<div class="queryBox">
        <div class="queryToolBar">
            <P class="foot">当前报警设备</p>
        </div>
        <div class="queryResult">

        <#if deviceAlarmInfoList?? && deviceAlarmInfoList?size &gt; 0>
            <#list deviceAlarmInfoList as deviceAlarmInfo>
                <div class="resultItem" onclick="location.href='overview.html?serialNo=${(deviceAlarmInfo.serialNo)!""}'">
                    <div class="resultTitle">${(deviceAlarmInfo.serialNo)!""}</div>
                    <div class="resultText">${((deviceAlarmInfo.collectTime)?string("yyyy-MM-dd HH:mm:ss"))!''}</div>
                </div>
            </#list>
        </#if>
        </div>
    </div>
    

  

</div>

    
    <script type="text/javascript">

        // 百度地图API功能
        var map = new BMap.Map("map", {
            enableMapClick: false
        });    // 创建Map实例
        map.centerAndZoom(new BMap.Point(120.4160688014, 36.3106964017), 16);  // 初始化地图,设置中心点坐标和地图级别
        map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放

        map.setMapStyle({
            style: "midnight"
        });

        var randomCount = 300;

        var data = [];

        var citys = ["北京","天津","上海","重庆","石家庄","太原","呼和浩特","哈尔滨","长春","沈阳","济南","南京","合肥","杭州","南昌","福州","郑州","武汉","长沙","广州","南宁","西安","银川","兰州","西宁","乌鲁木齐","成都","贵阳","昆明","拉萨","海口"];

        // 构造数据
        while (randomCount--) {
            var cityCenter = mapv.utilCityCenter.getCenterByCityName(citys[parseInt(Math.random() * citys.length)]);
            data.push({
                geometry: {
                    type: 'Point',
                    coordinates: [cityCenter.lng - 2 + Math.random() * 4, cityCenter.lat - 2 + Math.random() * 4]
                },
                count: 30 * Math.random()
            });
        }

        var dataSet = new mapv.DataSet(data);

        var options = {
            fillStyle: 'rgba(255, 50, 50, 0.6)',
            shadowColor: 'rgba(255, 50, 50, 1)',
            shadowBlur: 30,
            globalCompositeOperation: 'lighter',
            methods: {
                click: function (item) {
                    console.log(item);
                }
            },
            size: 5,
            draw: 'simple'
        }

        var mapvLayer = new mapv.baiduMapLayer(map, dataSet, options);

        // dataSet.set(data); // 修改数据

        // mapvLayer.show(); // 显示图层
        // mapvLayer.hide(); // 删除图层
		
		function openDepartment(){
			var url='./department.html';                             //转向网页的地址; 
           var name='';                            //网页名称，可为空; 
           var iWidth=900;                          //弹出窗口的宽度; 
           var iHeight=600;                         //弹出窗口的高度; 
           //获得窗口的垂直位置 
           var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
           //获得窗口的水平位置 
           var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
           window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
			//window.open ("./department.html", "newwindow", "height=700, width=900, top=0,left=100,toolbar=no, menubar=no, scrollbars=no, resizable=no, //location=no, status=no")
		}
    </script>
</body>
</html>
