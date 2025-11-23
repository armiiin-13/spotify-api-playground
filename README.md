# Spotify_API
General information, tutorials and proyects using the Spotify API to create music-base webpages

API Documentation: https://developer.spotify.com/documentation/web-api <br>
Web Playback SDK: https://developer.spotify.com/documentation/web-playback-sdk <br> <br>

## Spotify for Developers
Spotify for Developers is the Spotify's oficial platform that provides tools, APIs, SDKs, and documentation that allow developers to build applications integrating Spotify’s music streaming features. <br><br>
To access to all features you can log in using your Spotify account, which will let you to use your personal dashboard to create applications using the Spotify API. <br><br>

### Creating an App
To create an app you need to specify the following information:
- App's name
- App's description
- Redirect URI: where users can be redirected after authentication success or failure
- Specify all the Spotify API you are going to use in the project
> If you do not need to put a redirect URI use: http://127.0.0.1:3000

### Request an Access Token
When the app is created, in its information you will see two IDs: _client ID_ and _Client Secret_, which will be used when authenticating credentials (requesting an access token).

To request an access token you have to send a __POST request__ to the token endpoint API with the following structure:
```
curl -X POST "https://accounts.spotify.com/api/token" \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "grant_type=client_credentials&client_id=your-client-id&client_secret=your-client-secret"
```
The response will return an access token valid for _1 hour_.
```
{
  "access_token": "access_token",
  "token_type": "Bearer",
  "expires_in": 3600
}
```