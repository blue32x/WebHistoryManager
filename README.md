# WebHistoryManager
웹사이트 방문기록 관리 웹어플리케이션
=================================
웹사이트 방문 기록을 관리하는 웹어플리케이션 시스템 구현 


개발환경
==========

| 영역 | 사용기술 | 버전 |
|------|---------|------|
| 개발툴 | eclipse -spring STS - | Oxygen |
| 서버 | Spring Boot | v2.0.3 |
| 서버 | embedded mongodb| v1.50.5 |
| 서버 | embedded tomcat | v2.0.3 |
| 화면 | thymeleaf |  |
| 화면 | datatables |  |
|언어  | java  |v1.8

문제 해결전략
===============
1. 서버 구현 : Spring Boot를 기반으로 REST API 서버 구현.
2. FRONT - BACK 데이터 전송 : HTMLFORM 사용하여 데이터 송수신(get/post) 처리.
3. 페이징처리 : jquery 기반 플러그인 datatables를 사용하여  페이징 처리 구현.
4. 데이터베이스 : in-memory-db embedded mongodb를 설치하여 따로 db서버를 만들지 않음.
5. Host 분리 : 정규표현식을 사용하여 Host 분리.



빌드 및 실행방법
================

1. 빌드
 ※ maven ,java v1.8 설치 필수
 
2. 실행 
java -jre /target/[].war
