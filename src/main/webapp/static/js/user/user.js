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
            insertSetting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'
                    }
                },
                callback: {
                    onClick: this.insertOnClick
                }
            },
            searchName: '',
            officeName: '全部',
            insertNodes: [],
            insertTreeObject: {},
            nodes: [],
            treeObj: {},
            pageInfo: {},
            params: {
                rid: '',
                uid: '',
                oid: '',
                name: ''
            },
            roles: [],
            rids: [],
            sysUser: {},
            officeId: '',
            insertOfficeName: '全部'
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
                        this.selectAll(this.pageInfo.pageNum, 5);
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
        insertUsers: function () {
            let options = document.querySelectorAll('#role-choose option');
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    this.rids.push(options[i].value);
                }
            }
            let user = JSON.stringify(this.sysUser);
            axios({
                url: 'manager/sysuser/insertBatch',
                params: {oid: this.officeId, rids: this.rids + '', sysUser: user}
            }).then(response => {
                console.log("添加成功~~")
                this.selectAll(this.pageInfo.pages, 5);
                this.clear();
                $("#myTab").find("li[class = 'active']").attr('class', '').siblings().attr('class', 'active');
                $("#home").addClass('active');
                $("#profile").removeClass('active');
            }).catch(error => {
                    layer.msg(error.message)
                }
            )
        },
        clear: function () {
            this.officeId = '全部';
            this.rids.clear();
            this.sysUser = ''
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
        initInsertTree: function () {
            axios({
                url: 'manager/office/list'
            }).then(response => {
                this.insertNodes = response.data;
                this.insertNodes[this.insertNodes.length] = {"id": 0, "name": "所有机构", open: true};
                this.insertTreeObject = $.fn.zTree.init($("#pullDownTreetwo"), this.insertSetting, this.insertNodes);
                $('.scrollable').each(function () {
                    var $this = $(this);
                    $(this).ace_scroll({
                        size: $this.data('height') || 250
                    });
                });
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        onClick: function (event, treeId, treeNode) {
            if (!treeNode.id == 0) {
                this.officeId = treeNode.id;
                this.officeName = treeNode.name;
            } else {
                this.officeId = '';
                this.officeName = ''
            }
            // this.selectAll(1, 5)
        },
        insertOnClick: function (event, treeId, treeNode) {
            if (!treeNode.id == 0) {
                console.log(treeNode.id);
                this.officeId = treeNode.id;
                this.insertOfficeName = treeNode.name;
            } else {
                this.officeId = '';
                this.insertOfficeName = ''
            }

            // 获取 select
            // 遍历里面 checked=checked 的 option
            // 把 checked 的option 的value 获取，放进 rids 中

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
        this.initInsertTree();
        $("#role-select").chosen({width: "80%", search_contains: true});
        $("#role-select").on("change", (e, param) => {
            this.params.rid = param.selected;
        });
        $("#role-choose").chosen({width: "100%", search_contains: true});
    }
});