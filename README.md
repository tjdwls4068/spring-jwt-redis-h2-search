# 🧩 Spring Boot 게시판 API with Redis & JWT

Spring Boot 기반으로 구현한 **로그인/회원 인증 시스템** 및  
**게시글/댓글 기능**을 포함한 **RESTful API 서버 프로젝트**입니다.

---

## 🚀 기능 요약

### 🔐 로그인 및 인증
- **Redis 기반 세션 저장** (로그인 시 사용자 정보 Redis에 저장)
- **JWT 토큰 발급 및 인증** (Spring Security + JWT 필터 적용)
- 로그인 시 토큰 발급 → 요청 헤더에 토큰 포함 시 인증 처리

### 📚 게시판 기능
- 게시글 등록 / 조회 / 삭제
- 댓글 등록 / 조회
- JWT 토큰으로 인증된 사용자만 작성 가능
- 향후: 작성자 본인만 수정/삭제 가능 기능 예정

---

## 🛠 사용 기술

| 기술 | 설명 |
|------|------|
| Java 17 | 최신 LTS 버전 |
| Spring Boot 3.x | 애플리케이션 프레임워크 |
| Redis | 세션 저장소 (Docker 기반) |
| JWT | 인증 방식 |
| Spring Security | 인증/인가 필터 적용 |
| JPA + H2 | 게시글/댓글 DB 저장 (RDBMS 연습용) |
| Postman | API 테스트 |

---
## 📬 API 예시
🔑 로그인

POST /auth/login
```json
{
"username": "성진",
"password": "1234"
} 
```
응답 헤더에 JWT 토큰 발급됨.

---
## 📄 게시글 등록
POST /posts

Headers:
Authorization: Bearer {token}