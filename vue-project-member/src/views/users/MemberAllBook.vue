<template>
    <section class="main">
        <section class="information" v-if="book.id">
            <h2>{{ book.name }}</h2>
            <section class="infor-wrap">
                <img :src="book.photo" alt="Image Preview" class="infor-image"/>
                <section class="infor-middle-right">
                    <div>
                        <label>作者:</label>
                        <p>{{ book.author }}</p>
                    </div>
                    <div>
                        <label>出版日期:</label>
                        <p>{{ book.publicationDate }}</p>
                    </div>
                    <div>
                        <label>租借金額(本/元):</label>
                        <p>{{ book.price }}</p>
                    </div>
                    <div>
                        <label>剩餘數量:</label>
                        <p>{{ book.nowQuantity }}</p>
                    </div>
                </section>
            </section>
            <div>
                <pre class="intro">{{ book.introduce }}</pre>
            </div>
        </section>
        <div class="form-floating sl bar"  v-if="stores">
            <select class="form-select" id="floatingSelectGrid" v-model="storeId" @change="searchStore">
                <!-- 隱藏的預設選項 -->
                <option value="" style="display: none" disabled selected>無</option>
                <option :value="sto.id" v-for="(sto,index) in stores" :key="index">{{sto.name}}</option>
            </select>
            <label for="floatingSelectGrid">選擇店家</label> 
        </div>
        <!-- 上架table -->
        <table class="table table-dark table-striped main_table">
            <thead class="main_thead">
                <tr>
                    <th class="infor"></th>
                    <th class="photo">漫畫封面</th>
                    <th class="name">漫畫名稱</th>
                    <th class="author">作者</th>
                    <th class="price">租借價格</th>
                    <th class="nowQuantity">剩餘數量</th>
                    <th class="quantity"></th>
                    <th class="order"></th>
                </tr>
            </thead>
            <tbody v-if="books && books.length > 0">
                <tr v-for="(book,index) in books" :key="index" v-show="book.checkCondition==true">
                    <td>
                        <button type="button" class="btn btn-light" @click.prevent="bookInfor(book.id)">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-info-circle" viewBox="0 0 16 16">
                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                <path d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0"/>
                            </svg>
                        </button>
                    </td>
                    <td>
                        <img :src="book.photo" alt="Image Preview" class="preview-image"/>
                    </td>
                    <td>{{ book.name }}</td>
                    <td>{{ book.author }}</td>
                    <td>{{ book.price }}</td>
                    <td>{{ book.nowQuantity }}</td>
                    <td class="">
                        <div class="form-floating mb-3 inputBar">
                            <input type="number" class="form-control " id="floatingInput" @change="setOrder(book.id,$event.target.value)" min="0" :max="book.nowQuantity" 
                            >
                            <label for="floatingInput">租借數量</label>
                        </div>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary bt" @click="sendOrder(book.id)">租借</button>
                    </td>
                </tr>
            </tbody>
        </table>

    </section>
</template>
    
<script setup>
    import { onMounted,ref,toRaw,defineProps,watch } from 'vue';
    import axiosInstance from '../../utils/axiosInstance';
    import { useRoute } from 'vue-router';
	import { useRouteStore } from '../../stores/routeStore'
	const route = useRoute();
	const routeStore = useRouteStore();
	// 當路由發生變化時，更新路由參數
	routeStore.setMemberId(route.params.id);
    // 獲取URL中的參數 id
    const memberId = Number(route.params.id); // 將 id 轉換為數字
    const book=ref([]);
    const books=ref([]);
    const stores=ref();
    const storeId=ref();
    const orderMap = new Map();
    //mounted階段初始化
    onMounted(() => {
        callAllStores();
    });
    //選完店搜尋所有書籍
    function searchStore(){
        callAllBooks(storeId.value);
    }
    //查看書籍詳情
    function bookInfor(bookId){
        callFindById(bookId);
    }
    //設置暫時order
    function setOrder(bookId,quantity){
        orderMap.set(bookId,quantity);
    }
    function sendOrder(bookId){
        callInsertOrder(bookId);
        setTimeout(()=>{
            callAllBooks(storeId.value);
        },200)
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
    //搜尋單本書籍
    async function callFindById(id) {
        Swal.fire({
            title: "Loading.....",
            showConfirmButton: false,
            allowOutsideClick: false,
        });
        axiosInstance.get(`/books/findById/${id}`).then(function(response) {
            if(response.data.message=="Success") {
                book.value=response.data.data;
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
    //新增訂單
    async function callInsertOrder(bookId) {
        Swal.fire({
            title: "Loading.....",
            showConfirmButton: false,
            allowOutsideClick: false,
        });
        let rawData ={
            memberId:memberId,
            bookId:bookId,
            quantity:orderMap.get(bookId),
        };
        axiosInstance.post("/order/insert", rawData).then(function(response) {
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
    

</script>
    
<style scoped>
    td{
        padding: 5px 20px 5px 20px;
        text-align: center;
        line-height:100px ;
        vertical-align: middle;
    }
    .information{
        background-color: rgb(132, 132, 172);
        width: 1400px;
        padding: 20px 140px 20px 140px;
        margin-bottom: 20px;
    }
    .infor-wrap{
        display: flex;
        padding: 10px 80px 30px 80px;
    }
    .infor-middle-right{
        margin-left: 160px;
        margin-top: 30px;
    }
    .intro {
    white-space: pre-wrap;
    word-wrap: break-word;
    color: #3d3b3b;
    /* 字體顏色變淺 */
    margin-top: 10px;
    font-size: 16px;
}
    .main{
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 60px;
        flex-direction: column;
    }
    .main_table{
        margin-top: 20px;
        width: 1400px;
    }
    .main_thead{
        height: 50px;
    }
    .main_thead>tr>th{
        text-align: center;
    }
    .quantity{
        width: 300px;
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
    .nowQuantity{
        width: 300px;
    }
    .infor{
        width: 200px;
    }
    .photo{
        width: 200px;
    }
    .infor-image {
        margin-top: 30px;
        height: 250px;
        width: 180px;
        object-fit: cover;
    }
    .preview-image {
        width: 100%;
        height: 100px;
        object-fit: cover;
    }
    .inputBar{
        width: 100px;
        margin-top: 20px;
    }
    .bt{
        width: 80px;
    }
</style>