# 应用
/app/applicationIndex
request
~~~
{}
~~~
response
~~~
{"appInfos":[{"address":"127.0.0.1","appName":"aa","port":8080}]}
~~~
/app/appInfo
request
~~~
{"appName","app"}
~~~
response
~~~
{"appInfo":{"address":"127.0.0.1","appName":"aa","port":8080}}
~~~
# job
/job/executeNow
request
~~~
{"jobId",2}
~~~
response
~~~
{}
~~~
/job/modifyJobInfo
request
~~~
{"jobInfo",{"cronParam":"aa","id":1,,"scheduleAddr":"22"}}
~~~
response
~~~
{}
~~~