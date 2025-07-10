# 📝 SpringBoard with JWT & Redis

JWT + Redis 기반 인증 시스템을 사용하는 게시판 프로젝트입니다.

## 🔧 사용 기술

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA (H2 Database)
- Redis (Docker 사용)
- Postman (API 테스트)
- GitHub (버전 관리)

---

## 📌 주요 기능

### ✅ 회원 기능
- 회원가입
    - 사용자명 중복 검사
    - 비밀번호 암호화 (BCrypt)
- 로그인
    - JWT 토큰 발급
    - Redis에 토큰 저장
- 로그아웃
    - 토큰 블랙리스트 처리 (Redis)

---

### ✅ 게시글 기능
- 게시글 등록 / 조회 / 수정 / 삭제
- 내 게시글만 조회 (JWT 인증 기반)
- 페이징 처리 (`/posts?page=0&size=5`)
- 제목/작성자/내용 통합 검색

---

### ✅ 댓글 기능
- 댓글 등록 / 조회 / 삭제
- 본인 댓글만 삭제 가능

---

### ✅ 좋아요 기능
- 게시글 좋아요 / 취소
- 사용자 & 게시글 기반 중복 방지
- 좋아요 수 조회

---

### ✅ 기타 기술
- ✅ JWT + Redis 결합 인증 처리
- ✅ H2 콘솔 연결 (개발용)
- ✅ 전체 API 테스트 완료 (Postman)

---

## 🔐 인증 흐름

[로그인] → [JWT 생성] → [Redis 저장] → [요청 시 JWT 인증 + Redis 유효성 체크]
↘︎ [로그아웃 시 블랙리스트 처리]

---

처음부터 하나씩 기능을 구현하며 JWT, Redis, Spring Security, H2 등을 연동하며 학습한 결과물입니다.
기능이 완성될 때마다 GitHub에 커밋하고 버전을 관리했습니다.