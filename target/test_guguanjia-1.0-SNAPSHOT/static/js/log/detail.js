let vm = new Vue({
    el: '#main-container',
    data: {
        log: ''
    },
    methods: {
        doUpdate: function () {

        }
    },
    created: function () {
        this.log = parent.layer.obj;
    }
});