<template>
    <section class="main" >

        <div class="accordion wrap" id="accordionExample">
            <div class="accordion-item">
                <h2 class="accordion-header">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                    歸還中
                </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        <!-- 已還待確認table -->
                        <table class="table table-dark table-striped main_table">
                            <thead class="main_thead">
                                <tr>
                                    <th class="photo">漫畫封面</th>
                                    <th class="name">漫畫名稱</th>
                                    <th class="quantity">租借數量</th>
                                    <th class="price">租借價格</th>
                                    <th class="time">租借時間</th>
                                    <th class="return"></th>
                                </tr>
                            </thead>
                            <tbody >
                                <tr v-for="(order,index) in orders" :key="index" v-show="order.checkReturn==1">
                                    <td>
                                        <img :src="order.booksBean.mimeType+order.booksBean.photo" alt="Image Preview" class="preview-image"/>
                                    </td>
                                    <td>{{ order.booksBean.name }}</td>
                                    <td>{{ order.memberOrderDetailsBean.quantity }}</td>
                                    <td>{{ order.booksBean.price }}</td>
                                    <td>{{ order.tradeTime }}</td>
                                    <td class="">
                                        已歸還，盤點中
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
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                    未歸還
                </button>
                </h2>
                <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        <!-- 未還table -->
                        <table class="table table-dark table-striped main_table">
                            <thead class="main_thead">
                                <tr>
                                    <th class="photo">漫畫封面</th>
                                    <th class="name">漫畫名稱</th>
                                    <th class="quantity">租借數量</th>
                                    <th class="price">租借價格</th>
                                    <th class="time">租借時間</th>
                                    <th class="return"></th>
                                </tr>
                            </thead>
                            <tbody >
                                <tr v-for="(order,index) in orders" :key="index" v-show="order.checkReturn==0">
                                    <td>
                                        <img :src="order.booksBean.mimeType+order.booksBean.photo" alt="Image Preview" class="preview-image"/>
                                    </td>
                                    <td>{{ order.booksBean.name }}</td>
                                    <td>{{ order.memberOrderDetailsBean.quantity }}</td>
                                    <td>{{ order.booksBean.price }}</td>
                                    <td>{{ order.tradeTime }}</td>
                                    <td class="">
                                        <button type="button" class="btn btn-light" @click.prevent="checkReturnBtn(order.id)">
                                            還書
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        
                    </div>
                </div>
            </div>
        </div>

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
    const orders=ref([]);
    //mounted階段初始化
    onMounted(() => {
        callAllOrders();
    });
    //歸還按鍵
    function checkReturnBtn(orderId){
        callUpdateCheckReturn(orderId);
        setTimeout(()=>{
            callAllOrders();
        },300)
    }
    //歸還按鍵後狀態更改(0=>1)
    //修改書籍
    async function callUpdateCheckReturn(orderId) {
        Swal.fire({
            title: "Loading.....",
            showConfirmButton: false,
            allowOutsideClick: false,
        });
        axiosInstance.put(`/order/update/member/${orderId}`).then(function(response) {
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
    //搜尋所有訂單
    async function callAllOrders() {
        axiosInstance.get(`/order/getorder/${memberId}`).then(function(response) {
            if(response.data.message=="Success") {
                orders.value=response.data.data;
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
    .wrap{
        margin-top: 30px;
        width: 1450px;
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

    .price{
        width: 500px;
    }
    .return{
        width: 300px;
    }
    .time{
        width: 800px;
    }
    .photo{
        width: 300px;
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