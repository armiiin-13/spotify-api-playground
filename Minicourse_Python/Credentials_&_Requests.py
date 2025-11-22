import requests

# API Credentials
client_ID = 'f2299a8d1b7d40eabd0213156819f1d3'
client_secret = 'f0cd8ed55492490799de26d1d03087fd'

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

print('Search Response Data:')
print(response.json())

# Minicourse 3 --> minute 34:31