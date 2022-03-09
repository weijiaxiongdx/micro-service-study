package com.wjx.common.constant;

/**
 * @Desc:
 * @File name：com.wjx.common.constant.Constant
 * @Create on：2022/3/8 13:56
 * @Author：wjx
 */
public class Constant {

    /**
     * 请求返回码对象
     */
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
