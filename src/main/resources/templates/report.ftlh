<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Urls</title>

    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans"/>


    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: coral;
        }
    </style>
</head>
<body>
<h1>${weeklyReport.getFrom()}-${weeklyReport.getTo()}</h1>
<h2>Total Weekly Generated URLs Count : ${weeklyReport.getTotalCount()}</h2>
<#list weeklyReport.getDailyReportDTOList() as dailyReport >
    <hr/>
    <table class="table table-striped">
        <h1>${dailyReport.getDayName()}</h1>
        <tr>
            <td>ID</td>
            <td>Description</td>
            <td>Path</td>
        </tr>
        <tbody>
        <#list dailyReport.getUrlReports() as urlReport>
            <tr style="background-color: <#if urlReport.isExpired()>lightcoral<#else></#if>">
                <td>
                    ${urlReport?index + 1}
                </td>
                <td>
                    <a href="${urlReport.path}">${urlReport.description}</a>
                </td>
                <td>
                    <a href="${urlReport.path}">${urlReport.code}</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <h2>Total Daily Generated URLs Count For ${dailyReport.getDayName()} : ${dailyReport.getTotalCount()}</h2>
</#list>
</body>
</html>