let vm = new Vue({
    el: '#main-container',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        },
        params: {
            pageNum: '',
            pageSize: '',
            aid: 0,
            name:''
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
                beforeEditName: this.beforeEditName
            },
            edit: {
                enable: true
            },
            view: {//自定义节点上的元素
                addHoverDom: this.addHoverDom,
                removeHoverDom: this.removeHoverDom
            }
        }
    },
    methods: {
        download: function () {
            //下载区域信息数据
            location.href = 'manager/area/download';
        },
        upload: function () {
            //获取事件源   上传input
            console.log(event.target.files[0]);
            //构建表单对象
            let formData = new FormData();
            //绑定文件到upFile  upFile需要与后台接收方法参数MultipartFile的名字对应
            formData.append("upFile", event.target.files[0]);
            axios({
                url: 'manager/area/upload',
                method: 'post',
                data: formData,
                headers: {
                    'Content/Type': 'Multipart/form-data'
                }
            }).then(response => {
                layer.msg(response.data.msg);
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        clear:function(){
            this.params = {
                pageNum: '',
                pageSize: '',
                aid: 0,
                name:''
            };
        },
        select:function(){
            this.clear();
            this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
        },
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/area/toList',
                method: 'post',
                data: this.params
            }).then(response => {
                this.pageInfo = response.data;
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        toUpdate: function (id) {
            axios({
                url: 'manager/area/toUpdate',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                layer.open({
                    type: 2,
                    content: 'manager/area/toUpdatePage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
                        this.initTree();
                    }
                })
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        initTree: function () {//初始化ztree
            //获取nodes
            axios({
                url: 'manager/area/selectAll'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)

                this.treeObj = $.fn.zTree.init($("#treeMenu"), this.setting, this.nodes);
                console.log(this.treeObj);

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        onClick: function (event, treeId, treeNode) {
            this.params.aid = treeNode.id;
            this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize)
            // console.log(11)
        },
        beforeEditName: function (treeId, treeNode) {//结合开启修改节点按钮、点击修改按钮事件回调处理更新节点弹框
            this.toUpdate(treeNode.id);
            return false;//阻止进入修改节点状态
        },
        addHoverDom: function (treeId, treeNode) {
            let aObj = $("#" + treeNode.tId + "_a");
            if ($("#treeMenu_" + treeNode.id + "_add").length > 0) return;
            //<span class="button edit" id="treeMenu_3_edit" title="rename" treenode_edit="" style=""></span>
            let editStr = `<span class="button add" id="treeMenu_${treeNode.id}_add" title="add"  style=""></span>`;
            aObj.append(editStr);
            let span = $("#treeMenu_" + treeNode.id + "_add");
            if (span) span.bind("click", function () {
                alert("diy Button for " + treeNode.name);
            });
        },
        removeHoverDom: function (treeId, treeNode) {
            $("#treeMenu_" + treeNode.id + "_add").unbind().remove();
        }
    },
    created: function () {
        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
    },
    mounted: function () {
        this.initTree();
    }
});