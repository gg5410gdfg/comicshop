<template>
    <section class="wrap">
        <section class="main">
            <section class="top-left">
                <section class="top-middle">
                    <div class="form-floating mb-3  bar">
                        <input type="text" class="form-control" id="floatingInput" v-model="store.name">
                        <label for="floatingInput">店名</label>
                    </div>
                    <div class="form-floating mb-3  bar">
                        <input type="text" class="form-control" id="floatingInput" v-model="store.telePhone">
                        <label for="floatingInput">聯絡方式</label>
                    </div>
                    <div class="form-floating mb-3  bar">
                        <input type="text" class="form-control" id="floatingInput" v-model="store.address">
                        <label for="floatingInput">地址</label>
                    </div>
                </section>
                <button type="button" class="btn btn-primary bt" @click="reset">清空</button>
                <button type="button" class="btn btn-primary bt" @click.prevent="callFunc()" v-if="isInsert===true">新增</button>
                <button type="button" class="btn btn-primary bt" @click="callChange()" v-if="isInsert===false">修改</button>
            </section>
        </section>
        
        <table class="table table-dark table-striped main_table">
                <thead class="main_thead">
                    <tr>
                        <th class="edit"></th>
                        <th class="name">店名</th>
                        <th class="telePhone">聯絡方式</th>
                        <th class="address">地址</th>
                        <th class="delete"></th>
                    </tr>
                </thead>
                <tbody v-if="stores && stores.length > 0">
                    <tr v-for="(store,index) in stores" :key="index">
                        <td class="edit">
                            <button type="button" class="btn btn-light" @click="callFindFunc(store.id)">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                    <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
                                </svg>
                            </button>
                        </td>
                        <td>{{ store.name }}</td>
                        <td>{{ store.telePhone }}</td>
                        <td>{{ store.address }}</td>
                        <td>
                            <button type="button" class="btn btn-danger" @click="callDelete(store.id)">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-square" viewBox="0 0 16 16">
                                    <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
                                </svg>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
    </section>
</template>
    
<script setup>
    import { onMounted,ref,toRaw,defineProps,watch } from 'vue';
    import axiosInstance from '../../utils/axiosInstance';
    const store=ref({
        name:"",
        telePhone:"",
        address:"",
    });
    const isInsert=ref(true);
    const stores=ref([]);
    //mounted階段初始化
    onMounted(() => {
        callAllStores();
        console.log("stores.value",stores.value)
    });
    //重置函數
    function reset(){
        store.value={
            name:"",
            telePhone:"",
            address:"",
        };
    }
    //新增函數
    function callFunc(){
        callInsert();
        reset();
        setTimeout(() => {
            callAllStores();
        }, 300);
    }
    //修改函數
    function callChange(){
        callUpdate();
        reset();
        setTimeout(() => {
            callAllStores();
        }, 300);
    }
    //刪除函數
    function callDelete(id){
        callRemove(id);
        callAllStores();
    }
    //賦值函數(callFindById)
    function callFindFunc(storeId){
        callFindById(storeId);
        isInsert.value=false;
    }
    //搜尋所有店家
    async function callAllStores() {
        axiosInstance.get("/store/getstore").then(function(response) {
            if(response.data.message=="Success") {
                stores.value=response.data.data;
            }else{
                Swal.fire({
                    icon: "warning",
                    title: response.data.errorDetail,
                })
            }
        }).catch(function(error) {
            Swal.fire({
                icon: "error",
                title: error.message,
            });
        });
    }
    //新增書籍
    async function callInsert() {
        Swal.fire({
            title: "Loading.....",
            showConfirmButton: false,
            allowOutsideClick: false,
        });
        let rawData = toRaw(store.value);
        axiosInstance.post("/store/insert", rawData).then(function(response) {
            if(response.data.message=="Success") {
                Swal.fire({
                    icon: "success",
                    title: response.data.message,
                })
        
            }else{
                Swal.fire({
                icon: "warning",
                title: response.data.errorDetail,
                })
            }
        }).catch(function(error) {
            Swal.fire({
                icon: "error",
                title: error.message,
            });
        });
    }
    //修改書籍
    async function callUpdate() {
        Swal.fire({
            title: "Loading.....",
            showConfirmButton: false,
            allowOutsideClick: false,
        });
        let rawData = toRaw(store.value);
        axiosInstance.put("/store/update", rawData).then(function(response) {
            if(response.data.message=="Success") {
                Swal.fire({
                    icon: "success",
                    title: response.data.message,
                })
        
            }else{
                Swal.fire({
                icon: "warning",
                title: response.data.errorDetail,
                })
            }
        }).catch(function(error) {
            Swal.fire({
                icon: "error",
                title: error.message,
            });
        });
    }
    //搜尋單店
    async function callFindById(id) {
        Swal.fire({
            title: "Loading.....",
            showConfirmButton: false,
            allowOutsideClick: false,
        });
        axiosInstance.get(`/store/findById/${id}`).then(function(response) {
            if(response.data.message=="Success") {
                store.value=response.data.data;
                setTimeout(function() {
                Swal.close();
            }, 300);
        
            }else{
                Swal.fire({
                icon: "warning",
                title: response.data.errorDetail,
                })
            }
        }).catch(function(error) {
            Swal.fire({
                icon: "error",
                title: error.message,
            });
        });
    }
    //刪除該筆書籍
    async function callRemove(id) {
        const result = await Swal.fire({
            title: "確定刪除嗎？",
            showCancelButton: true,
        });
        if(result.isConfirmed) {
            Swal.fire({
                title: "Loading.....",
                showConfirmButton: false,
                allowOutsideClick: false,
            });
            axiosInstance.delete(`/store/delete/${id}`).then(function(response) {
                if(response.data.message=="Success") {
                    Swal.fire({
                        icon: "success",
                        title: response.data.message,
                    })
                    console.log("134354341");
                    callAllStores();
                }else{
                    Swal.fire({
                        icon: "warning",
                        title: response.data.errorDetail,
                    })
                }
            }).catch(function(error) {
                Swal.fire({
                    icon: "error",
                    title: error.message,
                });
            });
        }
    }
</script>
    
<style scoped>
    th,td{
        text-align: center;
    }
    .wrap{
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .main{
        background-color: rgb(189, 205, 226);
        width: 730px;
        border-radius: 20px;
        padding: 20px;
        display: flex;
        justify-content: center;
        margin-top: 60px;
    }
    .bar{
        width: 400px;
    }
    .box {
        height: 180px;
        width:  150px;
        text-align: center;
        position: relative;
        margin: 50px 20px 50px 20px;
    }
    .top-left{
        display: flex;
    }
    .top-middle{
        margin-top: 40px;
        margin-right: 20px;
    }
    .bt{
        height: 50px;
        margin-top:10px;
        margin-left: 40px;
    }
    .main_table{
        width: 1000px;
        margin-top: 60px;
    }
    .edit{
        width: 10%;
        margin-left: 10px;
    }
    .name{
        width: 20%;
    }
    .telePhone{
        width: 30%;
    }
    .delete{
        width: 10%;
        margin-right: 10px;
    }
</style>