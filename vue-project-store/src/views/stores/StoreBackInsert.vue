<template>

        <section class="main">
            <section class="top-left">
                <div class="box">
                    <!--沒有圖像的話顯示+號-->
                    <div v-if="!book.photo" class="add" @click="triggerFileInput">➕</div>
        
                    <!--有圖像的話顯示圖像 -->
                    <img v-if="book.photo" :src="book.photo" alt="Image Preview" class="preview-image" @click="triggerFileInput" />
        
                    <!-- 隱藏input -->
                    <input type="file" ref="fileInput" class="theFile" accept="image/gif, image/jpeg, image/png" @input="handleFileChange"/>
                </div>
                <section class="top-middle">
                    <div class="form-floating mb-3  bar">
                        <input type="text" class="form-control" id="floatingInput" v-model="book.name">
                        <label for="floatingInput">漫畫名稱</label>
                    </div>
                    <div class="form-floating mb-3  bar">
                        <input type="text" class="form-control" id="floatingInput" v-model="book.author">
                        <label for="floatingInput">漫畫作者</label>
                    </div>
                    <div class="form-floating mb-3  bar">
                        <input type="date" class="form-control" id="floatingInput" v-model="book.publicationDate">
                        <label for="floatingInput">出版日期</label>
                    </div>
                    
                </section>
                <div class="form-floating">
                    <label for="floatingTextarea2">漫畫介紹</label>
                    <textarea cols="30" rows="10" class="form-control textBar" name="introduce" id="floatingTextarea2" style="height: 300px" v-model="book.introduce"  ></textarea>
                </div>
            </section>
            <section class="number-section">
                <div class="form-floating mb-3  bar number">
                    <input type="number" class="form-control" id="floatingInput" min="1" v-model="book.totalQuantity" max="9999">
                    <label for="floatingInput">全部庫存</label>
                </div>
                <div class="form-floating mb-3  bar number">
                    <input type="number" class="form-control" id="floatingInput" v-model="book.nowQuantity" min="1" :max="book.totalQuantity">
                    <label for="floatingInput">剩餘庫存</label>
                </div>
                <div class="form-floating mb-3  bar number">
                    <input type="number" class="form-control" id="floatingInput" v-model="book.price" max="999">
                    <label for="floatingInput">租借價格</label>
                </div>
                <section class="radio">
                    <div class="form-check rad">
                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" checked v-model="book.checkCondition" :value=true>
                        <label class="form-check-label" for="flexRadioDefault1">
                            上架
                        </label>
                    </div>
                    <div class="form-check rad">
                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" v-model="book.checkCondition" :value=false>
                        <label class="form-check-label" for="flexRadioDefault2">
                            下架
                        </label>
                    </div>
                </section>
                <button type="button" class="btn btn-primary bt" @click="reset">清空</button>
                <button type="button" class="btn btn-primary bt" @click="callFunc('Insert')" v-if="isInsert===true">新增</button>
                <button type="button" class="btn btn-primary bt" @click="callFunc('Update')" v-if="isInsert===false">修改</button>
            </section>
        </section>
</template>
    
<script setup >
    import { onMounted,ref,toRaw,defineProps,watch } from 'vue';
    import axiosInstance from '../../utils/axiosInstance';
    const stores=ref();
    const isInsert=ref(true);
    const book=ref({
        storeId:"",
        name:"",
        totalQuantity:"1",
        nowQuantity:"1",
        photo:"",
        introduce:"",
        author:"",
        publicationDate:"",
        price:"",
        checkCondition:true
    });
    //mounted階段初始化
    onMounted(() => {
        callAllStores();
    });
    function reset(){
        book.value={
            storeId:props.storeId,
            name:"",
            totalQuantity:"1",
            nowQuantity:"1",
            photo:"",
            introduce:"",
            author:"",
            publicationDate:"",
            price:"",
            checkCondition:true
        };
        isInsert.value=true;
    }
    //接收父組件id
    const props=defineProps(["storeId","id","updateFlag","resetFlag"]);
    //監聽props
    watch(()=>props.storeId,
        ()=>{
            book.value.storeId=props.storeId;
    });
    watch(()=>props.updateFlag,
        ()=>{
        callFindById(props.id);
        isInsert.value=false;
    });
    watch(()=>props.resetFlag,
        ()=>{
            reset();
    });
    //隱藏input的代理click事件
    function triggerFileInput() {
        const fileInput = document.querySelector('.theFile');
        fileInput.click();
    }
    //上傳圖片處理
    function handleFileChange(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (e) => {
                book.value.photo=e.target.result;
            };    
        }
        event.target.value = '';
    };
    //傳遞update通知
    const emits = defineEmits(["update-ok"]);
        
    //通知函數
    function callFunc(condition){
        if(condition=="Update"){
            callUpdate();
        }else{
            callInsert();
        }
        emits("update-ok");
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
        let rawData = toRaw(book.value);
        axiosInstance.post("/books/insert", rawData).then(function(response) {
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
        let rawData = toRaw(book.value);
        axiosInstance.put("/books/update", rawData).then(function(response) {
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

</script>
    
<style scoped>
    .main{
        background-color: rgb(189, 205, 226);
        width: 1030px;
        border-radius: 20px;
        padding: 20px;
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
    .number-section{
        display: flex;
        width: 990px;
        /* background-color: blue; */
        /* justify-content: center; */
    }
    .number{
        width: 80px;
        margin-left: 70px;
    }
    .radio{
        margin-top: 10px;
        margin-left: 240px;
    }
    .rad{
        /*  */
        margin-left: 20px;
    }
    .bt{
        height: 50px;
        margin-top:10px;
        margin-left: 40px;
    }
    .add {
        font: bold 100px Tahoma;
        color: #ccc;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        line-height: 180px;
        vertical-align: middle;
        margin-top: 0px;
        cursor: pointer;
        height: 180px;
        width:  150px;
        background-color: grey;
    }
    .preview-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
        cursor: pointer;
    }
    .theFile {
        height: 180px;
        width:  150px;
        opacity: 0;
        position: absolute;
        top: 0;
        left: 0;
    }
    .textBar{
        height: 350px;
        width:  380px;
    }
</style>