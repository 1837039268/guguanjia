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
            searchName: '',
            officeName: '全部',
            nodes: [],
            treeObj: {},
            pageInfo: {},
            params: {
                rid: '',
                uid: '',
                oid: '',
                name: ''
            },
            roles: []
        }
    },
    methods: {
        clear: function () {
            this.params = {
                rid: '',
                uid: '',
                oid: '',
                name: ''
            };
            this.officeName = '全部';
        },
        select: function () {
            this.clear();
            this.selectAll(1, 5);
        },
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/sysuser/toList',
                method: 'post',
                data: this.params
            }).then(response => {
                this.pageInfo = response.data;
                this.roles = response.data.roles;
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        userUpdate: function (id) {
            axios({
                url: 'manager/sysuser/detail',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                console.log(layer.obj);
                layer.open({
                    type: 2,
                    title: '用户编辑',
                    content: 'manager/sysuser/toUpdatePage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(1, 5);
                    }
                })
            })
        },
        userDetail: function (id) {
            axios({
                url: 'manager/sysuser/detail',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                console.log(layer.obj);
                layer.open({
                    type: 2,
                    title: '用户详情',
                    content: 'manager/sysuser/toDetailPage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(1, 5);
                    }
                })
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        initTree: function () {
            axios({
                url: 'manager/office/list'
            }).then(response => {
                this.nodes = response.data;
                this.nodes[this.nodes.length] = {"id": 0, "name": "所有机构", open: true};
                this.treeObj = $.fn.zTree.init($("#pullDownTreeone"), this.setting, this.nodes);
                $('.scrollable').each(function () {
                    var $this = $(this);
                    $(this).ace_scroll({
                        size: $this.data('height') || 250
                    });
                });
                console.log(this.treeObj)
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        onClick: function (event, treeId, treeNode) {
            if (!treeNode.id == 0) {
                this.params.oid = treeNode.id;
                this.officeName = treeNode.name;
            } else {
                this.params.oid = '';
                this.officeName = ''
            }
            // this.selectAll(1, 5)
        },
        search: function () {
            this.treeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");
            this.nodes = this.treeObj.getNodesByParamFuzzy("searchName", this.searchName, null);
            let treeNodes = this.treeObj.transformToArray(this.treeObj.getNodes());//转换成简单数据格式
            for (let i = 0; i < treeNodes.length; i++) {
                // treeNodes[i].highLight = false;
                this.treeObj.updateNode(treeNodes[i]);
            }
            for (let i = 0; i < treeNodes.length; i++) {
                for (let j = 0; j < this.nodes.length; j++) {
                    if (treeNodes[i].name == this.nodes[j].name) {
                        // treeNodes[i].highLight = true;
                        this.treeObj.updateNode(treeNodes[i]);
                        break;
                    }
                }
            }
        }
    },
    created: function () {
        this.selectAll(1, 5);
    },
    mounted: function () {
        this.initTree();
        $("#role-select").chosen({width: "80%", search_contains: true});
        $("#role-select").on("change", (e, param) => {
            this.params.rid = param.selected;
        })
    }
});