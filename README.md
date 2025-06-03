# Kafka 聊天室與註冊系統

這是一個基於 Spring Boot 和 Kafka 的即時聊天應用程式，同時整合了同步和異步的註冊功能。

## 功能特點

### 聊天室功能
- 即時訊息傳送
- 支援多人同時在線聊天
- 特殊訊息效果（當發送"跑車"時會觸發動畫效果）
- 響應式設計，支援手機和電腦瀏覽

### 註冊系統
- 提供同步和異步兩種註冊方式
- 同步註冊：模擬郵件發送失敗的情況
- 異步註冊：使用 Kafka 處理郵件發送
- 即時通知註冊狀態

## 技術架構

### 後端技術
- Spring Boot
- Spring Kafka
- Spring Mail
- Server-Sent Events (SSE)
- WebSocket

### 前端技術
- HTML5
- CSS3
- JavaScript
- Bootstrap 5

## 系統需求

- Java 17 或以上
- Maven 3.6 或以上
- Kafka 2.8 或以上
- 現代瀏覽器（Chrome、Firefox、Safari、Edge）

## 安裝步驟

1. 克隆專案
```bash
git clone [專案URL]
cd kafkademo
```

2. 安裝依賴
```bash
mvn clean install
```

3. 啟動 Kafka
```bash
# 啟動 Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# 啟動 Kafka
bin/kafka-server-start.sh config/server.properties
```

4. 啟動應用程式
```bash
mvn spring-boot:run
```

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

## 專案結構

```
src/main/java/com/example/kafkademo/
├── config/          # 配置類
├── controller/      # 控制器
├── model/          # 數據模型
├── service/        # 服務層
└── KafkaDemoApplication.java

src/main/resources/
├── static/         # 靜態資源
├── templates/      # 模板文件
└── application.properties
```

## 注意事項

1. 確保所有設備都在同一個網路環境下
2. 檢查防火牆設置，確保 8080 端口可訪問
3. 如果遇到連接問題，請檢查：
   - Kafka 服務是否正常運行
   - 網路連接是否正常
   - 應用程式日誌是否有錯誤信息

## 開發者

[您的名字/團隊名稱]

## 授權

[授權信息] 