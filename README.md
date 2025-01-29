# Route Tracker

## 📌 Overview
Route Tracker is an Android application designed to **record user routes** and **display the history of tracked routes** with detailed information. The app utilizes **Google Maps**, **Geocoder**, and **Room Database** to provide accurate tracking and storage of location data. It is built using **Jetpack Compose** and **Hilt** for dependency injection.

## 🚀 Features
- **Real-time route tracking** using GPS.
- **Offline storage** of routes using Room Database.
- **Detailed route history** with start and destination addresses.
- **Google Maps integration** for visualizing recorded routes.
- **Geocoding support** to retrieve address details from latitude and longitude.
- **Dependency injection with Hilt** for efficient state management.

## 🛠️ Tech Stack
- **Kotlin** (Primary language)
- **Jetpack Compose** (UI framework)
- **Room Database** (Local data storage)
- **Google Maps API** (Map visualization)
- **Geocoder** (Address lookup from coordinates)
- **Hilt** (Dependency injection)
- **Coroutines & Flow** (Asynchronous programming)

## 📂 Project Structure
```
app/
├── data/          # Room Database and Repository
├── di/            # Hilt Dependency Injection Modules
├── ui/            # Jetpack Compose UI Components
├── viewmodel/     # ViewModels and State Management
└── utils/         # Utility classes (e.g., Geocoder, Location Helper)
```

## 📦 Installation
1. Clone the repository:
   ```sh
   git clone [https://github.com/yourusername/route-tracker.git](https://github.com/NovikFeed/GpsTraker.git)
   ```
2. Open the project in **Android Studio**.
3. Ensure you have **Google Maps API Key**:
   - Obtain an API key from [Google Cloud Console](https://console.cloud.google.com/)
   - Add it to `local.properties`:
     ```properties
     MAPS_API_KEY=your_api_key_here
     ```
4. Build and run the app on an emulator or physical device.

## 📜 Usage
1. **Grant location permissions** when prompted.
2. **Start recording a route** – the app will track and save your movement.
3. **View your history** – previously recorded routes are listed with timestamps.
4. **Click on a route** to see detailed information and its path on the map.

## ⚠️ Troubleshooting
- **Google Maps not displaying?**
  - Double-check your **Google Maps API Key**.
  - Make sure your Google Cloud project has **Maps SDK for Android enabled**.
- **App crashes on startup?**
  - Check **logcat** for errors.
  - Ensure **Hilt dependencies** are correctly set up.

## 📌 Future Enhancements
- Add **cloud storage** for syncing route history across devices.
- Implement **custom map styles** for better visualization.
- Improve UI with **Material 3 components**.

## 🤝 Contributing
Pull requests are welcome! Feel free to **open an issue** if you find bugs or have feature suggestions.

