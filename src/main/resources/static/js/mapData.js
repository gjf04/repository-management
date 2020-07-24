function MapData(box, map) {


    var htmlBox = box;  //地图控件

    var mapObj = map;  //地图对象
   var panoramaService = new BMap.PanoramaService();   //3D场景服务

    var panorama = null;//mapObj.getPanorama();
  

    //单位数据集
    var gDataset = null;

    //大数据图层
    var gMapLayer = null;

    //末次请求数据
    var gLastResult = null;



    //大数据点样式
    var gOptions = {
        fillStyle: 'rgba(0, 255, 50, 1)',
        shadowColor: 'rgba(255, 255, 0, 1)',
        shadowBlur: 30,
        globalCompositeOperation: 'lighter',
        methods: {
            click: function (item) {
                openCom(item.data.ID, item.data.MC);
            }
        },
        size: 10,
        draw: 'simple'
    };


    //加载单位信息
    var loadData = function () {

        //doAction("SElec/GetMapData", "GET", {}, function (result) {
			var lstCompany = [{
				Address: "义堂镇中心幼儿园（二园）",
				AreaBH: "0137130208",
				AreaMC: "山东省-临沂市-兰山区-义堂镇",
				FireManagerTel: "15163996106",
				ID: "126296",
				MC: "义堂镇中心幼儿园（二园）",
				MLat: "35.160076",
				MLng: "118.251391",
				TypeBH: "01",
				TypeMC: "消防安全重点单位"
			}];
			var ComIDS = 101086;
			var lstDevice = [{
					BH: "01",
					CreateTime: "0001-01-01 00:00:00",
					DataPic: [],
					MC: null,
					Score: 0,
					Status: null,
					SysBH: null,
					TRANSDEVICEID: null,
					Text: null,
					TypeMC: null,
					Value: 1403,
					levelScore: null
			}];
			var lstAlarm = [{
					BH: "01",
					CreateTime: "0001-01-01 00:00:00",
					DataPic: [],
					MC: null,
					Score: 0,
					Status: null,
					SysBH: null,
					TRANSDEVICEID: null,
					Text: null,
					TypeMC: null,
					Value: 1403,
					levelScore: null
			}];
			var result = {
				Company: lstCompany,
				AlarmCom: ComIDS, 
				LstDevice: lstDevice , 
				LstAlarm: lstAlarm
			};
            gLastResult = result;
            //return CJson.ModelToJson(new { Company= lstCompany,AlarmCom= ComIDS, LstDevice = lstDevice , LstAlarm = lstAlarm })

            mapObj.clearOverlays();
            var comids=result.AlarmCom+"";
            if (comids != ",") {
                //playAlarm();
            }
            //添加大数据单位点
            var data = [];
            for (var i = 0; i < result.Company.length; i++) {
                var pp = result.Company[i];

                //大数据处理
                data.push({
                    geometry: {
                        type: 'Point',
                        coordinates: [pp.MLng, pp.MLat]
                    },
                    data: pp
                });

                //报警处理
                if (comids != "") {
                    if (comids.indexOf(',' + pp.ID + ',') >= 0) { //添加报警点
                        var point = new BMap.Point(pp.MLng, pp.MLat);
                        var tm = new MyFocus(point, pp.ComName);
                        tm.Data = pp;
                        mapObj.addOverlay(tm);
                    }
                }
            }

            if (gDataset) {
                gDataset.set(data);  //修改数据
            }else{
                gDataset = new mapv.DataSet(data);
            }
            gMapLayer = new mapv.baiduMapLayer(mapObj, gDataset, gOptions);

            var DevAll = 0;
            //接入设备数  
            for (var idx = 0; idx < result.LstDevice.length; idx++) {
                var dd = result.LstDevice[idx];
                if (dd.BH == "01") {
                    DevAll += parseInt(dd.Value);
                    $("#Dev01").html(dd.Value);
                } else if (dd.BH == "02") {
                    DevAll += parseInt(dd.Value);
                    $("#Dev02").html(dd.Value);
                } else if (dd.BH == "03") {
                    $("#Dev03").html(dd.Value);
                } 
            }

            var DevAllStr = DevAll + "";
            DevAllStr = "000000" + DevAllStr;
            DevAllStr = DevAllStr.substr(DevAllStr.length-6,6);

            $("#Dev00").find(".numCell").each(function (i, v) {
                 $(v).html(DevAllStr.substr(i,1));
            });



            var AlarmAll = 0;
            //接入设备数  
            var tt1 = 0;
            var tt2 = 0;
            AlarmAll += tt1;
            $("#Alarm01").html("共" + tt1 + "/" + tt2 + "未处理");


            tt1 = 0;
            tt2 = 0;
            AlarmAll += tt1;
            $("#Alarm02").html("共" + tt1 + "/" + tt2 + "未处理");


            tt1 = 0;
            tt2 = 0;
            AlarmAll += tt1;
            $("#Alarm03").html("共" + tt1 + "/" + tt2 + "未处理");


            tt1 = 0;
            tt2 = 0;
            AlarmAll += tt1;
            $("#Alarm04").html("共" + tt1 + "/" + tt2 + "未处理");



            var AlarmAllStr = AlarmAll + "";
            AlarmAllStr = "000000" + AlarmAllStr;
            AlarmAllStr = AlarmAllStr.substr(AlarmAllStr.length - 6, 6);

            $("#Alarm00").find(".numCell").each(function (i, v) {
                $(v).html(AlarmAllStr.substr(i, 1));
            });

            setTimeout(loadData, 60000);
        //});
    }

    //打开单位用电情况
    var openCom = function (comid,comName) {
        //window.top.OpenFrame("/ExtApp/SElectric?ComID=" + comid, 870, 600, "SElect", comName, "", function (r) {

        //});

        window.top.OpenFrame("/SElec/ElecComDetail?ComID=" + comid + "&ComName=" + escape(comName), 870, 600, "SElect", comName, "", function (r) {

        });
    }

    this.OpenDetail = function (comObj) {
        openCom(comObj.ID, comObj.MC);
    }
    
    this.GetResult = function () {

        return gLastResult;
    }

    //清除痕迹
    this.Clear = function (vg) {
        
    }

    //加载数据
    this.Open = function () {
        this.Clear();
        loadData();
        
    }

   
}