# 🌙달콤한 우리의 방으로 초대합니다, 달방🌙
## 프리이빗 한 우리만의 공간에서 서로의 관심사를 놓고 얘기 할 수 있는 공간의 모임을 추구 합니다.
<hr>


## 기술 스택

### 📚 Tech Stack
#### 💻 Development
<img src="https://skillicons.dev/icons?i=java,spring,mysql,docker& perline="/>

#### ⌛ Developed Period
##### 2024.01.25

## 진행 방식

**도커 파일 작성:**
```dockerfile
# 기본 이미지 선택
FROM some-base-image

# 작업 디렉토리 설정
WORKDIR /usr/src/app

# ARG 속성 추가 - 여러번 사용되는 문자열이나 숫자 등을 변수로 만들어주는 속성
ARG JAR_PATH=./build/libs

# 로컬 빌드 경로에서 JAR 파일을 이미지로 복사
COPY ./build/libs/dalbang-0.0.1-SNAPSHOT.jar ./build/libs/dalbang-0.0.1-SNAPSHOT.jar

# 애플리케이션 실행 명령어 설정
CMD ["java","-jar","./build/libs/dalbang-0.0.1-SNAPSHOT.jar"]
```

### 도커 이미지 빌드
```
docker build -t springbootapp .
```

### 도커 컨테이너 생성 및 실행
```
docker run -d --name my_app_container springbootapp
```


# 트러블 슈팅


<hr>

## 💡 Commit Convention

|       Tags       |               Explanation               |
| :--------------: | :-------------------------------------: |
|       Feat       |            새로운 기능 추가             |
|       Fix        |                버그 수정                |
| !BREAKING CHANGE |         커다란 API 변경의 경우          |
|     !HOTFIX      |          급한 치명적 버그 수정          |
|      Build       |           빌드 관련 파일 수정           |
|      Design      |        CSS를 포함 UI 디자인 변경        |
|       Docs       |                문서 수정                |
|      Style       | 코드 포맷팅, 세미콜론 누락, 코드 변경 X |
|     Refactor     |              코드 리팩토링              |
|     Comment      |        필요한 주석 추가 및 변경         |
|       Test       |            테스트 코드 수정             |
|      Rename      |         파일, 폴더명 이름 수정          |
|      Remove      |             파일, 폴더 삭제             |
|      chore       |            빌드, 패키지 수정            |
