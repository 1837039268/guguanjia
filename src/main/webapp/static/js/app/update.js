let vm = new Vue({
    el: '#main-container',
    data: {
        app: ''
    },
    methods: {
        doUpdate: function () {

            console.log(this.app);

            axios({
                url: 'manager/app/doUpdate',
                method: 'post',
                data: this.app
            }).then(response => {
                if (response.data.success) {
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    return;
                }
                layer.msg(response.data.msg)
            }).catch(error => {
                layer.msg(error)
            })
        },
        closeFrame:function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    },
    created:function () {
        this.app = parent.layer.obj;
    }
});