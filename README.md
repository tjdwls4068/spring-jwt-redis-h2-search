# 🛠 SpringBoard-JWT-Redis

Spring Boot, JWT, Redis, JPA(H2)를 활용한 인증 기반 게시판 프로젝트입니다.

---

## 🚀 프로젝트 개요

- JWT 로그인 인증 구현
- Redis 기반 토큰 저장 및 로그아웃 처리 (블랙리스트)
- 로그인한 사용자만 게시글 작성/수정/삭제 가능
- H2 데이터베이스를 활용한 게시글/댓글 저장
- Postman으로 테스트 가능

---

## 🧰 사용 기술

| 분야 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Database | H2 (in-memory), Redis |
| ORM | Spring Data JPA |
| Build Tool | Gradle |
| Test Tool | Postman |
| Security | Spring Security, JWT |
| Dev Tool | IntelliJ, Docker, Git |

---

## 📦 주요 기능

### 🔐 사용자 인증

- 회원가입 (`/auth/signup`)
- 로그인 및 JWT 토큰 발급 (`/auth/login`)
- JWT 토큰 Redis 저장
- 로그아웃 시 Redis에서 토큰 삭제 (블랙리스트 등록)

### 📝 게시글

- 게시글 작성 (`POST /posts`)
- 게시글 목록 조회 (`GET /posts`)
- 게시글 상세 조회 (`GET /posts/{id}`)
- 게시글 수정 (`PUT /posts/{id}`)
- 게시글 삭제 (`DELETE /posts/{id}`)
- 작성자 본인만 수정/삭제 가능

---

## 🔐 인증 흐름 요약

1. 로그인 → JWT 발급
2. Redis에 유효한 토큰 저장
3. 인증이 필요한 요청 시 JWT 인증 필터 통과
4. 로그아웃 → Redis에서 토큰 삭제 (블랙리스트)

---

## 📂 

Postman으로 요청 시 Header에 아래와 같이 입력:

Authorization: Bearer <JWT 토큰>

---

## ✅ 향후 확장 예정 기능
댓글 CRUD 및 작성자 검증

좋아요 기능

Swagger (API 문서 자동화)

PostgreSQL 또는 MySQL 외부 DB 연동

프로필 조회 및 수정


