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
    - 파라미터: `?userId={userId}&roomId={roomId}`

- **채팅방 구독 (Subscribe)**
    - 구독 대상: `/sub/chatroom/{roomId}`
    - 읽음 상태 실시간 반영: `/sub/chatroom/{roomId}/read-update`
    - 전체 읽지 않은 메시지 개수 푸시: `/sub/user/{userId}/unread-summary`

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
    - `/sub/chatroom/{roomId}`로 응답:
    - 형식 (ChatMessageResponse):
      ```json
      {
        "chatId": 123,
        "senderId": 1,
        "senderName": "닉네임",
        "content": "닉네임님이 입장했습니다.",
        "messageType": "TEXT",
        "eventType": "ENTER",
        "createdAt": "2025-04-28T02:17:52.939Z",
        "unreadCount": 2
      }
      ```
- **입장 후 기존 메시지 조회 (REST API)**
  - URL: `GET /api/chatmessage/{roomId}`
  - 응답 형식: `[ChatMessageResponse]`

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
        "chatId": 123,
        "senderId": 1,
        "senderName": "닉네임",
        "content": "보낼 메시지",
        "messageType": "TEXT",
        "eventType": "NONE",
        "createdAt": "2025-04-28T02:17:52.939Z",
        "unreadCount": 1
      }
      ```
---

## 5. 읽음 상태 실시간 업데이트 (WebSocket)
- **서버가 전송하는 구독 메시지**
  - 구독 주소: `/sub/chatroom/{roomId}/read-update`
  - 전송 메시지 (단건 또는 배열):
    ```json
    [
      {
        "messageId": 123,
        "unreadCount": 0
      }
    ]
    ```
---

## 6. 채팅방 나가기(뒤로 가기) (WebSocket)
- **전송 대상:** `/pub/chatroom/{roomId}/exit`

## 7. 채팅방 퇴장 (WebSocket)
- **전송 대상:** `/pub/chatroom/{roomId}/leave`
- **요청 Body:**
  ```json
  {
    "senderId": 1,
    "senderName": "닉네임",
    "content": "",
    "messageType": "TEXT",
    "eventType": "LEAVE"
  }
  ```

- **서버 응답 메시지:**
  ```json
  {
    "chatId": 123,
    "senderId": 1,
    "senderName": "닉네임",
    "content": "닉네임님이 퇴장하셨습니다.",
    "messageType": "TEXT",
    "eventType": "LEAVE",
    "createdAt": "2025-04-28T02:17:52.939Z",
    "unreadCount": 2
  }
  ```

---

## 7. 채팅방별 읽지 않은 메시지와 안읽은 메세지 개수 푸시 (WebSocket)
- **서버가 오프라인 유저에게 전송하는 구독 메시지**
  - 구독 주소: `/sub/user/{userId}/unread-summary`
  - 메시지 예시:
    ```json
    {
      "roomId": 6,
      "preview": "안녕하세요", // 또는 최근 텍스트
      "createdAt": "2025-05-07T16:47:00",
      "unreadCount": "3"
    }
    ```
  - 50개 초과 시:
    ```json
    {
      "roomId": 6,
      "preview": "안녕하세요", // 또는 최근 텍스트
      "createdAt": "2025-05-07T16:47:00",
      "unreadCount": "50+"
    }
    ```

---

## 8. Redis 기반 동작 정리
- `chatroom:online:{roomId}`: 채팅방에 접속한 유저 ID 목록 (Set)
- `message:read:{messageId}`: 해당 메시지를 읽은 유저 ID 목록 (Set)
- 메시지 전송 시: 현재 접속 중인 유저 + 발신자 모두 읽은 것으로 간주하고 Redis에 기록
- 유저 입장 시: 입장 이후 메시지 읽음 처리 후 `/read-update` 브로드캐스트
- 유저가 접속하지 않은 상태에서 메시지 수신 시: `/unread-summary` 푸시로 안읽은 메시지 개수 전송

---

# 채팅 메시지 타입 (messageType)
- `TEXT`: 일반 텍스트 메시지
- `PICTURE`: 이미지 메시지
- `VIDEO`: 동영상 메시지

# 채팅 이벤트 타입 (eventType)
- `ENTER`: 채팅방 입장
- `LEAVE`: 채팅방 퇴장
- `NONE`: 일반 채팅 메시지
