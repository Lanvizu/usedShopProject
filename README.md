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

## 🍳 Priview

<details>
    <summary>자세히</summary>
    🍳 회원가입
    <p align="center">
    <img src="https://user-images.githubusercontent.com/121706341/269230037-c6e8d2ee-f4be-416f-a2b2-bb74b5e72ddb.gif">
    </p>
    🍳 로그인
    <p align="center">
    <img src="https://user-images.githubusercontent.com/121706341/269232381-77a54b8c-9818-4325-abd6-8f471b110fe3.gif">
    </p>
    🍳 경매가 입력, 취소
    <p align="center">
    <img src="https://user-images.githubusercontent.com/121706341/269234789-a6b6e0d7-826a-4df3-a8fd-4864ae4860f2.gif">
    </p>
    🍳 판매 물건 등록
    <p align="center">
    <img src="https://user-images.githubusercontent.com/121706341/269257307-4e9f012e-242a-4db3-adcb-7dd78959c433.gif">
    </p>

</details>

## 🍳 마주했던 이슈들

<details>
  <summary>SpringSecurity 문제</summary>
  <br/>
  
  로그인 과정에 보안을 생각해 SpringSecurity를 통해 진행했다. 
  
  비밀번호를 암호화하지않고 저장해 로그인이 실행되지 않은 에러 등 여러가지가 있었다...

  >수많은 에러로 인해 구글링과 공식문서로 공부하면서 문제를 해결했다.
  
  [에러 벨로그](https://velog.io/@lanvizu/%EC%BD%94%EB%94%A9-%EC%9D%BC%EA%B8%B023-05-15)
</details>

<details>
  <summary>이메일 인증을 통한 비밀번호 재설정</summary>
  <br/>
  저번 프로젝트에서 진행하지 못했던 비밀번호 재설정 문제를 이메일을 통한 방법으로 해결했다.
  
  SpringSecurity를 사용할 때 단점 중 하나인 암호화된 비밀번호를 복호화하지 못하는 문제가 있었다.

  >Spring Boot Mail을 통해 이메일 발송을 확인했으며 정상적으로 비밀번호가 재설정된다.

  [에러 벨로그](https://velog.io/@lanvizu/%EC%BD%94%EB%94%A9-%EC%9D%BC%EA%B8%B023-05-21)
</details>

<details>
  <summary>Dto 사용해 데이터를 전달</summary>
  <br/>
  
  LazyInitializationException 에러 - 세션 종료와 세션 범위를 벗어난 상태에서 지연 로딩 발생했다.

  해당 에러 덕분에 Dto에 대해 공부하고 그 중요성을 알게되었다.

  >모든 데이터는 Dto를 통해 전달되도록 코드를 수정했으며 정상적으로 데이터를 전달한다.

  [에러 벨로그](https://velog.io/@lanvizu/%EC%BD%94%EB%94%A9%EC%9D%BC%EA%B8%B023-05-27)
</details>

<details>
  <summary>LocalDateTime을 이용해 시간 표현</summary>
  <br/>

  포스팅 부분에 몇 분전 글을 작성했는지 표시가 되지않아 식별이 불가한 문제가 발생했다.

  LocalDateTime를 공부해 javaScript를 통해 표현하려 했지만 공부하지 못한 부분이라 어려움이 있었다.

  >@EnableJpaAuditing를 통해 자동으로 생성과 수정 시간을 설정하도록 해결했습니다.

  [에러 벨로그](https://velog.io/@lanvizu/%EC%BD%94%EB%94%A9%EC%9D%BC%EA%B8%B023-06-16)

</details>

🍳 **부족한 부분**
<details>
  <summary>트랜잭션</summary>
  <br/>

  이번 프로젝트에선 예외처리를 Throw로 던지는 쪽으로 회피하면서 코드를 작성했다.

  트랜잭션에 대해서 공부를 하고서 코드를 다시보니 예외 처리 부분에 많은 문제가 있어보였다.

  >예외 처리를 Checked와 Unchecked로 나눠 명확한 메세지를 통해 예외처리하는 부분이 필요하다고 느낀다.

  [트랜잭션 벨로그](https://velog.io/@lanvizu/%EC%BD%94%EB%94%A9%EC%9D%BC%EA%B8%B023-06-22)

</details>

