# ⏰ 내가 공부한 소중한 시간들을 아름다운 기록으로 남길 수 있는 TimerTiTi 📸
### [Play Store](https://play.google.com/store/apps/details?id=com.titi.app)
![cover2](https://github.com/TimerTiTi/TiTi_Android/assets/61337202/ea2126a7-ce8e-4106-9924-fe266ba354a2)

- 사용자들은 다른 사람들과의 경쟁 없이, 나만의 기록을 남기는 것에만 집중할 수 있어요. 🖊️
- 티티에 남겨진 기록들은 이쁜 그래프로 표현돼요. 많은 사용자들이 인스타그램에 기록을 공유하고 있어요. 🖼
- 티티는 사용자에게 간편한 사용성을 제공하기 위해 사용자 친화적인 UI/UX를 고민하고 있어요. 🦄

# Features
<img width="969" alt="스크린샷 2024-03-31 오후 2 01 34" src="https://github.com/TimerTiTi/TiTi_Android/assets/61337202/3482344a-7c09-47af-a54c-4b03fd91d5eb">

### [요구사항 명세서](https://docs.google.com/spreadsheets/d/1tVOiMd1-MYbk3dLCwa5GBPjIDEpp2Jq_xJRfrKCzQPo/edit#gid=1705278728)
- 기록 측정 - Timer 모드 (앱 종료시에도 기록 진행)
- 기록 측정 - Stopwatch 모드 (앱 종료시에도 기록 진행)
- 기록 설정 - 목표시간, 타이머시간 설정
- 기록 설정 - Log 창 표시를 위한 Month, Week, Daily 목표시간 설정
- Log Daily - 24시간 시간대별 누적시간 그래프 제공
- Log Daily - 기록명 별 누적시간 그래프 제공
- Log Week - 주간 요일별 누적시간 및 상위 5가지 기록명 별 누적시간 그래프 제공
- Log Home - Total 총 누적시간 및 상위 5가지 기록명 별 누적시간 그래프 제공
- Log Home - 현재 Month 누적시간 그래프 제공
- Log Home - 현재 Month 상위 5가지 기록명 별 누적시간 그래프 제공
- Log Home - 현재 Week 누적시간 그래프 제공
- Notification - 타이머 종료 5분전, 종료 알림
- Notification - 스탑워치 1시간단위 경과 알림
- Color - Timer & Stopwatch 배경색 컬러 커스터마이징 제공
- Color - 그래프 테마색상 12가지 제공 (다크 & 라이트 모드)

# Architecture
- ### [Google Recommended Architecture](https://developer.android.com/topic/architecture#recommended-app-arch)
![image](https://github.com/TimerTiTi/TiTi_Android/assets/61337202/be44c90a-3149-4472-85c3-66b6f3ef1a1e)
- ### Multi Modularization
![project dot](https://github.com/TimerTiTi/TiTi_Android/assets/61337202/2d398069-84f0-4295-a2cd-eec0384870a7)
- ### MVI ([Mavericks](https://airbnb.io/mavericks/#/))
![image](https://github.com/TimerTiTi/TiTi_Android/assets/61337202/09c2bcbe-f634-4129-8d57-655c86d1e758)

# Tech Spec
- Kotlin based, Coroutines + Flow for asynchronous.
- [ksp](https://github.com/google/ksp) : Kotlin Symbol Processing API.
- [Hilt](https://dagger.dev/hilt/) : for dependency injection.
- [Compose](https://developer.android.com/develop/ui/compose) : Android’s recommended modern toolkit for building native UI.
- [Compose Navigation](https://developer.android.com/develop/ui/compose/navigation) : navigate between composables
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) : Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
- [Room](https://developer.android.com/training/data-storage/room) : Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Moshi](https://github.com/square/moshi/) : A modern JSON library for Kotlin and Java.


