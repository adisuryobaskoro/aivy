# 🤖 Aivy — AI-Powered Career Planner

**Aivy** is an intelligent web application that helps job seekers and professionals generate a personalized learning roadmap based on their **uploaded CV** and **desired job qualifications**.

> "The Future of Career Planning Starts with Aivy."

---

## 🚀 Features

- 📄 **Optical Character Recognition (OCR)** — Extracts text from PDF and image files using Tesseract
- 🧠 **AI Integration** — Connects with Large Language Models (LLMs) via OpenRouter or other providers
- 📝 **Smart Input Form** — Simple interface to upload CV and enter job criteria
- 📊 **Learning Path Suggestions** — Clean, structured AI response displayed as HTML

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.5
- Thymeleaf
- Bootstrap 5
- MongoDB
- Tesseract OCR (via Tess4J)
- OpenRouter API / Compatible LLMs
- JUnit 5, Mockito

---

## 📷 UI Preview

| Input CV + Job Info | AI-Powered Suggestions |
|---------------------|------------------------|
| ![image](https://github.com/user-attachments/assets/146a9c5d-9958-49bb-85ef-8e6affffe3c6) | ![image](https://github.com/user-attachments/assets/605b242b-ae62-46dc-b7fa-9825e389c1f6) |

---

## ⚙️ Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/adisuryobaskoro/aivy.git
cd aivy
```

### 2. Create application.yml

Put your config in config/application.yml: (or just simply edit src/main/resources/appliation.yml)
```yml
open-router:
  api-key: your-api-key
  model: mistralai/mistral-7b-instruct
  url: https://openrouter.ai/api/v1/chat/completions

tesseract:
  datapath: C:/tessdata/
```

### 3. Run the App

```
./gradlew bootRun
```

## ✅ Upcoming Improvements

- Multi-language OCR support (English, Indonesian, Japanese, etc.)
- Model selector (OpenAI, DeepSeek, Claude, etc.)
- Optimize AI Services
- Dark mode UI
- Export result as downloadable PDF

## 🤝 Contributing
Contributions are welcome!
Feel free to fork, submit pull requests, or open an issue.

