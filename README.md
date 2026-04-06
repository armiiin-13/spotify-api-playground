# 🟢 Spotify Playground
General information, tutorials and proyects using the Spotify API to create music-base webpages

API Documentation: https://developer.spotify.com/documentation/web-api <br>
Web Playback SDK: https://developer.spotify.com/documentation/web-playback-sdk <br> <br>

## 📑 Table of Contents
- [Spotify for Developers](#1-spotify-for-developers): introducing the API
- [Quick Start](#2-quick-start): auxiliar files
- [QueuePlayer Application](#3-queueplayer-application): main application of the repository

## 🧩 Spotify for Developers
Spotify for Developers is the Spotify's oficial platform that provides tools, APIs, SDKs, and documentation that allow developers to build applications integrating Spotify’s music streaming features. <br><br>
To access to all features you can log in using your Spotify account, which will let you to use your personal dashboard to create applications using the Spotify API. <br><br>

### ⚙️ 1.1 Creating an App
To create an app you need to specify the following information:
- App's name
- App's description
- Redirect URI: where users can be redirected after authentication success or failure
- Specify all the Spotify API you are going to use in the project
> If you do not need to put a redirect URI use: http://127.0.0.1:3000

>[!Warning] The Spotify Developer's Web do not let the URI to be http://localhost:XXXX. In order to specify your local URI use the http://127.0.0.1

### 🔐 1.2 Request an Access Token
When the app is created, in its information you will see two IDs: _client ID_ and _Client Secret_, which will be used when authenticating credentials (requesting an access token).

To request an access token you have to send a __POST request__ to the token endpoint API with the following structure:
```shell
curl -X POST "https://accounts.spotify.com/api/token" \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "grant_type=client_credentials&client_id=your-client-id&client_secret=your-client-secret"
```
The response will return an access token valid for _1 hour_.
```json
{
  "access_token": "access_token",
  "token_type": "Bearer",
  "expires_in": 3600
}
```

With this `access_token` you will be able to do the rest endpoints request to the Spotify Web API.

### ▶️ 1.3 Playback SDK


## 🚀 Quick Start
Before starting to develop a functional web application that uses the Spotify API, it is necessary to first become familiar with how it works. To achieve this, an introductory course on the API was followed through a YouTube tutorial in order to understand the basic principles of using it. The tests carried out can be found in the `Minicourse_Python` folder, and the course/tutorial can be accessed via the following [link](https://www.youtube.com/watch?v=MSBUMMcPnLk).

Additionally, since the goal is to integrate these external API calls into a Spring (Java) application, further research was conducted on how to accomplish this, as it is a topic not covered within the Computer Engineering degree curriculum. All the notes and materials gathered can be found in the `External_API_Requests.ipynb` file.

> [!Note] The notes only cover the usage of the `RestTemplate` class in Java, as it is one of the most commonly used tools in this programming language and the one that will be used in the web application to be developed later. Therefore, other libraries have not been covered yet (TBD).


## 🎧 QueuePlayer Application
