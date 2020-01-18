let vm = new Vue({
    el: '#main-container',
    data: {
        pageInfo: {},
        params: {
            pageNum: '',
            pageSize: '',
            type: '',
            description: ''
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/syslog/toList',
                data: this.params,
                method: 'post'
            }).then(response => {
                this.pageInfo = response.data;
                console.log(this.pageInfo);
            }).catch(error => {
                layer.msg(error.message);
            })
        },
        detail: function (id) {
            axios({
                url: 'manager/syslog/detail',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                console.log(666 + layer.obj);
                layer.open({
                    type: 2,
                    title: '日志详情',
                    content: 'manager/syslog/toDetailPage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(this.pageInfo.pageNum, 5);
                    }
                })
            }).catch(error => {
                layer.msg(error.message);
            })
        }
    },
    created: function () {
        this.selectAll(1, 5)
    },
    mounted: function () {
        $("#chosen-select").chosen({width: "80%", search_contains: true});
        $("#chosen-select").on("change", (e, param) => {
            this.params.type = param.selected;
        })
    }

});