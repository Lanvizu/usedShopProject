# usedShopProject (중고경매 프로젝트)

## 🍳 Intro
- Spring, Jpa, 클론코딩 공부 후 두번째 개인 프로젝트
- 공부한 내용을 종합하여 적용, 필요한 부분은 구글링을 통해 공부 후 적용
<br/>

## 🍳 프로젝트 소개
- 기존 중고 거래 플랫폼인 당근 마켓의 중고 거래 시스템과 Kream의 경매 시스템을 융합하는 중고경매 프로젝트를 진행.
- 중고 물품을 포스팅한 후 다른 유저들은 해당 물건에 원하는 가격 제시 가능
- 물건 주인은 원하는 가격으로 낙찰 가능

  
<p align="center">
<img src="https://img.shields.io/badge/JAVA-v11-blue">
<img src="https://img.shields.io/badge/Spring_Boot-v2.7.11-green?logo=springboot">
<img src="https://img.shields.io/badge/Spring_Data_JPA-green">
<img src="https://img.shields.io/badge/Spring_Security-green?logo=springsecurity">
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf">
<img src="https://img.shields.io/badge/Gradle-02303A?logo=gradle">
</p>
<br/>


## 🍳 핵심 기능
- 회원 관련
  - 닉네임, 이름, 비밀번호, 이메일, 전화번호를 입력하여 회원가입 진행
  - 회원 비밀번호 Spring Security 통해 암호화 후 저장
  - 비밀번호 찾기 -> 등록된 이메일을 통해 임시 비밀번호 전송
- 로그인
  - 등록된 닉네임과 비밀번호를 통해 로그인 가능
  - 로그인 시 인증 부여
  - 개인정보 수정 시 인증 부여된 유저 본인만 접근가능
- 판매 상품 등록
  - 상품 이미지, 이름, 즉시 구매가 가격, 상품 설명을 입력하여 상품 등록 진행
  - 다른 구매자가 등록한 제시 가격 체결 가능
- 상품 구매
  - 특정 상품에 원하는 가격 제시 가능
  - 즉시 구매가를 입력 시 즉시 체결

## 🍳 기술 스택
**1. Back-End**

- Java 11
- Spring Data JPA
- Spring Boot 2.7.11
- Gradle
- Thymeleaf
- H2 Database
- Spring Security
- Commons IO
- Validation
- Spring Boot Mail

**2. Front-End**
- HTML
- JavaScript
- CSS
<br/>

## 🍳 ERD 테이블
![erd table 1](https://github.com/Lanvizu/usedShopProject/assets/121706341/8e144c70-042b-474b-9676-c312107a594b)
