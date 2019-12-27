let vm = new Vue({
    el: "#main-container",
    data: {
        pageInfo: '',
        app: {
            forceUpdate: 1
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            axios({
                url: 'manager/app/toList',
                params: {pageNum: pageNum, pageSize: pageSize}
            }).then(response => {
                this.pageInfo = response.data
            }).catch(error => {
                layer.msg(error)
            })
        },
        toUpdate: function (id) {
            axios({
                url: 'manager/app/toUpdate',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                layer.open({
                    type: 2,
                    content: 'manager/app/toUpdatePage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(this.pageInfo.pageNum, 5)
                    }
                })
            }).catch(error => {

            })
        },
        insert: function () {
            axios({
                url: 'manager/app/insert',
                method: 'post',
                data: this.app
            }).then(response => {
                if (response.data.success) {
                    this.selectAll(this.pageInfo.pages, 5);
                    this.clear();
                    $("#myTab").find("li[class = 'active']").attr('class', '').siblings().attr('class', 'active');
                    $("#home").addClass('active');
                    $("#profile").removeClass('active');
                }
            }).catch(error => {

            })
        },
        clear: function () {
            this.app = '';
        },
        detail: function (id) {
            axios({
                url: 'manager/app/toUpdate',
                params: {id: id}
            }).then(response => {
                layer.obj = response.data;
                console.log(layer.obj);
                layer.open({
                    type: 2,
                    content: 'manager/app/toDetailPage',
                    area: ["80%", "80%"],
                    end: () => {
                        this.selectAll(this.pageInfo.pageNum, 5)
                    }
                })
            })
        }
    },
    created: function () {
        this.selectAll(1, 5)
    }
});