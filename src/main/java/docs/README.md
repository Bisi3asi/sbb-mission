## 구현과제

---
-[ ] 페이징 기능
    -[ ] `page 0 -> page 1`부터 페이징 가능하게 URL 매핑
    -[ ] 댓글 페이징 기능

-[ ] 예외 처리
    -[ ] 잘못된 페이지 접근
    -[ ] 페이지 댓글 작성 `POST` 실행 단계에서 본문 글 삭제되어 있는 경우


## 트러블슈팅

---
-[x] Problem : `Form DTO -> View` 전송 시 `html` 상 컴파일 에러로 표기(실제 기능에는 문제 없음)    
-[x] Solved :  `@ModelAddAttribute` 명시해 해결 (생략해도 문제는 없음)  
* `Spring Boot 2 -> 3`으로 버전 업이 되면서 생략이 가능
* `IntelliJ IDEA` 내부의 인식 문제로 확인

-[x] Problem : `Spring Security` 회원 로그인 `DTO` `POST` 전송 시 로그인 ID 값이 넘어가지 않는 문제   
`Spring Security UserDetailService`, `loadUserByUsername(String signInId)`    
-[x] Why? : `Spring Security`에서 자동으로 입력받은 login의 파라미터 이름이 `username` 이기 때문에 문제 발생  
-[x] Solved : `Spring SecurityFilterChain` `.formLogin` 에서 `.usernameParameter("signInId)`로 수정 