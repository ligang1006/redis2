local localKey = KEYS[1]
local localValue = KEYS[2]

--setnx info
local result_1=redis.call('SETNX',localKey,localValue)
if result_1==true
then
local result_2=redis.call('SETEX',localKey,3600,localValue)
return result_1
else
return result_1
end