import requests

# API Credentials
with open("Spotify_API/token.txt", "r", encoding="utf-8") as f:
    client_ID = f.readline().rstrip("\n")
    client_secret = f.readline().rstrip("\n")

# Using the requests library, it sends the POST request well formed with the IDs as the parameters
url = 'https://accounts.spotify.com/api/token' # the endpoint
headers = {'Content-Type': 'application/x-www-form-urlencoded'} # header
data = {'grant_type': 'client_credentials',
        'client_id': client_ID,
        'client_secret': client_secret} # data

auth_response = requests.post(url, headers=headers, data=data)

# Checking the value of the response, it shows that the request was succesfull
print('Authorization Request Response: ' + str(auth_response))

# To use the information we have to convert the response to a JSON
access_token = auth_response.json() # it is a dictionary

# With this token (only active for 1 hour) we can obtain information about all items on Spotify
    # To obtain we will use GET request using the requests library and giving the token on each request

# Search Request = functions like the search bar on the Spotify App
url = 'https://api.spotify.com/v1/search' # endpoint
search = 'So Close To What Tate McRae'
url_params = {'q': search,
              'type': 'album',
              'limit': 3} # limit = number of elements of the search
header = {'Authorization': f'Bearer {access_token['access_token']}'}
response = requests.get(url, params=url_params, headers=header)
print('Search Request Response: ' + str(response))

info = response.json()

print('Number of total items: ' + str(info['albums']['total']))
    # total = total number of items to return (NOT THE NUMBER OF ITEMS IT RETURNS)

print('Number of items (return from the request): ' + str(info['albums']['limit']))
    # limit = number of items it returns

# With the album's id we can obtain information about it
id_album = info['albums']['items'][0]['id']
url = f'https://api.spotify.com/v1/albums/{id_album}'

response = requests.get(url, params=url_params, headers=header)
print('Get Album Request Response: ' + str(response))

album = response.json()

# Information on the response
    # tracks = dictionary with all the information of the album tracks

song_id = album['tracks']['items'][0]['id']

# With the song id we can get its information
url = f'https://api.spotify.com/v1/tracks/{song_id}'

response = requests.get(url, params=url_params, headers=header)

print('Get Track Request Response: ' + str(response))

print('Track Information:')
print(response.json())
