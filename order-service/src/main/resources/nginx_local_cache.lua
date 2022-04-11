-- OpenResty多级缓存脚本内容
-- 导入common函数库
local common = require('common') -- require中的common为OpenResty中lualib目录下lua文件的名称，如果有多层目录，则以a.b.filename方式引入
local read_http = common.read_http
local read_redis = common.read_redis
local cjson= require('cjson')

local goods_cache = ngx.shared.goods_cache -- 获取本地缓存对象

-- 获取路径参数
local id = ngx.var[1]
local key "goods:id:"..id

-- 先查本地缓存
local val = goods_cache.get(key)
if not val then
        -- 再查redis，redis查不到再查数据库
	local val = read_redis("127.0.0.1",6379,id)
	if not val then
	    -- 从数据库中查询
	    resp = read_http("/goods/",key)
	    resp2 = read_http("/goods/stock/",key)
	    -- json转为lua中的tables
	    local goods = cjson.decode(resp)
	    local stock = cjson.decode(resp2)

	    -- 组合数据，库存、销量
	    goods.stock = stock.stock
	    goods.sold = stock.sold
	    val = cjson.encode(goods)
	end
end

-- 存入本地缓存
goods_cache.set(key,val,6000)

-- 这里的ngx.say()函数就是写数据到Response中
ngx.say(val)