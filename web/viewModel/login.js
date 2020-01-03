let app = new Vue({
    el: '#app',
    data: {
        id: '',
        password: '',
        error: '',
    },
    methods: {
        login: function () {
            alert("LLL");
            console.log("login==========");
            let self = this;
            reqwest({
                url: 'http://localhost:8080/SPMS/loginServlet'
                , method: 'post'
                , data: {
                    id: self._data.id,
                    password: self._data.password,
                }
                // , type: 'json'
                , error: function (err) {
                    console.log(err);
                    alert(err.toString());
                }
                , success: function (data) {
                    alert("SUCCESS!!!!!!!!");
                    console.log("success============");
                    if (data) {
                        console.log(data);
                        if(data['code']===200){
                            let data = data['data'];
                            // window.location.href = "http://localhost:8080/SPMS/index.html";
                            console.log("ok")
                        } else {
                            alert('登录失败');
                            // window.location.href = "http://localhost:8080/SPMS/login.html";
                        }

                    }
                }
            })
        },

        toRegister() {
            window.location.href="./register.html"
        }
    },
    computed: {

    }

})