# MDBD API

무드보드(Moodboard) 관리를 위한 REST API 서버입니다.

## 필요 프로그램

다음 프로그램들이 설치되어 있어야 합니다:

### 1. Java 17 이상
- **macOS**: 
  ```bash
  brew install openjdk@17
  ```
- **Linux (Ubuntu/Debian)**:
  ```bash
  sudo apt update
  sudo apt install openjdk-17-jdk
  ```
- **Windows**: 
  - [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17) 또는 [OpenJDK](https://adoptium.net/) 다운로드 및 설치
  - 환경 변수 `JAVA_HOME` 설정 필요

설치 확인:
```bash
java -version
```

### 2. Gradle 8.0 이상
- **macOS**:
  ```bash
  brew install gradle
  ```
- **Linux (Ubuntu/Debian)**:
  ```bash
  sudo apt install gradle
  ```
- **Windows**: 
  - [Gradle 공식 사이트](https://gradle.org/install/)에서 다운로드
  - 환경 변수 `PATH`에 Gradle bin 디렉토리 추가

설치 확인:
```bash
gradle -v
```

## 프로젝트 실행

### 1. 프로젝트 클론/다운로드
```bash
cd mdbd-api
```

### 2. 애플리케이션 실행
```bash
gradle bootRun
```

서버가 시작되면 다음 메시지가 표시됩니다:
```
:: Spring Boot :: (v3.3.1)
```

### 3. 서버 확인
서버는 기본적으로 `http://localhost:8080`에서 실행됩니다.

## API 엔드포인트

### 무드보드 생성
```http
POST /api/moodboards
Content-Type: application/json

{
  "title": "제목",
  "description": "설명",
  "items": [
    {
      "text": "아이템 텍스트",
      "imageUrl": "https://example.com/image.jpg",
      "color": "#FF5733",
      "orderIndex": 0
    }
  ]
}
```

### 무드보드 목록 조회
```http
GET /api/moodboards
```

### 무드보드 조회
```http
GET /api/moodboards/{id}
```

### 무드보드 수정
```http
PUT /api/moodboards/{id}
Content-Type: application/json

{
  "title": "수정된 제목",
  "description": "수정된 설명",
  "items": [...]
}
```

### 무드보드 삭제
```http
DELETE /api/moodboards/{id}
```

## 주의사항

- 현재 데이터는 메모리(`ConcurrentHashMap`)에 저장되므로 서버를 재시작하면 모든 데이터가 사라집니다.
- 프로덕션 환경에서는 데이터베이스 연동이 필요합니다.

## 프로젝트 구조

```
mdbd-api/
├── src/
│   ├── main/
│   │   ├── java/com/mdbd/api/
│   │   │   ├── controller/    # REST API 컨트롤러
│   │   │   ├── dto/           # 데이터 전송 객체
│   │   │   ├── model/         # 도메인 모델
│   │   │   └── service/       # 비즈니스 로직
│   │   └── resources/
│   │       └── application.yml # 설정 파일
│   └── test/                  # 테스트 코드
├── build.gradle               # Gradle 빌드 설정
└── settings.gradle           # Gradle 프로젝트 설정
```

## 기술 스택

- **Java 17**
- **Spring Boot 3.3.1**
- **Gradle 8.12.1**
- **Spring Web**
- **Spring Validation**

