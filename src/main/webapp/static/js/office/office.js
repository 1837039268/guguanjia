let vm = new Vue({
    el: '#main-container',
    data: function () {
        return {
            pageInfo: '',
            params: {
                pageNum: '',
                pageSize: '',
                name: '',
                aid: 0
            },
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'//根据node节点中的parentId属性来作为pId属性值
                    }
                },
                callback: {
                    // beforeClick:this.beforeClick,
                    onClick: this.onClick,
                    beforeEditName: this.beforeEditName,//在触发修改前调用  如果返回false则会阻止原ztree默认的编辑节点名的行为
                    beforeRemove: this.beforeRemove//触发删除前调用

                },
                edit: {
                    enable: true,//设置开启支持修改nodes
                    showRemoveBtn: true,//开启删除按钮   默认true
                    removeTitle: '删除公司',
                    renameTitle: '修改公司'
                },
                view: {
                    addHoverDom: this.addHoverDom,
                    removeHoverDom: this.removeHoverDom
                }
            },
            nodes: [],
            treeObj: {}
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/office/toList',
                method: 'post',
                data: this.params
            }).then(response => {
                this.pageInfo = response.data;
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        detail: function (id) {
            axios({
                url: 'manager/office/toUpdate',
                params: {oid: id}
            }).then(response => {
                layer.obj = response.data;
                console.log(layer.obj);
                layer.open({
                    type: 2,
                    content: 'manager/office/toDetailPage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(this.pageInfo.pageNum, 5)
                    }
                })
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        toUpdate: function (id) {
            axios({
                url: 'manager/office/toUpdate',
                params: {oid: id}
            }).then(response => {
                layer.obj = response.data;
                console.log(layer.obj);
                layer.open({
                    type: 2,
                    content: 'manager/office/toUpdatePage',
                    area: ["80%", "80%"],
                    end: () => {
                        console.log("...................")
                        this.selectAll(this.pageInfo.pageNum, 5)
                    }
                })
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        initTree: function () {//初始化ztree
            //获取nodes
            axios({
                url: 'manager/office/list'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)
                this.nodes[this.nodes.length] = {
                    "id": 0,
                    "name": "所有机构"
                };
                this.treeObj = $.fn.zTree.init($("#treeMenu"), this.setting, this.nodes);
                $('.scrollable').each(function () {
                    var $this = $(this);
                    $(this).ace_scroll({
                        size: $this.data('height') || 250
                    });
                });
                // console.log(this.treeObj)  ;

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        onClick: function (event, treeId, treeNode) {
            this.params.aid = treeNode.id;
            this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize)
            // console.log(11)
        },
        beforeEditName: function (treeId, treeNode) {//树的id  和  当前的修改按键对应的node对象
            // console.log(treeNode);
            // console.log("执行修改功能.....")
            this.toUpdate(treeNode.id);
            return false;
        },
        beforeRemove: function (treeId, treeNode) {//树的id  和  当前的修改按键对应的node对象
            // console.log(treeNode);
            // console.log("执行删除功能。。。。。")
            return true;
        },
        //1.鼠标移动到节点上，自动触发该回调函数
        //2.设置动态添加一个新的图标
        //3.绑定点击事件
        addHoverDom: function (treeId, treeNode) {
            // console.log(treeNode.tId);
            let aObj = $("#" + treeNode.tId + "_a");//选择节点上的a连接
            if ($("#diyBtn_space_" + treeNode.id).length > 0) {
                return;
            }//如果已经存在有addspan  则不操作
            let editStr = "<span id='diyBtn_space_" + treeNode.id + "' class='button add'> </span>";
            aObj.append(editStr);
            let span = $("#diyBtn_space_" + treeNode.id);
            if (span) span.on("click", function () {
                console.log("diy Button for " + treeNode.name);
            });
        },
        removeHoverDom: function (treeId, treeNode) {
            $("#diyBtn_space_" + treeNode.id).unbind().remove();
        }
    },
    created: function () {
        this.selectAll(1, 5)
    },
    mounted: function () {
        this.initTree();
    }
});