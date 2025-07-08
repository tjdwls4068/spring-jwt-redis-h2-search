# 🛡️ SpringBoard - JWT + Redis 기반 게시판 프로젝트

Spring Boot로 만든 간단한 게시판 API 프로젝트입니다.  
JWT를 활용한 인증 / Redis를 활용한 토큰 관리 / H2를 활용한 테스트용 DB 연동이 포함되어 있습니다.

---

## 🧩 사용 기술

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- H2 Database
- Redis (Docker 기반)
- Postman (API 테스트)

---

## ✨ 주요 기능

### 🔐 인증 / 인가
- 회원가입 (중복검사 포함)
- 로그인 (JWT 발급)
- 로그아웃 (Redis 블랙리스트 처리)
- JWT + Redis 기반 인증 검증

### 📬 게시글
- 게시글 등록 / 조회 / 수정 / 삭제
- 게시글 검색 (제목, 내용, 작성자에 키워드 포함)

### 💬 댓글
- 댓글 작성 / 수정 / 삭제 (작성자만 가능)

---

## 🧪 H2 콘솔 접속

- 접속 주소: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- ID / PW: `sa` / *(비밀번호 없음)*

---

## 🔁 Redis 설정 (Docker)

```bash
docker run -d -p 6379:6379 --name my-redis redis

```

---
## 📬 API 예시

메서드	| 엔드포인트	| 설명
|------|---|---|
|POST	|/auth/signup|	회원가입
POST	|/auth/login	|로그인 (JWT 발급)
POST	|/auth/logout	|로그아웃
POST	|/posts	|게시글 작성
GET	|/posts	|게시글 전체 조회
PUT	|/posts/{id}	|게시글 수정
DELETE	|/posts/{id}	|게시글 삭제
GET	|/posts/search?keyword=키워드	|게시글 검색
POST	|/comments	|댓글 작성
PUT	|/comments/{id}	|댓글 수정
DELETE	|/comments/{id}	|댓글 삭제

---~~~~