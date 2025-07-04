# 📌 SpringBoard - JWT + Redis + H2 게시판 프로젝트

간단한 게시판 API 프로젝트입니다.  
Spring Boot 기반으로 JWT 인증, 댓글 기능, Redis & H2를 활용한 데이터 저장 기능까지 포함되어 있습니다.

---

## ✅ 기술 스택

- Java 17
- Spring Boot 3
- Spring Web / Spring Security / Spring Data JPA
- JWT (io.jsonwebtoken)
- H2 Database (in-memory)
- Redis (Docker 기반)
- Postman 테스트
- GitHub 연동 (버전 관리)

---

## 🚀 주요 기능

| 기능 | 설명 |
|------|------|
| 회원가입 | 중복 검사 포함 (`existsByUsername`) |
| 로그인 | JWT 발급 후 인증 토큰 |
| 게시글 등록 | JWT 인증 필요 |
| 게시글 조회 | 목록 + 단건 상세 |
| 게시글 수정/삭제 | 작성자 본인만 가능 |
| 댓글 등록/조회 | JWT 인증 사용자만 가능 |
| 댓글 수정/삭제 | 작성자 본인만 가능 |
| 예외 처리 | `CustomException` + 통일된 응답 구조 (`ResponseDto`) |
| DB 연동 | H2 + Redis(JSON 직렬화) 혼합 |
| GitHub 업로드 | 커밋/버전 관리 완료

---

## 📂 API 예시 (Postman)

### ✅ 회원가입

```
POST /auth/register
Body (JSON):
{
  "username": "성진",
  "password": "1234"
}
```

### ✅ 로그인 (토큰 발급)

```
POST /auth/login
Body (x-www-form-urlencoded):
username=성진
password=1234
```

> 응답: `"eyJhbGciOiJIUzI1NiJ9..."` (JWT)

### ✅ 게시글 작성

```
POST /posts
Header: Authorization: Bearer {JWT}
Body:
{
  "title": "첫 게시글",
  "content": "내용입니다"
}
```

### ✅ 댓글 등록

```
POST /posts/{postId}/comments
Header: Authorization: Bearer {JWT}
Body:
{
  "content": "댓글입니다"
}
```

---

## 💡 기타

- `localhost:8080/h2-console` → H2 접속
- Redis는 Docker 기반 실행  
  → 포트 `6379` 확인 필요
- JWT는 Authorization 헤더에 `"Bearer <token>"` 형식으로 전달

---

## ✍️ 프로젝트 목적

- Spring + JWT + Redis 학습
- 백엔드 실무에 가까운 API 설계 및 인증 구조 연습
- 추후 Swagger UI, 마이페이지, 좋아요 기능 등 확장 가능

---
