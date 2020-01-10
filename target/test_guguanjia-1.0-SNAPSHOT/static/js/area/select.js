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
            nodes: [],
            treeObj: {},
            params: {
                aId: '',
                aName: ''
            }
        }
    },
    methods: {
        initTree: function () {
            axios({
                url: 'manager/area/selectAll'
            }).then(response => {
                this.nodes = response.data;
                this.treeObj = $.fn.zTree.init($("#select-tree"), this.setting, this.nodes);
                console.log(this.treeObj);
            })
        },
        onClick: function (event, treeId, treeNode) {
            this.params.aId = treeNode.id;
            this.params.aName = treeNode.name;
        }
    },
    mounted: function () {
        this.initTree();
    }
});