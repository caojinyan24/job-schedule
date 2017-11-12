<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard Template for Bootstrap</title>
    <!-- Bootstrap core CSS -->
    <link href="/js/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/js/dist/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="dailogForm" action="../application/applicationAdd.do" method="POST">
            <input type="hidden" id="btn_sub" class="btn_sub"/>
            <div class="form-group mno">
                <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">应用名</label>
                <div class="col-sm-8">
                    <input type="text" value="${appInfo.appName}" name="appName" id="appName" class="form-control"
                           placeholder="应用名"/>
                </div>
            </div>
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
                    <input type="text" value="${appInfo.port}" name="port" id="port" class="form-control"
                           placeholder="服务器地址"/>
                </div>
            </div>
        </form>
    </div>
    <script type="text/javascript" src="/resources/js/Validform_v5.3.2.js"></script>
    <script type="text/javascript" src="/resources/js/forminit.js"></script>

</div>
</body>

</html>
