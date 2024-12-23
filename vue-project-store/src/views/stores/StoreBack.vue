<template>
    <section class="main">
        <div class="form-floating sl bar"  v-if="stores">
            <select class="form-select" id="floatingSelectGrid" v-model="storeId">
                <!-- 隱藏的預設選項 -->
                <option value="" style="display: none" disabled selected>無</option>
                <option :value="sto.id" v-for="(sto,index) in stores" :key="index">{{sto.name}}</option>
            </select>
            <label for="floatingSelectGrid">選擇店家</label> 
        </div>
        <StoreBackInsert :storeId="storeId" :id="id" :resetFlag="resetFlag"  :updateFlag="updateFlag" @update-ok="update"></StoreBackInsert>
        <StoreBackAllBooks :storeId="storeId" :resetFlag="resetFlag"  :updateFlag="updateFlag" @delete-ok="update" @updateData="updateData"></StoreBackAllBooks>
    </section>
</template>
    
<script setup>
    import StoreBackInsert from './StoreBackInsert.vue';
    import StoreBackAllBooks from './StoreBackAllBooks.vue';
    import { onMounted,ref,toRaw,defineProps } from 'vue';
    import axiosInstance from '../../utils/axiosInstance';
    const stores=ref();
    const storeId=ref();
    const updateFlag=ref(true);
    const resetFlag=ref(true);
    const id=ref();
    function updateData(bookId){
        id.value=bookId;
        updateFlag.value=!updateFlag.value;
    }
    function update(){
        resetFlag.value=!resetFlag.value;
    }
     //mounted階段初始化
    onMounted(() => {
        callAllStores();
    });
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
</script>
<style scoped>
    .main{
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-top: 20px;
    }
    .bar{
        width: 400px;
        margin-bottom: 30px;
    }
</style>