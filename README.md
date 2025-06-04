# Kafka 多功能展示系統

這是一個使用 Spring Boot 和 Kafka 實現的多功能系統，包含即時聊天、註冊流程對比和交易處理功能。

## 功能特點

### 1. 聊天室功能
- 即時訊息傳送
- 支援多人同時在線聊天
- 特殊訊息效果（當發送"跑車"時會觸發動畫效果）
- 響應式設計，支援手機和電腦瀏覽

### 2. 註冊流程對比
- 提供同步和異步兩種註冊方式
- 同步註冊：模擬郵件發送失敗的情況
- 異步註冊：使用 Kafka 處理郵件發送
- 即時通知註冊狀態

### 3. 交易管理
- 新增交易記錄
- 查詢交易歷史
- 交易狀態追蹤
- 交易統計分析

### 4. 交易查詢
- 按日期範圍查詢
- 按交易狀態篩選
- 即時統計數據顯示
  - 總交易筆數
  - 總交易金額
  - 成功/失敗交易數量

### 5. 交易重播功能
- 支援特定時間範圍的交易重播
- 自動重新發送到 Kafka 主題
- 適用於：
  - 系統恢復
  - 數據遷移
  - 歷史數據分析
  - 錯誤修復

## 技術架構

- Spring Boot 3.2.3
- Spring Kafka
- Spring Data JPA
- Spring Mail
- Server-Sent Events (SSE)
- WebSocket
- PostgreSQL
- Thymeleaf
- Bootstrap 5

## 系統需求

- Java 17 或以上
- PostgreSQL 資料庫
- Kafka 伺服器
- 現代瀏覽器（Chrome、Firefox、Safari、Edge）

## 設置步驟

1. 資料庫設置
   ```sql
   CREATE DATABASE kafkademo;
   ```

2. 配置資料庫連接
   - 編輯 `src/main/resources/application.properties`
   - 確認資料庫連接資訊：
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/kafkademo
     spring.datasource.username=postgres
     spring.datasource.password=!QAZ2wsx#EDC
     ```

3. 啟動應用程式
   ```bash
   ./mvnw spring-boot:run
   ```

4. 訪問系統
   - 聊天室：http://localhost:8080
   - 註冊頁面：http://localhost:8080/register
   - 交易歷史：http://localhost:8080/payment-history

## 使用說明

### 聊天室
1. 訪問 `http://localhost:8080`
2. 輸入用戶名進入聊天室
3. 開始聊天
4. 發送"跑車"可以觸發特殊動畫效果

### 註冊系統
1. 訪問 `http://localhost:8080/register`
2. 選擇註冊方式：
   - 同步註冊：會模擬郵件發送失敗
   - 異步註冊：使用 Kafka 處理郵件發送
3. 填寫用戶名和郵箱
4. 提交註冊

### 新增交易
1. 在「新增交易」區塊填寫：
   - 交易金額
   - 交易狀態
   - 交易描述
   - 用戶名稱
2. 點擊「新增交易」按鈕

### 查詢交易
1. 在「查詢條件」區塊選擇：
   - 開始日期
   - 結束日期
   - 交易狀態
2. 點擊「查詢」按鈕

### 重播交易
1. 在「查詢條件」區塊選擇：
   - 開始日期
   - 結束日期
2. 點擊「重播交易」按鈕

## 注意事項

1. 聊天室功能
   - 確保所有設備都在同一個網路環境下
   - 檢查防火牆設置，確保 8080 端口可訪問

2. 交易重播功能
   - 重播會將所有符合條件的交易重新發送到 Kafka
   - 請確保下游系統能夠處理重複的交易數據
   - 建議在非尖峰時段執行重播操作

3. 資料庫維護
   - 定期備份資料庫
   - 監控資料庫空間使用情況
   - 定期清理過期數據

## 開發團隊

- 開發者：William.Fan

## 授權

MIT License
