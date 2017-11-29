<#import "../common/layout.ftl" as layout>
<@layout.layout>
<h2 class="sub-header">调度历史</h2>
    <#list histories as history>
    <div>任务编码:${history.jobId}
        调度地址:${history.scheduleAddress}
        调度参数:${history.scheduleParam}
        执行时间:${history.executeTime?string("yyyy-MM-dd HH:mm:ss")}
        执行状态:${history.executeStatus}
    </div>
    </#list>
</@layout.layout>


