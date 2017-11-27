<#import "../common/layout.ftl" as layout>
<@layout.layout>
<h2 class="sub-header">应用详情</h2>

<div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="dailogForm">
            <input type="hidden" id="appName" name="appName" value="${appInfo.appName}"/>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">服务器地址</label>
                <div class="col-sm-8">
                    <input type="text" value="${appInfo.address}" name="address" id="address" class="form-control"
                           placeholder="服务器地址"/>
                </div>
            </div>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">端口号</label>
                <div class="col-sm-8">

                    <input type="text" pattern="[1-9][0-9]{1,5}" value="${appInfo.port.toString()}" name="port" id="port" class="form-control"
                           placeholder="端口号"/>
                </div>
            </div>

        </form>
        <a class="btn btn-primary" role="button" name="submit"  onclick="saveInfo()">保存</a>
        <a class="btn btn-primary" role="button" href="/job/jobs?appName=${appInfo.appName}">查看任务信息</a>

    </div>
</div>
</@layout.layout>
<script>
    function saveInfo(){
        jQuery.ajax({
            type: "GET",
            url: "/app/appSave",
            data: $("#dailogForm").serialize(),
            contentType: "json",
            async: false,
            success: function (data) {
                alert(data);
            }
        });
    }
</script>

