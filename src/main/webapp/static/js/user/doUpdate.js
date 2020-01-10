let vm = new Vue({
    el: '#main-container',
    data: {
        user: ''
    },
    methods:{
        doUpdate:function () {
            console.log(this.user)
        }
    },
    created:function () {
        this.user = parent.layer.obj;
    }
});