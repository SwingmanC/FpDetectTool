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
function watchViolation(violationId) {
    $.ajax({
        url:'/violation/'+violationId,
        success:function (data) {
            $('#patternNameLabel').text(data.type);
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