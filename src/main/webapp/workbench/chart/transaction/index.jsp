<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>ECharts</title>
        <base href="<%=basePath%>">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script src="ECharts/ECharts.js"></script>
        <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

        <script type="text/javascript">
            $(function () {

                getCharts();
            })

            function getCharts() {

                $.ajax({
                    url: "workbench/transaction/getChartsInfo.do",
                    type: "get",
                    dataType: "json",
                    success: function (data) {

                        // 基于准备好的dom，初始化echarts实例
                        var myChart = echarts.init(document.getElementById('main'));

                        //我们要画的图
                        var option = {
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                top: '5%',
                                left: 'center'
                            },
                            series: [
                                {
                                    //name: 'Access From',
                                    type: 'pie',
                                    radius: ['40%', '70%'],
                                    avoidLabelOverlap: false,
                                    itemStyle: {
                                        borderRadius: 10,
                                        borderColor: '#fff',
                                        borderWidth: 2
                                    },
                                    label: {
                                        show: false,
                                        position: 'center'
                                    },
                                    emphasis: {
                                        label: {
                                            show: true,
                                            fontSize: '25',
                                            fontWeight: 'bold'
                                        }
                                    },
                                    labelLine: {
                                        show: false
                                    },
                                    data: data.dataList/*[
                                        { value: 1048, name: 'Search Engine' },
                                        { value: 735, name: 'Direct' },
                                        { value: 580, name: 'Email' },
                                        { value: 484, name: 'Union Ads' },
                                        { value: 300, name: 'Video Ads' },
                                        { value: 300, name: 'Video AAds' }
                                    ]*/
                                }
                            ]
                        };

                        myChart.setOption(option);
                    }
                })

            }
        </script>
    </head>

    <body>
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="main" align="center" style="width: 1500px;height:900px;"></div>

    </body>
</html>
