-- redis+lua脚本释放锁
local value2 = redis.call('get',KEYS[1])
if(value2 == ARGV[1]) then
  return redis.call('del',KEYS[1])
end
return 0