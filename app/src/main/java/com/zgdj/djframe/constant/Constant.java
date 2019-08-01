package com.zgdj.djframe.constant;

import android.os.Environment;

import java.io.File;

/**
 * description: 常量
 * author: Created by ShuaiQi_Zhang on 2018/4/20
 * version: 1.0
 */
public class Constant {

    public static final String TOKEN = "n5em1e3m0n0t4s83tjpq1e8bd5"; // Token
    public static final int TAKE_PHOTO_REQUEST_CODE = 103; // 拍照返回的 requestCode
    public static final int CHOICE_FROM_ALBUM_REQUEST_CODE = 104; // 相册选取返回的 requestCode
    private static final int CROP_PHOTO_REQUEST_CODE = 105; // 裁剪图片返回的 requestCode
    public static final int MSG_AUTH_ERROR = 301; //指纹识别错误返回
    public static final int MSG_AUTH_SUCCESS = 302; //指纹识别成功返回
    public static final int MSG_AUTH_FAILED = 303; //指纹识别失败返回
    public static final int MSG_AUTH_HELP = 304; //指纹识别异常返回
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "fengning";

    public static final String TOKEN_LOST = "登录失效，请重新登录";// token 失效提示

    public static final String KEY_USER_ID = "userId"; // user id的key
    public static final String KEY_USER_NAME = "userName"; // user name的key
    public static final String KEY_USER_NICK = "userNick"; // user nick的key
    public static final String KEY_TOKEN = "appToken"; // user token的key

    /*************************************  URL  **********************************************/
//    public static final String BASE_URL = "http://192.168.1.61:82";//- 胡涛
//    public static final String BASE_URL = "http://192.168.1.59:82";//- 余晶
    public static final String BASE_URL = "http://192.168.1.2:81";// - 测试服务器
    //   public static final String BASE_URL = "http://zzbhidi.f3322.net:10081";// -    正式服务器
//    public static final String BASE_URL = "http://183.196.236.186:1031";// -    现场服务器

    public static final String COOKIE_KEY = "Cookie";   //cookie key

    public static final String COOKIE_ID = "PHPSESSID=sm5daao94p1tm34nf1c42ctor4";  //cookie id

    public static final String URL_SUPERVISION = "/admin/common/getapi/";//监理日志 RUL

    public static final String URL_MODEL_ALL = "http://720yun.com/t/wly5w7j6lghnqyj9uq?pano_id=xZ" +
            "Tw3ggz2UVI3e2c";//全景模型图

    public static final String URL_MODEL_TILT = "https://map.bhidi.com/model/2017/tiaoyajing0809/a" +
            "pp/?scene=Production_1&cX=-65.6716&cY=-126.9394&cZ=1774.0857&upX=0.0000&upY=0.0000&upZ" +
            "=1.0000&tX=20.5940&tY=-19.4661&tZ=1687.8310";//倾斜摄影 模型图

    public static final String URL_LOGIN = BASE_URL + "/api/login/login";//登录接口

    public static final String URL_LOGOUT = BASE_URL + "/api/Login/logout";//退出登录接口

    public static final String URL_PERSONAL_INFO = BASE_URL + "/api/Login/personal";//个人信息接口

    public static final String URL_QUALITY_EVALUATIOHN = BASE_URL + "/api/division/gettree";//质量验评接口

    public static final String URL_QUALITY_EVALUATIOHN_1 = BASE_URL + "/api/division/section";//质量验评接口 - 1

    public static final String URL_QUALITY_EVALUATIOHN_2 = BASE_URL + "/api/division/division";//质量验评接口 - 2

    public static final String URL_QUALITY_EVALUATIOHN_3 = BASE_URL + "/api/unit/getUnit";//质量验评接口 - 3

    public static final String URL_QUALITY_EVALUATIOHN_COTROL = BASE_URL + "/api/unit/getProcedures";//质量验评 控制点列表接口

    public static final String URL_QUALITY_EVALUATIOHN_COTROL_CONTENT = BASE_URL + "/api/unit/norm_materialtrackingdivision";//质量验评 控制点内容列表接口

    public static final String URL_QUALITY_EVALUATIOHN_CONTENT_DATE = BASE_URL + "/api/unit/getEvaluation";//质量验评 验评结果 与 验评日期

    public static final String URL_QUALITY_EVALUATIOHN_DETAIL_SCAN = BASE_URL + "/api/controlpoint/copy";//扫描回传

    public static final String URL_QUALITY_EVALUATIOHN_DETAIL_ENCLOSURE = BASE_URL + "/api/controlpoint/uploads";//附件资料

    public static final String URL_QUALITY_EVALUATIOHN_DETAIL_FORM = BASE_URL + "/api/controlpoint/quality_form_info";//在线报表

    public static final String URL_CHART_GET_MONTH = BASE_URL + "/api/Qualityanalysis/getAllMonth";//获取柱状图月份

    public static final String URL_CHART_AND_TALBE_INFO = BASE_URL + "/api/Qualityanalysis/getIndexLeft";//获取柱状图信息和表格信息

    public static final String URL_LINE_CHART_YEAR = BASE_URL + "/api/Qualityanalysis/getAllYear";//获取折线图年份

    public static final String URL_LINE_CHART_INFO = BASE_URL + "/api/Qualityanalysis/getIndexRight";//获取折线图数据

    public static final String URL_MESSAGE_LIST = BASE_URL + "/api/Dashboard/getAllMessage";//获取消息列表

    public static final String URL_MESSAGE_HISTORY_INFO = BASE_URL + "/api/Dashboard/getRefundData";//获取消息item -- 历史信息

    public static final String URL_MESSAGE_GET_AUTOGRAPH = BASE_URL + "/api/Dashboard/getSignature";//获取消息审批 - 签名图片

    public static final String URL_MESSAGE_HANDLE_FORMINFO = BASE_URL + "/api/Dashboard/handleFormInfo";//获取消息审批 - 更新表单信息

    public static final String URL_MESSAGE_APPROVAL = BASE_URL + "/api/Approve/approve";//获取消息审批 - 通过或退回审批

    public static final String URL_MESSAGE_GET_OFTEN_EXECUTOR = BASE_URL + "/api/Dashboard/getTopContacts";//获取消息审批 - 常用联系人

    public static final String URL_MESSAGE_GET_ALL_EXECUTOR = BASE_URL + "/api/Dashboard/getAllUsers";//获取消息审批 - 所有联系人

    public static final String URL_MESSAGE_APPROVE_CHANGESTATUS = BASE_URL + "/api/Approve/changeStatus";//更改消息列表中的处理状态

    public static final String URL_MESSAGE_LIST_ITEM = BASE_URL + "/api/Send/preview";//获取推送消息单个item详情

    public static final String URL_MESSAGE_SOLVE_STATE = BASE_URL + "/api/Send/editSend";//处理收文状态

    public static final String URL_MESSAGE_MONTH_REMIND_DETAILS = BASE_URL + "/api/monthly/warnMessage";//月计划提醒弹框

    public static final String URL_MESSAGE_YEAR_REMIND_DETAILS = BASE_URL + "/api/monthly/warnMessage2";//年计划提醒弹框

    public static final String URL_MESSAGE_TOTAL_REMIND_DETAILS = BASE_URL + "/api/monthly/warnMessage3";//总计划提醒弹框

    public static final String URL_MESSAGE_CHECK_REMIND_PLAN = BASE_URL + "/api/monthly/warnDispose";//处理月计划提醒

    public static final String URL_TRANSFORM_TO_PDF = BASE_URL + "/api/Send/attachmentPreview";//格式转换-PDF

    public static final String URL_MESSAGE_LIST_ITEM_H5 = BASE_URL + "/quality/Qualityform/edit";//消息列表H5页面

    public static final String URL_QUALITY_EVALUATIOHN_UPLOAD_SINGE_IMG = BASE_URL + "/api/Upload/upload";//质量管控表单图片上传

    public static final String URL_QUALITY_EVALUATIOHN_IMAGE_SET_BINDING = BASE_URL + "/quality/element/addExecution";//质量管控表单图片进行绑定吧（估计是这个意思）

    public static final String URL_QUALITY_EVALUATIOHN_DELETE_IMG = BASE_URL + "/quality/Unitqualitymanage/relationDel";//质量管控表单图片删除

    public static final String URL_WORK_REAL_TIME_PROGRESS_SELECTOR = BASE_URL + "/api/actual/index";//实时进度 - 标段列表选项

    public static final String URL_WORK_REAL_TIME_PROGRESS_INFO = BASE_URL + "/api/actual/actualList";//实时进度 - 列表信息

    public static final String URL_WORK_REAL_TIME_PROGRESS_DELETE = BASE_URL + "/api/actual/del";//实时进度 - 删除当前item

    public static final String URL_WORK_REAL_TIME_PROGRESS_ADD = BASE_URL + "/api/actual/add";//实时进度 - 新增进度

    public static final String URL_WORK_STANDARD_TREE = BASE_URL + "/api/Appnorm/normTree";

    public static final String URL_WORK_STANDARD_LIST = BASE_URL + "/api/Appnorm/fileList";

    public static final String URL_WORK_STANDARD_DELETE = BASE_URL + "/api/Appnorm/normDel";


    public static final String URL_WORK_DOCUMENT_TREE = BASE_URL + "/api/Appatlas/atlasTree";
    public static final String URL_WORK_DOCUMENT_LIST = BASE_URL + "/api/Appatlas/atlasList";
    public static final String URL_WORK_DOCUMENT_DELETE = BASE_URL + "/api/Appatlas/delCateone";
    public static final String URL_WORK_DOCUMENT_DOWNLOAD = BASE_URL + "/api/Appatlas/atlascateDownload";
    public static final String URL_WORK_DOCUMENT_DOWNLOAD_HISTORY = BASE_URL + "/api/Appatlas/atlasDownList";
    public static final String URL_WORK_DOCUMENT_SHARE_TREE = BASE_URL + "/api/Appatlas/getOrganization";
    public static final String URL_WORK_DOCUMENT_SHARE_ADD = BASE_URL + "/api/Appatlas/addAdminname";// 添加白名单
    public static final String URL_WORK_DOCUMENT_SHARE_GET = BASE_URL + "/api/Appatlas/getAdminname";// 获取白名单
    public static final String URL_WORK_DOCUMENT_SHARE_DEL = BASE_URL + "/api/Appatlas/delAdminname";// 删除白名单
    public static final String URL_WORK_DOCUMENT_PICTURE_ADD = BASE_URL + "/api/Appatlas/addPicture";// 新增图纸


    /*************************************  URL  **********************************************/

}
