# 채팅 서비스 API 명세
### 1. 채팅방 생성
- **요청**
  - URL: POST /api/chatroom
  - Headers
    - Authorization: Bearer {accessToken}
  - Body (JSON):
    ```json
    {
      "roomTitle": "채팅방 제목"
    }
    ```
- **응답**
  - HTTP 200 OK
  - Body (JSON):
    ```json
    {
      "id": 1,
      "userId": 1,
      "title": "채팅방 제목",
      "createdAt": "2025-04-28T02:17:52.939Z"
    }
    ```
    
---

## 2. 나의 채팅방 목록 조회 (GET /api/chatrooms)

- **요청**
    - URL: `/api/chatrooms`
    - Method: `GET`
    - Header: Authorization (Bearer AccessToken 필요)

- **응답**
    - HTTP 200 OK
    - Body:
      ```json
      [
        {
          "id": 1,
          "userId": 1,
          "title": "채팅방 제목",
          "createdAt": "2025-04-28T02:17:52.939Z"
        },
        ...
      ]
      ```

---

## 3. 채팅방 입장 및 채팅 내역 조회 (WebSocket)

- **WebSocket 연결**
    - 엔드포인트: `/ws-stomp`
    - 프로토콜: STOMP over SockJS

- **채팅방 구독 (Subscribe)**
    - 구독 대상: `/sub/chatroom/{roomId}`
    - 예시:
      ```
      /sub/chatroom/1
      ```

- **입장 시 메시지 전송 (Send)**
    - 전송 대상: `/pub/chatroom/{roomId}`
    - Body (JSON):
      ```json
      {
        "senderId": 1,
        "senderName": "닉네임",
        "content": "",
        "messageType": "TEXT",
        "eventType": "ENTER"
      }
      ```

- **입장 후 서버가 보내는 메시지**
    - 형식 (ChatMessageResponse):
      ```json
      {
        "senderId": 1,
        "senderName": "닉네임",
        "content": "닉네임님이 입장했습니다.",
        "messageType": "TEXT",
        "eventType": "ENTER",
        "createdAt": "2025-04-28T02:17:52.939Z"
      }
      ```

---

## 4. 채팅 메시지 전송 (WebSocket)

- **메시지 전송 (Send)**
    - 전송 대상: `/pub/chatroom/{roomId}`
    - Body (JSON):
      ```json
      {
        "senderId": 1,
        "senderName": "닉네임",
        "content": "보낼 메시지",
        "messageType": "TEXT" | "PICTURE" | "VIDEO",
        "eventType": "NONE" | "ENTER" | "LEAVE",
      }
      ```

- **서버에서 보내는 메시지**
    - 형식 (ChatMessageResponse):
      ```json
      {
        "senderId": 1,
        "senderName": "닉네임",
        "content": "보낼 메시지",
        "messageType": "TEXT",
        "eventType": "NONE",
        "createdAt": "2025-04-28T02:17:52.939Z"
      }
      ```

---

## 5. 채팅방 내 채팅 내역 조회 (GET /api/chatmessage/{roomId})

- **요청**
    - URL: `/api/chatmessage/{roomId}`
    - Method: `GET`
    - Header: Authorization (Bearer AccessToken 필요)

- **응답**
    - HTTP 200 OK
    - Body:
      ```json
      [
        {
          "id": 1,
          "senderId": 1,
          "roomId": 1,
          "senderName": "닉네임",
          "content": "보낸 메시지",
          "messageType": "TEXT",
          "eventType": "NONE",
          "createdAt": "2025-04-28T02:17:52.939Z"
        },
        ...
      ]
      ```

---

# 채팅 메시지 타입 (messageType)
- `TEXT`: 일반 텍스트 메시지
- `PICTURE`: 이미지 메시지
- `VIDEO`: 동영상 메시지

# 채팅 이벤트 타입 (eventType)
- `ENTER`: 채팅방 입장
- `LEAVE`: 채팅방 퇴장
- `NONE`: 일반 채팅 메시지