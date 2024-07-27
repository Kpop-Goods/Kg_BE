# K-POP Goods & Event


> 이 게시물은 백엔드 작업 과정을 정리한 글입니다. 프론트엔드에 관련된 내용은 [여기](https://github.com/Kpop-Goods/Kg-FE)에서 확인하실 수 있습니다.

## 프로젝트 소개

**K-POP Goods & Event**는 K-POP 산업의 팬들이 직접 제작한 굿즈와 다양한 이벤트를 한 눈에 볼 수 있는 통합 플랫폼입니다. K-POP 팬들은 아이돌의 생일 카페 이벤트를 기획하거나 굿즈를 제작하는 등 소비자이자 동시에 공급자의 역할을 수행합니다. 그러나 이러한 팬 제작 물품과 이벤트를 한 곳에서 모아 볼 수 있는 사이트는 많지 않습니다. 

이에 **K-POP Goods & Event**는 이러한 개인 제작 굿즈와 이벤트 정보를 한 눈에 모아보고 검색할 수 있는 통합 플랫폼을 목표로 개발되었습니다. 팬들이 손쉽게 자신이 원하는 정보를 찾고, 공유하며, 즐길 수 있는 공간을 제공합니다.

## 💻 개발 환경

- **IntelliJ Ultimate**
- **JDK 17**
- **Spring Boot 3.2.2**
- **MySQL 8.0.35**
- **Gradle**

## 🛠️ 기술 스택

#### **`Frameworks & Libraries`**
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-2A2A2A?style=for-the-badge&logo=lombok&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-2A2A2A?style=for-the-badge&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-4285F4?style=for-the-badge&logo=oauth&logoColor=white)
![JavaMailSender](https://img.shields.io/badge/JavaMailSender-0078d7?style=for-the-badge&logo=mail&logoColor=white)

#### **`Security`**
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

#### **`Databases & Storage`**
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Neo4J](https://img.shields.io/badge/Neo4j-4A91D8?style=for-the-badge&logo=neo4j&logoColor=white)

#### **`Test Platforms`**
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

#### **`Cloud Services`**
![AWS EC2](https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS_RDS-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS_S3-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white)

#### **`CI/CD & DevOps`**
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)

## 외부 플랫폼

프로젝트 협업과 관리를 위해 다음과 같은 외부 플랫폼을 사용합니다.

### 1. 디스코드 (Discord)
- **용도**: 팀 커뮤니케이션 및 실시간 협업
- **초대 링크**: [Discord 링크](https://discord.com/invite/R7aaWnUp)

### 2. 컨플루언스 (Confluence)
- **용도**: 프로젝트 문서화 및 진행 상황 공유
- **접속 링크**: [Confluence 페이지](https://k-good.atlassian.net/wiki/spaces/SD/overview)

### 3. ERDCloud
- **용도**: 데이터베이스 설계 및 ER 다이어그램 작성
- **접속 링크**: [ERDCloud 다이어그램](https://www.erdcloud.com/d/hCGzw2tz8vL29GH2R)

---

# 주요 기능 
## 👤 회원 관련 기능

> _**API 명세서: [👤 유저 API](https://k-good.atlassian.net/wiki/x/PQCo)**_

- **회원 가입**: 사용자가 회원 가입을 할 수 있으며, 이메일 인증을 통해 계정을 활성화합니다.
- **자체 로그인**: 사용자는 등록된 이메일과 비밀번호로 로그인할 수 있으며, JWT를 사용하여 인증 토큰을 발급합니다.
- **OAuth 2.0 로그인**: 사용자는 외부 서비스 제공자(`Google`, `Naver`, `Kakao`)를 통해 로그인할 수 있으며, JWT를 사용하여 인증 토큰을 발급합니다.
- **로그아웃**: 사용자는 로그아웃 요청을 보내면 해당 사용자의 토큰을 Redis에서 등록해 사용하지 못하도록 합니다.
- **회원 정보 수정**: 로그인된 사용자는 자신의 정보를 수정할 수 있으며, 변경된 정보는 데이터베이스에 업데이트됩니다.
- **회원 조회, 삭제**: 데이터베이스와의 연동을 통해 특정 회원을 조회하거나 삭제할 수 있습니다.
- **이메일 인증**: 회원 가입 시 이메일을 통해 인증 링크를 전송하고, 링크를 클릭하여 계정을 인증할 수 있습니다.
- **계정 잠금 처리**: 로그인 실패 횟수를 추적하고 최대 실패 횟수를 초과할 경우 계정을 잠그며, 일정 시간이 지나면 자동으로 잠금이 해제됩니다.

## 🪪 프로필 관리

- **프로필 관리**: 사용자는 닉네임과 프로필 이미지를 관리할 수 있습니다.
- **찜 목록**: 굿즈와 장소를 별도로 관리할 수 있습니다.
- **관심사**: 사용자는 관심사를 설정할 수 있습니다.

## ♥️ 팔로우/좋아요 기능

> _**API 명세서: [♥️ 팔로우/좋아요 API](https://k-good.atlassian.net/wiki/x/CQAc)**_

- **유저 팔로우**: 사용자는 다른 유저를 팔로우할 수 있습니다.
- **아티스트 팔로우**: 사용자는 아티스트를 팔로우할 수 있습니다.
- **장소 좋아요**: 사용자는 장소에 좋아요를 표시할 수 있습니다.
- **굿즈 좋아요**: 사용자는 굿즈에 좋아요를 표시할 수 있습니다.
- **마이페이지에서 확인 가능**: 사용자는 마이페이지에서 팔로우 및 좋아요 상태를 확인할 수 있습니다.

## 🗒 공지사항

> _**API 명세서: [🗒 공지사항 API](https://k-good.atlassian.net/wiki/x/oYA3AQ)**_

- **공지사항 게시판**: 사이트 공지사항을 올리는 게시판을 운영합니다.

## 🧸 굿즈 관련 기능

> _**API 명세서: [🧸 굿즈 API](https://k-good.atlassian.net/wiki/x/E4BiAQ)**_

- **필터링**: 아티스트 별, 굿즈 유형 별로 굿즈를 필터링할 수 있습니다.
- **등록**: 굿즈를 등록할 수 있습니다.
- **상세페이지**: 굿즈의 상세 정보를 볼 수 있는 페이지를 제공합니다.
- **외부 사이트 연결**: 굿즈의 출처를 기입하여 외부 사이트로 연결할 수 있습니다.

## 🎉 이벤트 관련 기능

> _**API 명세서: [🎉 이벤트 API](https://k-good.atlassian.net/wiki/x/YgCo)**_

- **장소 확인**: 해당 이벤트의 장소를 지도 상으로 확인할 수 있습니다.
- **필터링**: 아티스트 별, 굿즈 유형 별로 이벤트를 필터링할 수 있습니다.
- **등록**: 이벤트를 등록할 수 있습니다.
- **상세페이지**: 이벤트 상세 정보를 볼 수 있는 페이지를 제공합니다.

## 👨‍🎤 아티스트 관련 기능

> _**API 명세서: [👨‍🎤 아티스트 API](https://k-good.atlassian.net/wiki/x/aIAYAQ)**_

- **아티스트 상세 페이지**: 아티스트의 상세 정보를 볼 수 있는 페이지를 제공합니다.

## 🏢 소속사 관련 기능

> _**API 명세서: [🏢 소속사 API](https://k-good.atlassian.net/wiki/x/BYCBAQ)**_

- **소속사 필터링**: 소속사 별로 아티스트를 필터링하여 정보를 확인할 수 있습니다.

## 👥 소셜링 (오프라인 만남) 관련 기능

> _**API 명세서: [👥 소셜링 API](https://k-good.atlassian.net/wiki/x/DwAeAQ)**_

- **소셜링 상세 페이지**: 소셜링의 상세 정보를 볼 수 있는 페이지를 제공합니다.
- **소셜링 등록**: 사용자가 소셜링을 등록할 수 있습니다.

## 📅 캘린더 관련 기능

> _**API 명세서: [📅 캘린더 API](https://k-good.atlassian.net/wiki/x/1oAFAQ)**_

- **아티스트 별 필터링**: 캘린더에서 아티스트 별로 일정을 필터링할 수 있습니다.
- **캘린더 일정 등록**: 사용자가 캘린더에 일정을 등록할 수 있습니다.

## 👤 Admin

- **유저 관리**: 관리자는 유저를 관리할 수 있습니다.
- **아티스트 정리**: 아티스트를 등록하거나 관리할 수 있습니다.
- **굿즈 장르 별 데이터 정리**: 굿즈 데이터를 장르 별로 정리합니다.
- **공지사항 관리**: 관리자는 공지사항 게시물을 작성하고 관리할 수 있습니다.

---

# 프로젝트 멤버

## Back-End
- [김찬미](https://github.com/kcm02)

- [박태훈](https://github.com/Anthony-Park)

- [천다연](https://github.com/cdayeon)

- [황희승](https://github.com/hwazxcv)
