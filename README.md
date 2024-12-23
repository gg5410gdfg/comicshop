# ComicStore

## 功能
  漫畫店CRUD
  會員資料CRUD
  漫畫CRUD
  租借漫畫功能
  還書審核功能
  漫畫下架功能

## 環境設定
  Spring:Maven4.0.0
  spring-boot-starter-parent3.4.1

```application.properties(修改username與password):
  spring.application.name=ComicStore
  
  spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
  spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ComicStore;trustServerCertificate=true
  spring.datasource.username=sa
  spring.datasource.password=b30403040
  
  
  spring.jpa.hibernate.ddl-auto=none
  #維持資料庫命名策略一致
  spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
  spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
  spring.jpa.properties.hibernate.format_sql=true
  spring.jpa.show-sql=false
  #循環依賴
  spring.main.allow-circular-references=true
```

## 執行步驟
* Mssql資料庫匯入
執行資料夾內的createSql.sql生成資料庫格式與初始資料

* 將資料夾放至c:\下,Spring IDE匯入import ComicStore(專案)
無法匯入需開啟新的spring starter project
* type:maven packaging:jar java version:17
    * group:com.store
    * artifact:ComicStore
    * version:0.0.1-SNAPSHOT
    * description:Demo project for Spring Boot
    * package:com.store.comic
* depencies
    * graalvm native support
    * lombok
    * ms sql server driver
    * spring boot devtools
    * spring configuration processor
    * spring data jpa
    * spring web
將下載的檔案中的src/mmain/java下檔案放到新檔案對應位置
application.properties(修改username與password)
啟動ComicStore(埠號8080)

* VScode開啟外層資料夾 終端輸入 
cd c:\bookstore\vue-project-store
npm install
npm run dev
(連到網址http://localhost:5173/)(後台)
* 再開啟一個終端輸入
cd c:\bookstore\vue-project-member
npm install
npm run dev
(連到網址http://localhost:5174/)(前台)


## 測試方式

### 書店增刪改查
* (後台)
(書店後台>>書店一覽)
* 新增
    * 正義北店
    * 0222481144
    * 新北市三重區正義北路

* 修改
    * 淡水店>>象山店
    *  1111111111>>9876543210
    * 新北市淡水區應專路>>台北市信義區吳興街250號

* 刪除象山店

* (前台)
### 用戶增刪改查
* 新增
    * 王超
    * 0911221221

* 修改
    * 老趙>>老高
    * 2222222222>>0922313313

* 刪除老高

### 漫畫增刪改查
點擊王超姓名或聯絡方式欄位進到(書籍一覽)

選擇店家選擇大安店
應有4筆資料

* (後台)
回到(書店後台>>書店管理)
選擇店家選擇大安店
應有已上架4筆與未上架1筆

* 新增
    * 膽大黨 7 (首刷限定版)
    * 龍幸伸
    * 2024/12/05
    * 圖片(photos資料夾裡的getImage (3).jpg)
    * 25(全部庫存)     25(全部庫存)     60(租借價格)
    * 
```
附在寺仁身上的邪視，被小桃的奶奶‧星子關在人體模型太郎的體內，然而仍然留在寺仁的身體裡，而且寺仁的身體一接觸涼水，邪視就會出現，接觸熱水就會變回寺仁。

　　因此厄卡倫和愛羅連日在小桃家過夜，所有人監視寺仁，不讓他變成邪視！然後，祓除邪視的儀式要開始了，但？

　　首刷限定
　　明信片2張 約10*15cm 紙質

ISBN：9786263602021
叢書系列：少年系列
規格：平裝 / 194頁 / 11.3 x 17 x 1 cm / 普通級 / 單色印刷 / 初版
出版地：台灣
本書分類：漫畫/圖文書> 靈異／神怪
```
   * 選擇上架之後新增

查看下方已上架為五筆 回到前台重整後選擇(大安店)也為五筆

* 修改
回到(書店後台>>書店管理)
選擇店家選擇大安店
選擇未上架 拳願奧米迦的左側編輯鍵
選擇上架後點修改鍵
回到前台(書籍一覽)重整選擇大安店資料為六筆
且可點選左側資訊鍵在上方頁面查看詳情

### 租借訂單
* 前台(書籍一覽)(大安店)
在四月是你的謊言 (4) (電子書)選擇7本後點選租借
剩餘數量17>>10
到(訂單一覽)
點開未歸還可看到該訂單
在(書店後台>>訂單管理)點選未歸還也可以看到該筆訂單

在會員端(訂單一覽)未歸還的該筆訂單點選還書鍵 該訂單會移動到上方歸還中
在(書店後台>>訂單管理)重整後點選歸還申請將該會員的訂單確認歸還後
資料庫會刷新書店現有的剩餘書籍數量
回到前台(書籍一覽)(大安店)可以看到四月是你的謊言 (4) (電子書)的剩餘數量重新回到17本
