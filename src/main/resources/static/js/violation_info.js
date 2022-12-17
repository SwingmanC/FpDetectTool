$(function () {
    let violationInfo = {
        text:'主要信息'
    };
    let knowledgeInfo = {
        text:'知识条目'
    };
    let treeData1 = [violationInfo];
    let treeData2 = [knowledgeInfo];
    $("#evidenceTree").treeview({
        data:treeData1,
        color:'#428bca',
        showBorder: false,
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        nodeIcon: 'glyphicon glyphicon-bookmark',
    });
    $("#knowledgeTree").treeview({
        data:treeData2,
        color:'#428bca',
        showBorder: false,
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        nodeIcon: 'glyphicon glyphicon-bookmark',
    });
});
function watchViolation() {
    let violationId = window.localStorage.getItem('violationId');
    $.ajax({
        url:'/violation/'+violationId,
        success:function (data) {
            let violationInfo = {
                text:'主要信息',
                nodes:[
                    {
                        text:'漏洞类型：'+data.category
                    },
                    {
                        text:'漏洞所在类：'+data.className
                    },
                    {
                        text:'漏洞所在方法：'+data.methodName+":"+data.signature
                    },
                    {
                        text:data.startLine+'-'+data.endLine
                    }
                ]
            };
            $("#evidenceTree").treeview({
                data:[violationInfo],
                color:'#428bca',
                showBorder: false,
                expandIcon: 'glyphicon glyphicon-chevron-right',
                collapseIcon: 'glyphicon glyphicon-chevron-down',
                nodeIcon: 'glyphicon glyphicon-bookmark',
                onNodeSelected:function(event,node){

                }
            });
        }
    })
}
function watchKnowledge(violationId){
    $.ajax({
        url: '',
        success:function (data){
            let nodeList = [];
            let knowledgeData = data.knowledgeList;
            for(let i=0;i<knowledgeData.length;++i){
                let node = {
                    text:knowledgeData[i].knowledgeName,
                    nodes:[{
                        text:knowledgeData[i].content
                    }]
                };
                nodeList.push(node);
            }
            let knowledgeInfo = {
                text:'知识条目',
                nodes: nodeList
            };
            $("#knowledgeTree").treeview({
                data:[knowledgeInfo],
                color:'#428bca',
                showBorder: false,
                expandIcon: 'glyphicon glyphicon-chevron-right',
                collapseIcon: 'glyphicon glyphicon-chevron-down',
                nodeIcon: 'glyphicon glyphicon-bookmark'
            })
        }
    })
}

function watchCode(){
    let violationId = window.localStorage.getItem('violationId');
    $.ajax({
        url:'/violation/code/'+violationId,
        method:'get',
        success:function (msg){
            if (msg !== null){
                $("#codeInfo").text(msg.snippet);
            }
            else{
                $("#codeInfo").text("");
            }
        }
    })
}

function watchSlice(){
    let violationId = window.localStorage.getItem('violationId');
    $.ajax({
        url: '/violation/slice/'+violationId,
        method: 'get',
        success: function (msg){
            if (msg !== null){
                $("#codeInfo").text(msg.snippet);
                $("#patternInfo").val(msg.snapshot);
            }
            else{
                $("#codeInfo").text("");
            }
        }
    })
}
function editCodePattern(){
    let violationId = window.localStorage.getItem('violationId');
    $.ajax({
        url: '/editSlice',
        method: 'post',
        data:{
            'violationId':violationId,
            'snapshot':$('#patternInfo').val()
        },
        success: function (msg){
            layer.msg("代码模式编辑成功");
        }
    })
}
function snippet2AST(){
    layer.open({
        title: false,
        content: 'AST提取中，请等待',
        anim: 3,
        btn: [],
        yes: function(index, layero) {

        },
        cancel: function() {
            return false
        }
    });
    let violationId = window.localStorage.getItem('violationId');
    $.ajax({
        url:'/tran2AST/'+violationId,
        method:'get',
        success: function (msg){
            if (msg !== null){
                layer.msg("AST提取成功");
                $("#vectorSnippet").text(msg.vectorSnippet);
                if(msg.state === 1){
                    $("#state").text("正报");
                }else{
                    $("#state").text("误报");
                }
            }else{
                layer.msg("源代码为空或代码片段信息缺失");
            }
        }
    })
}