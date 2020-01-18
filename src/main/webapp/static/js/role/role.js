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
                callback: {
                    onClick: this.onClick
                }
            },
            role:{},
            nodes: [],
            treeObj: {},
            pageInfo: {},
            params: {
                dataScope: '',//默认值，让下拉出现的时候默认被选中
                oid: '',
                name: '',
                remarks: '',
                officeName: ''
            },
            insertOid: '',
            insertOfficeName: '',
            insertRole: {},
            insertRoleNodes: [],
            insertRoleTreeObj: {},
            insertOfficeNodes: [],
            insertOfficeTreeObj: {}
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/role/toList',
                method: 'post',
                data: this.params
            }).then(response => {
                this.pageInfo = response.data;
                console.log(this.pageInfo.list[0]);
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        managerUsers: function (rid, name) {
            layer.rid = rid;
            layer.name = name;
            layer.open({
                type: 2,
                content: 'manager/role/toRoleUser',
                title: '用户角色授权',
                area: ["80%", "80%"],
                end: () => {
                    this.selectAll(this.pageInfo.pageNum, 5)
                }
            })
        },
        detail: function (id) {
            axios({
                url: 'manager/role/toUpdate',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                console.log(layer.obj);
                layer.open({
                    type: 2,
                    content: 'manager/role/toDetailPage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(this.pageInfo.pageNum, 5)
                    }
                })
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        toUpdate: function (role) {
            layer.obj = role;
            console.log(layer.obj);
            layer.open({
                type: 2,
                title: '角色修改',
                content: 'manager/role/toUpdatePage',
                area: ["80%", "80%"],
                end: () => {
                    this.selectAll(this.pageInfo.pageNum, 5)
                }
            })
        },
        initTree: function () {
            axios({
                url: 'manager/office/list'
            }).then(response => {
                this.nodes = response.data;
                this.nodes[this.nodes.length] = {"id": 0, "name": "所有机构"};
                this.treeObj = $.fn.zTree.init($("#pullDownTreeone"), this.setting, this.nodes);
                this.treeObj.expandAll(true);
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        onClick: function (event, treeId, treeNode) {
            if (!treeNode.id == 0) {
                this.params.oid = treeNode.id;
                this.params.officeName = treeNode.name;
            } else {
                this.params.oid = '';
                this.params.officeName = '';
            }
            this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
        },
        insertSelectOffice: function () {
            layer.open({
                type: 2,
                content: 'manager/role/findSelect',
                area: ["80%", "80%"],
                end: () => {
                    this.insertOid = layer.officeId;
                    this.insertOfficeName = layer.officeName;
                }
            })
        },
        insertInitTree() {
            axios({
                url: 'manager/menu/list'
            }).then(response => {
                this.insertRoleNodes = response.data;
                this.insertRoleNodes[this.insertRoleNodes.length] = {"id": 0, "name": "所有权限"}
                this.selectOfficeByRid();
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        selectOfficeByRid: function () {
            axios({
                url: 'manager/office/selectByRid',
                params: {rid: this.role.id}
            }).then(response => {

            })
        }
    },
    created: function () {
        this.selectAll(1, 5)
    },
    mounted: function () {
        this.initTree();
        $("#userDataScope").chosen({width: "80%", search_contains: true});
        $("#saveUserDataScope").chosen({width: "50%", search_contains: true});
        $("#userDataScope").on("change", (e, param) => {
            this.params.dataScope = param.selected;
        })
    }
});