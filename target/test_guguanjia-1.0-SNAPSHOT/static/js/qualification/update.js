let vm = new Vue({
    el: '#main-container',
    data: {
        qualification: '',
        oid: ''
    },
    methods: {
        doUpdate: function () {
            axios({
                url: 'manager/qualification/doUpdate',
                method: 'post',
                data: this.qualification
            }).then(response => {
                if (response.data.success) {
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    return;
                }
                layer.msg(response.data.msg);
            }).catch(error => {
                layer.msg(error)
            })
        },
        doNotUpdate: function () {
            axios({
                url: 'manager/qualification/doNotUpdate',
                method: 'post',
                data: this.qualification
            }).then(response => {
                if (response.data.success) {
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    return;
                }
                layer.msg(response.data.msg);
            }).catch(error => {
                layer.msg(error)
            })
        }
    },
    created: function () {
        this.qualification = parent.layer.obj;
        this.oid = parent.layer.oid;
    }
});