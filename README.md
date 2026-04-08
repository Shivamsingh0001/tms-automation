# TMS Automation

API Test Automation Framework for TMS Services — mirroring the `core-os-automation` patterns.

## Tech Stack
- Java 21
- Gradle + Maven (dual build)
- TestNG
- REST Assured
- Allure Reports
- Lombok

## Quick Start
```bash
./gradlew testAll                          # Run all tests on QA
./gradlew testTMS -Pgroups=smoke           # TMS smoke tests
./gradlew testTMS -Denvironment=staging    # TMS on staging
./gradlew testHelp                         # Show all options
```
