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
                    onClick: this.clickNode
                },
                view: {
                    fontCss: this.fontCss
                }
            },
            officeName: '全部',
            nodes: [],
            treeObj: {},
            rid: '',//角色id
            name: '',//角色名
            yxUsers: [],//已选人员的列表
            showRemoveClass: 'hide',//控制移除已选人员的按钮样式
            uids: [],//移除人员的id列表

            companyUsers: [],//公司待选人员列表
            cids: [],//待选人员id列表
            companyShowClass: 'hide'//控制待选人员的按钮样式
        }
    },
    methods: {
        yxUser: function () {
            axios({
                url: 'manager/sysuser/selectByRid',
                params: {rid: this.rid}
            }).then(response => {
                this.yxUsers = response.data;
                console.log(this.yxUsers);
                for (let i = 0; i < this.yxUsers.length; i++) {
                    this.yxUsers[i].show = false;
                }
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        changeShow: function (id) {
            for (let i = 0; i < this.yxUsers.length; i++) {
                if (id == this.yxUsers[i].id) {
                    this.yxUsers[i].show = !this.yxUsers[i].show;
                    if (this.yxUsers[i].show) {
                        this.showRemoveClass = 'show';//修改显示移除人员按钮
                        this.uids.push(this.yxUsers[i].id);//将需要移除的人员id放入uids
                        return;
                    } else {
                        for (let j = 0; j < this.uids.length; j++) {
                            if (this.uids[j] == id) {
                                this.uids.splice(j);//将取消选中的人员id从uids中移除
                            }
                        }
                    }
                }
            }
            if ($("#yxuser input:checked").length == 0) {
                this.showRemoveClass = 'hide';
            }
        },
        removeUsers: function () {
            axios({
                url: 'manager/role/deleteBatch',
                params: {rid: this.rid, uids: this.uids + ''}
            }).then(response => {
                if (response.data.success) {
                    this.yxUser();
                    this.showRemoveClass = 'hide';
                }
                layer.msg(response.data.msg);
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        dxUser: function () {
            axios({
                url: 'manager/sysuser/selectNoRole',
                params: {oid: this.treeNode.id, rid: this.rid}
            }).then(response => {
                this.companyUsers = response.data;
                console.log(this.companyUsers);
                for (let i = 0; i < this.companyUsers.length; i++) {
                    this.companyUsers[i].show = false;
                }
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        changeCompanyShow: function (id) {
            console.log("点进来了");
            for (let i = 0; i < this.companyUsers.length; i++) {
                if (id == this.companyUsers[i].id) {
                    this.companyUsers[i].show = !this.companyUsers[i].show;
                    if (this.companyUsers[i].show) {
                        this.companyShowClass = 'show';//修改显示移除人员按钮
                        this.cids.push(this.companyUsers[i].id);//将需要移除的人员id放入uids
                        return;
                    } else {
                        for (let j = 0; j < this.cids.length; j++) {
                            if (this.cids[j] == id) {
                                this.cids.splice(j);//将取消选中的人员id从uids中移除
                            }
                        }
                    }
                }
            }
            if ($("#dxuser input:checked").length == 0) {
                this.companyShowClass = 'hide';
            }
        },
        insertUsers: function () {
            axios({
                url: 'manager/role/insertBatch',
                params: {rid: this.rid, cids: this.cids + ''}
            }).then(response => {
                console.log("gengixinle");
                layer.msg(response.data.msg);
                //更新当前的用户未授权列表
                this.dxUser();
                this.companyShowClass = 'hide';//隐藏提交按钮
                this.yxUser();//更新已分配角色列表

            }).catch(error => {
                layer.msg(error.message)
            });
        },
        initTree: function () {//初始化ztree
            axios({
                url: 'manager/office/list'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)
                // console.log(this.nodes);

                this.nodes[this.nodes.length] = {id: 0, name: "所有机构"};
                this.treeObj = $.fn.zTree.init($("#treeOffice"), this.setting, this.nodes);
                // console.log(this.treeObj)  ;

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        clickNode: function (event, treeId, treeNode) {
            //             // console.log("有咩反映...");
            this.treeNode = treeNode;
            let treeObj = $.fn.zTree.getZTreeObj("treeOffice");
            let treeNodes = this.treeObj.transformToArray(this.treeObj.getNodes());
            //清除原高亮标记
            for (let i = 0; i < treeNodes.length; i++) {
                if (treeNodes[i].id == treeNode.id) {
                    treeNodes[i].higtLine = true;//设置高亮标记
                } else {
                    treeNodes[i].higtLine = false;
                }
                this.treeObj.updateNode(treeNodes[i]);//更新节点，自动调用清除css
            }
            this.dxUser(treeNode);
        },
        fontCss: function (treeId, treeNode) {
            return treeNode.higtLine ? {color: "red"} : {color: "black"};//根据标记显示高亮
            // return {color: "red"};//根据标记显示高亮
        }
    },
    created: function () {
        this.rid = parent.layer.rid;
        this.name = parent.layer.name;
    },
    mounted: function () {
        this.initTree();//初始化公司树
        this.yxUser();
    }
});