# WebHistoryManager

# 웹사이트 방문기록 관리 웹어플리케이션
---------------

- 웹사이트 방문 기록을 관리하는 웹어플리케이션 시스템 구현 


# 개발환경
---------------

| 영역 | 사용기술 | 버전 |
|------|---------|------|
| 개발툴 | eclipse -spring STS - | Oxygen |
| 서버 | Spring Boot | v2.0.3 |
| 서버 | embedded mongodb| v1.50.5 |
| 서버 | embedded tomcat | v2.0.3 |
| 화면 | thymeleaf |  |
| 화면 | datatables |  |
|언어  | java  |v1.8|
|단위테스트 | junit | |

# 문제 해결전략
---------------
1. 서버 구현 : Spring Boot를 기반으로 REST API 서버 구현.
2. FRONT - BACK 데이터 전송 : HTMLFORM 사용하여 데이터 송수신(get/post) 처리.
3. 페이징처리 : jquery 기반 플러그인 datatables를 사용하여  페이징 처리 구현.
4. 데이터베이스 : in-memory-db embedded mongodb를 설치하여 따로 db서버를 만들지 않음.
5. Host 분리 : 정규표현식을 사용하여 Host 분리.
6. 단위테스트 : junit 통한 단위테스트 


# 빌드 및 실행방법
---------------

 빌드
 -----------------
 - ※ maven ,java v1.8 설치 필수, 빌드 오류 발생 시 pom.xml에서 spring-boot의 버전을 변경(현재 v2.0.0)
 - eclipse : project -> run as -> maven build(goal:package)
 
 
 - command : 
 - 해당 프로젝트의 경로에서...
 - mvn package

실행 
---------------
- ※ maven ,java v1.8 설치 필수
- eclipse : project -> run as -> Spring Boot App  (Alt +Shift +X,B)
 
 
- command : java -jre [생성된WAR경로]/demo-0.0.1-SNAPSHOT.war
