$(function() {
    let violation_category = echarts.init(document.getElementById('violation_category'));
    $.get('/violations/category', function(data) {
        console.log(data);
        // violation_category.resize({
        //     width: 800,
        //     height: 400
        // });
        violation_category.setOption({
            tooltip: {
                trigger: 'axis'
            },
            xAxis: [{
                type: 'category',
                data: ['BAD_PRACTICE', 'STYLE', 'MALICIOUS_CODE', 'CORRECTNESS', 'MT_CORRECTNESS', 'PERFORMANCE', 'I18N', 'SECURITY','EXPERRIMENTAL']
            }],
            yAxis: [{
                type: 'value'
            }],
            calculable: true,
            series: [{
                name: '漏洞种类',
                type: 'bar',
                data: data
            }]
        })
    }, 'json');
    let violation_priority = echarts.init(document.getElementById('violation_priority'));
    $.get('/violations/priority', function(data) {
        console.log(data);
        violation_priority.resize({
            width: 800,
            height: 400
        });
        violation_priority.setOption({
            tooltip: {
                trigger: 'axis'
            },
            xAxis: [{
                type: 'category',
                data: ['1', '2', '3', '>=4']
            }],
            yAxis: [{
                type: 'value'
            }],
            calculable: true,
            series: [{
                name: '优先级',
                type: 'bar',
                data: data
            }]
        })
    }, 'json');
});