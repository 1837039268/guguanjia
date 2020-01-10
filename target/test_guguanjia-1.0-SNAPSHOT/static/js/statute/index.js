let vm = new Vue({
    el: '#main-container',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        },
        params: {
            type: '',
            pageNum: '',
            pageSize: ''
        },
        statute: {
            description: ''
        },
        myConfig: {
            UEDITOR_HOME_URL: 'static/ueditor/',  //默认ueditor加载资源的前缀路径
            charset: "utf-8",
            serverUrl: 'doExec'  //  后端统一接口路径   /ueditor/doExce
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/statute/toList',
                method: 'post',
                data: this.params
            }).then(response => {
                this.pageInfo = response.data;
            }).catch(error => {
                layer.msg(error)
            })
        },
        toUpdate: function (id) {
            axios({
                url: 'manager/statute/toUpdate',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                layer.open({
                    type: 2,
                    title: '修改法规',
                    content: 'manager/statute/toUpdatePage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize)
                    }
                })
            }).catch(error => {
                layer.msg(error)
            })
        },
        insert: function () {
            axios({
                url: 'manager/statute/insert',
                method: 'post',
                data: this.statute
            }).then(response => {
                if (response.data.success) {
                    this.selectAll(this.pageInfo.pages, 5);
                    this.statute = {description: ''};
                    $("#myTab").find("li[class = 'active']").attr('class', '').siblings().attr('class', 'active');
                    $("#home").addClass('active');
                    $("#profile").removeClass('active');
                }
            }).catch(error => {
                layer.msg(error);
            })
        },
        select: function () {
            this.clear();
            this.selectAll(1, 5);
        },
        clear: function () {
            this.params = {
                type: ''
            };
        }
    },
    created: function () {
        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
    },
    components: {//引入vue的富文本编辑器组件
        VueUeditorWrap
    }
});