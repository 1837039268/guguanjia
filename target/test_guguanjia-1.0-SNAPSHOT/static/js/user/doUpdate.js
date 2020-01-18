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
            officeName: '',
            nodes: [],
            treeObj: {},
            user: {},
            rid: ''
        }

    },
    methods: {
        doUpdate: function () {
            let sysUser = JSON.stringify(this.user);
            axios({
                url: 'manager/sysuser/doUpdate',
                params: {user: sysUser}
                // data: this.user,
                // method: 'post'
            }).then(response => {
                if (response.data.success) {
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                }
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        doNotUpdate: function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        initTree: function () {
            axios({
                url: 'manager/office/list'
            }).then(response => {
                this.nodes = response.data;
                this.nodes[this.nodes.length] = {"id": 0, "name": "所有机构"};
                this.treeObj = $.fn.zTree.init($("#pullDownTreethree"), this.setting, this.nodes);
                $('.scrollable').each(function () {
                    var $this = $(this);
                    $(this).ace_scroll({
                        size: $this.data('height') || 250
                    });
                });
                console.log(this.treeObj);
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        onClick: function (event, treeId, treeNode) {
            if (!treeNode.id == 0) {
                this.user.officeId = treeNode.id;
                this.officeName = treeNode.name;
            } else {
                this.user.officeId = '';
                this.officeName = ''
            }
        }
    },
    created: function () {
        this.user = parent.layer.obj;
        this.officeName = this.user.officeName;
    },
    mounted: function () {
        this.initTree();
        $("#user-role-update").chosen({width: "100%", search_contains: true});
        $("#user-role-update").on("change", (e, param) => {
            this.rid = param.selected;
        });
    }
});