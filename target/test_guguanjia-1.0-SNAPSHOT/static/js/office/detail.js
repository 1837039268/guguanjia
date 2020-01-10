let vm = new Vue({
    el: '#main-container',
    data: {
        detail: ''
    },
    methods: {
        doUpdate: function () {

        }
    },
    created: function () {
        this.detail = parent.layer.obj;
    }
});