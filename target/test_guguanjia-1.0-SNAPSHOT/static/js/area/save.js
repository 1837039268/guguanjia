let vm = new Vue({
    el: '#main-container',
    data: {
        area: {},
        oldParentId: ''//旧的父id
    },
    methods: {
        doUpdate: function () {
            // this.oldParentIds = this.area.parentIds;
            this.area.type = $("#chosen-select").chosen({width: '100%', disable_search: true}).val();
            console.log(this.area);
            axios({
                url: 'manager/area/doUpdate',
                method: 'post',
                data: this.area
            }).then(response => {
                if (response.data.success) {
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    return;
                }
                layer.msg(response.data.msg)
            }).catch(error => {
                layer.msg(error.message)
            })
        },
        doNotUpdate: function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        selectIcon: function () {
            layer.open({
                type: 2,
                title: '图标修改',
                content: 'manager/area/awesome',
                area: ['80%', '80%'],
                end: () => {//将then函数中的this传递到end的回调函数中
                    this.area.icon = layer.icon;
                    console.log(this.area.icon)
                }
            });
        },
        selectArea:function () {
            layer.open({
                type: 2,
                title: '区域修改',
                content: 'manager/area/selectArea',
                area: ['80%', '80%'],
                end: () => {//将then函数中的this传递到end的回调函数中
                    this.area.parentName = layer.parentName;
                    console.log(666)
                }
            });
        }
    },
    created: function () {
        this.area = parent.layer.obj;
        this.oldParentId = this.area.parentId;
    },
    mounted: function () {
        $("#chosen-select").chosen({width: '100%', disable_search: true});//初始化chosen
    }
});