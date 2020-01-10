let vm = new Vue({
    el: '#main-container',
    data: function () {
        return {
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'
                    }
                },
                check: {//设置checkbox 处理
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"}
                },
            },
            nodes: [],
            treeObj: {},
            role: {},
            resources: [],//存放当前角色的权限数组
            officeNodes: [],//公司节点数组
            officeTreeObj: '',//公司树对象
            offices: [],//存放当前角色的授权公司数组
            params: {}
        }
    },
    methods: {
        update: function () {

        },
        doNotUpdate: function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        selectByRid: function () {
            axios({
                url: 'manager/menu/selectByRid',
                params: {rid: this.role.id}
            }).then(response => {
                this.resources = response.data;
                console.log(this.resources);
                for (let i = 0; i < this.nodes.length; i++) {
                    for (let j = 0; j < this.resources.length; j++) {
                        if (this.resources[j].id == this.nodes[i].id) {
                            this.nodes[i].checked = true;
                            this.nodes[this.nodes.length - 1].checked = true;
                            break;
                        }
                    }
                }
                this.treeObj = $.fn.zTree.init($("#select-treeSelectResEdit"), this.setting, this.nodes);
            }).catch(error => {
                layer.msg(error.msg);
            })
        },
        selectOfficeByRid: function () {
            axios({
                url: 'manager/office/selectByRid',
                params: {rid: this.role.id}
            }).then(response => {
                this.offices = response.data;
                for (let i = 0; i < this.officeNodes.length; i++) {
                    for (let j = 0; j < this.offices.length; j++) {
                        if (this.offices[j].id == this.officeNodes[i].id) {
                            this.officeNodes[i].created = true;
                            this.officeNodes[this.officeNodes.length - 1].checked = true;
                            break;
                        }
                    }
                }
                this.officeTreeObj = $.fn.zTree.init($("#select-treeSelectOfficeEdit"), this.setting, this.officeNodes);
                $("#treeSelectOfficeEdit").css("display", "inline-block");
            })
        },
        initTree: function () {//初始化ztree
            axios({
                url: 'manager/menu/list'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)
                this.nodes[this.nodes.length] = {"id": 0, "name": "所有权限"};
                this.selectByRid();
            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        initOfficeTree: function () {
            axios({
                url: 'manager/office/list'
            }).then(response => {
                this.officeNodes = response.data;
                this.officeNodes[this.officeNodes.length] = {"id": 0, "name": "所有机构"};
                this.selectOfficeByRid();
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        changeDataScope: function (e, param) {
            this.role.dataScope = param.selected;
            if (this.role.dataScope != 9) {
                $("#treeSelectOfficeEdit").css("display", "none");
            } else {
                if (this.officeTreeObj == '') {
                    this.initOfficeTree();
                }
            }
        }
    },
    created: function () {
        this.role = parent.layer.obj;
        console.log(this.role);
        this.initTree();
        // this.initOfficeTree();
    },
    mounted: function () {
        $("#chosenSelectEdit").chosen({width: "40%", search_contains: true});
        if (this.role.dataScope == 9) {//如果role的dataScope是按明细设置，则需要初始化公司树
            this.initOfficeTree();
        }
        $("#chosenSelectEdit").on("change", this.changeDataScope);
        // $("#chosenSelectEdit").trigger("chosen:updated");
    }
});