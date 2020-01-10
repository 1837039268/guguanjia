new Vue({
    el: '#main-container',
    data: {
        view: ''
    },
    methods: {},
    created: function () {
        // console.log(parent.layer.obj)
        this.view = parent.layer.obj;

    }
});