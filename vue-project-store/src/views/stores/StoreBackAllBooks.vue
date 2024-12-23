<template>
    <div class="accordion wrap" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                已上架
            </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <!-- 上架table -->
                    <table class="table table-dark table-striped main_table">
                        <thead class="main_thead">
                            <tr>
                                <th class="edit"></th>
                                <th class="name">漫畫名稱</th>
                                <th class="author">作者</th>
                                <th class="price">租借價格</th>
                                <th class="checkCondition">上架狀態</th>
                                <th class="delete"></th>
                            </tr>
                        </thead>
                        <tbody v-if="books && books.length > 0">
                            <tr v-for="(book,index) in books" :key="index" v-show="book.checkCondition==true">
                                <td>
                                    <button type="button" class="btn btn-light" @click="doclick1(book.id)">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                            <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
                                        </svg>
                                    </button>
                                </td>
                                <td>{{ book.name }}</td>
                                <td>{{ book.author }}</td>
                                <td>{{ book.price }}</td>
                                <td>{{ book.checkCondition }}</td>
                                <td>
                                    <button type="button" class="btn btn-danger" @click="callDelete(book.id)">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-square" viewBox="0 0 16 16">
                                            <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
                                        </svg>
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    
                </div>
            </div>
        </div>
    </div>

    <div class="accordion wrap" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                未上架
            </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <!-- 未上架table -->
                    <table class="table table-dark table-striped main_table">
                        <thead class="main_thead">
                            <tr>
                                <th class="edit"></th>
                                <th class="name">漫畫名稱</th>
                                <th class="author">作者</th>
                                <th class="price">租借價格</th>
                                <th class="checkCondition">上架狀態</th>
                                <th class="delete"></th>
                            </tr>
                        </thead>
                        <tbody v-if="books && books.length > 0">
                            <tr v-for="(book,index) in books" :key="index" v-show="book.checkCondition==false">
                                <td>
                                    <button type="button" class="btn btn-light" @click="doclick1(book.id)">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                            <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
                                        </svg>
                                    </button>
                                </td>
                                <td>{{ book.name }}</td>
                                <td>{{ book.author }}</td>
                                <td>{{ book.price }}</td>
                                <td>{{ book.checkCondition }}</td>
                                <td>
                                    <button type="button" class="btn btn-danger" @click="callDelete(book.id)">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-square" viewBox="0 0 16 16">
                                            <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
                                        </svg>
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    
                </div>
            </div>
        </div>
    </div>
</template>
    
<script setup>
    import { onMounted,ref,toRaw,defineProps,watch } from 'vue';
    import axiosInstance from '../../utils/axiosInstance';
    const books=ref([]);
    const storeId=ref();
    //接收父組件id
    const props=defineProps(["storeId","resetFlag"]);
    //監聽props
    watch(()=>props.storeId,
        ()=>{
            storeId.value=props.storeId;
            callAllBooks(storeId.value);
    });
    watch(()=>props.resetFlag,
        ()=>{
    
            setTimeout(()=>{
                callAllBooks(storeId.value);
            },200)
    });
    //刪除用函數
    async function callDelete(id){
        await callRemove(id);
        await callAllBooks(storeId.value);
        emits("delete-ok");
    }
    //搜尋所有書籍
    async function callAllBooks(storeId) {
        axiosInstance.get(`/books/getbook/${storeId}`).then(function(response) {
            if(response.data.message=="Success") {
                books.value=response.data.data;
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
            axiosInstance.delete(`/books/delete/${id}`).then(function(response) {
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
    }
    //傳遞update請求
    const emits = defineEmits(["update","updateData","delete-ok"]);
    function doclick1(id) {
        emits("updateData", id);
    }
    

</script>
    
<style scoped>
    td{
        padding: 5px 20px 5px 20px;
        text-align: center;
    }
    .wrap{
        margin-top: 30px;
        width: 1050px;
    }
    .main_table{
        margin-top: 20px;
        width: 1000px;
    }
    .main_thead{
        height: 50px;
    }
    .main_thead>tr>th{
        text-align: center;
    }
    .edit{
        width: 100px;
    }
    .name{
        width: 1000px;
    }
    .author{
        width: 600px;
    }
    .price{
        width: 500px;
    }
    .checkCondition{
        width: 300px;
    }
    .delete{
        width: 200px;
    }
</style>