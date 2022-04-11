local redis = require("resty.redis")
local redisObj = redis:new()
redisObj:set_timeouts(1000,1000,1000)

-- 释放redis连接
local function close_redis(redisObj)
	local pool_max_idle_time = 10000 -- 连接的空闲时间，单位：毫秒
	local pool_size = 100 -- 连接池大小
	local ok,err = redisObj:set_keepalive(pool_max_idle_time,pool_size)
	if not ok then
	    ngx.log(ngx.ERR,"redis连接释放失败: ",err)
	end
end

-- 从redis中查询数据
 local function read_redis(ip,port,key)
     local ok,err = redisObj:connect(ip,port) -- 获取连接
     if not ok then
	 ngx.log(ngx.ERR,"连接redis失败: ",err)
	 return nil
     end

     local resp,err = redisObj:get(key) -- 查询
     if not resp then -- 查询失败处理
	 ngx.log(ngx.ERR,"查询redis失败: ",err,",key = ",key)
     end

     if resp == ngx.null then -- 查询数据为空处理
	 resp = nil
	 ngx.log(ngx.ERR,"查询redis数据为空,key = "，key)
     end

     close_redis(redisObj) -- 释放连接
     return resp
end


-- 从应用服务器(数据库)查询数据
local function read_http(path,params)
	local resp = ngx.location.capture(path,{
			  method = ngx.HTTP_GET, --请求方式
			  args = params,--get方式传参
		      })
	if not resp then
	    -- 记录错误信息，返回404
	    ngx.log(ngx.ERR,"http not found,path:",path,",args:",args)
	    ngx.exit(404)
	end
	return resp.body#成功则返回json字符串结果
end

-- 将方法导出
local _M = {
    read_http = read_http
    read_redis = read_redis
}
return _M