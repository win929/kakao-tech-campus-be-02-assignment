## ERD Diagram
![ERD Diagram](https://github.com/win929/kakao-tech-campus-be-02-assignment/blob/main/ERD.png)

## 1. Schedule API

### 1.1. Create Schedule
- API: POST /schedules
- 요청 본문(Request Body):
```
{
  "userId": 1,
  "title": "Sample Title",
  "content": "Sample Content"
}
```
- 응답(Response):
```
{
  "id": 1,
  "userId": 1,
  "title": "Sample Title",
  "content": "Sample Content",
  "createdAt": "2025-05-25T09:00:00",
  "updatedAt": "2025-05-25T09:00:00"
}
```
- 상태 코드(Status Code): 201 Created
- 설명: 새로운 일정을 생성합니다.

### 1.2. Get All Schedules
- API: GET /schedules
- 요청 본문(Request Body):
```
{}
```
- 응답(Response):
```
[
  {
    "id": 2,
    "userId": 2,
    "title": "Another Title",
    "content": "Another Content",
    "createdAt": "2025-05-26T10:00:00",
    "updatedAt": "2025-05-26T10:00:00"
  },
  {
    "id": 1,
    "userId": 1,
    "title": "Sample Title",
    "content": "Sample Content",
    "createdAt": "2025-05-25T09:00:00",
    "updatedAt": "2025-05-25T09:00:00"
  }
]
```
- 요청 본문(Request Body):
```
{
    "updatedAt": "2025-05-25"
}
```
- 응답(Response):
```
[
  {
    "id": 1,
    "userId": 1,
    "title": "Sample Title",
    "content": "Sample Content",
    "createdAt": "2025-05-25T09:00:00",
    "updatedAt": "2025-05-25T09:00:00"
  }
]
```
- 요청 본문(Request Body):
```
{
    "username": "name2"
}
```
- 응답(Response):
```
[
  {
    "id": 2,
    "userId": 2,
    "title": "Another Title",
    "content": "Another Content",
    "createdAt": "2025-05-26T10:00:00",
    "updatedAt": "2025-05-26T10:00:00"
  }
]
```
- 요청 본문(Request Body):
```
{
    "updatedAt": "2025-05-26",
    "username": "name2"
}
```
- 응답(Response):
```
[
  {
    "id": 2,
    "userId": 2,
    "title": "Another Title",
    "content": "Another Content",
    "createdAt": "2025-05-26T10:00:00",
    "updatedAt": "2025-05-26T10:00:00"
  }
]
```
- 상태 코드(Status Code): 200 OK
- 설명: 모든 일정을 반환합니다. updated_at을 기준으로 내림차순 정렬됩니다.

### 1.3. Get Schedule By ID
- API: GET /schedules/{id}
- 요청 본문: 없음
- 응답(Response):
```
{
  "id": 1,
  "userId": 1,
  "title": "Sample Title",
  "content": "Sample Content",
  "createdAt": "2025-05-25T09:00:00",
  "updatedAt": "2025-05-25T09:00:00"
}
```
- 상태 코드(Status Code): 200 OK
- 설명: 지정된 ID에 해당하는 일정을 반환합니다.

### 1.4. Update Schedule
- API: PUT /schedules/{id}
- 요청 본문(Request Body):
```
{
  "username": "updatedUsername",
  "title": "Updated Title",
  "content": "Updated Content",
  "password": "userPassword"
}
```
- 응답(Response):
```
{
  "id": 1,
  "userId": 1,
  "title": "Updated Title",
  "content": "Updated Content",
  "createdAt": "2025-05-25T09:00:00",
  "updatedAt": "2025-05-25T09:30:00",
  "username": "updatedUsername"
}
```
- 상태 코드(Status Code): 200 OK
- 설명: 지정된 ID에 해당하는 일정을 수정합니다. 수정하려면 비밀번호가 일치해야 합니다.

### 1.5. Delete Schedule
- API: DELETE /schedules/{id}
- 요청 본문(Request Body):
```
{
  "password": "userPassword"
}
```
- 응답(Response): 없음
- 상태 코드(Status Code): 200 OK
- 설명: 지정된 ID에 해당하는 일정을 삭제합니다. 삭제하려면 비밀번호가 일치해야 합니다.

# 2. User API
### 2.1. Create User
- API: POST /users
- 요청 본문(Request Body):
```
{
  "username": "newUser",
  "password": "userPassword"
}
```
- 응답(Response):
```
{
  "id": 1,
  "username": "newUser",
  "password": "userPassword"
}
```
- 상태 코드(Status Code): 201 Created
- 설명: 새로운 사용자 계정을 생성합니다.