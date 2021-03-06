let vm = new Vue({
    el: '#main-container',
    data: function () {
        return {
            setting: {
                data: {
                    simpleData: {
                        enable: true,//开启简单数据模式支持
                        pIdKey: 'parentId'
                    }
                },
                callback: {
                    onClick: this.clickNode
                },
                view: {
                    fontCss: this.fontCss
                }
            },
            name: '',
            pageInfo: '',
            params: {
                status: '',
                startTime: '',
                endTime: ''
            },
            officeName: '全部'
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/admin/work/toList',
                data: this.params,
                method: 'post'
            }).then(response => {
                this.pageInfo = response.data
            }).catch(error => {
                layer.msg(error)
            })
        },
        print:function(){

        },
        initTree: function () {
            axios({
                url: 'manager/office/list'
            }).then(response => {
                let nodes = response.data;
                nodes[nodes.length] = {id: 0, name: '所有机构', open: true};
                let treeObj = $.fn.zTree.init($("#pullDownTreeone"), this.setting, nodes);
            }).catch(error => {
                layer.msg(error);
            })
        },
        clickNode: function (event, treeId, treeNode) {
            this.officeName = treeNode.name;
            this.params.officeId = treeNode.id;
        },
        search: function () {
            let treeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");
            let nodes = treeObj.getNodesByParamFuzzy("name", this.name, null);

            let treeNodes = treeObj.transformToArray(treeObj.getNodes());//转换成简单数据格式
            for (let i = 0; i < treeNodes.length; i++) {
                treeNodes[i].highLight = false;
                treeObj.updateNode(treeNodes[i]);
            }

            for (let i = 0; i < treeNodes.length; i++) {
                for (let j = 0; j < nodes.length; j++) {
                    if (treeNodes[i].name == nodes[j].name) {
                        treeNodes[i].highLight = true;
                        treeObj.updateNode(treeNodes[i]);
                        break;
                    }
                }
            }
        },
        fontCss: function (treeId, treeNode) {
            return treeNode.highLight ? {color: "red"} : {color: "black"};
        },
        select: function () {//查询全部资质
            this.clear();
            this.selectAll(1, 5);
        },
        selectOne:function(id){
            axios({
                url:'manager/admin/work/selectByOid',
                params:{oid:id}

            }).then(response => {//箭头函数可以自动将上下文的this传递到当前函数中
                //
                layer.obj = response.data;
                layer.open({
                    type:2,
                    content:'manager/admin/work/toDetail',
                    area:['80%','80%']
                })
            }).catch(function (error) {
                layer.msg(error);
            })
        },
        clear: function () {
            this.params = {
                status: '',
                startTime: '',
                endTime: ''
            };
            this.officeName = '全部';
        }
    },
    created: function () {
        this.selectAll(1, 5)
    },
    mounted: function () {//在挂在dom后调用
        this.initTree();
    }
});