/**
 * Author SkyeGao (yingyinggao@sohu-inc.com) .
 * Date  2016/12/9.
 */
;void(function(window,document,undefined){
    if(!(typeof ssktUtil != 'undefined' && ssktUtil) ){
        var ua = navigator.userAgent.toLowerCase();
        var ssktUtil = window.ssktUtil = {};

        ssktUtil.interFace = 'http://101.200.155.137/api';//配置统一接口前缀
        //ssktUtil.interFace = 'http://101.200.155.137/';//配置统一接口前缀
        //ssktUtil.interFace = 'http://localhost:63342/xinshen/sskt'; //本地测试

        ssktUtil.globalParam = {
            //调用接口的终端类型  1-pc 2-app
            "terminalType":'1',
            "platform":'web'
        };

        //本项目所有接口地址
        ssktUtil.interFaceAfterParamObj = {
            /************************************  首页 start ************************************/
            //注册
            "registParam":'/user/register', // over
            //登录
            "loginParam":'/user/login', //  over

            //教师申请开课
            "applyCourse":'/sskt/course/apply',

            //请求图片轮换数据 typeId-0
            "picDataParame":'/resource/jsonData/picRotateJSON.json',//done
            //左侧导航数据
            "leftNavDataParame":'/common/category/get',//over
            //通栏广告  模块广告 typeId-1
            "tonglanAdParameType1":'/resource/jsonData/tonglanAdJSON.json',//done
            //通栏广告 在线课堂广告 typeId-3
            "tonglanAdParameType2":'/resource/jsonData/tonglanAdJSON2.json',//done
            //通栏广告 精品课堂广告 typeId-2
            "tonglanAdParameType3":'/resource/jsonData/tonglanAdJSON3.json',//done
            //新闻 typeId-2
            "getNewsParame":'/backend/news/latestnews/{TYPEID}/{PAGENUM}/{PAGESIZE}',  // over
            //公告 typeId-1
            "getNoticeParame":'/backend/news/latestnews/{TYPEID}/{PAGENUM}/{PAGESIZE}',// over
            //心神二维码
            "getQRCodePicParame":'/resource/jsonData/getQRCodePicJSON.json',


            //首页在线课堂左侧分类
            "zxktCategoryParame":'/common/category/online/get',//over  "zxktCategoryParame":'/resource/jsonData/getZxktCategoryJSON.json',
            //最新课程
            "zxktNewCourceParame":'/sskt/course/latest/{TYPEID}/{PAGENUM}/{PAGESIZE}', // "/resource/jsonData/getNewCourceJSON.json"
            //在线课堂评论
            "zxktCommentParame":'/resource/jsonData/zxktCommentJSON.json', //done
            //在线课堂热门老师
            "zxktHotTeacherParame":'/resource/jsonData/zxktHotTeacherJSON.json', //done


            //首页精品课堂左侧分类
            "jpktCategoryParame":'/common/category/good/get',//"jpktCategoryParame":'/resource/jsonData/getZxktCategoryJSON.json',
            //精品课堂最新课程
            "jpktNewCourceParame":'/resource/jsonData/getNewCourceJSON.json',
            //精品课堂评论
            "jpktCommentParame":'/resource/jsonData/zxktCommentJSON.json',//done
            //精品课堂热门老师
            "jpktHotTeacherParame":'/resource/jsonData/zxktHotTeacherJSON.json',//done

            //名师推荐左侧分类
            "famousTeacherCatogoryParame":'/resource/jsonData/famousTeacherCatogoryJSON.json',
            //名师推荐图像列表
            "famousTeacherImgDataParame":'/resource/jsonData/famousTeacherImgDataJSON.json',//done

            //新闻、公告详情数据
            "articalInfoDataParame":'/resource/jsonData/articalInfoDataJSON.json',

            /************************************  首页 end ************************************/



            /*******************  tea  start  **************************************/
            //教师 进入 测评页 获取题库列表
            "teaTikuList":'/exam/question/queryList',   //over ing
            //教师 进入 测评页  增加题库
            "teaAddTiku":'/exam/question/addQuestion',   //over ing
            //教师 进入 测评页  修改题库
            "teaEditTiku":'/exam/question/update',      //over ing
            // 教师 进入 测评页  删除题目
            "teaCepingDelTi":'/exam/question/delete/{TIID}',   //TIID - 删除题目的id  over ing

            // 教师 测评页  修改题目—— 请求题目详细信息
            "tiInfo":'/exam/question/query?id={TIID}',   //TIID - 删除题目的id  over ing

            //获取教师个人资料的url
            "teaInfoDataUrl":"/resource/jsonData/teaInfoJSON.json", //done
            //获取教师教授课程的url
            "teaCourseDataUrl":"/resource/jsonData/teaCourseJSON.json",
            /*******************  tea  end  **************************************/



            /************************************  在线课堂 start ************************************/
            //在线课堂-首页-全部课程
            "zxktIndexAllCourceUrl":"/resource/jsonData/zxktIndexAllCourceJSON.json",
            //在线课堂-首页-免费课程
            "zxktIndexFreeCourceUrl":"/resource/jsonData/zxktIndexFreeCourceJSON.json",
            //在线课堂-首页-付费课程
            "zxktIndexPayCourceUrl":"/resource/jsonData/zxktIndexPayCourceJSON.json",
            //课程详情页
            "courceInfoUrl":"/sskt/course/{COURSEID}",   //"courceInfoUrl":"/resource/jsonData/courceInfoJSON.json"
            /************************************  在线课堂 end ************************************/


            /************************************  webSocket ************************************/
            //进入课堂
            "enterCourseUrl":"/sskt/classroom/enter/{COURSEID}/{USERID}",
            //离开课堂
            "leaveCourseUrl":"/sskt/classroom/leave/{COURSEID}/{USERID}",
            //教师vedio窗口获取 学生列表
            "courseStuListUrl":"/sskt/classroom/{COURSEID}"

            /************************************  webSocket ************************************/



        };

        ssktUtil.init = function($config){
            if($config.hasOwnProperty("interFace")){
                this.interFace = $config.interFace;
            }
            if($config.hasOwnProperty("terminalType")){
                this.globalParam.terminalType = $config.terminalType;
            }
        };

        //正则验证
        ssktUtil.regexp = {
            "require": /.+/,
            "mobile": /^1[3|4|5|7|8][0-9]\d{8}$/,
            "isNum": /^\+?[1-9][0-9]*$/           //非零的正整数
        };

        ssktUtil.checkLength = function(str,l){
          if(str.length>l){return false;} return true;
        };

        ssktUtil.arrHasKey = function(key,arr){
            var i = 0 ,l = arr.length;
            for(;i<l;i+=1){
                if(arr[i].id==key){
                    return {"plg":true,"index":i};
                }
            }
            return {"plg":false,"index":""};
        };


        //browser of type
        ssktUtil.browser = {
            "firefox" : /firefox/.test(ua),
            "safari" : /!chrome/.test(navigator.userAgent.toLowerCase()) && /safari/.test(navigator.userAgent.toLowerCase()),
            "chrome" : /chrome/.test(ua),
            "opera" : /opera/.test(ua),
            "ie" : /msie/.test(ua),
            "ie11" : /trident 7.0/.test(ua) || /trident\/7.0/.test(ua)
        };

        //解析UrlParam
        ssktUtil.getUrlParam = function(name){
            var parentUrlParams = window.location.search.substr(1);
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = parentUrlParams.match(reg);
            if (r!=null) return unescape(r[2]); return null;
        };

        //关闭当前页
        ssktUtil.closeWindows = function(){
            if (navigator.userAgent.indexOf("MSIE") > 0) {
                if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
                    window.opener = null;
                    window.close();
                } else {
                    window.open('','_top');
                    window.top.close();
                }
            }else {
                window.open('about:blank', '_self').close();
                window.opener = null;
                window.close();
            }
        };

        //cokie
        ssktUtil.setCokie = function(c_name,value,expiredays){
            var exdate=new Date();
            exdate.setDate(exdate.getDate()+expiredays);
            document.cookie=c_name+ "=" +escape(value)+
                ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
        };
        ssktUtil.getCokie = function(c_name){
            if (document.cookie.length>0){
                c_start=document.cookie.indexOf(c_name + "=");
                if (c_start!=-1){
                    c_start=c_start + c_name.length+1;
                    c_end=document.cookie.indexOf(";",c_start);
                    if (c_end==-1) c_end=document.cookie.length;
                    return unescape(document.cookie.substring(c_start,c_end));
                }
            }
            return "";
        };

        ssktUtil.isNull = function(exp){
            if(!exp && typeof exp != "undefined" && exp != 0){
                //exp is null
                return true;
            }
            return false;
        };

        //time
        ssktUtil.exTime = function(ctime,plg){
            if(!ctime){return "---";}
            var time = new Date(ctime),
                month = time.getMonth()+1,
                date = time.getDate(),
                hour = time.getHours(),
                minutes = time.getMinutes(),
                seconds = time.getSeconds();
            if(month<10){month = "0"+month;}
            if(date<10){date = "0"+date;}
            if(hour<10){hour = "0"+hour;}
            if(minutes<10){minutes = "0"+minutes;}
            if(seconds<10){seconds = "0"+seconds;}
            if(arguments.length>1){
                return time.getFullYear()+"-"+month+"-"+date+"&nbsp;&nbsp;&nbsp;"+hour+":"+minutes+":"+seconds;
            }else{
                return time.getFullYear()+"-"+month+"-"+date;
            }
        };

        ssktUtil.exOptionData = [
            {"num":1,"alp":"A"},{"num":2,"alp":"B"},{"num":3,"alp":"C"},{"num":4,"alp":"D"},
            {"num":5,"alp":"E"},{"num":6,"alp":"F"},{"num":7,"alp":"G"},{"num":8,"alp":"H"},
            {"num":9,"alp":"I"},{"num":10,"alp":"J"},
        ];
        ssktUtil.exOption = function(num){
            var i = 0 ,l = this.exOptionData.length;
            for(;i<l;i+=1){
                if(this.exOptionData[i].num==num){
                    return this.exOptionData[i].alp;
                }
            }
            return "";
        };
    }
})(this,this.document);