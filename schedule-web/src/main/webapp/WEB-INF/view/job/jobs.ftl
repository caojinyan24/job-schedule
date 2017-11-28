<#import "../common/layout.ftl" as layout>
<@layout.layout>

<h2>应用信息</h2>
<a class="btn btn-primary" role="button" href="/app/appInfo?appName=${appName}">查看应用信息</a>
<h2>任务信息</h2>
    <#list jobs as job>
    <div>${job.beanName}.${job.methodName}
        <a class="btn btn-primary" role="button" onclick="executeNow(${job.id})"">立即执行</a>
        <a class="btn btn-primary" role="button" href="/job/scheduleHistory?jobId=${job.id} ">查看执行历史</a>
        <a class="btn btn-primary" role="button" href="/job/jobInfo?jobId=${job.id}">修改信息</a>
    </div>
    </#list>
</@layout.layout>

<script>
    function executeNow(jobId) {
        jQuery.ajax({
            type: "GET",
            url: "/job/executeNow",
            data: {"jobId": jobId},
            contentType: "json",
            async: false,
            success: function (data) {
                alert(data);
            }
        });
    }


</script>