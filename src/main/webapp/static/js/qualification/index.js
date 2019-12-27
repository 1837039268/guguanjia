let vm = new Vue({
    el: '#main-container',
    data: {
        pageInfo: '',
        params: {
            type: '',
            check: '',
            start: '',
            end: ''
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum = pageNum;
            this.params.pageSize = pageSize;
            axios({
                url: 'manager/qualification/toList',
                data: this.params,
                method: 'post'
            }).then(response => {
                this.pageInfo = response.data;
            }).catch(function (error) {
                layer.msg(error);
            })
        },
        toUpdate: function (id) {
            axios({
                url: 'manager/qualification/toUpdate',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data.qualification;
                layer.oid = response.data.oid;
                layer.open({
                    type: 2,
                    content: 'manager/qualification/toUpdatePage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(1, 5)
                    }
                })
            }).catch(error => {

            })
        },
        select: function () {
            this.clear();
            this.selectAll(1, 5);
        },
        clear: function () {
            this.params = {
                type: '',
                check: '',
                start: '',
                end: ''
            };
        }
    },
    created: function () {
        this.selectAll(1, 5);
    }
});