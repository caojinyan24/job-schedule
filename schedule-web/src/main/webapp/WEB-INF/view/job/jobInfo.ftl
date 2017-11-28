<#import "../common/layout.ftl" as layout>
<@layout.layout>
<h2 class="sub-header">任务详情</h2>

<div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="dailogForm">
            <input type="hidden" id="id" name="id" value="${job.id}"/>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">应用名</label>
                <div class="col-sm-8">
                    <input type="text" readonly  value="${job.appName}" name="appName" id="appName" class="form-control"
                           placeholder="应用名"/>
                </div>
            </div>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">类名</label>
                <div class="col-sm-8">
                    <input type="text" readonly value="${job.beanName}" name="beanName" id="beanName" class="form-control"
                           placeholder="类名"/>
                </div>
            </div>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">方法名</label>
                <div class="col-sm-8">
                    <input type="text" readonly value="${job.methodName}" name="methodName" id="methodName" class="form-control"
                           placeholder="方法名"/>
                </div>
            </div>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">参数</label>
                <div class="col-sm-8">
                    <input type="text"  value="${job.cronParam}" name="cronParam" id="cronParam" class="form-control"
                           placeholder="参数"/>
                </div>
            </div>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">调度地址</label>
                <div class="col-sm-8">
                    <input type="text"  value="${job.scheduleAddr}" name="scheduleAddr" id="scheduleAddr" class="form-control"
                           placeholder="调度地址"/>
                </div>
            </div>

            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">执行参数</label>
                <div class="col-sm-8">
                    <input type="text"  value="${job.param}" name="param" id="param" class="form-control"
                           placeholder="执行参数"/>
                </div>
            </div>

        </form>
        <a class="btn btn-primary" role="button" name="submit"  onclick="saveInfo()">保存</a>
        <#--<a class="btn btn-primary" role="button" href="/job/jobs?appName=${appInfo.appName}">查看任务信息</a>-->

    </div>
</div>
</@layout.layout>
<script>
    function saveInfo(){
        jQuery.ajax({
            type: "GET",
            url: "/job/modifyJobInfo",
            data: $("#dailogForm").serialize(),
            contentType: "json",
            async: false,
            success: function (data) {
                alert(data);
            }
        });
    }
</script>

