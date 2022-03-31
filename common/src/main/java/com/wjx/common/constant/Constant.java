package com.wjx.common.constant;

/**
 * @Desc:
 * @File name：com.wjx.common.constant.Constant
 * @Create on：2022/3/8 13:56
 * @Author：wjx
 */
public class Constant {

    // 登录验证码key
    public static final String LOGIN_SMS_CODE_KEY = "login_sms_code:%s";

    // 用户信息缓存key
    public static final String USER_INFO_CACHE = "userInfo:%s";

    // redis库存扣减lua脚本
    public static String CREATE_ORDER_STOCK_DEDUCE_SCRIPT = "redis.call('sadd',KEYS[1],ARGV[2]) \n"  //将传入的商品id参数productId保存到传入的集合参数PRODUCT_SCHEDULE_SET中
            + "local productPurchaseList = KEYS[2]..ARGV[2] \n" //购买列表
            + "local userId = ARGV[1] \n" //用户id
            + "local product = 'product_'..ARGV[2]) \n" //商品key
            + "local quantity = tonumber(ARGV[3]) \n" //购买数量
            + "local stock = tonumber(redis.call('hget',product,'stock')) \n" // 从redis中获取商品当前库存
            + "local price = tonumber(redis.call('hget',product,'price')) \n" // 从redis中获取商品价格
            + "local purchase_date = ARGV[4] \n" // 购买日期
            + "if stock < quantity then return 0 end \n" //库存判断，不足则返回0
            + "stock = stock - quantity  \n"
            + "redis.call('hset',product,'stock',tostring(stock)) \n" //扣减库存
            + "local sum = price * quantity \n" //计算订单金额
            + "local purchaseRecord = userId..','..quantity..','"
            + "..sum..','..price..','..purchase_date \n" //合并购买记录
            + "redis.call('rpush',productPurchaseList, purchaseRecord) \n" // 将购买记录保存list里
            + "return 1 \n"; //返回成功

    // redis购买记录集合前缀
    public static final String PURCHASE_PRODUCT_LIST = "purchase_list_";

    // redis抢购商品集合
    public static final String PRODUCT_SCHEDULE_SET = "product_schedule_set";

    // 请求返回码对象
    public static class RESULT {

        public final static int SC0 = 0;//成功

        public final static int FI1000 = 1000;//参数为空或非法
        public final static int FI1001 = 1001;//添加失败
        public final static int FI1002 = 1002;//更新失败
        public final static int FI1003 = 1003;//删除失败
        public final static int FI1004 = 1004;//操作失败
        public final static int FI1005 = 1005;//已存在
        public final static int FI1006 = 1006;//密码错误
        public final static int FI1007 = 1007;//用户被锁定或者删除
        public final static int FI1008 = 1008;//限制重复提交
        public final static int FI1009 = 1009;//数据不存在
        public final static int FI1010 = 1010;//两次输入不一样
        public final static int FI1011 = 1011;//验证码错误
        public final static int FI1012 = 1012;//无效

        public final static int EX401 = 401;//token无效
        public final static int EX404 = 404;//找不到指定页面
        public final static int EX500 = 500;//服务器异常
        public final static int EX9109 = 9109;//验签失败
        public final static int EX9110 = 9110;//没有权限，请联系管理员授权
        public final static int EX9111 = 9111;//数据库中已存在该记录
        public final static int EX9112 = 9112;//token失效，请重新登录
        public final static int EX9113 = 9113;//账号已被锁定,请联系管理员
        public final static int EX9114 = 9114;//数据库操作异常
        public final static int EX9115 = 9115;//空指针异常
        public final static int EX9116 = 9116;//文件操作异常
        public final static int EX9990 = 9990;//系统异常
        public final static int EX9999 = 9999;//未知异常

        public final static boolean TRUE = true; //存在
        public final static boolean FALSE = false;//不存在
    }
}
