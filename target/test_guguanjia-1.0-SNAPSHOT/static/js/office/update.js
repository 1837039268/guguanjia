let vm = new Vue({
    el: '#main-container',
    data: {
        office: '',
        wasteTypes: [],//用于存放所有的wasteType
        wastes: [],//用于存放wasteType的所有wastes
        wasteTypeCode: ''
    },
    methods: {
        doUpdate: function () {
            axios({
                url: 'manager/office/doUpdate',
                method: 'post',
                data: this.office
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
        createWasteTypes: function () {
            axios({
                url: 'manager/office/selectWasteType'
            }).then(response => {
                this.wasteTypes = response.data;
                // console.log(this.wasteTypes);
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        createWastes: function (e, param) {
            axios({
                url: 'manager/office/selectWaste',
                params: param
            }).then(response => {
                this.wastes = response.data;
                // console.log(this.wastes);
                //遍历所有wasteTypes  得到  wasteTypeCode
                for (let i = 0; i < this.wasteTypes.length; i++) {
                    if (this.wasteTypes[i].id == param.selected) {
                        this.wasteTypeCode = this.wasteTypes[i].code
                    }
                }
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        selectWaste: function (e, param) {
            for (let i = 0; i < this.wastes.length; i++) {
                if (this.wastes[i].id == param.selected) {
                    let flag = false;
                    for (let j = 0; j < this.office.wastes.length; j++) {
                        if (this.office.wastes[i].id == this.wastes[i].id) {
                            flag = true;//判断office.wastes中是否已存在选中的
                            break;
                        }
                    }
                    if (!flag) {
                        //不存在选中的则添加进去
                        this.wastes[i].wasteTypeCode = this.wasteTypeCode;
                        this.office.wastes.push(this.wastes[i]);
                    }
                }
            }
        }
    },
    created: function () {
        //在vue对象创建的时候获取父窗口layer对象绑定的数据，放入当前app对象中
        this.office = parent.layer.obj;
        console.log(this.office.wastes);
        this.createWasteTypes();
    },
    mounted: function () {

        $("#chosen-select").chosen({width: '100%'});//初始化机构类型
        $("#wasteType").chosen({width: '100%'});
        $("#waste").chosen({width: '100%'});
        //动态绑定wasteType的change事件  不能用vue@change绑定，因为页面上点击到的元素，是chonse动态
        //生成的元素，所以得绑定到chonse的元素身上
        $("#wasteType").on("change", this.createWastes);
        $("#waste").on("change", this.selectWaste);
    },
    updated: function () {
        $("#wasteType").trigger('chosen:updated');
        $("#waste").trigger('chosen:updated');
    }
});