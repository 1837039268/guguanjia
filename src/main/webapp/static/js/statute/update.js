let vm = new Vue({
    el: '#main-container',
    data: {
        statute: '',
        myConfig: {
            UEDITOR_HOME_URL: 'static/ueditor/',  //默认ueditor加载资源的前缀路径
            charset: "utf-8",
            serverUrl: 'doExec'  //  后端统一接口路径   /ueditor/doExce
        }
    },
    methods: {
        doUpdate: function () {
            axios({
                url: 'manager/statute/doUpdate',
                method: 'post',
                data: this.statute
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
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    },
    created: function () {
        this.statute = parent.layer.obj;
    },
    components: {//引入vue的富文本编辑器组件
        VueUeditorWrap
    }
});